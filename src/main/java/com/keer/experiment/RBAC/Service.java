package com.keer.experiment.RBAC;

import com.keer.experiment.Contract.PIG.Pig;
import com.keer.experiment.Contract.RBAC.User;
import com.keer.experiment.Util.ContractUtil;
import com.keer.experiment.Util.EthereumUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;

import java.io.File;
import java.math.BigInteger;
import java.util.*;
@Component
public class Service {

    private static Logger logger= LoggerFactory.getLogger(Service.class);
    @Autowired
    ContractUtil contractUtil;
    @Autowired
    EthereumUtil ethereumUtil;

    public void power() throws Exception {
        User user = contractUtil.UserLoad();
        int i = 1;
        List<Map> time = new ArrayList<>();
        for (; i <= 100; i++) {
            ethereumUtil.UnlockAccount();
            long addPowerStart = System.currentTimeMillis();
            user.addPower(new BigInteger(""+i), ""+i, "bihao"+i).send();
            long addPowerend = System.currentTimeMillis();
            Tuple4<BigInteger, String, String, Boolean> tuple4 = user.getPowerInfoBypowerId(new BigInteger(""+i)).send();
            long select = System.currentTimeMillis();
            logger.info("第"+i+"次操作，数据："+tuple4.getValue1().toString()+","+tuple4.getValue2()+","+tuple4.getValue3());
            Map map = new HashMap();
            map.put("add", addPowerend - addPowerStart);
            map.put("select", select - addPowerend);
            time.add(map);
        }
        Map powerMap = new HashMap();
        powerMap.put("data", time);


        long selectStart = System.currentTimeMillis();
        List<BigInteger> powerid = user.getAllPowerID().send();
        for (BigInteger a : powerid) {
            user.getPowerInfoBypowerId(a).send();
        }
        long selectEnd = System.currentTimeMillis();
        powerMap.put("allPowerInfo", selectEnd - selectStart);
        buildPowerExecl("./power100.xls",powerMap);



        time = new ArrayList<>();
        for (; i <= 1000; i++) {
            ethereumUtil.UnlockAccount();
            long addPowerStart = System.currentTimeMillis();
            user.addPower(new BigInteger(""+i), ""+i, "bihao"+i).send();
            long addPowerend = System.currentTimeMillis();
            Tuple4<BigInteger, String, String, Boolean> tuple4 = user.getPowerInfoBypowerId(new BigInteger(""+i)).send();
            long select = System.currentTimeMillis();
            logger.info("第"+i+"次操作，数据："+tuple4.getValue1().toString()+","+tuple4.getValue2()+","+tuple4.getValue3());
            Map map = new HashMap();
            map.put("add", addPowerend - addPowerStart);
            map.put("select", select - addPowerend);
            time.add(map);
        }
        powerMap = new HashMap();
        powerMap.put("data", time);


        selectStart = System.currentTimeMillis();
        powerid = user.getAllPowerID().send();
        for (BigInteger a : powerid) {
            user.getPowerInfoBypowerId(a).send();
        }
        selectEnd = System.currentTimeMillis();
        powerMap.put("allPowerInfo", selectEnd - selectStart);
        buildPowerExecl("./power1000.xls",powerMap);


        time = new ArrayList<>();
        for (; i <= 10000; i++) {
            ethereumUtil.UnlockAccount();
            long addPowerStart = System.currentTimeMillis();
            user.addPower(new BigInteger(""+i), ""+i, "bihao"+i).send();
            long addPowerend = System.currentTimeMillis();
            Tuple4<BigInteger, String, String, Boolean> tuple4 = user.getPowerInfoBypowerId(new BigInteger(""+i)).send();
            long select = System.currentTimeMillis();
            logger.info("第"+i+"次操作，数据："+tuple4.getValue1().toString()+","+tuple4.getValue2()+","+tuple4.getValue3());
            Map map = new HashMap();
            map.put("add", addPowerend - addPowerStart);
            map.put("select", select - addPowerend);
            time.add(map);
        }
        powerMap = new HashMap();
        powerMap.put("data", time);


        selectStart = System.currentTimeMillis();
        powerid = user.getAllPowerID().send();
        for (BigInteger a : powerid) {
            user.getPowerInfoBypowerId(a).send();
        }
        selectEnd = System.currentTimeMillis();
        powerMap.put("allPowerInfo", selectEnd - selectStart);
        buildPowerExecl("./power10000.xls",powerMap);



    }


    public void role() throws Exception {
        User user = contractUtil.UserLoad();
        int i = 1;
        List<Map> time = new ArrayList<>();

        for(;i<=100;i++){
            long addRoleStart = System.currentTimeMillis();
            user.createRole(new BigInteger(""+i),"root","角色"+i).send();
            long addRoleEnd = System.currentTimeMillis();
            Tuple3<List<BigInteger>, String, String>tuple3= user.getRoleInfo("角色"+i).send();
            long selectEnd = System.currentTimeMillis();
            logger.info("第"+i+"次操作，数据："+tuple3.getValue1().toString()+","+tuple3.getValue2()+","+tuple3.getValue3());
            Map map = new HashMap();
            map.put("add", addRoleEnd - addRoleStart);
            map.put("select", selectEnd - addRoleEnd);
            time.add(map);
        }
        Map powerMap = new HashMap();
        powerMap.put("data", time);

        long start=System.currentTimeMillis();
        BigInteger sum=user.getRoleCount().send();
        for(int j=1;j<=sum.intValue();j++){
            user.getRoleInfo("角色"+i).send();
        }
        long end=System.currentTimeMillis();
        powerMap.put("allPowerInfo", end - start);
        buildPowerExecl("./role100.xls",powerMap);


        for(;i<=1000;i++){
            long addRoleStart = System.currentTimeMillis();
            user.createRole(new BigInteger(""+i),"root","角色"+i).send();
            long addRoleEnd = System.currentTimeMillis();
            Tuple3<List<BigInteger>, String, String>tuple3= user.getRoleInfo("角色"+i).send();
            long selectEnd = System.currentTimeMillis();
            logger.info("第"+i+"次操作，数据："+tuple3.getValue1().toString()+","+tuple3.getValue2()+","+tuple3.getValue3());
            Map map = new HashMap();
            map.put("add", addRoleEnd - addRoleStart);
            map.put("select", selectEnd - addRoleEnd);
            time.add(map);
        }
        powerMap = new HashMap();
        powerMap.put("data", time);

        start=System.currentTimeMillis();
        sum=user.getRoleCount().send();
        for(int j=1;j<=sum.intValue();j++){
            user.getRoleInfo("角色"+i).send();
        }
        end=System.currentTimeMillis();
        powerMap.put("allPowerInfo", end - start);
        buildPowerExecl("./role1000.xls",powerMap);



        for(;i<=10000;i++){
            long addRoleStart = System.currentTimeMillis();
            user.createRole(new BigInteger(""+i),"root","角色"+i).send();
            long addRoleEnd = System.currentTimeMillis();
            Tuple3<List<BigInteger>, String, String>tuple3= user.getRoleInfo("角色"+i).send();
            long selectEnd = System.currentTimeMillis();
            logger.info("第"+i+"次操作，数据："+tuple3.getValue1().toString()+","+tuple3.getValue2()+","+tuple3.getValue3());
            Map map = new HashMap();
            map.put("add", addRoleEnd - addRoleStart);
            map.put("select", selectEnd - addRoleEnd);
            time.add(map);
        }
        powerMap = new HashMap();
        powerMap.put("data", time);
        start=System.currentTimeMillis();
        sum=user.getRoleCount().send();
        for(int j=1;j<=sum.intValue();j++){
            user.getRoleInfo("角色"+i).send();
        }
        end=System.currentTimeMillis();
        powerMap.put("allPowerInfo", end - start);
        buildPowerExecl("./role10000.xls",powerMap);



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
            Map data = list.get(0);
            Set set = data.keySet();

            Label label = new Label(0, 0, "add");
            sheet.addCell(label);
            label = new Label(1, 0, "select");
            sheet.addCell(label);

            label=new Label(3,4,"allPowerInfo");
            sheet.addCell(label);
            label=new Label(4,4,map.get("allPowerInfo").toString());
            sheet.addCell(label);


            for (int i = 1; i <= list.size(); i++) {
                Label label1 = new Label(0, i, list.get(i).get("add").toString());
                sheet.addCell(label1);
                label1 = new Label(1, i, list.get(i).get("select").toString());
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
