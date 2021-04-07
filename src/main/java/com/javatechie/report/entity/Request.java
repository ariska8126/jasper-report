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
@Table(name = "request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Request.findAll", query = "SELECT r FROM Request r"),
    @NamedQuery(name = "Request.findByRequestId", query = "SELECT r FROM Request r WHERE r.requestId = :requestId"),
    @NamedQuery(name = "Request.findByRequestDate", query = "SELECT r FROM Request r WHERE r.requestDate = :requestDate"),
    @NamedQuery(name = "Request.findByRequestDateStart", query = "SELECT r FROM Request r WHERE r.requestDateStart = :requestDateStart"),
    @NamedQuery(name = "Request.findByRequestDateEnd", query = "SELECT r FROM Request r WHERE r.requestDateEnd = :requestDateEnd")})
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "request_id")
    private String requestId;
    @Basic(optional = false)
    @Column(name = "request_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;
    @Basic(optional = false)
    @Column(name = "request_date_start")
    @Temporal(TemporalType.DATE)
    private Date requestDateStart;
    @Basic(optional = false)
    @Column(name = "request_date_end")
    @Temporal(TemporalType.DATE)
    private Date requestDateEnd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestId")
    private List<Approval> approvalList;

    public Request() {
    }

    public Request(String requestId) {
        this.requestId = requestId;
    }

    public Request(String requestId, Date requestDate, Date requestDateStart, Date requestDateEnd) {
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.requestDateStart = requestDateStart;
        this.requestDateEnd = requestDateEnd;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getRequestDateStart() {
        return requestDateStart;
    }

    public void setRequestDateStart(Date requestDateStart) {
        this.requestDateStart = requestDateStart;
    }

    public Date getRequestDateEnd() {
        return requestDateEnd;
    }

    public void setRequestDateEnd(Date requestDateEnd) {
        this.requestDateEnd = requestDateEnd;
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
        hash += (requestId != null ? requestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Request)) {
            return false;
        }
        Request other = (Request) object;
        if ((this.requestId == null && other.requestId != null) || (this.requestId != null && !this.requestId.equals(other.requestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hadirapp.HadirApproval.entity.Request[ requestId=" + requestId + " ]";
    }
    
}
