package com.keer.experiment.RBACExperiment;

import com.keer.experiment.Util.EthereumUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class controller {
    private static Logger logger = LoggerFactory.getLogger(controller.class);
    @Autowired
    Service service;
    @Autowired
    EthereumUtil ethereumUtil;

    public static int start;

    @GetMapping("/testPower")
    public String TestPower() throws Exception {
//        service.power();
//        service.role();
        for(int i=0;i<20;i++){
            logger.info("账号"+i+":"+ethereumUtil.createNewAccount("12345678"));
        }
        return "finish";
    }

    @GetMapping("/testAllFunction/{total}")
    public String testAllFunction(@PathVariable int total) throws Exception {
        service.testAllFunction(total);
        return "finish";
    }
    @GetMapping("/testAllFunctionByAllView/{total}")
    public String testAllFunctionByAllView(@PathVariable int total) throws Exception {
        service.testAllFunctionByAllView(total);
        return "finish";
    }

    @GetMapping("/tps")
    public void tps() throws InterruptedException, IOException {

        start=ethereumUtil.getBlockNumber();
        for(int i=0;i<60*20;i++) {
            ethereumUtil.UnlockAccount();
            test();
            Thread.sleep(1000);
        }



    }

    private void test(){


        logger.info("start submit");

        for(int i=0;i<100;i++) {
            //启动一个异步任务
            service.executeAsync();
        }

        logger.info("end submit");
    }
}
