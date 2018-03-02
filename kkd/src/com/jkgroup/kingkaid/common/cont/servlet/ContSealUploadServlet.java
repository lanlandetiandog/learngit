package com.jkgroup.kingkaid.common.cont.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContSealUploadServlet  extends HttpServlet {

    private String ContSealUploadDir = "/file/output/sealpdf"; 	// 上传文件的目录
    private String ContSealUploadTmp = "/tmp/sealpdf"; 			// 临时文件目录
    File ContSealUploadTmpFile;

    private static String ContextFileName = "cfg.properties";
    private static Map<String, String> cfg = null;

	private static final Logger log = LoggerFactory.getLogger(ContSealUploadServlet.class);

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response)
           throws IOException, ServletException {

       try {
    	   String uppath=null;
    	   String upname=null;
    	   String filepathpara = request.getParameter("filepathpara");
    	   if (StringUtils.isNotEmpty(filepathpara)){
    		   if(filepathpara.indexOf("/") != -1)  {
    			   uppath = filepathpara.substring(0,filepathpara.lastIndexOf('/'));
    			   upname = filepathpara.substring(filepathpara.lastIndexOf('/')+1);
    			   log.info("uppath="+uppath+" upname="+upname);
    			   ContSealUploadDir = uppath;
    		   }    		 
    	   }
    	   if (upname == null){
	    	   if (cfg == null || cfg.size()==0){
	    		   try {
	    			   String cfgfile = "/WEB-INF/classes/" + ContextFileName;
	    			   InputStream is = getServletContext().getResourceAsStream(cfgfile);
	    			   Properties pro = new Properties();
	    			   pro.load(is);
	        		   cfg = new HashMap<String, String>();
	        		   
	    			   @SuppressWarnings("rawtypes")
	    			   Iterator it = pro.keySet().iterator();
	    			   while(it.hasNext()){
	    				   String o = (String)it.next();
	    				   cfg.put(o, (String)pro.get(o));
	    			   }
	    			   pro.clear();
	    			   is.close();
	    		   } catch (Exception e) {
	    			   e.printStackTrace();
	    		   }
	    	   }
	    	   if (cfg != null){
	    		   ContSealUploadDir = cfg.get("ContSealUploadDir");
	    		   ContSealUploadTmp = cfg.get("ContSealUploadTmp");
	    	   }		   
    	   }
    	   log.info("ContSealUploadDir:"+ContSealUploadDir);
		   log.info("ContSealUploadTmp:"+ContSealUploadTmp);
   
    	   File uploadFile = new File(ContSealUploadDir);
    	   if (!uploadFile.exists()) {
    		   uploadFile.mkdirs();
    	   }
    	   ContSealUploadTmpFile = new File(ContSealUploadTmp);
    	   if (!ContSealUploadTmpFile.exists()) {
    		   ContSealUploadTmpFile.mkdirs();
    	   }
    	   
           // Create a factory for disk-based file items
           DiskFileItemFactory factory = new DiskFileItemFactory(); 

           // Set factory constraints
           factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
           factory.setRepository(ContSealUploadTmpFile);// 设置缓冲区目录 

           // Create a new file upload handler
           ServletFileUpload upload = new ServletFileUpload(factory); 

           // Set overall request size constraint
           upload.setSizeMax(419430400); // 设置最大文件尺寸，这里是400MB 
           List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
           Iterator<FileItem> i = items.iterator();

           while (i.hasNext()) {
              FileItem fi = (FileItem) i.next();
              String fileName = fi.getName();
              if (fileName != null) {
                  File fullFile = new File(fi.getName());
                  File savedFile = null;                  
                  if (upname != null){
                	  savedFile = new File(ContSealUploadDir, upname);
                  }else{
                	  savedFile = new File(ContSealUploadDir, fullFile.getName());
                  }
                  fi.write(savedFile);
              }
           }
       } catch (Exception e) {
           // 可以跳转出错页面
           e.printStackTrace();
       }
    }

	public void init() throws ServletException {
		log.info("ContSealUploadServlet init...");
    }
}