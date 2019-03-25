package com.tensquare.qa.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="tb_pl")
public class Pl implements Serializable {
    @Id
    private String problemid;//问题ID
    @Id
    private String lableid;//标签ID
    public String getLableid() {
        return lableid;
    }
    public void setLableid(String lableid) {
        this.lableid = lableid;
    }
    public String getProblemid() {
        return problemid;
    }
    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }
}