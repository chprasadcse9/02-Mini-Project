package com.Aashwik.Service.Impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Aashwik.Entity.UserDetails;
import com.Aashwik.FormBinding.LoginForm;
import com.Aashwik.FormBinding.SingupForm;
import com.Aashwik.FormBinding.UnlockAccountForm;
import com.Aashwik.Repository.UserRepository;
import com.Aashwik.Service.UserService;
import com.Aashwik.Utils.EmailUtils;
import com.Aashwik.Utils.PasswordUtils;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;
	
	
	@Override
	public boolean signup(SingupForm form) {

		UserDetails userDtlsEntity = userRepo.findByEmail(form.getEmail());

		if (userDtlsEntity != null) {

			return false;
		}

		// copy data from binding object to entity object

		UserDetails user = new 		UserDetails();

		BeanUtils.copyProperties(form, user);

		// Generate the Random password and set to the object

		String tempPwd = PasswordUtils.generateRandomPassword();

		user.setPwd(tempPwd);
		// Set the account Status Locked

		user.setAcStatus("LOCKED");

		// insert record

		userRepo.save(user);

		// sand email to unlock the account
		String to = form.getEmail();
		String subject ="To Unlock Your Account";
		StringBuffer body = new StringBuffer("");
		body.append("<h1>Use below temporary password to unlock your account</h1>");
		body.append("Temporary pwd : " + tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\"> Click Here To unlock Your Account</a>");

		emailUtils.sendEmail(to,subject, body.toString());

		return true;

	}


	@Override
	public boolean unlockAccount(UnlockAccountForm form) {
		// TODO Auto-generated method stub
		UserDetails entity = userRepo.findByEmail(form.getEmail());
		if(entity.getPwd().equals(form.getTempPwd()))
		{
			entity.setPwd(form.getNewPwd());
			entity.setAcStatus("UNLOCKED");
			userRepo.save(entity);
			return true;
		}else
		{
			return false;
		}
		
	}


	@Override
	public String login(LoginForm form) {
		
		UserDetails entity = userRepo.findByEmail(form.getEmail());
		
		if (entity==null) {
			return "Invalid Credentials";
			
		}
		if (entity.getAcStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}
		//create Session and store user data in session
		
		session.setAttribute("userId", entity.getUserId());
		
		return "SUCCESS";
}



	@Override
	public String forgotPassword(String email) {
		// TODO Auto-generated method stub
		UserDetails entity = userRepo.findByEmail(email);
		
		Optional<UserDetails> optional = Optional.ofNullable(entity);
		
		if(optional.isEmpty()) {
		
			return "FAILED";
		}else {
			
			String to=email;
			String subject="Your Password @ | Aashwik It is";
			String body="<h2>"+"password :"+entity.getPwd()+"</h2>";
			emailUtils.sendEmail(subject, body, to);
			
			return "Password sand to your Email  "+entity.getEmail();
		}
	}

}
