package com.jkgroup.kingkaid.web.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/auth")
public class AuthIndexController {

	@RequestMapping(value="test")
	public String test(){
		return "index";
	}
}
