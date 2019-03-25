package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import entity.StatusMessage;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HttpServletRequest request;
    /**
     * 查询全部数据
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return  new Result(true, StatusCode.OK, StatusMessage.Success,spitService.findAll());
    }

    /**
     * 根据Id查询数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return  new Result(true,StatusCode.OK,StatusMessage.Success,spitService.findById(id));
    }

    /**
     * 增加
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        Claims claims=(Claims)request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false,StatusCode.ACCESSERROR,StatusMessage.Jurisdiction_NO);
        }
        spit.setUserid(claims.getId());
        spitService.add(spit);
        return  new Result(true,StatusCode.OK,StatusMessage.Success);
    }

    /**
     * 修改
     * @param spit
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody Spit spit,@PathVariable String id){
        spit.set_id(id);
        spitService.update(spit);
        return  new Result(true,StatusCode.OK,StatusMessage.Success);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public  Result deleteById(@PathVariable String id){
        spitService.deleteById(id);
        return  new Result(true,StatusCode.OK,StatusMessage.Success);
    }

    /**
     * 根据父级ID 返回分页数据
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentId}/{page}/{size}",method = RequestMethod.GET)
    public Result findBypParentid(@PathVariable String parentId,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageList = spitService.findByParentid(parentId,page, size);
        return  new Result(true,StatusCode.OK,StatusMessage.Success,new PageResult<Spit>(pageList.getTotalElements(),pageList.getContent()));
    }

    /**
     * 点赞
     * @param id
     * @return
     */
    @RequestMapping(value="/thumbup/{id}",method=RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String id){
        String userId="";//获取当前登陆用户
        if (redisTemplate.opsForValue().get("thumbup_"+userId+"_"+id)!=null){
            return new Result(false,StatusCode.REPERROR,StatusMessage.Fail);
        }
        spitService.updateThumbup(id);
        redisTemplate.opsForValue().set( "thumbup_"+userId+"_"+ id,"1");
        return new Result(true,StatusCode.OK,StatusMessage.Success);
    }

    /**
     * 分享
     * @param id
     * @return
     */
    @RequestMapping(value="/share/{id}",method=RequestMethod.PUT)
    public Result updateShare(@PathVariable String id){
        spitService.updateShare(id);
        return new Result(true,StatusCode.OK,StatusMessage.Success);
    }

    /**
     * 浏览
     * @param id
     * @return
     */
    @RequestMapping(value="/visits/{id}",method=RequestMethod.PUT)
    public Result updateVisits(@PathVariable String id){
        spitService.updateVisits(id);
        return new Result(true,StatusCode.OK,StatusMessage.Success);
    }
}
