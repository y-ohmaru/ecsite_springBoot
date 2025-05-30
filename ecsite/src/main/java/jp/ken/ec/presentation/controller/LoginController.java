package jp.ken.ec.presentation.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
	
	//ログイン画面表示
	@GetMapping("/login")
	public String login(@RequestParam Optional<String> error, Model model) {
		if (error.isPresent()){
			model.addAttribute("error","ユーザー名またはパスワードが間違っています。");
		}
		return "login";
		
	}
	

}
