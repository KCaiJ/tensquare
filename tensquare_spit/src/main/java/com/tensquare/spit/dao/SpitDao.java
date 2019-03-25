package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 数据层
 */
public interface SpitDao extends MongoRepository<Spit,String> {

    /**
     * 根据上级ID返回分页结果
     * @param parentid
     * @param pageable
     * @return
     */
    public Page<Spit> findByParentid(String parentid, Pageable pageable);
}
