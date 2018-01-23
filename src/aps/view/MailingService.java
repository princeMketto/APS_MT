package aps.view;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailConnectException;

public class MailingService {
	Properties props;
	Session session;
	public void Init(){
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		//props.put("mail.debug", "true");
		props.put("mail.smtp.port", "465");
	}
	public void PropClose() throws Exception, MessagingException{
		
	}
	public void InSession(String username,String password){
			
		 session = Session.getDefaultInstance(props,new GMailAuthenticator(username,password));
	}
	public void sendMail(String senderaddress,String receiveraddress,String subject,String content) throws Exception{
	//	try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderaddress));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiveraddress));
			
			message.setSubject(subject);
			message.setText(content);

			Transport.send(message);
			session.getTransport().close();
			//System.out.println("Done");
			done();
	//	}catch(MailConnectException e){
			Notdone();
	//	}catch (MessagingException e) {
		
		//	throw new RuntimeException(e);
	//	}
	}
	public boolean  done(){
		props.clear();
		try {
			session.getTransport().close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public boolean  Notdone(){
		return true;
	}
/*	 public static void main(String[] args) {
		 try {
		 MailingService mai = new MailingService();
		 mai.Init();
		 mai.InSession("jmketto3@gmail.com", "Charming");
		
			mai.sendMail("jmketto3@gmail.com", "kiloveleandason@gmail.com", "HELLO", "testing mail");
			System.out.println("SENNT");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }*/
}
