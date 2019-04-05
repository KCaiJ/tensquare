package com.tensquare.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "tensquare-base")
@Component
public interface LabelClient {
    @RequestMapping(value="/label/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);

}
