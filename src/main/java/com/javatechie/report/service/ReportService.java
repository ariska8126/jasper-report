package com.javatechie.report.service;

import com.javatechie.report.entity.Attendance;
import com.javatechie.report.entity.Employee;
import com.javatechie.report.repository.AttendanceRepository;
import com.javatechie.report.repository.EmployeeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private EmployeeRepository repository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;


    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "D:\\Deploy";
//        List<Employee> employees = repository.findAll();
        List<Attendance> attendances = attendanceRepository.findAll();
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:employees.jrxml");
        
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(attendances);
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\attendance.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\attendance.pdf");
        }

        return "report generated in path : " + path;
    }
}
