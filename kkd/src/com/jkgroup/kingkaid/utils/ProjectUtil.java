package com.jkgroup.kingkaid.utils;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

import java.util.Date;
import java.util.List;

public class ProjectUtil {

    public static List<FormData> projectsAddSurplusSecond(List<FormData> projects) {
        Date workDate = getWorkDate();
        for(FormData invest : projects)
            if("14".equals(FormDataUtil.getProperty(invest, "apprstate")))
                FormDataUtil.setProperty(invest, "surplussecond", getSurplusSecond(invest, workDate));
        return projects;
    }

    public static FormData projectAddSurplusSecond(FormData project) {
        Date workDate = getWorkDate();
        if("14".equals(FormDataUtil.getProperty(project, "apprstate")))
            FormDataUtil.setProperty(project, "surplussecond", getSurplusSecond(project, workDate));
        return project;
    }

    private static Date getWorkDate() {
        FormData getworkdate = FormDataUtil.createInputForm("WebGetWorkDate");
        FormData output = ServiceClient.getResponseFormData(getworkdate, "WebGetWorkDate");
        return (Date) FormDataUtil.getProperty(output, "workdate");
    }

    private static Long getSurplusSecond(FormData project, Date workDate) {
        long surplusSecond = 0;
        Object pubBidDate = FormDataUtil.getProperty(project, "pubbiddate");
        if(pubBidDate != null)
            surplusSecond = (((Date)pubBidDate).getTime() - workDate.getTime()) / 1000;
        return surplusSecond;
    }
}
