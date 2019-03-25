package com.tensquare.sms;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信监听类
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${sms.tpl_id}")
    private String tpl_id;//模板编号
    @Value("${sms.key}")
    private String key;//签名
    /**
     * 发送短信
     * @param map
     */
    @RabbitHandler
    public void sendSms(Map map){
        String mobile=(String) map.get("mobile");
        String code=(String) map.get("code");
        System.out.println(mobile+"    "+code+"   "+key+"   "+tpl_id);
        Map params = new HashMap();//请求参数
        params.put("mobile",mobile);//接受短信的用户手机号码
        params.put("tpl_id",tpl_id);//您申请的短信模板ID，根据实际情况修改
        params.put("tpl_value","#code#="+code);//您设置的模板变量，根据实际情况修改
        params.put("key",key);//应用APPKEY(应用详细页查询)
        String response = smsUtil.sendSms(params);
        System.out.println("response=" + response);
    }
}
