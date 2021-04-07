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
@Table(name = "bootcamp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bootcamp.findAll", query = "SELECT b FROM Bootcamp b"),
    @NamedQuery(name = "Bootcamp.findByBootcampId", query = "SELECT b FROM Bootcamp b WHERE b.bootcampId = :bootcampId"),
    @NamedQuery(name = "Bootcamp.findByBootcampName", query = "SELECT b FROM Bootcamp b WHERE b.bootcampName = :bootcampName"),
    @NamedQuery(name = "Bootcamp.findByBootcampActive", query = "SELECT b FROM Bootcamp b WHERE b.bootcampActive = :bootcampActive")})
public class Bootcamp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "bootcamp_id")
    private String bootcampId;
    @Basic(optional = false)
    @Column(name = "bootcamp_name")
    private String bootcampName;
    @Basic(optional = false)
    @Lob
    @Column(name = "bootcamp_location")
    private String bootcampLocation;
    @Basic(optional = false)
    @Column(name = "bootcamp_active")
    private String bootcampActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bootcampId")
    private List<BootcampDetail> bootcampDetailList;

    public Bootcamp() {
    }

    public Bootcamp(String bootcampId) {
        this.bootcampId = bootcampId;
    }

    public Bootcamp(String bootcampId, String bootcampName, String bootcampLocation, String bootcampActive) {
        this.bootcampId = bootcampId;
        this.bootcampName = bootcampName;
        this.bootcampLocation = bootcampLocation;
        this.bootcampActive = bootcampActive;
    }

    public String getBootcampId() {
        return bootcampId;
    }

    public void setBootcampId(String bootcampId) {
        this.bootcampId = bootcampId;
    }

    public String getBootcampName() {
        return bootcampName;
    }

    public void setBootcampName(String bootcampName) {
        this.bootcampName = bootcampName;
    }

    public String getBootcampLocation() {
        return bootcampLocation;
    }

    public void setBootcampLocation(String bootcampLocation) {
        this.bootcampLocation = bootcampLocation;
    }

    public String getBootcampActive() {
        return bootcampActive;
    }

    public void setBootcampActive(String bootcampActive) {
        this.bootcampActive = bootcampActive;
    }

    @XmlTransient
    public List<BootcampDetail> getBootcampDetailList() {
        return bootcampDetailList;
    }

    public void setBootcampDetailList(List<BootcampDetail> bootcampDetailList) {
        this.bootcampDetailList = bootcampDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bootcampId != null ? bootcampId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bootcamp)) {
            return false;
        }
        Bootcamp other = (Bootcamp) object;
        if ((this.bootcampId == null && other.bootcampId != null) || (this.bootcampId != null && !this.bootcampId.equals(other.bootcampId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hadirapp.HadirApproval.entity.Bootcamp[ bootcampId=" + bootcampId + " ]";
    }
    
}
