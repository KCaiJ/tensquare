package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 * 业务层
 */
@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *查询全部记录
     * @return
     */
    public List<Spit> findAll(){
        return  spitDao.findAll();
    }

    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    public Spit findById(String id){
        Spit spit=spitDao.findById(id).get();
        return  spit;
    }

    /**
     * 增加
     * @param spit
     */
    public void add(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());
        spit.setVisits(0);//浏览量
        spit.setComment(0);//回复数
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setState("1");
        //存在父级ID时 评论
        if (spit.getParentid()!=null&&!"".equals(spit.getParentid())){
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update=new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /**
     * 修改
     * @param spit
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }


    /**
     * 根据上级ID返回数据列表
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentid(String parentid,int page,int size){
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return  spitDao.findByParentid(parentid,pageRequest);
    }
    /**
     * 点赞
     * @param id
     */
    public void updateThumbup(String id){
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

    /**
     * 浏览
     * @param id
     */
    public void updateVisits(String id){
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update=new Update();
        update.inc("visits",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }

    /**
     * 分享
     * @param id
     */
    public void updateShare(String id){
        Query query=new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update=new Update();
        update.inc("share",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}