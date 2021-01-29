package com.quxinjun.email.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 发送简单文本邮件
     *
     * @param to      发送给谁
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);

        javaMailSender.send(message);
        System.out.println("运行完成");
    }

    /**
     * 发送HTML邮件
     *
     * @param to      发送给谁
     * @param subject 主题
     * @param content 内容
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
        System.out.println("运行完成");
    }

    /**
     * 发送富文本文件
     *
     * @param to       发送给谁
     * @param subject  主题
     * @param content  内容
     * @param filePath 文件路径（可多个）
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(content, true);
        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
        String fileName = fileSystemResource.getFilename();
        helper.addAttachment(fileName, fileSystemResource);
        javaMailSender.send(message);
        System.out.println("运行完成");
    }

    /**
     * 发送HTML带图片的邮件
     *
     * @param to      发送给谁
     * @param subject 主题
     * @param content 内容
     * @param rscPath 图片路径
     * @param rscId   图片指定id
     * @throws MessagingException
     */
    public void sendInLineResourceMail(String to, String subject, String content, String rscPath, String rscId) {

        logger.info("发送静态邮件开始：{},{},{},{},{}",to,subject,content,rscPath,rscId);
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);
            javaMailSender.send(message);
            logger.info("发送静态邮件成功!");
        } catch (MessagingException e) {
            logger.error("发送静态邮件失败：",e);
        }


        System.out.println("运行完成");
    }
}
