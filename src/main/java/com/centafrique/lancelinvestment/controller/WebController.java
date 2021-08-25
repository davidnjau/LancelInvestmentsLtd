package com.centafrique.lancelinvestment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping(value ="/")
    public String getHome(){
        return "/users/home";
    }

    @RequestMapping(value ="/blog")
    public String getBlog(){
        return "/users/blog";
    }

    @RequestMapping(value ="/blog-details")
    public String getBlogDetails(){
        return "/users/blog_details";
    }
}
