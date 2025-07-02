package com.utils.email.service;

import com.utils.email.MailProperties;
import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Properties;


@Slf4j
@Service
public class EmailService {

    // 注入郵件設定
    @Autowired
    private MailProperties mailProperties;

    // MailSession 單例，避免每次都新建
    private Session session;

    // 取得單例 Session，並根據設定初始化
    private Session getSession() {
        if (session == null) {
            Properties props = new Properties();
            props.put("mail.smtp.host", mailProperties.getHost());
            props.put("mail.smtp.port", String.valueOf(mailProperties.getPort()));
            props.put("mail.smtp.auth", "true");
            if (mailProperties.isSsl()) {
                props.put("mail.smtp.socketFactory.port", String.valueOf(mailProperties.getPort()));
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            }
            session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 使用設定檔中的帳號密碼
                    return new PasswordAuthentication(mailProperties.getSender(), mailProperties.getPassword());
                }
            });
        }
        return session;
    }

    /**
     * 發送郵件，支援單收件人、HTML 內容、附件
     * @param to 收件人地址
     * @param subject 主旨
     * @param htmlContent HTML 內容
     * @param attachments 附件檔案清單
     * @return 發送是否成功
     */
    public boolean sendMail(String to, String subject, String htmlContent, List<File> attachments) {
        // 檢查收件人地址是否為空
        if (to == null || to.isEmpty()) {
            log.error("收件人地址不可為空");
            return false;
        }
        return sendMail(List.of(to), subject, htmlContent, attachments);
    }

        /**
         * 發送郵件，支援多收件人、HTML 內容、附件
         * @param toList 收件人清單
         * @param subject 主旨
         * @param htmlContent HTML 內容
         * @param attachments 附件檔案清單
         * @return 發送是否成功
         */
    @Async
    public boolean sendMail(List<String> toList, String subject, String htmlContent, List<File> attachments) {
        try {
            // 建立郵件訊息
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(mailProperties.getSender()));
            // 多收件人處理
            InternetAddress[] addresses = toList.stream().map(addr -> {
                try {
                    return new InternetAddress(addr);
                } catch (AddressException e) {
                    log.error("收件人地址格式錯誤: {}", addr, e);
                    return null;
                }
            }).filter(addr -> addr != null).toArray(InternetAddress[]::new);
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            // HTML 內容
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);

            // 處理附件
            if (attachments != null) {
                for (File file : attachments) {
                    if (file != null && file.exists()) {
                        MimeBodyPart attachPart = new MimeBodyPart();
                        attachPart.setDataHandler(new DataHandler(new FileDataSource(file)));
                        attachPart.setFileName(file.getName());
                        multipart.addBodyPart(attachPart);
                    }
                }
            }

            message.setContent(multipart);

            // 發送郵件
            Transport.send(message);
            log.info("郵件發送成功: subject={}, to={}", subject, toList);
            return true;
        } catch (MessagingException e) {
            // 發送失敗記錄
            log.error("郵件發送失敗: subject={}, to={}", subject, toList, e);
            return false;
        }
    }
}
