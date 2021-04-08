package com.javatechie.report.service;

import com.javatechie.report.entity.Attendance;
import com.javatechie.report.entity.Employee;
import com.javatechie.report.entity.Report;
import com.javatechie.report.repository.AttendanceRepository;
import com.javatechie.report.repository.EmployeeRepository;
import java.io.ByteArrayOutputStream;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class ReportService {

    @Autowired
    private EmployeeRepository repository;
    
    @Autowired
    private AttendanceRepository attendanceRepository;


    public String exportReport(List<Report> attendances, String id, String name, String div,
    String periode) throws FileNotFoundException, JRException {

        String path = "D:\\Deploy";
        String reportFormat = "pdf";

        System.out.println("list: "+attendances);

        File file = ResourceUtils.getFile("classpath:employees.jrxml");
        
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(attendances);
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", id);
        parameters.put("username", name);
        parameters.put("division", div);
        parameters.put("periode", periode);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\attendance.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\attendance.pdf");
        }
        
        getReportXlsx(jasperPrint);
        System.out.println("byte: "+getReportXlsx(jasperPrint));
        return "report generated in path : " + path;
    }
    
    public byte[] getReportXlsx(final JasperPrint jasperPrint) throws RuntimeException {
        final JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        final byte[] rawBytes;
        System.out.println("running export xlsx");

        try (final ByteArrayOutputStream xlsReport = new ByteArrayOutputStream()) {
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
            xlsxExporter.exportReport();

            rawBytes = xlsReport.toByteArray();
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }

        return rawBytes;
    }
}
