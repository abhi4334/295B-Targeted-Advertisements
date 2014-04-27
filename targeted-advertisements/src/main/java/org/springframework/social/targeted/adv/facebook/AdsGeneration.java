package org.springframework.social.targeted.adv.facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.org.apache.regexp.internal.recompile;

public class AdsGeneration {
	
	private final String l_ZIPCODE = "postcode";
	private final String l_NAME = "name";
	private final String l_ADDRESS = "address";
	private final String l_LOCALITY = "locality";
	private final String l_TEL = "tel";
	private final String l_WEBSITE = "website";
	
	public boolean generateAds(ArrayList<HashMap<String, String>> restaurantMapList,String toemail) {

		// To address
		String to = toemail;
		String cc = "";
		String bcc = "";
		String subject = "Auto: Mail Demo";
		String emailText = "";
		
		for(HashMap<String, String> restaurantMap : restaurantMapList)
		{
			if(restaurantMap.get(l_NAME)!=null)
			{
			emailText+= "\n"+restaurantMap.get("profilename")+" has checked in at "+restaurantMap.get("placename")
						+", "+restaurantMap.get("city")+", "+restaurantMap.get("zipcode")+".\n"
						+"Recommended:\n"
						+restaurantMap.get(l_NAME)
						+"\n"+restaurantMap.get(l_ADDRESS)
						+"\n"+restaurantMap.get(l_LOCALITY)
						+"\n"+restaurantMap.get(l_ZIPCODE)
						+"\n"+restaurantMap.get(l_TEL)
						+"\n"+restaurantMap.get(l_WEBSITE)+"\n";
			}
		}

		// Sending Email using Gmail SMTP
		boolean isEmailSent = sendEmail(to, cc, bcc, subject, emailText);
		if(isEmailSent)
		{
			return true;
		}
		return false;
	}

	private  boolean sendEmail(String to, String cc, String bcc,
			String subject, String emailText) {

		boolean isEmailSend = false;
		// From address (Need Gmail ID)
		String from = "adsuggestion@gmail.com";
		// Password of from address
		String password = "test123456";
		// Gmail host address
		String host = "smtp.gmail.com";

		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.user", from);
		props.put("password", password);

		Session session = Session.getDefaultInstance(props, null);

		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));

			// Adding To address
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to, false));
			// Adding CC email id
			// msg.setRecipients(Message.RecipientType.CC,
			// InternetAddress.parse(cc, false));
			// Adding BCC email id
			// msg.setRecipients(Message.RecipientType.BCC,
			// InternetAddress.parse(bcc, false));

			msg.setSubject(subject);
			msg.setText(emailText);

			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

			System.out.println("Email sent successfully.....");
			isEmailSend = true;
			return isEmailSend;
		} catch (AddressException e) {
			e.printStackTrace();
			return isEmailSend;
		} catch (MessagingException e) {
			e.printStackTrace();
			return isEmailSend;
		}
	}
}
