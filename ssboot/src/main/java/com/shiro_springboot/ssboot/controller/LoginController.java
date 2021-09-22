package com.shiro_springboot.ssboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("/goIndex")
    public String goIndex(Model model){
        model.addAttribute("msg","hello shiro");
        return "index";
    }

}
