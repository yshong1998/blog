package com.practice.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocsController {

    @GetMapping("/docs/users")
    public String getRestApiDocs(){
        return "userdocs.html";
    }
}
