package com.kelystor.boatcross.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

    @RequestMapping
    public String index(){
        return "authority/index";
    }
}
