package com.keer.experiment.MiddleButtonExperiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class controller {
    @Autowired
    Service service;

    Map map = new HashMap();
    @PostMapping("/testPig")
    public String TestPower() throws Exception {
        service.pigContract();
        return "finish";
    }
}
