/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatechie.report.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author creative
 */
@Entity
@Table(name = "approval")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Approval.findAll", query = "SELECT a FROM Approval a"),
    @NamedQuery(name = "Approval.findByApprovalId", query = "SELECT a FROM Approval a WHERE a.approvalId = :approvalId"),
    @NamedQuery(name = "Approval.findByApprovalDate", query = "SELECT a FROM Approval a WHERE a.approvalDate = :approvalDate"),
    @NamedQuery(name = "Approval.findByApprovalDateUpdate", query = "SELECT a FROM Approval a WHERE a.approvalDateUpdate = :approvalDateUpdate"),
    @NamedQuery(name = "Approval.findByApprovalDateStart", query = "SELECT a FROM Approval a WHERE a.approvalDateStart = :approvalDateStart"),
    @NamedQuery(name = "Approval.findByApprovalDateEnd", query = "SELECT a FROM Approval a WHERE a.approvalDateEnd = :approvalDateEnd")})
public class Approval implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "approval_id")
    private String approvalId;
    @Basic(optional = false)
    @Column(name = "approval_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
    @Basic(optional = false)
    @Column(name = "approval_date_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDateUpdate;
    @Basic(optional = false)
    @Lob
    @Column(name = "approval_remark")
    private String approvalRemark;
    @Basic(optional = false)
    @Column(name = "approval_date_start")
    @Temporal(TemporalType.DATE)
    private Date approvalDateStart;
    @Basic(optional = false)
    @Column(name = "approval_date_end")
    @Temporal(TemporalType.DATE)
    private Date approvalDateEnd;
    @JoinColumn(name = "request_id", referencedColumnName = "request_id")
    @ManyToOne(optional = false)
    private Request requestId;
    @JoinColumn(name = "approval_status_id", referencedColumnName = "approval_status_id")
    @ManyToOne(optional = false)
    private ApprovalStatus approvalStatusId;
    @JoinColumn(name = "approval_requester_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users approvalRequesterId;
    @JoinColumn(name = "approval_approver_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users approvalApproverId;

    public Approval() {
    }

    public Approval(String approvalId) {
        this.approvalId = approvalId;
    }

    public Approval(String approvalId, Date approvalDate, Date approvalDateUpdate, 
            String approvalRemark, Date approvalDateStart, Date approvalDateEnd, 
            Request requestId, ApprovalStatus approvalStatusId, Users approvalRequesterId, 
            Users approvalApproverId) {
        this.approvalId = approvalId;
        this.approvalDate = approvalDate;
        this.approvalDateUpdate = approvalDateUpdate;
        this.approvalRemark = approvalRemark;
        this.approvalDateStart = approvalDateStart;
        this.approvalDateEnd = approvalDateEnd;
        this.requestId = requestId;
        this.approvalStatusId = approvalStatusId;
        this.approvalRequesterId = approvalRequesterId;
        this.approvalApproverId = approvalApproverId;
    }
    
    public Approval(String approvalId, Date approvalDate, Date approvalDateUpdate, String approvalRemark, Date approvalDateStart, Date approvalDateEnd) {
        this.approvalId = approvalId;
        this.approvalDate = approvalDate;
        this.approvalDateUpdate = approvalDateUpdate;
        this.approvalRemark = approvalRemark;
        this.approvalDateStart = approvalDateStart;
        this.approvalDateEnd = approvalDateEnd;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Date getApprovalDateUpdate() {
        return approvalDateUpdate;
    }

    public void setApprovalDateUpdate(Date approvalDateUpdate) {
        this.approvalDateUpdate = approvalDateUpdate;
    }

    public String getApprovalRemark() {
        return approvalRemark;
    }

    public void setApprovalRemark(String approvalRemark) {
        this.approvalRemark = approvalRemark;
    }

    public Date getApprovalDateStart() {
        return approvalDateStart;
    }

    public void setApprovalDateStart(Date approvalDateStart) {
        this.approvalDateStart = approvalDateStart;
    }

    public Date getApprovalDateEnd() {
        return approvalDateEnd;
    }

    public void setApprovalDateEnd(Date approvalDateEnd) {
        this.approvalDateEnd = approvalDateEnd;
    }

    public Request getRequestId() {
        return requestId;
    }

    public void setRequestId(Request requestId) {
        this.requestId = requestId;
    }

    public ApprovalStatus getApprovalStatusId() {
        return approvalStatusId;
    }

    public void setApprovalStatusId(ApprovalStatus approvalStatusId) {
        this.approvalStatusId = approvalStatusId;
    }

    public Users getApprovalRequesterId() {
        return approvalRequesterId;
    }

    public void setApprovalRequesterId(Users approvalRequesterId) {
        this.approvalRequesterId = approvalRequesterId;
    }

    public Users getApprovalApproverId() {
        return approvalApproverId;
    }

    public void setApprovalApproverId(Users approvalApproverId) {
        this.approvalApproverId = approvalApproverId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (approvalId != null ? approvalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Approval)) {
            return false;
        }
        Approval other = (Approval) object;
        if ((this.approvalId == null && other.approvalId != null) || (this.approvalId != null && !this.approvalId.equals(other.approvalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hadirapp.HadirApproval.entity.Approval[ approvalId=" + approvalId + " ]";
    }
    
}
