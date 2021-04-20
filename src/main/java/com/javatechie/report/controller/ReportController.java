/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatechie.report.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.report.entity.Attendance;
import com.javatechie.report.entity.Report;
import com.javatechie.report.entity.Users;
import com.javatechie.report.projections.AttendanceProjections;
import com.javatechie.report.repository.AttendanceRepository;
import com.javatechie.report.repository.UsersRepository;
import com.javatechie.report.service.ReportService;
import io.swagger.annotations.Api;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author herli
 */
@RestController
@RequestMapping("/api/transac/report")
@Api(tags = "Report")
public class ReportController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ReportService service;

    @Autowired
    private AttendanceRepository attendanceRepository;

    private static final String URL = "jdbc:mysql://localhost:3306/hadir_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Metrodata.5";

    @GetMapping("/trial/{userId}")
    public HttpEntity<byte[]> getXlsxNew(@PathVariable String userId,
            final HttpServletResponse response) throws RuntimeException,
            FileNotFoundException, JRException, JsonProcessingException {

        System.out.println("get trial running");

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatPeriode = new SimpleDateFormat("MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date lastMonth = cal.getTime();

        Calendar lastDateCal = Calendar.getInstance();
        lastDateCal.add(Calendar.MONTH, -1);

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.add(Calendar.MONTH, -1);

        int lastDateInt = cal.getActualMaximum(Calendar.DATE);
        int firstDateInt = cal.getActualMinimum(Calendar.DATE);

        lastDateCal.set(Calendar.DAY_OF_MONTH, lastDateInt);
        lastDateCal.set(Calendar.HOUR_OF_DAY, 23);
        lastDateCal.set(Calendar.MINUTE, 59);
        lastDateCal.set(Calendar.SECOND, 59);
        lastDateCal.set(Calendar.MILLISECOND, 0);

        firstDateCal.set(Calendar.DAY_OF_MONTH, firstDateInt);
        firstDateCal.set(Calendar.HOUR_OF_DAY, 0);
        firstDateCal.set(Calendar.MINUTE, 0);
        firstDateCal.set(Calendar.SECOND, 0);
        firstDateCal.set(Calendar.MILLISECOND, 1);

        Date lastDate = lastDateCal.getTime();
        Date firstDate = firstDateCal.getTime();

        String startDate = formater.format(firstDate);
        String endDate = formater.format(lastDate);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        List<AttendanceProjections> listAttendance = attendanceRepository
                .findAllAttendance(userId, startDate, endDate);

        System.out.println("get value runnnig");

        for (AttendanceProjections a : listAttendance) {
            System.out.println("date: " + a.getDate());
            System.out.println("start: " + a.getStart());
            System.out.println("end: " + a.getEnd());
            System.out.println("remark: " + a.getRemark());
            System.out.println("status: " + a.getStatus());
            System.out.println("total hour: " + a.getTotal_Hour());

            JSONObject jsonObjectWhile = new JSONObject();
            jsonObjectWhile.put("attendance_date", a.getDate());
            jsonObjectWhile.put("start", a.getStart());
            jsonObjectWhile.put("end", a.getEnd());
            jsonObjectWhile.put("total_hour", a.getTotal_Hour());
            jsonObjectWhile.put("attendance_status_name", a.getStatus());
            jsonObjectWhile.put("attendance_remark", a.getRemark());
            jsonArray.add(jsonObjectWhile);
        }

        String json = jsonArray.toString();
        System.out.println("mapping JSON to LIST");
        final ObjectMapper objectMapper = new ObjectMapper();
        List<Report> reportList = objectMapper.readValue(json, new TypeReference<List<Report>>() {
        });

        System.out.println("userId: " + userId);
        String periode = formatPeriode.format(lastMonth);
        System.out.println("periode: " + periode);
        Users user = usersRepository.findUserById(userId);
        String division = user.getDivisionId().getDivisionName();
        System.out.println("division: " + division);
        String username = user.getUserFullname();
        System.out.println("username: " + username);

//            service.exportReport(reportList, userId, username, division, periode); //enable after test
//            System.out.println("list: " + reportList);
        jsonObject.put("attendanceList", jsonArray);

//            return jsonObject.toJSONString();
        String fileType = "xlsx";
        String checker = "ariska";
        String approver = "ariska";
//            String fileType = "pdf";
        final byte[] data = service.getReportXlsx(reportList, userId, username,
                division, periode, fileType, checker, approver);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=attendanceReport.xlsx");
        header.setContentLength(data.length);

        System.out.println("downloading");

        return new HttpEntity<>(data, header);
    }

    @GetMapping(value = "/xlsx/{userId}", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @ResponseBody
    public HttpEntity<byte[]> getXlsx(@PathVariable String userId,
            final HttpServletResponse response) throws RuntimeException,
            JsonProcessingException, FileNotFoundException, JRException {

        System.out.println("initial download xlsx report");

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatPeriode = new SimpleDateFormat("MMMM yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date lastMonth = cal.getTime();
//        String lM = formater.format(lastMonth);
//        System.out.println("last month: " + lM);

        Calendar lastDateCal = Calendar.getInstance();
        lastDateCal.add(Calendar.MONTH, -1);

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.add(Calendar.MONTH, -1);

        int lastDateInt = cal.getActualMaximum(Calendar.DATE);
        int firstDateInt = cal.getActualMinimum(Calendar.DATE);

        lastDateCal.set(Calendar.DAY_OF_MONTH, lastDateInt);
        lastDateCal.set(Calendar.HOUR_OF_DAY, 23);
        lastDateCal.set(Calendar.MINUTE, 59);
        lastDateCal.set(Calendar.SECOND, 59);
        lastDateCal.set(Calendar.MILLISECOND, 0);

        firstDateCal.set(Calendar.DAY_OF_MONTH, firstDateInt);
        firstDateCal.set(Calendar.HOUR_OF_DAY, 0);
        firstDateCal.set(Calendar.MINUTE, 0);
        firstDateCal.set(Calendar.SECOND, 0);
        firstDateCal.set(Calendar.MILLISECOND, 1);

        Date lastDate = lastDateCal.getTime();
        Date firstDate = firstDateCal.getTime();

        String startDate = formater.format(firstDate);
        String endDate = formater.format(lastDate);

        System.out.println("startdate: " + startDate);
        System.out.println("enddate: " + endDate);

        String query = "SELECT s.attendance_date AS date, time_format(s.attendance_time, \"%H:%i\") as start, "
                + "time_format( e.attendance_time , \"%H:%i\") as end, "
                + "time_format(e.attendance_time-s.attendance_time, \"%H:%i\") as total_hour, "
                + "s.attendance_status_name as status, e.attendance_remark as remark FROM (SELECT a.attendance_date, "
                + "a.attendance_time, a.attendance_remark, at.attendance_status_name FROM attendance a "
                + "JOIN attendance_status at ON a.attendance_status_id = at.attendance_status_id "
                + "WHERE attendance_type = 'start' AND a.user_id= '" + userId + "' AND a.attendance_date BETWEEN '" + startDate + "' AND '" + endDate + "') s "
                + "JOIN (SELECT attendance_date, attendance_time, attendance_remark FROM attendance "
                + "WHERE attendance_type = 'end' AND user_id='" + userId + "' AND attendance_date BETWEEN '" + startDate + "' AND '" + endDate + "') e "
                + "ON s.attendance_date = e.attendance_date";

//        System.out.println("query: " + query);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = conn.createStatement();
            ResultSet resultset = statement.executeQuery(query);

            while (resultset.next()) {
                JSONObject jsonObjectWhile = new JSONObject();

                jsonObjectWhile.put("attendance_date", resultset.getString("date"));
                jsonObjectWhile.put("start", resultset.getString("start"));
                jsonObjectWhile.put("end", resultset.getString("end"));
                jsonObjectWhile.put("total_hour", resultset.getString("total_hour"));
                jsonObjectWhile.put("attendance_status_name", resultset.getString("status"));
                jsonObjectWhile.put("attendance_remark", resultset.getString("remark"));
                jsonArray.add(jsonObjectWhile);
            }

            String json = jsonArray.toString();
            System.out.println("mapping JSON to LIST");
            final ObjectMapper objectMapper = new ObjectMapper();
            List<Report> reportList = objectMapper.readValue(json, new TypeReference<List<Report>>() {
            });

            System.out.println("userId: " + userId);
            String periode = formatPeriode.format(lastMonth);
            System.out.println("periode: " + periode);
            Users user = usersRepository.findUserById(userId);
            String division = user.getDivisionId().getDivisionName();
            System.out.println("division: " + division);
            String username = user.getUserFullname();
            System.out.println("username: " + username);

            jsonObject.put("attendanceList", jsonArray);

            String fileType = "xlsx";
            String checker = "ariska";
            String approver = "ariska";
            final byte[] data = service.getReportXlsx(reportList, userId, username,
                    division, periode, fileType, checker, approver);

            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=attendanceReport.xlsx");
            header.setContentLength(data.length);

            System.out.println("downloading");

            return new HttpEntity<>(data, header);
        } catch (SQLException e) {
        }
        return null;
    }

    @GetMapping("/getAttendance/{userId}")
    public String getAttendanceByUserId(@PathVariable String userId) {

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date lastMonth = cal.getTime();
        String lM = formater.format(lastMonth);
        System.out.println("last month: " + lM);

        Calendar lastDateCal = Calendar.getInstance();
        lastDateCal.add(Calendar.MONTH, -1);

        Calendar firstDateCal = Calendar.getInstance();
        firstDateCal.add(Calendar.MONTH, -1);

        int lastDateInt = cal.getActualMaximum(Calendar.DATE);
        int firstDateInt = cal.getActualMinimum(Calendar.DATE);

        System.out.println("first date: " + firstDateInt);
        System.out.println("last date: " + lastDateInt);

        lastDateCal.set(Calendar.DAY_OF_MONTH, lastDateInt);
        lastDateCal.set(Calendar.HOUR_OF_DAY, 23);
        lastDateCal.set(Calendar.MINUTE, 59);
        lastDateCal.set(Calendar.SECOND, 59);
        lastDateCal.set(Calendar.MILLISECOND, 0);

        firstDateCal.set(Calendar.DAY_OF_MONTH, firstDateInt);
        firstDateCal.set(Calendar.HOUR_OF_DAY, 0);
        firstDateCal.set(Calendar.MINUTE, 0);
        firstDateCal.set(Calendar.SECOND, 0);
        firstDateCal.set(Calendar.MILLISECOND, 1);

        Date lastDate = lastDateCal.getTime();
        Date firstDate = firstDateCal.getTime();

        System.out.println("fisrt date: " + firstDate);
        System.out.println("last date: " + lastDate);

        String startDate = formater.format(firstDate);
        String endDate = formater.format(lastDate);

        System.out.println("startdate: " + startDate);
        System.out.println("enddate: " + endDate);
        List<Attendance> attendances = attendanceRepository.findLastMonthAttendanceByUserId(userId, startDate, endDate);
        System.out.println("attendance" + attendances);

        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();

        for (Attendance att : attendances) {
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("date", att.getAttendanceDate().toString());
            jsonobject.put("time", att.getAttendanceTime().toString());
            jsonobject.put("working", att.getAttendanceType());
            jsonobject.put("status", att.getAttendanceStatusId().getAttendanceStatusName());
            jsonobject.put("remark", att.getAttendanceRemark());
            jSONArray.add(jsonobject);
        }

        jSONObject2.put("attendance", jSONArray);
        return jSONObject2.toJSONString();
    }

}
