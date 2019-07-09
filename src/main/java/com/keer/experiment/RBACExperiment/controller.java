package com.keer.experiment.RBACExperiment;

import com.keer.experiment.Util.EthereumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {
    private static Logger logger = LoggerFactory.getLogger(controller.class);
    @Autowired
    Service service;
    @Autowired
    EthereumUtil ethereumUtil;

    @GetMapping("/testPower")
    public String TestPower() throws Exception {
//        service.power();
//        service.role();
        for(int i=0;i<20;i++){
            logger.info("账号"+i+":"+ethereumUtil.createNewAccount("12345678"));
        }
        return "finish";
    }

    @GetMapping("/testAllFunction")
    public String testAllFunction() throws Exception {
        service.testAllFunction();
        return "finish";
    }
}
