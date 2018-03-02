package com.jkgroup.kingkaid.service.chinapnr.command;

import java.util.Map;

import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import com.jkgroup.kingkaid.utils.pay.PayConstant;

/**
 * vip费、系统内资金划转
 * 处理入口
 *
 * @author pan
 */
public class CommandVIPFee implements HFCommand {

    @Override
    public FormData exec(Map<String, String> resultMap) {
        String listId = resultMap.get("MerPriv");
        String listType = listId.substring(0, 2);
        FormData formData;
        if (listType.equals(PayConstant.LIST_TYPE_VIPFEE)) {
            //会员缴纳VIP费
            formData = FormDataUtil.createInputForm("VipFeeCheck");
            FormDataUtil.setProperty(formData, "respcode", resultMap.get("RespCode"));
            FormDataUtil.setProperty(formData, "respdesc", resultMap.get("RespDesc"));
            FormDataUtil.setProperty(formData, "listid", resultMap.get("MerPriv"));
            formData = ServiceClient.getResponseFormData(formData, "VipFeeCheck");
            String respCode = String.valueOf(FormDataUtil.getProperty(formData, "respcode"));
            if (respCode.equals(FormData.SUCCESS)) {
                User user = Utils.getCurrentUser();
                if (user != null) {
                    user.setVip(true);
                }
            }
        } else {
            //系统内资金划转 分支机构转账UsrAcctPayCheck
            formData = FormDataUtil.createInputForm("UsrAcctPayCheck");
            FormDataUtil.setProperty(formData, "listid", resultMap.get("MerPriv"));
            FormDataUtil.setProperty(formData, "transamt", resultMap.get("TransAmt"));
            FormDataUtil.setProperty(formData, "respcode", resultMap.get("RespCode"));
            FormDataUtil.setProperty(formData, "respdesc", resultMap.get("RespDesc"));
            formData = ServiceClient.getResponseFormData(formData, "UsrAcctPayCheck");
        }

        return formData;
    }

}
