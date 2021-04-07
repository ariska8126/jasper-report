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
    
    private Date date;
    private Date start;
    private Date end;
    private Date totalHour;
    private String status;
    private String remark;

    public Report() {
    }


    public Report(Date date, Date start, Date end, Date totalHour, String status, String remark) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.totalHour = totalHour;
        this.status = status;
        this.remark = remark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(Date totalHour) {
        this.totalHour = totalHour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
    
}
