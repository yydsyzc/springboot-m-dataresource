package org.spring.springboot.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/4 16:56
 */
@RequestMapping("/mail")
@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    /**
     * 发送简单邮件
     */
    @RequestMapping("/send")
    public void sendMail(){
        System.out.println("发送邮件");
        mailService.sendSimpleMail("1392179273@qq.com","这是⼀封简单邮件","⼤家好，这是我的第⼀封邮件！");
    }

    @RequestMapping("/sendhtml")
    public void sengHtmlMail(){
        System.out.println("发送HTML格式邮件");
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是⼀封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("1392179273@qq.com","这是⼀封HTML邮件",content);
    }

    @RequestMapping("/sendfile")
    public void sendAttachmentsMail() {
        String filePath="D:\\weiwo.mp3";
        mailService.sendAttachmentsMail("1392179273@qq.com", "主题：带附件的邮件",
                "有附件,请查收！", filePath);
    }

    @RequestMapping("/sendimg")
    public void sendInlineResourceMail() {
        String rscId = "neo006";
        String content="<html><body>这是有图⽚的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "D:\\weixin.png";
        mailService.sendInlineResourceMail("1392179273@qq.com", "主题：这是有图⽚的邮件", content, imgPath, rscId);
    }

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 模板化邮件格式
     */
    @RequestMapping("/sendthemlf")
    public void sendTemplateMail() {
        //创建邮件正⽂
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);
        mailService.sendHtmlMail("1392179273@qq.com","主题：这是模板邮件",emailContent);
    }
}
