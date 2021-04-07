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
@Table(name = "approval_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ApprovalStatus.findAll", query = "SELECT a FROM ApprovalStatus a"),
    @NamedQuery(name = "ApprovalStatus.findByApprovalStatusId", query = "SELECT a FROM ApprovalStatus a WHERE a.approvalStatusId = :approvalStatusId"),
    @NamedQuery(name = "ApprovalStatus.findByApprovalStatusName", query = "SELECT a FROM ApprovalStatus a WHERE a.approvalStatusName = :approvalStatusName"),
    @NamedQuery(name = "ApprovalStatus.findByApprovalStatusActive", query = "SELECT a FROM ApprovalStatus a WHERE a.approvalStatusActive = :approvalStatusActive")})
public class ApprovalStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "approval_status_id")
    private Integer approvalStatusId;
    @Basic(optional = false)
    @Column(name = "approval_status_name")
    private String approvalStatusName;
    @Basic(optional = false)
    @Column(name = "approval_status_active")
    private String approvalStatusActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approvalStatusId")
    private List<Approval> approvalList;

    public ApprovalStatus() {
    }

    public ApprovalStatus(Integer approvalStatusId) {
        this.approvalStatusId = approvalStatusId;
    }

    public ApprovalStatus(Integer approvalStatusId, String approvalStatusName, String approvalStatusActive) {
        this.approvalStatusId = approvalStatusId;
        this.approvalStatusName = approvalStatusName;
        this.approvalStatusActive = approvalStatusActive;
    }

    public Integer getApprovalStatusId() {
        return approvalStatusId;
    }

    public void setApprovalStatusId(Integer approvalStatusId) {
        this.approvalStatusId = approvalStatusId;
    }

    public String getApprovalStatusName() {
        return approvalStatusName;
    }

    public void setApprovalStatusName(String approvalStatusName) {
        this.approvalStatusName = approvalStatusName;
    }

    public String getApprovalStatusActive() {
        return approvalStatusActive;
    }

    public void setApprovalStatusActive(String approvalStatusActive) {
        this.approvalStatusActive = approvalStatusActive;
    }

    @XmlTransient
    public List<Approval> getApprovalList() {
        return approvalList;
    }

    public void setApprovalList(List<Approval> approvalList) {
        this.approvalList = approvalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (approvalStatusId != null ? approvalStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApprovalStatus)) {
            return false;
        }
        ApprovalStatus other = (ApprovalStatus) object;
        if ((this.approvalStatusId == null && other.approvalStatusId != null) || (this.approvalStatusId != null && !this.approvalStatusId.equals(other.approvalStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hadirapp.HadirApproval.entity.ApprovalStatus[ approvalStatusId=" + approvalStatusId + " ]";
    }
    
}
