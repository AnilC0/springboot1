package com.anilcakir.intranet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

	/**
	 * Project Health Check Method
	 * @return OK
	 */
    @GetMapping("/stat")
    @ResponseBody
    public String stat() {
    	return "OK";
    }
    
    @GetMapping("/credit")
    @ResponseBody
    public String credit() {
    	return "S. Anil CAKIR";
    }
    
    @GetMapping("/version")
    @ResponseBody
    public String version() {
    	return "V1.0";
    }
    
}
