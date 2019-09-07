package com.lance.mail.springbootmail.util;

import com.lance.mail.springbootmail.bean.MailBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailUtilTest {

    @Autowired
    private MailUtil mailUtil;

    @Test
    public void sendSimpleMail() throws Exception {
        MailBean mailBean = new MailBean();
        mailBean.setRecipient("15079150591@163.com");
        mailBean.setSubject("测试SpringBootMail");
        mailBean.setContent("测试SpringBootMail");

        mailUtil.sendSimpleMail(mailBean);
    }

    @Test
    public void sendHTMLMail() throws Exception {
        MailBean mailBean = new MailBean();
        mailBean.setRecipient("15079150591@163.com");
        mailBean.setSubject("测试SpringBootMailHTML");
//        mailBean.setContent("测试SpringBootMail ");

        mailUtil.sendHTMLMail(mailBean);
    }

    @Test
    public void sendAttachmentMail() throws Exception {
        MailBean mailBean = new MailBean();
        mailBean.setRecipient("15079150591@163.com");
        mailBean.setSubject("测试SpringBoot发送附件");
        mailBean.setContent("测试SpringBoot发送附件，正文部分");

        mailUtil.sendAttachmentMail(mailBean);

    }

    @Test
    public void sendInlineMail() throws Exception {
        MailBean mailBean = new MailBean();
        mailBean.setRecipient("15079150591@163.com");
        mailBean.setSubject("测试SpringBoot发送带静态资源邮件");
        mailBean.setContent("测试SpringBoot发送带静态资源邮件，正文部分");
        mailUtil.sendInlineMail(mailBean);
    }

    @Test
    public void sendTemplateMail() throws Exception {
        MailBean mailBean = new MailBean();
        mailBean.setRecipient("15079150591@163.com");
        mailBean.setSubject("测试SpringBoot发送带FreeMarker邮件");
        mailBean.setContent("希望大家能够学到自己想要的东西，谢谢各位的帮助！！！");
        mailUtil.sendTemplateMail(mailBean);
    }


}