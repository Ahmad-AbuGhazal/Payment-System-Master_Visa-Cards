package edu.mum.epayment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(value="/")
	public String homePage(){
		System.out.println("Welcome to Payment");
		return "index";
	}
	
	
}

