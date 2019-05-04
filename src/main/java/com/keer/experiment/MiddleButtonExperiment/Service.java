package com.keer.experiment.MiddleButtonExperiment;

import com.keer.experiment.BDQL.BDQLUtil;
import com.keer.experiment.BigchainDB.BigchainDBUtil;
import com.keer.experiment.Contract.PIG.Pig;
import com.keer.experiment.Util.ContractUtil;
import com.keer.experiment.Util.EthereumUtil;
import com.keer.experiment.domain.BDQL.BigchainDBData;
import com.keer.experiment.domain.BDQL.Table;
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
    @Autowired
    MiddleButon middleButon;
    @Autowired
    BDQLUtil bdqlUtil;



    public void pigContract()throws Exception{
        Pig seller = contractUtil.PigLoad("0x9d9ac22fd504354f9933d186fb68161778a2df22");
        Pig buyer = contractUtil.PigLoad("0x2d5d11df42a9c262430db7b3415cbb01cd3301bb");
        int i = 0;
        List<Map> time = new ArrayList<>();


        for (; i <= 10; i++) {
            ethereumUtil.UnlockAccount();
            ethereumUtil.UnlockAccount("0x2d5d11df42a9c262430db7b3415cbb01cd3301bb","78787878");
            long createPigStart = System.currentTimeMillis();
            seller.createPig("i",new BigInteger("i"),new BigInteger("i")).send();
            long createPigEnd = System.currentTimeMillis();

            long preSaleStart = System.currentTimeMillis();
            seller.preSale(new BigInteger("i")).send();
            long preSaleEnd = System.currentTimeMillis();

            long confirmBuyStart = System.currentTimeMillis();
            buyer.confirmBuy(new BigInteger("i"), new BigInteger("10000000000000000000")).send();
            long confirmBuyEnd = System.currentTimeMillis();

            long transferStart = System.currentTimeMillis();
            seller.transfer("0x2d5d11df42a9c262430db7b3415cbb01cd3301bb",new BigInteger("i")).send();
            long transferEnd = System.currentTimeMillis();

            long changeStatusStart = System.currentTimeMillis();
            buyer.changeStatus("0x9d9ac22fd504354f9933d186fb68161778a2df22", new BigInteger("i")).send();
            long changeStatusEnd = System.currentTimeMillis();

            Map map = new HashMap();
            map.put("createPig", createPigEnd - createPigStart);
            map.put("preSale", preSaleEnd - preSaleStart);
            map.put("confirmBuy", confirmBuyEnd - confirmBuyStart);
            map.put("transfer", transferEnd - transferStart);
            map.put("changeStatus", changeStatusEnd - changeStatusStart);
            time.add(map);
        }

        Map pigMap = new HashMap();
        pigMap.put("data", time);

        long start=System.currentTimeMillis();
        for(int j=1;j<=i;j++){
            buyer.getPig(new BigInteger("i")).send();
        }
        long end=System.currentTimeMillis();
        pigMap.put("getAllPigInfo", end - start);
        buildPowerExecl("./pig10.xls",pigMap);


        for (; i <= 100; i++) {
            ethereumUtil.UnlockAccount();
            ethereumUtil.UnlockAccount("0x2d5d11df42a9c262430db7b3415cbb01cd3301bb","78787878");
            long createPigStart = System.currentTimeMillis();
            seller.createPig("i",new BigInteger("i"),new BigInteger("i")).send();
            long createPigEnd = System.currentTimeMillis();

            long preSaleStart = System.currentTimeMillis();
            seller.preSale(new BigInteger("i")).send();
            long preSaleEnd = System.currentTimeMillis();

            long confirmBuyStart = System.currentTimeMillis();
            buyer.confirmBuy(new BigInteger("i"), new BigInteger("10000000000000000000")).send();
            long confirmBuyEnd = System.currentTimeMillis();

            long transferStart = System.currentTimeMillis();
            seller.transfer("0x2d5d11df42a9c262430db7b3415cbb01cd3301bb",new BigInteger("i")).send();
            long transferEnd = System.currentTimeMillis();

            long changeStatuStart = System.currentTimeMillis();
            buyer.changeStatus("0x9d9ac22fd504354f9933d186fb68161778a2df22", new BigInteger("i")).send();
            long changeStatuEnd = System.currentTimeMillis();

            Map map = new HashMap();
            map.put("createPig", createPigEnd - createPigStart);
            map.put("preSale", preSaleEnd - preSaleStart);
            map.put("confirmBuy", confirmBuyEnd - confirmBuyStart);
            map.put("transfer", transferEnd - transferStart);
            map.put("changeStatus", changeStatuEnd - changeStatuStart);
            time.add(map);
        }

       pigMap = new HashMap();
        pigMap.put("data", time);

        start=System.currentTimeMillis();
        for(int j=1;j<=i;j++){
            buyer.getPig(new BigInteger("i")).send();
        }
        end=System.currentTimeMillis();
        pigMap.put("getAllPigInfo", end - start);
        buildPowerExecl("./pig100.xls",pigMap);



        for (; i <= 1000; i++) {
            ethereumUtil.UnlockAccount();
            ethereumUtil.UnlockAccount("0x2d5d11df42a9c262430db7b3415cbb01cd3301bb","78787878");
            long createPigStart = System.currentTimeMillis();
            seller.createPig("i",new BigInteger("i"),new BigInteger("i")).send();
            long createPigEnd = System.currentTimeMillis();

            long preSaleStart = System.currentTimeMillis();
            seller.preSale(new BigInteger("i")).send();
            long preSaleEnd = System.currentTimeMillis();

            long confirmBuyStart = System.currentTimeMillis();
            buyer.confirmBuy(new BigInteger("i"), new BigInteger("10000000000000000000")).send();
            long confirmBuyEnd = System.currentTimeMillis();

            long transferStart = System.currentTimeMillis();
            seller.transfer("0x2d5d11df42a9c262430db7b3415cbb01cd3301bb",new BigInteger("i")).send();
            long transferEnd = System.currentTimeMillis();

            long changeStatuStart = System.currentTimeMillis();
            buyer.changeStatus("0x9d9ac22fd504354f9933d186fb68161778a2df22", new BigInteger("i")).send();
            long changeStatuEnd = System.currentTimeMillis();

            Map map = new HashMap();
            map.put("createPig", createPigEnd - createPigStart);
            map.put("preSale", preSaleEnd - preSaleStart);
            map.put("confirmBuy", confirmBuyEnd - confirmBuyStart);
            map.put("transfer", transferEnd - transferStart);
            map.put("changeStatus", changeStatuEnd - changeStatuStart);
            time.add(map);
        }

        pigMap = new HashMap();
        pigMap.put("data", time);

       start=System.currentTimeMillis();
        for(int j=1;j<=i;j++){
            buyer.getPig(new BigInteger("i")).send();
        }
        end=System.currentTimeMillis();
        pigMap.put("getAllPigInfo", end - start);
        buildPowerExecl("./pig1000.xls",pigMap);

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


            Label label = new Label(0, 0, "createPig");
            sheet.addCell(label);
            label = new Label(1, 0, "preSale");
            sheet.addCell(label);
            label = new Label(2, 0, "confirmBuy");
            sheet.addCell(label);
            label = new Label(3, 0, "transfer");
            sheet.addCell(label);
            label = new Label(4, 0, "changeStatus");
            sheet.addCell(label);


            label=new Label(5,4,"getAllPigInfo");
            sheet.addCell(label);
            label=new Label(6,4,map.get("getAllPigInfo").toString());
            sheet.addCell(label);

            for (int i = 1; i <= list.size(); i++) {
                Label label1 = new Label(0, i, list.get(i-1).get("createPig").toString());
                sheet.addCell(label1);
                label1 = new Label(1, i, list.get(i-1).get("preSale").toString());
                sheet.addCell(label1);
                label1 = new Label(2, i, list.get(i-1).get("confirmBuy").toString());
                sheet.addCell(label1);
                label1 = new Label(3, i, list.get(i-1).get("transfer").toString());
                sheet.addCell(label1);
                label1 = new Label(4, i, list.get(i-1).get("changeStatus").toString());
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

