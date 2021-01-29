package com.quxinjun.email.service;

import com.quxinjun.email.hello.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {


    @Autowired
    EmailService emailService;

    @Autowired
    TemplateEngine templateEngine;

    @Test
    public void sendSimpleMailTest() {
        emailService.sendSimpleMail("379093451@qq.com", "测试简单文本邮件", "Hello Email Test");
    }

    @Test
    public void sendHtmlMailTest() throws MessagingException {
        String content = "<html>" +
                "<body>" +
                "<h3>hello world ,这是一封html邮件</h3>" +
                "</body>" +
                "</html>";
        emailService.sendHtmlMail("379093451@qq.com", "测试HTML邮件", content);
    }

    @Test
    public void sendAttachmentsMailTest() throws MessagingException {
        String filePath = "D:\\我的下载\\idea-全家桶-不同方式激活\\设置插件仓库激活到2099年补丁 (推荐使用）\\key.txt";
        emailService.sendAttachmentsMail("379093451@qq.com", "测试富文本邮件", "这里面有一个文本文档",filePath);
    }

    @Test
    public void sendInLineResourceMailTest() throws MessagingException {
        String imagePath = "D:\\1.jpg";
        String rscId = "img_1";
        String content = "<html>" +
                "<body>" +
                "<img src='cid:"+ rscId +"'></img>" +
                "</body>" +
                "</html>";
        emailService.sendInLineResourceMail("379093451@qq.com", "测试带图片的邮件", content,imagePath,rscId);
    }

    @Test
    public void testTemplateMailTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("id","006");
        String emailContent = templateEngine.process("emailTemplate",context);

        emailService.sendHtmlMail("379093451@qq.com", "这是一个模板邮件", emailContent);
    }
}

