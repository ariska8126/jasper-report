/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatechie.report.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author creative
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByUserFullname", query = "SELECT u FROM Users u WHERE u.userFullname = :userFullname"),
    @NamedQuery(name = "Users.findByUserEmail", query = "SELECT u FROM Users u WHERE u.userEmail = :userEmail"),
    @NamedQuery(name = "Users.findByUserPassword", query = "SELECT u FROM Users u WHERE u.userPassword = :userPassword"),
    @NamedQuery(name = "Users.findByUserActive", query = "SELECT u FROM Users u WHERE u.userActive = :userActive"),
    @NamedQuery(name = "Users.findByUserUnixcodeValue", query = "SELECT u FROM Users u WHERE u.userUnixcodeValue = :userUnixcodeValue"),
    @NamedQuery(name = "Users.findByUserUnixcodeDate", query = "SELECT u FROM Users u WHERE u.userUnixcodeDate = :userUnixcodeDate")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;
    @Basic(optional = false)
    @Column(name = "user_fullname")
    private String userFullname;
    @Basic(optional = false)
    @Column(name = "user_email")
    private String userEmail;
    @Basic(optional = false)
    @Column(name = "user_password")
    private String userPassword;
    @Basic(optional = false)
    @Column(name = "user_active")
    private String userActive;
    @Basic(optional = false)
    @Column(name = "user_unixcode_value")
    private String userUnixcodeValue;
    @Basic(optional = false)
    @Column(name = "user_unixcode_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userUnixcodeDate;
    @Basic(optional = false)
    @Lob
    @Column(name = "user_photo")
    private String userPhoto;
    @Lob
    @Column(name = "user_token")
    private String userToken;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<BootcampDetail> bootcampDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approvalRequesterId")
    private List<Approval> approvalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approvalApproverId")
    private List<Approval> approvalList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Attendance> attendanceList;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Role roleId;
    @JoinColumn(name = "division_id", referencedColumnName = "division_id")
    @ManyToOne(optional = false)
    private Division divisionId;

    public Users() {
    }

    public Users(String userId) {
        this.userId = userId;
    }

    public Users(String userId, String userFullname, String userEmail, String userPassword, String userActive, String userUnixcodeValue, Date userUnixcodeDate, String userPhoto) {
        this.userId = userId;
        this.userFullname = userFullname;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userActive = userActive;
        this.userUnixcodeValue = userUnixcodeValue;
        this.userUnixcodeDate = userUnixcodeDate;
        this.userPhoto = userPhoto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserActive() {
        return userActive;
    }

    public void setUserActive(String userActive) {
        this.userActive = userActive;
    }

    public String getUserUnixcodeValue() {
        return userUnixcodeValue;
    }

    public void setUserUnixcodeValue(String userUnixcodeValue) {
        this.userUnixcodeValue = userUnixcodeValue;
    }

    public Date getUserUnixcodeDate() {
        return userUnixcodeDate;
    }

    public void setUserUnixcodeDate(Date userUnixcodeDate) {
        this.userUnixcodeDate = userUnixcodeDate;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @XmlTransient
    public List<BootcampDetail> getBootcampDetailList() {
        return bootcampDetailList;
    }

    public void setBootcampDetailList(List<BootcampDetail> bootcampDetailList) {
        this.bootcampDetailList = bootcampDetailList;
    }

    @XmlTransient
    public List<Approval> getApprovalList() {
        return approvalList;
    }

    public void setApprovalList(List<Approval> approvalList) {
        this.approvalList = approvalList;
    }

    @XmlTransient
    public List<Approval> getApprovalList1() {
        return approvalList1;
    }

    public void setApprovalList1(List<Approval> approvalList1) {
        this.approvalList1 = approvalList1;
    }

    @XmlTransient
    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public Division getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Division divisionId) {
        this.divisionId = divisionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hadirapp.HadirApproval.entity.Users[ userId=" + userId + " ]";
    }
    
}
