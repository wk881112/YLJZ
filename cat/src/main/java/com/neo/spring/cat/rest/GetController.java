package com.neo.spring.cat.rest;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/v2/tms/test")
public class GetController {

    @PostConstruct
    public void test() {
        System.out.println("-------");

    }

    @ResponseBody
    @RequestMapping(value = "/pure/str", method = RequestMethod.GET)
    public String greeting() {
        return UUID.randomUUID() + "222";
    }
}
