package com.javatechie.report;

import com.javatechie.report.entity.Attendance;
import com.javatechie.report.entity.Employee;
import com.javatechie.report.repository.AttendanceRepository;
import com.javatechie.report.repository.EmployeeRepository;
import com.javatechie.report.service.ReportService;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@SpringBootApplication
@RestController
public class SpringBootJasperReportApplication {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private ReportService service;

    @Autowired
    private AttendanceRepository attendanceRepository;

    private static final String URL = "jdbc:mysql://localhost:3306/hadir_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Metrodata.5";

    @GetMapping("/getAttendancenew/{userId}")
//    public List<Attendance> getAttendanceByUserId(@PathVariable String userId){
    public String getAttendance(@PathVariable String userId) {

//        List<Attendance> attendances = attendanceRepository.findAttendanceByUserId(userId);
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
//        List<Report> attendances = attendanceRepository.getReport(userId, startDate, endDate);
//        System.out.println("attendance"+attendances);
//        
//        JSONArray jSONArray = new JSONArray();
//        JSONObject jSONObject2 = new JSONObject();
//
//        for(Report att : attendances){
//            JSONObject jsonobject = new JSONObject();
//            jsonobject.put("date", att.getDate().toString());
//            jsonobject.put("start", att.getStart().toString());
//            
//            jsonobject.put("end", att.getEnd().toString());
//            jsonobject.put("totalHour", att.getTotalHour().toString());
//            jsonobject.put("status", att.getStatus());
//            jsonobject.put("remark", att.getRemark());
//            jSONArray.add(jsonobject);
//        }
//        jSONObject2.put("attendance", jSONArray);
//        return jSONObject2.toJSONString();

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

        System.out.println("query: "+query);
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

//                String weekNo = resultset.getString("weekNo");
//                String leaveNumber = resultset.getString("leaveNumber");

//                System.out.println(weekNo);
//                System.out.println(leaveNumber);

                jsonObjectWhile.put("attendance_date", resultset.getString("date"));
                jsonObjectWhile.put("start", resultset.getString("start"));
                jsonObjectWhile.put("end", resultset.getString("end"));
                jsonObjectWhile.put("total_hour", resultset.getString("total_hour"));
                jsonObjectWhile.put("attendance_status_name", resultset.getString("status"));
                jsonObjectWhile.put("attendance_remark", resultset.getString("remark"));
                jsonArray.add(jsonObjectWhile);
            }

            jsonObject.put("attendanceList", jsonArray);

            return jsonObject.toJSONString();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "test";
    }

    @GetMapping("/getAttendance/{userId}")
//    public List<Attendance> getAttendanceByUserId(@PathVariable String userId){
    public String getAttendanceByUserId(@PathVariable String userId) {

//        List<Attendance> attendances = attendanceRepository.findAttendanceByUserId(userId);
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

    @GetMapping("/getEmployees")
    public List<Employee> getEmployees() {

        return repository.findAll();
    }

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return service.exportReport(format);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJasperReportApplication.class, args);
    }

}
