package com.keer.experiment.MiddleButtonExperiment;

import com.keer.experiment.BigchainDB.BigchainDBUtil;
import com.keer.experiment.Contract.PIG.Pig;
import com.keer.experiment.Util.ContractUtil;
import com.keer.experiment.Util.EthereumUtil;
import com.keer.experiment.domain.BDQL.BigchainDBData;
import com.keer.experiment.domain.ParserResult;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {

    private static Logger logger= LoggerFactory.getLogger(com.keer.experiment.RBACExperiment.Service.class);
    @Autowired
    ContractUtil contractUtil;
    @Autowired
    EthereumUtil ethereumUtil;
    @Autowired
    BigchainDBUtil bigchainDBUtil;


    public ParserResult getPigERCID(Map map) {
        int i = 0;
        ParserResult parserResult = new ParserResult();
        for (;i <= 100; i++) {
            logger.info("开始在合约创建猪，并返回721id");
            Pig pig = contractUtil.PigLoad("0xb47d2a347736fc1e1e7102a86adae717fe63987e");
            ethereumUtil.UnlockAccount();
            logger.info("解锁成功！！");
            TransactionReceipt transactionReceipt = null;
            try {
                transactionReceipt = pig.createPig("i",new BigInteger(""+i),new BigInteger(""+i)).send();
                List<Pig.Log_BirthEventResponse> responses = pig.getLog_BirthEvents(transactionReceipt);
                int tokenId = responses.get(0).tokenId.intValue();
                parserResult.setData(tokenId + "");
                parserResult.setStatus(ParserResult.SUCCESS);
                parserResult.setMessage("success");
                return parserResult;
            } catch (Exception e) {
                e.printStackTrace();
            }
            parserResult.setStatus(ParserResult.ERROR);
            parserResult.setData(null);
            parserResult.setMessage("fail");
        }
        return parserResult;
    }


    public ParserResult createPig(Map info) throws Exception {
        int i = 0;
        List<Map> time = new ArrayList<>();
        ParserResult parserResult = new ParserResult();
        for (; i <= 100; i++) {
            long createPigStart = System.currentTimeMillis();
            BigchainDBData bigchainDBData = new BigchainDBData("pigInfo", info);
            logger.info("要增加的猪的信息   " + info.toString());
            String assetID;
            try {
                assetID = bigchainDBUtil.createAsset(bigchainDBData);
            } catch (Exception e) {
                parserResult.setStatus(ParserResult.ERROR);
                parserResult.setMessage("error");
                e.printStackTrace();
                return parserResult;
            }
            logger.info("创建资产成功，资产ID：" + assetID);
            logger.info("添加新猪成功");
            for (; true; ) {
                if (BigchainDBUtil.checkTransactionExit(assetID)) {
                    break;
                }
            }

            // 制作status表
            Map map = new HashMap();
            map.put("earId", i);
            map.put("tokenId", i);
            map.put("statu", "0");

            bigchainDBData = new BigchainDBData("pigStatus", map);
            String txID = bigchainDBUtil.transferToSelf(bigchainDBData, assetID);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            parserResult.setStatus(ParserResult.SUCCESS);
            parserResult.setMessage("success");
            long createPigEnd = System.currentTimeMillis();

            Map a = new HashMap();
            a.put("createPig", createPigEnd - createPigStart);
            time.add(a);
        }

        Map createPigMap = new HashMap();
        createPigMap.put("data", time);
        buildPowerExecl("./createPig100.xls",createPigMap);
        return parserResult;
    }





    private void buildPowerExecl(String path, Map map) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();

            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
            WritableSheet sheet = writableWorkbook.createSheet("sheet1", 0);

            List<Map> list = (List<Map>) map.get("data");


            Label label = new Label(0, 0, "add");
            sheet.addCell(label);


            for (int i = 1; i <= list.size(); i++) {
                Label label1 = new Label(0, i, list.get(i-1).get("createPig").toString());
                sheet.addCell(label1);
            }

            writableWorkbook.write();    //写入数据
            writableWorkbook.close();  //关闭连接
            logger.info("成功写入文件，请前往查看文件！");
        } catch (Exception e) {
            logger.info("文件写入失败，报异常...");
        }


    }
}

