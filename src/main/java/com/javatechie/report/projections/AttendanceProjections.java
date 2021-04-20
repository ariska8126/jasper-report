/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatechie.report.projections;

/**
 *
 * @author herli
 */
public interface AttendanceProjections {
    
    String getDate();
    String getStart();
    String getEnd();
    String getTotal_Hour();
    String getStatus();
    String getRemark();
    
}
