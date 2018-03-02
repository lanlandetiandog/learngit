package com.jkgroup.kingkaid.web.index;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jkgroup.kingkaid.utils.ProjectUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.jkd.seo.entity.DataResource;
import com.jkgroup.jkd.seo.service.SeoService;
import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.service.MessageHelpService;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

/**
 * @author pan
 * @CreateDate 2015-03-23
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private MessageHelpService mhs;

    @Autowired
    private SeoService seoService;

    @RequestMapping(value = {"index.html", "index"})
    public String oldIndex() {
        return "redirect:/";
    }

    @RequestMapping("/")
    public String toIndex(Model model, HttpServletRequest request) {

        String uri = request.getRequestURI();
        System.out.println("uri :" + request.getPathInfo());

        /**
         * 首页焦点图
         */

        FormData IndeximageList = FormDataUtil.createInputForm("IndeximageList");
        FormDataUtil.setProperty(IndeximageList, "channel", "3");
        List<FormData> indeximagelistinfo = ServiceClient.getResponseFormDataList(IndeximageList, "IndeximageList");
        model.addAttribute("indeximagelistinfo", indeximagelistinfo);
        /**
         * 注册人数统计 累计成交额  累计还款本息
         */
        FormData formDatacont = FormDataUtil.createInputForm("StaticNum");
        FormData outData = ServiceClient.getResponseFormData(formDatacont, "StaticNum");
        model.addAttribute("outData", outData);
        /**
         * 平台公告
         */
        FormData formData = FormDataUtil.createInputForm("PlatnewsList");
        FormDataUtil.setProperty(formData, "channel", "3");
        List<FormData> formdatainfo = ServiceClient.getResponseFormDataList(formData, "PlatnewsList");
        model.addAttribute("formdatainfo", formdatainfo);

        /**
         * 新闻资讯
         */
        FormData medialist = FormDataUtil.createInputForm("MediaList");
        List<FormData> medialistinfo = ServiceClient.getResponseFormDataList(medialist, "MediaList");
        model.addAttribute("medialistinfo", medialistinfo);

        /**
         * 平台活动
         */
        FormData activelist = FormDataUtil.createInputForm("ActiveList");
        FormDataUtil.setProperty(activelist, "channel", "3");
        List<FormData> activelistinfo = ServiceClient.getResponseFormDataList(activelist, "ActiveList");
        model.addAttribute("activelistinfo", activelistinfo);

        /**
         * 合作伙伴
         */
        FormData TeamworkList = FormDataUtil.createInputForm("TeamworkList");
        List<FormData> teamworklistinfo = ServiceClient.getResponseFormDataList(TeamworkList, "TeamworkList");
        model.addAttribute("teamworklistinfo", teamworklistinfo);

        return "index";

    }

    //获取投资列表
    @RequestMapping(value = "maininvest")
    @ResponseBody
    public String getMaininvest(HttpServletRequest request) {
        String prodid = request.getParameter("id");
        FormData fdw = FormDataUtil.createInputForm("ProjectList2");
        if(StringUtils.isNotEmpty(prodid))
            FormDataUtil.setProperty(fdw, "prodid", prodid) ;
        List<FormData> outDataList = ServiceClient.getResponseFormDataList(fdw, "ProjectList2");
        for (FormData f : outDataList) {
            FormDataUtil.print(f);
        }
        ProjectUtil.projectsAddSurplusSecond(outDataList);
        MessageWrapper mw = mhs.buildMessageWrapperWithOption(outDataList, "apprstate");
        return ServiceClient.parseObjToJson(mw);
    }

    //百度统计 start
    @RequestMapping(value = "getallurl")
    @ResponseBody
    public String getallurl(HttpServletRequest request) {
        String allurl = request.getParameter("allurl");
        String ip = Utils.getIpAddr(request);
        logger.debug("ip地址是：{}", ip);
        String websource = null;
        String keywords = null;
        String adcompany = null;
        String signtype = null;
        String keyterm = null;
        String spreadplanname = null;
        String id = null;
        List<String> list = new ArrayList<String>();


        if (allurl.contains("?")) {
            String counturl = allurl.substring(allurl.indexOf("?") + 1, allurl.length());
            Session session = SecurityUtils.getSubject().getSession();
            id = request.getSession().getId();
            if (counturl != null && counturl.length() != 0) {

                StringBuffer sb = new StringBuffer();
                sb = sb.append(counturl).append("&sessionid=" + id);

                session.setAttribute("counturl", sb);
                logger.debug("counturl后缀是：{}", sb);
                try {
                    String sq = URLDecoder.decode(counturl, "utf-8");
                    String[] array = sq.split("&");

                    for (String ss : array) {
                        String s0 = ss.substring(ss.indexOf("=") + 1, ss.length());
                        list.add(s0);
                    }
                } catch (UnsupportedEncodingException e) {
                    counturl = "";
                }

                signtype = "spread";
                websource = list.get(0);
                adcompany = list.get(1);
                keywords = list.get(2);
                keyterm = list.get(3);
                spreadplanname = list.get(4);

                try {
                    logger.debug("sessionid是:-----{}", id);
                    if ((seoService.findDataById(id)) != null) {
                        logger.debug("dataResource:-----{}已存在！", seoService.findDataById(id));
                    } else {
                        DataResource ndataResource = new DataResource();
                        ndataResource.setDataId(id);
                        ndataResource.setAdCompany(adcompany);
                        ndataResource.setIpAddr(ip);
                        ndataResource.setKeyTerm(keyterm);
                        ndataResource.setKeyWord(keywords);
                        ndataResource.setSpreadPlanName(spreadplanname);
                        ndataResource.setSignType(signtype);
                        ndataResource.setWebSource(websource);
                        ndataResource.setSignDate(new Date());
                        ndataResource.setSignChannel(null);
                        ndataResource.setMemberId(null);

                        seoService.saveData(ndataResource);
                    }

                } catch (Exception e) {
                    logger.debug("Exception是：{}", e);
                }
            }
        }
        return null;
    }

    //616活动接口
    @RequestMapping(value = "getStaticNum")
    @ResponseBody
    public String getStaticNum(HttpServletResponse response, HttpServletRequest request) {
        String json = "{}";
        String flag = "0";

        Cookie cookie = getcookie(request, "lastLogin");
        if (cookie != null) {
            flag = "1";
        } else {
            addCookie(response);
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("flag", flag);
        json = ServiceClient.parseObjToJson(map);

        return json;
    }

    //设置cookie存储并标记
    public void addCookie(HttpServletResponse response) {
        Date now = new Date();
        Cookie cookie = new Cookie("lastLogin", now.toString());

        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23点59分59秒的毫秒数
        int times = (int) ((twelve - current) / 1000);//当前时间至今天23点59分59秒的秒数差


        cookie.setMaxAge(times);
        //cookie.setPath("/");设置所有页面都立刻访问的路径
        response.addCookie(cookie);
    }

    //获取设置的cookie,以map键值对的方式。
    public Cookie getcookie(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    public Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {

        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }

        return cookieMap;
    }
}

	