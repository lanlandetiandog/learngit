/*
 *	@(#)ServiceEntryInsert.java
 *
 *	BeiMing Software Co.,Ltd
 *
 *	Copyright (c) 2011 BMSOFT. All Rights Reserved
 *
 *	http://www.bmsoft.com.cn
 *
 */

package com.jkgroup.kingkaid.web.usercenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jkgroup.kingkaid.Constants;
import com.jkgroup.kingkaid.bo.User;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.common.spring.PostInitializerRunner;
import com.jkgroup.kingkaid.service.ServiceClient;
import com.jkgroup.kingkaid.utils.Utils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;


/**
 *	
 *	@Title: ActivityControler.java
 *	@Description: 合同
 *	@Company : 北明软件有限公司
 *	@author zhengchengzhou@bmsoft.com.cn
 *	@version : 1.0, 2015-8-5 下午12:24:06
 */

@Controller
@RequestMapping(value=Constants.AUTH+"/cont")
public class ContControler {
	
	private static Logger logger = LoggerFactory.getLogger(PostInitializerRunner.class);
	
	@RequestMapping(value="continfo_page.html")
	public String contPage(Model model,String loanid){
		model.addAttribute("loanid", loanid);
		return "usercenter/continfo";
	}
	@RequestMapping(value="continfoconfirm_page.html")
	public String contConfirmPage(Model model,HttpServletRequest request){
		model.addAttribute("loanid", request.getParameter("loanidcont"));
		model.addAttribute("apprstate", request.getParameter("apprstate"));
		model.addAttribute("dealtype", request.getParameter("dealtype"));
		model.addAttribute("prodid", request.getParameter("prodid"));
		return "usercenter/continfoconfirm";
	}
	
	@RequestMapping(value="continfo")
	@ResponseBody
	public String getContinfo(String cont_type,String loanid){
		String json = "{}";
		
		FormData formData = FormDataUtil.createInputForm("ContInfoSelect");
		FormDataUtil.setProperty(formData,"cont_type", cont_type);
		FormDataUtil.setProperty(formData,"loanid", loanid);
		FormDataUtil.setProperty(formData, "custid", Utils.getCurrentUser().getCustId());
		FormData outData = ServiceClient.getResponseFormData(formData, "ContInfoSelect");
		if(FormDataUtil.isSucceed(outData)){
			String continfo = (String) FormDataUtil.getProperty(outData, "continfo");
			try {
				continfo = new String(Base64.decodeBase64(continfo), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// 不可能的错误
			}
			FormDataUtil.setProperty(outData, "continfo", continfo);
			json = ServiceClient.parseObjToJson(outData);
		}else{
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData)) ;
		}
		return json;
	}
	/**
	 * 借款人信息确认的合同展示
	 * @param cont_type
	 * @param loanid
	 * @return
	 */
	@RequestMapping(value="continfoconfirm", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getContinfoConfirm(String risk_type,String cont_type,String proxy_type,String loanid){
		String json = "{}";
		
		FormData formData = FormDataUtil.createInputForm("ContInfoConfirm");
		FormDataUtil.setProperty(formData,"risk_type", risk_type);
		FormDataUtil.setProperty(formData,"cont_type", cont_type);
		FormDataUtil.setProperty(formData,"proxy_type", proxy_type);
		FormDataUtil.setProperty(formData,"loanid", loanid);
		FormDataUtil.setProperty(formData,"custid", Utils.getCurrentUser().getCustId());
		FormData outData = ServiceClient.getResponseFormData(formData, "ContInfoConfirm");
		if(FormDataUtil.isSucceed(outData)){
			String riskinfo = (String) FormDataUtil.getProperty(outData, "riskinfo");
			String loancontinfo = (String) FormDataUtil.getProperty(outData, "loancontinfo");
			String proxycontinfo = (String) FormDataUtil.getProperty(outData, "proxycontinfo");
			try {
				riskinfo = new String(Base64.decodeBase64(riskinfo), "UTF-8");
				loancontinfo = new String(Base64.decodeBase64(loancontinfo), "UTF-8");
				proxycontinfo = new String(Base64.decodeBase64(proxycontinfo), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// 不可能的错误
			}
			FormDataUtil.setProperty(outData, "riskinfo", riskinfo);
			FormDataUtil.setProperty(outData, "loancontinfo", loancontinfo);
			FormDataUtil.setProperty(outData, "proxycontinfo", proxycontinfo);
			
			json = ServiceClient.parseObjToJson(outData);
		}else{
			return ServiceClient.parseObjToJson(FormDataUtil.buildFailedMsgWrapper(outData)) ;
		}
		return json;
	}
	
	/**
	 * 合同下载
	 */
	@RequestMapping(value="download")
	public void download(HttpServletRequest request,HttpServletResponse response){
		InputStream in = null;
		OutputStream os = null;
		try {
			String filePath = (String) request.getParameter("filePath");
			String fileName = (String) request.getParameter("fileName");
		    FileSystemManager fsManager = VFS.getManager();
		    FileObject fileObject = fsManager.resolveFile(filePath + "/" + fileName);
		    if(!fileObject.exists()){
				response.setContentType("text/html;charset=utf-8");
				PrintWriter pw = response.getWriter();
				pw.println("<html>");
				pw.println("<head>File not found</head>");
				pw.println("<script type='text/javascript'>");
				pw.println("alert('文件不存在')");
				pw.println("</script>");
				pw.println("</html>");
				pw.close();	
				return;
			}
		    in = fileObject.getContent().getInputStream();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			os = response.getOutputStream();
			IOUtils.copy(in, os);
			os.flush();
		} catch (FileSystemException e) {	
	      e.printStackTrace();
	    } catch (RemoteException e) {
			logger.error(" RemoteException ...",e);
		} catch (IOException e) {
			logger.error(" IOException ...",e);
		}catch (Exception e) {
			logger.error(" Exception ...",e);
		}finally{
			if(os != null ){
				try {
					os.close();
				} catch (IOException e) {
				}
			}
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		
	}
	
	@RequestMapping(value="contseal")
	public String contSeal(Model model,HttpServletRequest request, SitePreference sitePreference){
		String loanid = request.getParameter("loanid");		//业务编号
		String flag = request.getParameter("flag");			//意见， 1-同意，0-拒绝
		String dealtype = request.getParameter("dealtype");	//处理类型，1：借款人合同签订，2：担保公司合同签订
		model.addAttribute("loanid", loanid);
		model.addAttribute("flag", flag);
		model.addAttribute("dealtype", dealtype);

		//调用后台交易获取PDF签章控件下载地址
		FormData formData1 = FormDataUtil.createInputForm("GetControlDownLoadURL");
		FormDataUtil.setProperty(formData1,"ctltype", "1"); //下载控件类型，1：合同PDF签章控件
		FormData outData1 = ServiceClient.getResponseFormData(formData1, "GetControlDownLoadURL");
		if(FormDataUtil.isSucceed(outData1)){
			String ctlurl = (String)FormDataUtil.getProperty(outData1, "ctlurl");
			model.addAttribute("ctlurl", ctlurl);
			logger.info("---------------ctlurl签章控件下载地址："+ctlurl+"--------------------");
		}
		
		//调用后台交易获取PDF合同下载地址及保存上传地址
		FormData formData2 = FormDataUtil.createInputForm("GetPdfContractURL");
		FormDataUtil.setProperty(formData2,"loanid", loanid);
		FormData outData2 = ServiceClient.getResponseFormData(formData2, "GetPdfContractURL");
		if(FormDataUtil.isSucceed(outData2)){
			String filepath = (String)FormDataUtil.getProperty(outData2, "filepath");
			String filesavepath = (String)FormDataUtil.getProperty(outData2, "filesavepath");
			String fileserver = (String)FormDataUtil.getProperty(outData2, "fileserver");
			String fileuploadservlet = (String)FormDataUtil.getProperty(outData2, "fileuploadservlet");
			if (filepath != null && filepath.startsWith("/file")){
				filepath = filepath.substring(5);
			}			
			model.addAttribute("downloadurl", fileserver+filepath);
			model.addAttribute("uploadurl", fileuploadservlet);
			model.addAttribute("filepathpara", filesavepath);
			logger.info("---------------downloadurl合同文件下载地址："+fileserver+filepath+"--------------------");
			logger.info("---------------uploadurl合同文件上传servlet地址："+fileuploadservlet+"--------------------");
			logger.info("---------------filepathpara合同文件上传保存地址："+filesavepath+"--------------------");
		}	
		
		//借款人合同签订时需要到服务器调取印模（印模图片的base64编码字符串）
		if ("1".equals(dealtype)){	//处理类型，1：借款人合同签订，2：担保公司合同签订
			User user = Utils.getCurrentUser();
			FormData formData3 = FormDataUtil.createInputForm("GetBase64Seal");
			FormDataUtil.setProperty(formData3,"custid", user.getCustId());
			FormData outData3 = ServiceClient.getResponseFormData(formData3, "GetBase64Seal");
			if(FormDataUtil.isSucceed(outData3)){
				String sealbase64 = (String)FormDataUtil.getProperty(outData3, "imageBase64");
				model.addAttribute("sealbase64", sealbase64);
			}	
		}
		return "usercenter/contseal";
	}
}
