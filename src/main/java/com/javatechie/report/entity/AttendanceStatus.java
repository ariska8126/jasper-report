/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatechie.report.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author creative
 */
@Entity
@Table(name = "attendance_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttendanceStatus.findAll", query = "SELECT a FROM AttendanceStatus a"),
    @NamedQuery(name = "AttendanceStatus.findByAttendanceStatusId", query = "SELECT a FROM AttendanceStatus a WHERE a.attendanceStatusId = :attendanceStatusId"),
    @NamedQuery(name = "AttendanceStatus.findByAttendanceStatusActive", query = "SELECT a FROM AttendanceStatus a WHERE a.attendanceStatusActive = :attendanceStatusActive")})
public class AttendanceStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "attendance_status_id")
    private Integer attendanceStatusId;
    @Basic(optional = false)
    @Lob
    @Column(name = "attendance_status_name")
    private String attendanceStatusName;
    @Basic(optional = false)
    @Column(name = "attendance_status_active")
    private String attendanceStatusActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attendanceStatusId")
    private List<Attendance> attendanceList;

    public AttendanceStatus() {
    }

    public AttendanceStatus(Integer attendanceStatusId) {
        this.attendanceStatusId = attendanceStatusId;
    }

    public AttendanceStatus(Integer attendanceStatusId, String attendanceStatusName, String attendanceStatusActive) {
        this.attendanceStatusId = attendanceStatusId;
        this.attendanceStatusName = attendanceStatusName;
        this.attendanceStatusActive = attendanceStatusActive;
    }

    public Integer getAttendanceStatusId() {
        return attendanceStatusId;
    }

    public void setAttendanceStatusId(Integer attendanceStatusId) {
        this.attendanceStatusId = attendanceStatusId;
    }

    public String getAttendanceStatusName() {
        return attendanceStatusName;
    }

    public void setAttendanceStatusName(String attendanceStatusName) {
        this.attendanceStatusName = attendanceStatusName;
    }

    public String getAttendanceStatusActive() {
        return attendanceStatusActive;
    }

    public void setAttendanceStatusActive(String attendanceStatusActive) {
        this.attendanceStatusActive = attendanceStatusActive;
    }

    @XmlTransient
    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attendanceStatusId != null ? attendanceStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttendanceStatus)) {
            return false;
        }
        AttendanceStatus other = (AttendanceStatus) object;
        if ((this.attendanceStatusId == null && other.attendanceStatusId != null) || (this.attendanceStatusId != null && !this.attendanceStatusId.equals(other.attendanceStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hadirapp.HadirApproval.entity.AttendanceStatus[ attendanceStatusId=" + attendanceStatusId + " ]";
    }
    
}
