/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatechie.report.entity;

import java.util.Date;

/**
 *
 * @author herli
 */



public class Report {
    
    private Date attendance_date;
    private String start;
    private String end;
    private String total_hour;
    private String attendance_status_name;
    private String attendance_remark;

    public Date getAttendance_date() {
        return attendance_date;
    }

    public void setAttendance_date(Date attendance_date) {
        this.attendance_date = attendance_date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTotal_hour() {
        return total_hour;
    }

    public void setTotal_hour(String total_hour) {
        this.total_hour = total_hour;
    }

    public String getAttendance_status_name() {
        return attendance_status_name;
    }

    public void setAttendance_status_name(String attendance_status_name) {
        this.attendance_status_name = attendance_status_name;
    }

    public String getAttendance_remark() {
        return attendance_remark;
    }

    public void setAttendance_remark(String attendance_remark) {
        this.attendance_remark = attendance_remark;
    }

    @Override
    public String toString() {
        return "Report{" + "attendance_date=" + attendance_date + ", start=" + start + ", end=" + end + ", total_hour=" + total_hour + ", attendance_status_name=" + attendance_status_name + ", attendance_remark=" + attendance_remark + '}';
    }

    

    

}
