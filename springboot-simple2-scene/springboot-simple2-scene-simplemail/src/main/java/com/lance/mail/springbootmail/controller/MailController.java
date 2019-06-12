package com.lance.mail.springbootmail.controller;

import com.lance.mail.springbootmail.bean.MailBean;
import com.lance.mail.springbootmail.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MailController
 * @Description TODO
 * @Author longxiaonan@163.com
 * @Date 2019/6/10 0010 23:10
 */
@RequestMapping("/mail")
@RestController
public class MailController {

    @Autowired
    MailUtil mailUtil;

    @GetMapping("/send1")
    public void test(){

        MailBean record = new MailBean();
        record.setSubject("测试邮件");
        record.setRecipient("longxiaonan@163.com");
        record.setContent("测试邮件内容");
        mailUtil.sendSimpleMail(record);
    }
    @GetMapping("/send2")
    public void test2(){

        MailBean record = new MailBean();
        record.setSubject("测试邮件");
        record.setRecipient("longxiaonan@163.com");
        record.setContent("测试邮件内容");
        mailUtil.sendHTMLMail(record);
    }
    @GetMapping("/send3")
    public void test3(){

        MailBean record = new MailBean();
        record.setSubject("测试邮件");
        record.setRecipient("longxiaonan@163.com");
        record.setContent("测试邮件内容");
        mailUtil.sendTemplateMail(record);
    }

    @GetMapping("/send4")
    public void test4() throws Exception{

        MailBean record = new MailBean();
        record.setSubject("测试邮件");
        record.setRecipient("longxiaonan@163.com");
        record.setContent("测试邮件内容");
        mailUtil.sendMailByQueue(record);
    }
}
