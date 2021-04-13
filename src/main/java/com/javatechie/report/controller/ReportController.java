/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatechie.report.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.report.entity.Report;
import com.javatechie.report.entity.Users;
import com.javatechie.report.repository.UsersRepository;
import com.javatechie.report.service.ReportService;
import io.swagger.annotations.Api;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
@Api(tags="Report")
public class ReportController {
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private ReportService service;
    
    private static final String URL = "jdbc:mysql://localhost:3306/hadir_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Metrodata.5";
    
    @GetMapping(value = "/getAttendancenew/{userId}", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @ResponseBody
    public HttpEntity<byte[]> getXlsx(@PathVariable String userId,
            final HttpServletResponse response) throws RuntimeException,
            JsonProcessingException, FileNotFoundException, JRException {

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatPeriode = new SimpleDateFormat("MMMM yyyy");
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
//        String startDate = "2021-04-01";
//        String endDate = "2021-04-30";

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

        System.out.println("query: " + query);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = conn.createStatement();
            ResultSet resultset = statement.executeQuery(query);

            ResultSetMetaData metadata = resultset.getMetaData();
            int columnCount = metadata.getColumnCount();

            System.out.println("Column count : " + columnCount);
            System.out.println("Column Name : " + metadata.getColumnName(1));
            System.out.println("Column Name : " + metadata.getColumnName(2));
            System.out.println("Column Name : " + metadata.getColumnName(3));
            System.out.println("Column Name : " + metadata.getColumnName(4));
            System.out.println("Column Name : " + metadata.getColumnName(5));
            System.out.println("Column Name : " + metadata.getColumnName(6));

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
            System.out.println("json: " + json);

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
            System.out.println("list: " + reportList);

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

            return new HttpEntity<>(data, header);
//            return new HttpEntity<byte[]>(data, header);
        } catch (SQLException e) {
        }
        
        return null;

    }
    
    
}
