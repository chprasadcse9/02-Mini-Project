package com.Aashwik.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Aashwik.FormBinding.LoginForm;
import com.Aashwik.FormBinding.SingupForm;
import com.Aashwik.FormBinding.UnlockAccountForm;
import com.Aashwik.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("user", new SingupForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handelSignUp(@ModelAttribute("user") SingupForm form, Model model) {

		Boolean status = userService.signup(form);

		if (status) {
			model.addAttribute("succMsg", "Account Created ,Check Your Email");
		} else {
			model.addAttribute("errMsg", "Choose Unique Email");
		}

		return "signup";
	}
	
	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email, Model model) {

		UnlockAccountForm unlockForm = new UnlockAccountForm();
		unlockForm.setEmail(email);
		model.addAttribute("unlock", unlockForm);

		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockUserAccount(UnlockAccountForm unlock, Model model) {

		if (unlock.getNewPwd().equals(unlock.getConfirmPwd())) {

			boolean status = userService.unlockAccount(unlock);
			if (status) {
				
				bindingObj(model);
				
				model.addAttribute("succMsg", "Account Unlocked successful");

			} else {
				bindingObj(model);
				
				model.addAttribute("msg", "Temp password is not correct");

			}

		} else {
			bindingObj(model);
			
			model.addAttribute("msg", "new password and conform password not matched");
		}

		return "unlock";
	}


	@GetMapping("/login")
	public String loginPage(Model model) {

		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm ,Model model) {

		System.out.println(loginForm);
		
		String status = userService.login(loginForm);
		
		if (status.contains("SUCCESS")) {
			return "redirect:/dashboard";
		}else {
			
			model.addAttribute("errMsg", status);
			return "login";
		}
		
		
	}

	
	
	@GetMapping("/forgot")
	public String forgotpwdPage(Model model) {

		//model.addAttribute("forgotpwd", new ForgotPwd());
		
		return "forgotPwd";
	}
	
	@PostMapping("/forgotPwd")
	public String forgotpwd( @RequestParam("email") String email,Model model) {
		
		System.out.println(email);
		
		String status = userService.forgotPassword(email);
		if (status.contains("FAILED")) {
		
		model.addAttribute("errMsg", "Account not Exist with Given Email please check your Email");
		//model.addAttribute("forgotpwd", new ForgotPwd());
		return "forgotPwd";
		}
		else {
			
			model.addAttribute("succMsg",status );
			//model.addAttribute("forgotpwd", new ForgotPwd());
			return "forgotPwd";	
		}

		
	}
	
	private void bindingObj(Model model) {
		UnlockAccountForm unlockForm=new UnlockAccountForm();
		model.addAttribute("unlock",unlockForm);
	}

}

