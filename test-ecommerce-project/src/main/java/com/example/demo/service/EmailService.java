package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.example.demo.domain.EmailRequest;

import ch.qos.logback.core.joran.spi.XMLUtil;
import io.netty.util.internal.StringUtil;

@Service
public class EmailService {

	public void sendMail(EmailRequest emailReq) {
		
		String to = emailReq.getUser().getEmail();  
		String from = "swathi@gmail.com";
		
		String mailSubject = "Order Confirmation mail";
		
		Properties properties = System.getProperties();  
		properties.setProperty("mail.smtp.host", "localhost");  
		Session session = Session.getDefaultInstance(properties);
		
		try{  
	         MimeMessage message = new MimeMessage(session);  
	         message.setFrom(new InternetAddress(from));  
	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	         message.setSubject(mailSubject);  
	         message.setText(getMessageBody(emailReq));  
	  
	         Transport.send(message);  
	  
	      }catch (MessagingException mex) {
	    	  mex.printStackTrace();
	      }  catch (Exception e) {
			e.printStackTrace();
	      }
	}

	private String getMessageBody(EmailRequest emailReq) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", emailReq.getUser().getFirstName() + emailReq.getUser().getLastName());
		paramMap.put("products", emailReq.getCartItemDetails());
		paramMap.put("totalamount", emailReq.getTotalAmount());
		InputStream fileInputStream = null;
        try {
            fileInputStream = getXSLFileAsStream("order_confirmation.xsl");
            String body = performCachableXSLTFromStream(fileInputStream, "<start></start>", paramMap);
            return body;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
	}

	public static InputStream getXSLFileAsStream(String fileName) {
        InputStream inputStream = XMLUtil.class.getClassLoader().getResourceAsStream("/xsl/" + fileName);
        if (inputStream == null) {
            inputStream = XMLUtil.class.getClassLoader().getResourceAsStream("xsl/" + fileName);
        }

        return inputStream;
    }
	
	public static String performCachableXSLTFromStream(InputStream fileStream, String xml, Map<String, Object> params)
	        throws Exception {

	        SAXTransformerFactory stf = (SAXTransformerFactory) TransformerFactory.newInstance();

	        Document input = null;
	        if (!StringUtil.isNullOrEmpty(xml))
	            input = loadXMLFromString(xml);

	        Templates templates = null;
	        templates = stf.newTemplates(new StreamSource(fileStream));

	        Transformer t = templates.newTransformer();

	        if (params != null && params.size() > 0) {
	            Iterator it = params.entrySet().iterator();
	            while (it.hasNext()) {
	                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
	                t.setParameter(entry.getKey(), entry.getValue());
	            }
	        }

	        StringWriter writer = new StringWriter();
	        StreamResult result = new StreamResult(writer);
	        if (!StringUtil.isNullOrEmpty(xml))
	            t.transform(new DOMSource(input), result);
	        else
	            t.transform(new DOMSource(), result);
	        
	        return writer.toString();

	    }
	
	public static Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }
}
