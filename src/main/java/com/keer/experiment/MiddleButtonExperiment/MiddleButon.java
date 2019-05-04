package com.keer.experiment.MiddleButtonExperiment;

import com.keer.experiment.BDQL.BDQLUtil;
import com.keer.experiment.BigchainDB.BigchainDBUtil;
import com.keer.experiment.Contract.PIG.Pig;
import com.keer.experiment.Util.ContractUtil;
import com.keer.experiment.domain.BDQL.Table;
import com.keer.experiment.domain.ParserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.tuples.generated.Tuple6;

import java.math.BigInteger;
import java.util.Map;

@Component
public class MiddleButon {

    @Autowired
    ContractUtil contractUtil;
    @Autowired
    BDQLUtil bdqlUtil;

    public boolean work(String ID) throws Exception {
        Pig pig = contractUtil.PigLoad();
        ParserResult parserResult = bdqlUtil.work("select * from pigInfo where tokenId =" + ID);
        Table table = (Table) parserResult.getData();
        Map map =  table.getData().get(0);
        String earId = map.get("earId").toString();
        String TXID=map.get("TXID").toString();


        Tuple6<String, BigInteger, String, BigInteger, BigInteger, BigInteger> tuple6= pig.getPig(new BigInteger(ID)).send();
        String ETH_status=tuple6.getValue5().toString();

        ParserResult result=bdqlUtil.work("select * from pigStatus where earId="+earId);

        table = (Table) result.getData();
        int BD_status=0;
        for(Map map1:table.getData()){
             if(Integer.parseInt(map1.get("statu").toString())>BD_status){
                 BD_status=Integer.parseInt(map1.get("statu").toString());
             }
        }
        if((BD_status+"").equals(ETH_status)){
            return true;
        }else {
            return changeStatus(earId,ID,ETH_status,TXID);

        }
    }

    /**
     * 改变合约状态
     * @param earID
     * @param tokeID
     * @param EthStatus
     * @param asserID
     * @return
     * @throws InterruptedException
     */
    private boolean changeStatus(String earID,String tokeID,String EthStatus,String asserID) throws InterruptedException {
        ParserResult parserResult=bdqlUtil.work("update pigStatus set earID="+earID+",tokenId="+tokeID+",statu="+EthStatus+"where ID='"+asserID+"'");
        String TXID= (String) parserResult.getData();
        Thread.sleep(2000);
        if(BigchainDBUtil.checkTransactionExit(TXID)){
            return true;
        }else{
            return false;
        }
    }
}
