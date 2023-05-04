package com.Aashwik.Service;


import com.Aashwik.FormBinding.LoginForm;
import com.Aashwik.FormBinding.SingupForm;
import com.Aashwik.FormBinding.UnlockAccountForm;

public interface UserService {

	public boolean signup(SingupForm form);
	
	public boolean unlockAccount(UnlockAccountForm form);
	
	public String login(LoginForm form);
	
	public String forgotPassword(String email);

	
}
