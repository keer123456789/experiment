package com.keer.experiment.RBAC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {
    @Autowired
    Service service;
    @GetMapping("/testPower")
    public String TestPower() throws Exception {
        service.power();
        return "finish";
    }
}
