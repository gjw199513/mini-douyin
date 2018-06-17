package com.gjw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Created by gjw19 on 2018/6/17.
 */

@RestController
public class HelloWorldController {

	@RequestMapping("/hello")
	public String Hello() {
		return "Hello Spring Boot~";
	}

}
