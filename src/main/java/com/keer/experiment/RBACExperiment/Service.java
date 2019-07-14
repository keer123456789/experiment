package com.keer.experiment.RBACExperiment;

import com.keer.experiment.Contract.RBAC.allView.Power;
import com.keer.experiment.Contract.RBAC.allView.UserAllView;
import com.keer.experiment.Contract.RBAC.solidity.User;
import com.keer.experiment.Util.ContractUtil;
import com.keer.experiment.Util.EthereumUtil;
import com.keer.experiment.Util.FileUtil;
import com.keer.experiment.Util.HttpUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;


import java.io.File;
import java.math.BigInteger;
import java.util.*;

@Component
public class Service {

    private static Logger logger = LoggerFactory.getLogger(Service.class);
    @Autowired
    ContractUtil contractUtil;
    @Autowired
    EthereumUtil ethereumUtil;
    @Autowired
    FileUtil fileUtil;

    @Value("${account_address}")
    private String account_address;

    public void power() throws Exception {
        User user = contractUtil.UserLoad();
        int i = 1;
        List<Map> time = new ArrayList<>();
        for (; i <= 20; i++) {
            ethereumUtil.UnlockAccount();
            long addPowerStart = System.currentTimeMillis();
            user.addPower(new BigInteger("" + i), "" + i, "bihao" + i).send();
            long addPowerend = System.currentTimeMillis();
            Tuple4<BigInteger, String, String, Boolean> tuple4 = user.getPowerInfoBypowerId(new BigInteger("" + i)).send();
            long select = System.currentTimeMillis();
            logger.info("第" + i + "次操作，数据：" + tuple4.getValue1().toString() + "," + tuple4.getValue2() + "," + tuple4.getValue3());
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
        powerMap.put("allSelectInfo", selectEnd - selectStart);
        buildPowerExecl("./power100.xls", powerMap);


        time = new ArrayList<>();
        for (; i <= 1000; i++) {
            ethereumUtil.UnlockAccount();
            long addPowerStart = System.currentTimeMillis();
            user.addPower(new BigInteger("" + i), "" + i, "bihao" + i).send();
            long addPowerend = System.currentTimeMillis();
            Tuple4<BigInteger, String, String, Boolean> tuple4 = user.getPowerInfoBypowerId(new BigInteger("" + i)).send();
            long select = System.currentTimeMillis();
            logger.info("第" + i + "次操作，数据：" + tuple4.getValue1().toString() + "," + tuple4.getValue2() + "," + tuple4.getValue3());
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
        powerMap.put("allSelectInfo", selectEnd - selectStart);
        buildPowerExecl("./power1000.xls", powerMap);


        time = new ArrayList<>();
        for (; i <= 10000; i++) {
            ethereumUtil.UnlockAccount();
            long addPowerStart = System.currentTimeMillis();
            user.addPower(new BigInteger("" + i), "" + i, "bihao" + i).send();
            long addPowerend = System.currentTimeMillis();
            Tuple4<BigInteger, String, String, Boolean> tuple4 = user.getPowerInfoBypowerId(new BigInteger("" + i)).send();
            long select = System.currentTimeMillis();
            logger.info("第" + i + "次操作，数据：" + tuple4.getValue1().toString() + "," + tuple4.getValue2() + "," + tuple4.getValue3());
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
        powerMap.put("allSelectInfo", selectEnd - selectStart);
        buildPowerExecl("./power10000.xls", powerMap);


    }


    public void role() throws Exception {
        User user = contractUtil.UserLoad();
        int i = 1;
        List<Map> time = new ArrayList<>();

        for (; i <= 100; i++) {
            long addRoleStart = System.currentTimeMillis();
            user.createRole(new BigInteger("" + i), "root", "角色" + i).send();
            long addRoleEnd = System.currentTimeMillis();
            Tuple3<List<BigInteger>, String, String> tuple3 = user.getRoleInfo("角色" + i).send();
            long selectEnd = System.currentTimeMillis();
            logger.info("第" + i + "次操作，数据：" + tuple3.getValue1().toString() + "," + tuple3.getValue2() + "," + tuple3.getValue3());
            Map map = new HashMap();
            map.put("add", addRoleEnd - addRoleStart);
            map.put("select", selectEnd - addRoleEnd);
            time.add(map);
        }
        Map powerMap = new HashMap();
        powerMap.put("data", time);

        long start = System.currentTimeMillis();
        BigInteger sum = user.getRoleCount().send();
        for (int j = 1; j <= sum.intValue(); j++) {
            user.getRoleInfo("角色" + i).send();
        }
        long end = System.currentTimeMillis();
        powerMap.put("allSelectInfo", end - start);
        buildPowerExecl("./role100.xls", powerMap);


        for (; i <= 1000; i++) {
            long addRoleStart = System.currentTimeMillis();
            user.createRole(new BigInteger("" + i), "root", "角色" + i).send();
            long addRoleEnd = System.currentTimeMillis();
            Tuple3<List<BigInteger>, String, String> tuple3 = user.getRoleInfo("角色" + i).send();
            long selectEnd = System.currentTimeMillis();
            logger.info("第" + i + "次操作，数据：" + tuple3.getValue1().toString() + "," + tuple3.getValue2() + "," + tuple3.getValue3());
            Map map = new HashMap();
            map.put("add", addRoleEnd - addRoleStart);
            map.put("select", selectEnd - addRoleEnd);
            time.add(map);
        }
        powerMap = new HashMap();
        powerMap.put("data", time);

        start = System.currentTimeMillis();
        sum = user.getRoleCount().send();
        for (int j = 1; j <= sum.intValue(); j++) {
            user.getRoleInfo("角色" + i).send();
        }
        end = System.currentTimeMillis();
        powerMap.put("allSelectInfo", end - start);
        buildPowerExecl("./role1000.xls", powerMap);


        for (; i <= 10000; i++) {
            long addRoleStart = System.currentTimeMillis();
            user.createRole(new BigInteger("" + i), "root", "角色" + i).send();
            long addRoleEnd = System.currentTimeMillis();
            Tuple3<List<BigInteger>, String, String> tuple3 = user.getRoleInfo("角色" + i).send();
            long selectEnd = System.currentTimeMillis();
            logger.info("第" + i + "次操作，数据：" + tuple3.getValue1().toString() + "," + tuple3.getValue2() + "," + tuple3.getValue3());
            Map map = new HashMap();
            map.put("add", addRoleEnd - addRoleStart);
            map.put("select", selectEnd - addRoleEnd);
            time.add(map);
        }
        powerMap = new HashMap();
        powerMap.put("data", time);
        start = System.currentTimeMillis();
        sum = user.getRoleCount().send();
        for (int j = 1; j <= sum.intValue(); j++) {
            user.getRoleInfo("角色" + i).send();
        }
        end = System.currentTimeMillis();
        powerMap.put("allSelectInfo", end - start);
        buildPowerExecl("./role10000.xls", powerMap);


    }


    public void testAllFunction(int total) throws Exception {
        User user = contractUtil.UserLoad();

        List<String> accounts = new ArrayList<>();

        for (int j = 0; j < total; j++) {
            String address = ethereumUtil.createNewAccount("12345678");
            logger.info("创建第"+j+"个账号："+address);
            accounts.add(address);
            ethereumUtil.UnlockAccount();
            BigInteger value = Convert.toWei("50.0", Convert.Unit.ETHER).toBigInteger();
            user.transfer(address, value).send();
            logger.info("循环第" + j + "次，转账给：" + address + ",数值：" + value.toString());
        }


        List<Map> time = new ArrayList<>();
        for (int i = 2; i < (total+2); i++) {
            User user1 = contractUtil.UserLoad(accounts.get(i - 2).toString());
            Map map = new HashMap();

            ethereumUtil.UnlockAccount();
            long start = System.currentTimeMillis();
            user.addPower(new BigInteger("" + (i)), "" + i, "bihao" + i).send();
            long end = System.currentTimeMillis();
            map.put("addPermission", end - start);
            logger.info("第"+(i-1)+"次：addPermission:"+(end - start));


            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.changePowerInfo(new BigInteger("" + i), "test" + i).send();
            end = System.currentTimeMillis();
            map.put("changePermissionInfo", end - start);
            logger.info("第"+(i-1)+"次：changePermissionInfo:"+(end - start));

//            ethereumUtil.UnlockAccount();
//            start = System.currentTimeMillis();
//            user.changePowername(new BigInteger("" + i), "powerName" + i);
//            end = System.currentTimeMillis();
//            map.put("changePowerName", end - start);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            Tuple4<BigInteger, String, String, Boolean> tuple4 = user.getPowerInfoBypowerId(new BigInteger("" + i)).send();
            end = System.currentTimeMillis();
            map.put("getPermissionInfo", end - start);
            logger.info("第"+(i-1)+"次：getPermissionInfo:"+(end - start));
            logger.info("第" + (i - 1) + "次操作，数据：" + tuple4.getValue1().toString() + "," + tuple4.getValue2() + "," + tuple4.getValue3());

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.createRole(new BigInteger("" + i), "root", "roleName" + i).send();
            end = System.currentTimeMillis();
            map.put("addRole", end - start);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.changeRoleId("roleName" + i, new BigInteger("" + 1)).send();
            end = System.currentTimeMillis();
            map.put("addPermissionForRole", end - start);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            Tuple3<List<BigInteger>, String, String> tuple3 = user.getRoleInfo("roleName" + i).send();
            end = System.currentTimeMillis();
            map.put("getRoleInfo", end - start);
            logger.info("第" + (i - 1) + "次操作，数据：" + tuple3.getValue1().toString() + "," + tuple3.getValue2() + "," + tuple3.getValue3());

            ethereumUtil.UnlockAccount(accounts.get(i - 2).toString(), "12345678");
            start = System.currentTimeMillis();
            user1.registerUser("" + i, "15110074528@163.com").send();
            end = System.currentTimeMillis();
            map.put("registerUser", end - start);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.enroll(accounts.get(i - 2).toString(), "roleName" + i, "admin").send();
            end = System.currentTimeMillis();
            map.put("enrollUser", end - start);

//            ethereumUtil.UnlockAccount();
//            start = System.currentTimeMillis();
//            user.changeEmail(accounts.get(i - 2).toString(), "1073441240@qq.com");
//            end = System.currentTimeMillis();
//            map.put("changeEmail", end - start);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.changeRoleName(accounts.get(i - 2).toString(), "root").send();
            end = System.currentTimeMillis();
            map.put("changeUserRole", end - start);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.changeUserId(accounts.get(i - 2).toString(), "userName" + i).send();
            end = System.currentTimeMillis();
            map.put("changeUserId", end - start);
            Thread.sleep(5000);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            Tuple5<String, String, String, String, BigInteger> tuple5 = user.getUserInfo("userName" + i).send();
            end = System.currentTimeMillis();
            map.put("getUserAllInfo", end - start);
            logger.info("查询用户信息：" + tuple5.toString());

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.changeUnUse(new BigInteger("" + i)).send();
            end = System.currentTimeMillis();
            map.put("deletePermission", end - start);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.deleteUser(accounts.get(i - 2).toString()).send();
            end = System.currentTimeMillis();
            map.put("deleteUser", end - start);


            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            List list = user.getPower("userName" + i).send();
            end = System.currentTimeMillis();
            map.put("getPermissionByUserId", end - start);
            logger.info("用户：userName" + i + "的权限：" + list.toString());
            logger.info("第" + (i - 1) + "次循环结束");


            time.add(map);
        }
        logger.info(time.toString());
        buildTestAllFunction("./TestAllFunction.xls", time);
    }

    public void testAllFunctionByAllView(int total) throws Exception {
        UserAllView user = contractUtil.UserAllViewLoad();

        List<String> accounts = new ArrayList<>();

        for (int j = 0; j < total; j++) {
            String address = ethereumUtil.createNewAccount("12345678");
            logger.info("创建第"+j+"个账号："+address);
            accounts.add(address);
            ethereumUtil.UnlockAccount();
            BigInteger value = Convert.toWei("10.0", Convert.Unit.ETHER).toBigInteger();
            user.transfer(address, value).send();
            logger.info("循环第" + j + "次，转账给：" + address + ",数值：" + value.toString());
        }


        List<Map> time = new ArrayList<>();
        for (int i = 2; i < (total+2); i++) {
            UserAllView user1 = contractUtil.UserAllViewLoad(accounts.get(i - 2).toString());
            Map map = new HashMap();

            ethereumUtil.UnlockAccount();
            long start = System.currentTimeMillis();
            user.addPower(new BigInteger("" + (i)), "" + i, "bihao" + i).send();
            long end = System.currentTimeMillis();
            map.put("addPermission", end - start);
            logger.info("第"+(i-1)+"次：addPermission:"+(end - start));


            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.changePowerInfo(new BigInteger("" + 1), "test" + i).send();
            end = System.currentTimeMillis();
            map.put("changePermissionInfo", end - start);
            logger.info("第"+(i-1)+"次：changePermissionInfo:"+(end - start));

//            ethereumUtil.UnlockAccount();
//            start = System.currentTimeMillis();
//            user.changePowername(new BigInteger("" + i), "powerName" + i);
//            end = System.currentTimeMillis();
//            map.put("changePowerName", end - start);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            Tuple4<BigInteger, String, String, Boolean> tuple4 = user.getPowerInfoBypowerId(new BigInteger("" + i)).send();
            end = System.currentTimeMillis();
            map.put("getPermissionInfo", end - start);
            logger.info("第"+(i-1)+"次：getPermissionInfo:"+(end - start));
            logger.info("第" + (i - 1) + "次操作，数据：" + tuple4.getValue1().toString() + "," + tuple4.getValue2() + "," + tuple4.getValue3());

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.createRole(new BigInteger("" + i), "root", "roleName" + i).send();
            end = System.currentTimeMillis();
            logger.info("第"+(i-1)+"次：createRole:"+(end - start));
            map.put("addRole", end - start);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.changeRoleId("roleName" + i, new BigInteger("" + 1)).send();
            end = System.currentTimeMillis();
            logger.info("第"+(i-1)+"次：changeRoleId:"+(end - start));
            map.put("addPermissionForRole", end - start);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            Tuple3<List<BigInteger>, String, String> tuple3 = user.getRoleInfo("roleName" + i).send();
            end = System.currentTimeMillis();
            map.put("getRoleInfo", end - start);
            logger.info("第"+(i-1)+"次：getRoleInfo:"+(end - start));
            logger.info("第" + (i - 1) + "次操作，数据：" + tuple3.getValue1().toString() + "," + tuple3.getValue2() + "," + tuple3.getValue3());

            ethereumUtil.UnlockAccount(accounts.get(i - 2).toString(), "12345678");
            start = System.currentTimeMillis();
            user1.registerUser("" + i, "15110074528@163.com").send();
            end = System.currentTimeMillis();
            map.put("registerUser", end - start);
            logger.info("第"+(i-1)+"次：registerUser:"+(end - start));

//            ethereumUtil.UnlockAccount();
//            start = System.currentTimeMillis();
//            user.enroll(accounts.get(i - 2).toString(), "roleName" + i, "admin").send();
//            end = System.currentTimeMillis();
//            map.put("enrollUser", end - start);
//            logger.info("第"+(i-1)+"次：enroll:"+(end - start));




            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.changeRoleName(account_address, "root"+i).send();
            end = System.currentTimeMillis();
            map.put("changeUserRole", end - start);
            logger.info("第"+(i-1)+"次：changeRoleName:"+(end - start));

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.changeUserId(account_address, "userName" + i).send();
            end = System.currentTimeMillis();
            map.put("changeUserId", end - start);
            logger.info("第"+(i-1)+"次：changeUserId:"+(end - start));
            Thread.sleep(5000);

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            Tuple5<String, String, String, String, BigInteger> tuple5 = user.getUserInfo("userName" + i).send();
            end = System.currentTimeMillis();
            map.put("getUserAllInfo", end - start);
            logger.info("查询用户信息：" + tuple5.toString());
            logger.info("第"+(i-1)+"次：getUserInfo:"+(end - start));

            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            user.changeUnUse(new BigInteger("" + i)).send();
            end = System.currentTimeMillis();
            map.put("deletePermission", end - start);
            logger.info("第"+(i-1)+"次：changeUnUse:"+(end - start));

//            ethereumUtil.UnlockAccount();
//            start = System.currentTimeMillis();
//            user.deleteUser(accounts.get(i - 2).toString()).send();
//            end = System.currentTimeMillis();
//            map.put("deleteUser", end - start);
//            logger.info("第"+(i-1)+"次：deleteUser:"+(end - start));


            ethereumUtil.UnlockAccount();
            start = System.currentTimeMillis();
            List list = user.getPower("userName" + i).send();
            end = System.currentTimeMillis();
            map.put("getPermissionByUserId", end - start);
            logger.info("第"+(i-1)+"次：getPowerl:"+(end - start));
            logger.info("用户：userName" + i + "的权限：" + list.toString());
            logger.info("第" + (i - 1) + "次循环结束");



            time.add(map);
        }
        logger.info(time.toString());
        buildTestAllFunction("./TestAllFunctionByAllView.xls", time);
    }

    private void buildTestAllFunction(String path, List<Map> list) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();

            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
            WritableSheet sheet = writableWorkbook.createSheet("sheet1", 0);

            Label label = new Label(0, 0, "addPermission");
            sheet.addCell(label);

            label = new Label(1, 0, "changePermissionInfo");
            sheet.addCell(label);


            label = new Label(3, 0, "getPermissionInfo");
            sheet.addCell(label);

            label = new Label(4, 0, "addRole");
            sheet.addCell(label);

            label = new Label(5, 0, "addPermissionForRole");
            sheet.addCell(label);

            label = new Label(6, 0, "getRoleInfo");
            sheet.addCell(label);

            label = new Label(7, 0, "registerUser");
            sheet.addCell(label);

            label = new Label(8, 0, "enrollUser");
            sheet.addCell(label);


            label = new Label(10, 0, "changeUserRole");
            sheet.addCell(label);

            label = new Label(11, 0, "changeUserId");
            sheet.addCell(label);

            label = new Label(12, 0, "getUserAllInfo");
            sheet.addCell(label);

            label = new Label(13, 0, "deletePermission");
            sheet.addCell(label);

            label = new Label(14, 0, "deleteUser");
            sheet.addCell(label);

            label = new Label(15, 0, "getPermissionByUserId");
            sheet.addCell(label);

            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                label = new Label(0, i + 1, map.get("addPermission").toString());
                sheet.addCell(label);
                label = new Label(1, i + 1, map.get("changePermissionInfo").toString());
                sheet.addCell(label);

                label = new Label(3, i + 1, map.get("getPermissionInfo").toString());
                sheet.addCell(label);
                label = new Label(4, i + 1, map.get("addRole").toString());
                sheet.addCell(label);
                label = new Label(5, i + 1, map.get("addPermissionForRole").toString());
                sheet.addCell(label);
                label = new Label(6, i + 1, map.get("getRoleInfo").toString());
                sheet.addCell(label);
                label = new Label(7, i + 1, map.get("registerUser").toString());
                sheet.addCell(label);
                label = new Label(8, i + 1, map.get("enrollUser").toString());
                sheet.addCell(label);

                label = new Label(10, i + 1, map.get("changeUserRole").toString());
                sheet.addCell(label);
                label = new Label(11, i + 1, map.get("changeUserId").toString());
                sheet.addCell(label);
                label = new Label(12, i + 1, map.get("getUserAllInfo").toString());
                sheet.addCell(label);
                label = new Label(13, i + 1, map.get("deletePermission").toString());
                sheet.addCell(label);
                label = new Label(14, i + 1, map.get("deleteUser").toString());
                sheet.addCell(label);
                label = new Label(15, i + 1, map.get("getPermissionByUserId").toString());
                sheet.addCell(label);
            }
            writableWorkbook.write();    //写入数据
            writableWorkbook.close();  //关闭连接
            logger.info("成功写入文件，请前往查看文件！");
        } catch (Exception e) {
            logger.info("文件写入失败，报异常...");
        }
    }

    @Async("asyncServiceExecutor")
    public void executeAsync() {
        logger.info("start executeAsync");
        try{
            send();
        }catch(Exception e){
            e.printStackTrace();
        }
        logger.info("end executeAsync");
    }

    private void send() throws Exception {
        BigInteger value = Convert.toWei("0.01", Convert.Unit.ETHER).toBigInteger();
        User user=contractUtil.UserLoad();
        user.transfer("0x57fe57ad5a5e7ea0fb7b4b7c0a403789bc869908",value).send();
    }
    private void buildPowerExecl(String path, Map map) {
        File file = new File(path);
        try {
            file.createNewFile();        if (file.exists()) {
                file.delete();
            }


            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
            WritableSheet sheet = writableWorkbook.createSheet("sheet1", 0);

            List<Map> list = (List<Map>) map.get("data");


            Label label = new Label(0, 0, "add");
            sheet.addCell(label);
            label = new Label(1, 0, "select");
            sheet.addCell(label);

            label = new Label(3, 4, "allSelectInfo");
            sheet.addCell(label);
            label = new Label(4, 4, map.get("allSelectInfo").toString());
            sheet.addCell(label);


            for (int i = 1; i <= list.size(); i++) {
                Label label1 = new Label(0, i, list.get(i - 1).get("add").toString());
                sheet.addCell(label1);
                label1 = new Label(1, i, list.get(i - 1).get("select").toString());
                sheet.addCell(label1);
            }

            writableWorkbook.write();    //写入数据
            writableWorkbook.close();  //关闭连接
            logger.info("成功写入文件，请前往查看文件！");
        } catch (Exception e) {
            logger.info("文件写入失败，报异常...");
        }


    }

    public static void main(String[] args) throws Exception {

        Web3j web3j = Web3j.build(new HttpService("http://192.168.1.115:8545"));

        TransactionManager clientTransactionManager = new ClientTransactionManager(web3j, "0x09c448ddc837817d0a9e2d2212056a66ac710c6e");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        User user = User.load("0x39c25682cd21b304c0bcafb93a07eb2ce324a014", web3j, clientTransactionManager, contractGasProvider.getGasPrice(), contractGasProvider.getGasLimit());
        for (int i = 0; i < 20; i++) {
            long start = System.currentTimeMillis();
            List list = user.getPower("admin").send();
            long end = System.currentTimeMillis();
            logger.info(list.toString());
            logger.info("第" + i + "次操作时间：" + (end - start));
        }
    }
}
