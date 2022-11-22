package com.example.ecommerce_a.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class SignUpApiController {

	@ResponseBody
	@RequestMapping(value = "/checkConPass", method = RequestMethod.POST)
	public Map<String, String> checkConPass(String password, String confirmationPassword) {
		System.out.println("動いてるよん");
		Map<String, String> map = new HashMap<>();
		String duplicateMessage = null;
		if (confirmationPassword.isBlank()) {
			duplicateMessage = "確認用パスワードが空欄です";
		}else if (confirmationPassword.equals(password)) {
			duplicateMessage = "確認用パスワード入力OK!";
		} else {
			duplicateMessage = "パスワードと確認用パスワードが一致していません";
		}
		map.put("duplicateMessage", duplicateMessage);
		return map;
	}

}
