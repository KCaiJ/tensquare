package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import entity.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {
    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * 增加
     * @param article
     * @return
     */
    @RequestMapping(method= RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK, StatusMessage.Success);
    }

    /**
     * 根据内容或者标题模糊检索
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/search/{keywords}/{page}/{size}",method= RequestMethod.GET)
    public Result findByTitleLike(@PathVariable String keywords, @PathVariable int page, @PathVariable int size){
        Page<Article> articlePage = articleSearchService.findByTitleLike(keywords,page,size);
        return new Result(true, StatusCode.OK, StatusMessage.Success, new PageResult<Article>(articlePage.getTotalElements(), articlePage.getContent()));
    }
}