package com.project.util;
import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*;  

public class SendMail {
	// Java program generate a random AlphaNumeric String 
	// using Math.random() method 

		// function to generate a random string of length n 
		static String getAlphaNumericString(int n) 
		{ 

			String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
										+ "0123456789"
										+ "abcdefghijklmnopqrstuvxyz"; 

			StringBuilder sb = new StringBuilder(n); 

			for (int i = 0; i < n; i++) { 

				int index 
					= (int)(AlphaNumericString.length() 
							* Math.random()); 

				sb.append(AlphaNumericString 
							.charAt(index)); 
			} 

			return sb.toString(); 
		} 

		public static void main(String[] args) 
		{ 

			// Get the size n 
			int n = 20; 

			// Get and display the alphanumeric string 
			System.out.println(SendMail
								.getAlphaNumericString(n)); 
		} 
 
  
}  
	
