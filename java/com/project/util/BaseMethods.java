package com.project.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class BaseMethods {

	public static String getUser(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = user.getUsername();
		return userName;
	}
	
	public static String getAlphaNumericString() 
	{ 

		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
									+ "0123456789"
									+ "abcdefghijklmnopqrstuvxyz"; 

		StringBuilder sb = new StringBuilder(10); 

		for (int i = 0; i < 10; i++) { 

			int index 
				= (int)(AlphaNumericString.length() 
						* Math.random()); 

			sb.append(AlphaNumericString 
						.charAt(index)); 
		} 

		return sb.toString(); 
	} 

	

	public static void send(String to , String passwordToSend){
		
		final String from = "retail.intel01@gmail.com" ;
		final String password= "Retail123";
		
		   	Properties props = new Properties();    
	        
		   	props.put("mail.smtp.host", "smtp.gmail.com");    
	        
		   	props.put("mail.smtp.socketFactory.port", "465");    
	          
		   	props.put("mail.smtp.socketFactory.class",    
	                    "javax.net.ssl.SSLSocketFactory");    
	        
		   	props.put("mail.smtp.auth", "true");    
	        
		   	props.put("mail.smtp.port", "465");    
	        
		   	//get Session   
	        
		   	Session session = Session.getDefaultInstance(props,    
		   			new javax.mail.Authenticator() {    
		   				protected PasswordAuthentication getPasswordAuthentication() {    
		   					return new PasswordAuthentication(from,password);  
		   				}    
	          });    
	         
		   	//compose message    
	          try {    
	        
	        	  MimeMessage message = new MimeMessage(session);    
	           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
	           message.setSubject("RETAIL INTEL :");    
	           message.setText("YOUR PASSWORD IS :" + passwordToSend);    
	           //send message  
	           Transport.send(message);    
	           System.out.println("message sent successfully");    
	          } catch (MessagingException e) {throw new RuntimeException(e);}    
	             
	 } 
	
}
