package com.javagpt.common.mail;

import java.io.File;
import java.util.Map;

/**
 * @author JavaEdge
 * @date 2023/3/23
 */
public interface MailService {

    void sendSimpleMail(String receiver, String subject, String content) throws Exception;

    void sendHtmlMail(String receiver, String subject, String content, String[] ccReceiver) throws Exception;

    void sendHtmlMail(String[] receiver, String subject, String content, String[] ccReceiver) throws Exception;

    void sendTemplateMail(String receiver, MailTemplateEnum mailTemplateEnum, Map<String, Object> paramMap)throws Exception;

    void sendTemplateMail(String[] receiver, MailTemplateEnum mailTemplateEnum, Map<String, Object> paramMap)throws Exception;

    void sendTemplateMail(String receiver, MailTemplateEnum mailTemplateEnum, Map<String, Object> paramMap, String[] ccReceiver)throws Exception;

    void sendTemplateMail(String[] receiver, MailTemplateEnum mailTemplateEnum, Map<String, Object> paramMap, String[] ccReceiver) throws Exception;

    void sendAttachmentsMail(String receiver, MailTemplateEnum mailTemplateEnum, Map<String, Object> paramMap, File file) throws Exception;

    void sendAttachmentsMail(String receiver, MailTemplateEnum mailTemplateEnum, Map<String, Object> paramMap, File file, String[] ccReceiver)throws Exception;
}
