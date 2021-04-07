///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.javatechie.report.config;
//
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// *
// * @author herli
// */
//@Controller
//public class JasperController {
//    
//    @Autowired
//    private SimpleReportExporter simpleReportExporter;
//    
//    @Autowired
//    private SimpleReportFilter simpleReportFilter;
//    
//    @RequestMapping(value="/getreport", method = RequestMethod.GET)
//    public void getReport(){
//        String userId = "20210206";
////    @RequestMapping(value="/getreport/{userId}", method = RequestMethod.GET)
////    public void getReport(@PathVariable String userId){
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//        ctx.register(JasperReportSimpleConfig.class);
//        ctx.refresh();
//        
//        simpleReportFilter = ctx.getBean(SimpleReportFilter.class);
//        simpleReportFilter.setReportFileName("employeeReport.jrxml");
//        simpleReportFilter.compileReport();
//        
//        
//        
//        String startDate = "2021-03-01";
//        String endDate = "2021-03-01";
//        
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("userId", userId);
//        parameters.put("startDate", startDate);
//        parameters.put("endDate", endDate);
//        
//        simpleReportFilter.setParameters(parameters);
//        simpleReportFilter.fillReport();
//        
//        simpleReportExporter = ctx.getBean(SimpleReportExporter.class);
//        simpleReportExporter.setJasperPrint(simpleReportFilter.getJasperPrint());
//        
//        simpleReportExporter.exportToXlsx("employee.xlsx", "Employee Attendance");
//        
//    }
//    
//    
//}
