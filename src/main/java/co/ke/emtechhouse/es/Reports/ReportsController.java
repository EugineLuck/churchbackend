package co.ke.emtechhouse.es.Reports;

import co.ke.emtechhouse.es.Auth.Members.MemberDetails;
import co.ke.emtechhouse.es.Auth.Members.MembersRepository;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.repo.InputStreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/v1/reports")
public class ReportsController {

    @Value("${spring.others.filePath}")
    private String files_path;
    @Value("${spring.others.logo}")
    private String logo;
    @Value("${spring.datasource.url}")
    private String db;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    @GetMapping("/outstationmembers/{id}")
    public ResponseEntity<ByteArrayResource> outstationMembersReports(@PathVariable Long id) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/outstationMembers.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("churchId", id);
        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=outstation_members.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }


    @GetMapping("/outstationgroups/{id}")
    public ResponseEntity<ByteArrayResource> outstationGroupsReports(@PathVariable Long id) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/outstationGroups.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("id", id);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=outstation_group_"+id+".pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }

    @GetMapping("/outstationCommunities/{id}")
    public ResponseEntity<ByteArrayResource> outstationCommunitiesReports(@PathVariable Long id) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/outstationCommunities.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("id", id);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=outstation_communities_"+id+".pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }


    @GetMapping("/members")
    public ResponseEntity<ByteArrayResource> membersReports() throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/allMembers.jrxml"));
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=all_members.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }



    @GetMapping("/communitymembers/{id}")
    public ResponseEntity<ByteArrayResource> communityMembersReports(@PathVariable Long id) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/communityMembers.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("communityId", id);
        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=community_members.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }

    @GetMapping("/familymembers/{id}")
    public ResponseEntity<ByteArrayResource> familyMembersReports(@PathVariable Long id) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/familyMembers.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("familyId", id);
        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=family_members.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }

    @GetMapping("/groupmembers/{id}")
    public ResponseEntity<ByteArrayResource> groupMembersReports(@PathVariable Long id) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/groupMembers.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("groupId", id);
        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=group_members.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }

    @GetMapping("/transactions")
    public ResponseEntity<ByteArrayResource> transactionsReports() throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path+"/transactionsAll.jrxml"));
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }


    @GetMapping("/statement/{memberNumber}")
    public ResponseEntity<ByteArrayResource> memberStatementReports(@PathVariable String memberNumber) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/membersStatement.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("memberNumber", memberNumber);
        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+memberNumber+"_statement.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }

    @GetMapping("/transactions/{givingId}")
    public ResponseEntity<ByteArrayResource> categoryReports(@PathVariable Long givingId) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/transactionsType.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("givingId", givingId);
        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=giving_"+givingId+"_statement.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }

    @GetMapping("/churchfamily/{churchId}")
    public ResponseEntity<ByteArrayResource> churchfamily(@PathVariable Long churchId) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/outSationFamilies.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("churchId", churchId);
        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=outstation_"+churchId+"_statement.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }

    @GetMapping("/communityfamily/{communityId}")
    public ResponseEntity<ByteArrayResource> communityfamily(@PathVariable Long communityId) throws FileNotFoundException, JRException, SQLException {
        Connection connection = DriverManager.getConnection(this.db, this.username, this.password);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream(files_path + "/communityFamilies.jrxml"));
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("churchId", communityId);
        parameter.put("logo", logo);
        JasperPrint report = JasperFillManager.fillReport(compileReport, parameter, connection);
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=community_"+communityId+"_statement.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().headers(headers).body(byteArrayResource);
    }







}

