package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.HashMap;
import java.util.Map;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.pay.PayServiceClient;
import com.jkgroup.kingkaid.utils.MathUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;

/**
 * 
 * @author pan
 *
 */
public class CommandNetSave implements HFCommand {

	@Override
	public FormData exec(Map<String, String> resultMap) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("respcode", resultMap.get("RespCode"));
		map.put("respdesc", resultMap.get("RespDesc"));
		map.put("chanid", PayConstant.CHAN_CHINAPNR);
		map.put("otherlistno", resultMap.get("OrdId"));
		map.put("tradeno", resultMap.get("TrxId"));
		map.put("listid", resultMap.get("MerPriv"));
		map.put("rechargeamt", MathUtil.numFmt(Double.parseDouble(resultMap.get("TransAmt"))));
		map.put("cardid", resultMap.get("CardId"));
		map.put("gatebusiid", resultMap.get("GateBusiId"));
		map.put("GateBankId", resultMap.get("MerPriv"));
		map.put("FeeAmt", resultMap.get("FeeAmt"));
		return PayServiceClient.depositCheck(map);
	}

}
