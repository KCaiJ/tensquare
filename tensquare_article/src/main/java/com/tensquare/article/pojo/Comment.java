package com.tensquare.article.pojo;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章评论实体类
 */
public class Comment implements Serializable {
    @Id
    private String _id;
    private String articleid;//文章ID
    private String content;//评论内容
    private String userid;//评论人
    private String parentid;//评论ID,评论分级字段  0：顶级
    private Date publishdate;//评论日期

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }
}
