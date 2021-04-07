/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatechie.report.repository;

import com.javatechie.report.entity.Attendance;
import com.javatechie.report.entity.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author herli
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String>{
    
    @Query(value="SELECT * FROM `attendance` WHERE attendance_date BETWEEN ?2 AND ?3 AND user_id = ?1 ORDER BY attendance_date DESC, attendance_time ASC",nativeQuery = true)
    List<Attendance> findLastMonthAttendanceByUserId(@Param ("userId") String userId, @Param ("startDate") String startDate, @Param ("endDate") String endDate);
    
    @Query(value = "SELECT s.attendance_date AS date, time_format(s.attendance_time, \"%H:%i\") as start, time_format( e.attendance_time , \"%H:%i\") as end, time_format(e.attendance_time-s.attendance_time, \"%H:%i\") as total_hour, s.attendance_status_name as status, e.attendance_remark as remark FROM (SELECT a.attendance_date, a.attendance_time, a.attendance_remark, at.attendance_status_name FROM attendance a JOIN attendance_status at ON a.attendance_status_id = at.attendance_status_id WHERE attendance_type = 'start' AND a.user_id= ?1 AND a.attendance_date BETWEEN ?2 AND ?3) s JOIN (SELECT attendance_date, attendance_time, attendance_remark FROM attendance WHERE attendance_type = 'end' AND user_id=?1 AND attendance_date BETWEEN ?2 AND ?3) e ON s.attendance_date = e.attendance_date", nativeQuery = true)
    public List<Report> getReport(@Param ("id") String id, @Param ("start") String start,@Param ("end") String end);
    
}
