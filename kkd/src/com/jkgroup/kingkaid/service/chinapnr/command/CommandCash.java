package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;

/**
 * 提现处理
 *
 * @author pan
 */
public class CommandCash implements HFCommand {

    @Override
    public FormData exec(Map<String, String> resultMap) {
        String respType = StringUtils.trimToEmpty(resultMap.get("RespType"));
        FormData formData = new FormData();
        if (!StringUtils.endsWith(respType, "Cash")) {
            formData = FormDataUtil.createInputForm("CashCheck");
            FormDataUtil.setProperty(formData, "listid", resultMap.get("MerPriv"));
            FormDataUtil.setProperty(formData, "otherlistno", resultMap.get("OrdId"));
            FormDataUtil.setProperty(formData, "listtype", PayConstant.LIST_TYPE_DEPOSIT);
            FormDataUtil.setProperty(formData, "chanid", PayConstant.CHAN_CHINAPNR);
            FormDataUtil.setProperty(formData, "busitype", PayConstant.BUSI_TYPE_DEPOSIT);
            FormDataUtil.setProperty(formData, "respcode", resultMap.get("RespCode"));
            FormDataUtil.setProperty(formData, "respdesc", resultMap.get("RespDesc"));
            FormDataUtil.setProperty(formData, "transamt", resultMap.get("TransAmt"));
            FormDataUtil.setProperty(formData, "realtransamt", resultMap.get("RealTransAmt"));
            FormDataUtil.setProperty(formData, "fee", resultMap.get("FeeAmt"));
            FormDataUtil.setProperty(formData, "servfee", resultMap.get("ServFee"));
            FormDataUtil.setProperty(formData, "appopenacctid", resultMap.get("OpenAcctId"));
            FormDataUtil.setProperty(formData, "appopenbankid", resultMap.get("OpenBankId"));
        } else {
            //取现异常 、 异步返回异常报文
            if (respType.equals("400")) {
                //提现失败处理
            }
        }
        return ServiceClient.getResponseFormData(formData, "CashCheck");
    }
}
