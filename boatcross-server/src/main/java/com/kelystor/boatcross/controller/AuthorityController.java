package com.kelystor.boatcross.controller;

import com.kelystor.boatcross.dao.AuthorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorityController.class);
    @Autowired
    private AuthorityMapper authorityMapper;

    @RequestMapping
    public String index(){
        return "authority/index";
    }
}
