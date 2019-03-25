package com.tensquare.friend.pojo;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="tb_friend")
@IdClass(Friend.class)
public class Friend implements Serializable {
    @Id
    private String userid;
    @Id
    private String friendid;
    private String like;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getFriendid() {
        return friendid;
    }
    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }
}