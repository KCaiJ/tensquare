package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import entity.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 标签Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/label")
public class LabelController {
    @Autowired
    private LabelService labelService;
    /**
     * 查询全部列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, StatusMessage.Success,labelService.findAll());
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,StatusMessage.Success,labelService.findById(id));
    }

    /**
     * 增加
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add( @RequestBody Label label){
        labelService.add(label);
        return new Result(true,StatusCode.OK,StatusMessage.Success);
    }

    /**
     * 修改
     * @param label
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}" ,method = RequestMethod.PUT)
    public Result update( @RequestBody Label label,@PathVariable String
            id){
        label.setId(id);
        labelService.update(label);
        return new Result(true,StatusCode.OK,StatusMessage.Success);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}" ,method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        labelService.deleteById(id);
        return new Result(true,StatusCode.OK,StatusMessage.Success);
    }

    /**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,StatusMessage.Success,labelService.findSearch(searchMap));
    }

    /**
     * 条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap,@PathVariable int page,@PathVariable int size ){
        Page pageList= labelService.findSearch(searchMap,page,size);
        return new Result(true,StatusCode.OK,StatusMessage.Success,new PageResult(pageList.getTotalElements(),pageList.getContent() ));
    }
}
