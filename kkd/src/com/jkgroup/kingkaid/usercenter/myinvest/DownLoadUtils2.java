package com.jkgroup.kingkaid.usercenter.myinvest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.ehcache.search.aggregator.Sum;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;

public class DownLoadUtils2 {

	public static HSSFWorkbook getrtnPlanHSSFWorkBook(List<FormData> list){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(); 
		{
		 int cellIndex = 0;
		 Row title = sheet.createRow(0);
		 title.createCell(cellIndex++).setCellValue("应收本金");
		 title.createCell(cellIndex++).setCellValue("应收利息");
		 title.createCell(cellIndex++).setCellValue("应收本息");
		 title.createCell(cellIndex++).setCellValue("应收加息收益");
		 title.createCell(cellIndex++).setCellValue("还款期数");
		 title.createCell(cellIndex++).setCellValue("剩余本金");
		 title.createCell(cellIndex++).setCellValue("还款日期");
		}
		
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0;i<list.size();i++){
			HSSFRow row = sheet.createRow((short)i+1);
			 int cellIndex = 0;
			 FormData formdata = list.get(i);
			 
//			 String c = String.valueOf(total + total1);
			for(int j=0;j<=7;j++){
				sheet.setColumnWidth(j, 4000);
				HSSFCell cell = row.createCell((short)j+1);
				switch(j){
					
					case 0 : 
						row.createCell(cellIndex++).setCellValue((String)FormDataUtil.getProperty(formdata, "scapi")); break;
					case 1 : 
						row.createCell(cellIndex++).setCellValue((String)FormDataUtil.getProperty(formdata, "sinte")); break;
					case 2 : 
						row.createCell(cellIndex++).setCellValue((String)FormDataUtil.getProperty(formdata, "summoney")); break;
					case 3 : 
						row.createCell(cellIndex++).setCellValue((String)FormDataUtil.getProperty(formdata, "rinteadd")); break;
					case 4 : 
						row.createCell(cellIndex++).setCellValue((String)FormDataUtil.getProperty(formdata, "sterm")); break;
					case 5 : 
						row.createCell(cellIndex++).setCellValue((String)FormDataUtil.getProperty(formdata, "bal")); break;
 					case 6 :
 						row.createCell(cellIndex++).setCellValue(dd.format((Date)FormDataUtil.getProperty(formdata, "sdate"))); break;
				}
			}
		}
		return workbook;
	}
}

