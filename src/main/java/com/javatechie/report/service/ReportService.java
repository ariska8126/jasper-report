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
import javax.annotation.Resource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ReportService {

    @Autowired
    private EmployeeRepository repository;

    @Value("classpath:/mii-logo.png")
    Resource image;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public String exportReport(List<Report> attendances, String id, String name, String div,
            //    public byte[] exportReport(List<Report> attendances, String id, String name, String div,
            String periode) throws FileNotFoundException, JRException {

        String path = "D:\\Deploy";
        String reportFormat = "pdf";

        System.out.println("list: " + attendances);

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
//            getReportXlsx(jasperPrint);
//            System.out.println("running xlsx");
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\attendance.pdf");
            System.out.println("report generated in path : " + path);
        }

//        getReportXlsx(jasperPrint);
//        System.out.println("byte: "+getReportXlsx(jasperPrint));
        return "report generated in path : " + path;
    }

    public byte[] getReportXlsx(List<Report> attendances, String id, String name, String div,
            String periode) throws RuntimeException, FileNotFoundException, JRException {

        File file = ResourceUtils.getFile("classpath:employees.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(attendances);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", id);
        parameters.put("username", name);
        parameters.put("division", div);
        parameters.put("periode", periode);
        parameters.put("image", image);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        System.out.println("running xlsx");
        final JRXlsxExporter xlsxExporter = new JRXlsxExporter();
        final byte[] rawBytes;

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
