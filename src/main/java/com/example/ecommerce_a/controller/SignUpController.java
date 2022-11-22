package com.example.ecommerce_a.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ecommerce_a.domain.User;
import com.example.ecommerce_a.form.SignUpForm;
import com.example.ecommerce_a.service.SignUpService;

@Controller
@RequestMapping("/shop")
public class SignUpController {

	@Autowired
	private SignUpService signUpService;

	@ModelAttribute
	private SignUpForm setUpForm() {
		return new SignUpForm();
	}

	@RequestMapping("/to-signup")
	public String toSignUp() {
		return "sign-up";
	}

	@RequestMapping("/signup")
	public String signUp(@Validated SignUpForm form,BindingResult result,String confirmationPassword, Model model,RedirectAttributes redirectAttributes) {
		if(result.hasErrors()){
			return "sign-up";
		}

		if(!confirmationPassword.equals(form.getPassword())) {
			model.addAttribute("passMessage", "パスワードと確認用パスワードが一致していません");
			return "sign-up";
		}
		
		
		// データベースに入力されたメールアドレスを含むアカウントがいたら、サインアップ画面に戻す
		if (!signUpService.checkEmail(form.getEmail()).isEmpty()) {
			model.addAttribute("mailMessage", "このメールアドレスはすでに使用されています");
			return "sign-up";
		}

		// データベースに入力されたメールアドレスを含むアカウントがなければ、会員登録を実行、ログイン画面にリダイレクト
		User user = new User();
		BeanUtils.copyProperties(form, user);
		System.out.println(form);
		System.out.println(user);
		signUpService.signUp(user);

		redirectAttributes.addFlashAttribute("message", "会員登録に成功しました。");
		return "redirect:/shop/login";
	}

}
