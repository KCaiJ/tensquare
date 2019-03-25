package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;
    /**
     *查询全部记录
     * @return
     */
    public List<Comment> findAll(){
        return  commentDao.findAll();
    }

    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    public Comment findById(String id){
        Comment Comment=commentDao.findById(id).get();
        return  Comment;
    }

    /**
     * 增加
     * @param comment
     */
    public void add(Comment comment){
        comment.set_id( idWorker.nextId()+"" );
        comment.setPublishdate(new Date());
        commentDao.save(comment);
    }

    /**
     * 修改
     * @param Comment
     */
    public void update(Comment Comment){
        commentDao.save(Comment);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id){
        commentDao.deleteById(id);
    }

    /**
     * 根据文章ID查询评论列表
     * @param articleid
     * @return
     */
    public List<Comment> findByArticleid(String articleid){
        return commentDao.findByArticleid(articleid);
    }

}
