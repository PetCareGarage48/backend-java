package com.core.app.services.impl;

import com.core.app.entities.database.user.User;
import com.core.app.services.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;

import static com.core.app.constants.GeneralConstants.*;

@Service
public class MailServiceImpl implements MailService {

	private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String senderEmail;

	@Value("${spring.mail.verificationUrl}")
	private String verificationUrl;

	@Autowired
	MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Async
	@Override
	public CompletableFuture<String> sendSignUpConfirmation(User user) {
		CompletableFuture<String> sendEmailResult = null;
		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, TRUE);
//			helper.setTo(user.getEmail());
			helper.setFrom(senderEmail);
			helper.setSubject(SIGN_UP_CONFIRMATION_SUBJECT);
			helper.setText(buildSignUpConfirmationMessage(user), TRUE);
			mailSender.send(mail);
			sendEmailResult = CompletableFuture.completedFuture(EMAIL_SENT);
		} catch (Exception e) {
			logger.error(SIGN_UP_CONFIRMATION_EMAIL_FAILED);
		}
		return sendEmailResult;
	}

	@Async
	@Override
	public CompletableFuture<String> sendSignUpCompleted(User user) {
		CompletableFuture<String> sendEmailResult = null;
		try {
			MimeMessage mail = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, TRUE);
//			helper.setTo(user.getEmail());
			helper.setFrom(senderEmail);
			helper.setSubject(SIGN_UP_COMPLETED_SUBJECT);
			helper.setText(buildSignUpCompletedMessage(user), TRUE);
			mailSender.send(mail);
			sendEmailResult = CompletableFuture.completedFuture(EMAIL_SENT);
		} catch (Exception e) {
			logger.error(SIGN_UP_CONFIRMATION_EMAIL_FAILED);
		}
		return sendEmailResult;
	}

	private String buildSignUpCompletedMessage(User user) {
		//build receiver
		String receiver = "";
		return new StringBuilder()
				.append("<h3>Hello " + receiver + ".</h3>")
				.append("<span>This email confirms that you successfully completed registration in our system.</span><br>")
				.append("<span>Hope you'll enjoy time with our team.</span><br><br>")
				.append("<span>Thank you joining us.</span><br>")
				.toString();
	}

	private String buildSignUpConfirmationMessage(User user) {
		//build receiver
		String receiver = "";
		return new StringBuilder()
				.append("<h3>Hello " + receiver + ".</h3>")
				.append("<span>This email confirms that you were successfully registered in our system.</span><br>")
				.append("<span>To verify your account please click the button bellow. This link will expire in 24 hours.</span><br><br>")
				.append("<form action=" + verificationUrl + ">" +
						"<button style=\"background-color: #14467d; color: white; height: 35px; width: 175px; border-radius: 50px; border: none; font-weight: bold; font-size: 15px\" type=\"submit\">Verify my account</button></form><br>")
				.append("<span>Thank you joining us.</span><br>")
				.toString();
	}
}
