/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javatechie.report.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author creative
 */
@Entity
@Table(name = "bootcamp_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BootcampDetail.findAll", query = "SELECT b FROM BootcampDetail b"),
    @NamedQuery(name = "BootcampDetail.findByBootcampDetailId", query = "SELECT b FROM BootcampDetail b WHERE b.bootcampDetailId = :bootcampDetailId")})
public class BootcampDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "bootcamp_detail_id")
    private String bootcampDetailId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users userId;
    @JoinColumn(name = "bootcamp_id", referencedColumnName = "bootcamp_id")
    @ManyToOne(optional = false)
    private Bootcamp bootcampId;

    public BootcampDetail() {
    }

    public BootcampDetail(String bootcampDetailId) {
        this.bootcampDetailId = bootcampDetailId;
    }

    public String getBootcampDetailId() {
        return bootcampDetailId;
    }

    public void setBootcampDetailId(String bootcampDetailId) {
        this.bootcampDetailId = bootcampDetailId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Bootcamp getBootcampId() {
        return bootcampId;
    }

    public void setBootcampId(Bootcamp bootcampId) {
        this.bootcampId = bootcampId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bootcampDetailId != null ? bootcampDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BootcampDetail)) {
            return false;
        }
        BootcampDetail other = (BootcampDetail) object;
        if ((this.bootcampDetailId == null && other.bootcampDetailId != null) || (this.bootcampDetailId != null && !this.bootcampDetailId.equals(other.bootcampDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hadirapp.HadirApproval.entity.BootcampDetail[ bootcampDetailId=" + bootcampDetailId + " ]";
    }
    
}
