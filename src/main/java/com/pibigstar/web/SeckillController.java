package com.pibigstar.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeckillController {
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}

}
