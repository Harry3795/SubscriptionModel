package com.nrt.nrt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class Test {

	@GetMapping("/")
	public String s() {
		return "home"; 
	}
}
   