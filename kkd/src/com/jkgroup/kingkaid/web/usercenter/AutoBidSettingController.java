package com.jkgroup.kingkaid.web.usercenter;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.FilterExt;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjing@kingkaid.com on 2016/7/28.
 */
@Controller
@RequestMapping(value = Constants.AUTH + "/usercenter")
public class AutoBidSettingController {

    private Map<String, String> termMap;

    public AutoBidSettingController() {
        this.termMap = new HashMap<String, String>();
        termMap.put("3", "1,2,3");
        termMap.put("6", "4,5,6");
        termMap.put("9", "7,8,9");
        termMap.put("12", "10,11,12");
    }

    @RequestMapping("/myautobid.html")
    public String autoBidSettingPage(Model model) {
        FormData productInput = FormDataUtil.createInputForm("ParaProdKind");
        List<FormData> productOutData = ServiceClient.getResponseFormDataList(productInput, "ParaProdKind");
        FormData bankInput = FormDataUtil.createInputForm("ParaBank");
        List<FormData> bankOutData = ServiceClient.getResponseFormDataList(bankInput, "ParaBank");
        FormData settingInput = FormDataUtil.createInputForm("AutoBidSetSelect");
        String custId = Utils.getCurrentUser().getCustId();
        FormDataUtil.setProperty(settingInput, "custid", custId);
        FormData settingOutput = ServiceClient.getResponseFormData(settingInput, "AutoBidSetSelect");
        FormData rankOutput = null;
        if ("1".equals(FormDataUtil.getProperty(settingOutput, "ifon"))) {
            FormData rankInput = FormDataUtil.createInputForm("AutoBidRank");
            FormDataUtil.setProperty(rankInput, "custid", custId);
            rankOutput = ServiceClient.getResponseFormData(rankInput, "AutoBidRank");
        }
        model.addAttribute("products", productOutData);
        model.addAttribute("banks", bankOutData);
        model.addAttribute("setting", settingOutput);
        model.addAttribute("rank", rankOutput);
        return "usercenter/myautobid";
    }

    @RequestMapping("/autobid/saveSetting")
    @ResponseBody
    public String saveSetting(HttpServletRequest request) {
        List<FilterExt> list = FilterExt.buildFromHttpRequest(request);
        FormData saveInput = FormDataUtil.buildFormDataByFilterExt(list, "AutoBidSetUpdate");
        String termSet = (String) FormDataUtil.getProperty(saveInput, "termset");
        FormDataUtil.setProperty(saveInput, "termset", turnTermSet(termSet));
        FormDataUtil.setProperty(saveInput, "custid", Utils.getCurrentUser().getCustId());
        FormData saveOutput = ServiceClient.getResponseFormData(saveInput, "AutoBidSetUpdate");
        MessageWrapper mw = new MessageWrapper(true);
        if (!FormDataUtil.isSucceed(saveOutput)) {
            mw = FormDataUtil.buildFailedMsgWrapper(saveOutput);
        }
        return ServiceClient.parseObjToJson(mw);
    }

    @RequestMapping("/autobid/toggle")
    @ResponseBody
    public String toggleSetting(String ifOn, String ifThirdOn) {
        FormData toggleInput = FormDataUtil.createInputForm("AutoBidSetToggle");
        FormDataUtil.setProperty(toggleInput, "custid", Utils.getCurrentUser().getCustId());
        FormDataUtil.setProperty(toggleInput, "ifon", ifOn);
        FormDataUtil.setProperty(toggleInput, "ifthirdon", ifThirdOn);
        FormData toggleOutput = ServiceClient.getResponseFormData(toggleInput, "AutoBidSetToggle");
        MessageWrapper mw = new MessageWrapper(true);
        if (!FormDataUtil.isSucceed(toggleOutput)) {
            mw = FormDataUtil.buildFailedMsgWrapper(toggleOutput);
        }
        return ServiceClient.parseObjToJson(mw);
    }

    private String turnTermSet(String termSet) {
        if (termSet == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String[] terms = termSet.split(",");
        for (String term : terms) {
            sb.append(termMap.get(term)).append(",");
        }
        if (sb.length() >= 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
