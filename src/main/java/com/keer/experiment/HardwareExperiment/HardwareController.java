package com.keer.experiment.HardwareExperiment;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class HardwareController {
    private static Logger logger = LoggerFactory.getLogger(HardwareController.class);

    List<String> info1=new ArrayList<>();
    List<String> info2=new ArrayList<>();
    List<String> info3=new ArrayList<>();

    @GetMapping("/getInfo1/{info}")
    public void getInfo1(@PathVariable String info){
        info1.add(info);
        logger.info("Info_1_第"+info1.size()+"次请求，"+info);
        if(info1.size()==100){
            build100(info1,"./info1_100.xls");
        }
        if(info1.size()==1000){
            build1000(info1,"./info1_1000.xls");
        }
        if(info1.size()==10000){
            build10000(info1,"./info1_10000.xls");
        }

    }
    @GetMapping("/getInfo2/{info}")
    public void getInfo2(@PathVariable String info){
        info2.add(info);
        logger.info("Info_2_第"+info2.size()+"次请求，"+info);
        if(info2.size()==100){
            build100(info2,"./info2_100.xls");
        }
        if(info2.size()==1000){
            build1000(info2,"./info2_1000.xls");
        }
        if(info2.size()==10000){
            build10000(info2,"./info2_10000.xls");
        }
    }
    @GetMapping("/getInfo3/{info}")
    public void getInfo3(@PathVariable String info){
        info3.add(info);
        logger.info("Info_3_第"+info3.size()+"次请求，"+info);
        if(info3.size()==100){
            build100(info3,"./info3_100.xls");
        }
        if(info3.size()==1000){
            build1000(info3,"./info3_1000.xls");
        }
        if(info3.size()==10000){
            build10000(info3,"./info3_10000.xls");
        }
    }

    private void build100(List list,String name){
        build(list,name,100);
    }
    private void build1000(List list,String name){
        build(list,name,1000);
    }
    private void build10000(List list,String name){
        build(list,name,10000);
    }
    private void build(List list,String name,int count){
        int sum=0;
        File file = new File(name);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
            WritableSheet sheet = writableWorkbook.createSheet("sheet1", 0);
            Label label = new Label(1, 0, "数据");
            sheet.addCell(label);
            label = new Label(0, 0, "id");
            sheet.addCell(label);

            for (int j = 0; j < count; j++) {
                if(!isDouble(list.get(j).toString())){
                    sum++;
                }
                Label data = new Label(0, j + 1, "" + (j + 1));
                sheet.addCell(data);
                data = new Label(1, j + 1, list.get(j).toString());
                sheet.addCell(data);
            }
            label=new Label(4,4,"错误率");
            sheet.addCell(label);
            label=new Label(4,5,""+sum);
            sheet.addCell(label);

            writableWorkbook.write();    //写入数据
            writableWorkbook.close();  //关闭连接
            logger.info("成功写入文件，请前往查看文件！");
        } catch (Exception e) {
            logger.info("文件写入失败，报异常...");
        }
    }
    public static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
