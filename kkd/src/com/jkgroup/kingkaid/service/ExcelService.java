package com.jkgroup.kingkaid.service;

import com.jkgroup.kingkaid.bo.MessageWrapper;
import com.jkgroup.kingkaid.bo.formdata.FormData;
import com.jkgroup.kingkaid.utils.DateUtils;
import com.jkgroup.kingkaid.utils.cls.FormDataUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/5/26.
 */
@Service
public class ExcelService {

    private final String[] myProjectTitles = new String[] {"项目名称", "投资金额", "利率", "加息利率", "期限", "已得收益", "剩余期数", "下期还款日", "状态"};
    private final String[] overApprStates = new String[] {"已结清", "调查拒绝", "审批拒绝", "作废", "流标", "会员拒绝", "担保公司拒绝", "会员拒签合同", "担保公司拒签合同"};

    /**
     * 创建我的投资Excel
     * @param mw
     * @return
     */
    public HSSFWorkbook myInvestHSSFWorkBook(MessageWrapper mw){
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        HSSFCellStyle cellStyle = createTitleCellStyle(workbook);
        buildTitleRow(sheet, myProjectTitles, cellStyle);
        List<FormData> dataList = (List<FormData>) mw.getBody();
        if (dataList  == null || dataList.isEmpty()) {
            return workbook;
        }
        String[] labels = (String[]) mw.getAnnex().get("apprstatelabel");
        int rowIndex = 1;
        for(int i = 0; i < dataList.size(); i ++) {
            int cellIndex = 0;
            Row dataRow = sheet.createRow(rowIndex ++);
            FormData fd = dataList.get(i);
            dataRow.createCell(cellIndex ++).setCellValue((String) FormDataUtil.getProperty(fd, "projecttitle"));
            dataRow.createCell(cellIndex ++).setCellValue(Integer.parseInt((String) FormDataUtil.getProperty(fd, "conttotalamt")));
            dataRow.createCell(cellIndex ++).setCellValue(Double.parseDouble((String) FormDataUtil.getProperty(fd, "fee")));
            String add = (String) FormDataUtil.getProperty(fd, "inteaddrate");
            add = StringUtils.isEmpty((String) FormDataUtil.getProperty(fd, "intadd")) ? add : (String) FormDataUtil.getProperty(fd, "intadd");
            add = StringUtils.isEmpty(add) ? "0" : add;
            dataRow.createCell(cellIndex ++).setCellValue(Double.parseDouble(add));
            dataRow.createCell(cellIndex ++).setCellValue(Integer.parseInt((String) FormDataUtil.getProperty(fd, "apptterm")));
            dataRow.createCell(cellIndex ++).setCellValue(Double.parseDouble((String) FormDataUtil.getProperty(fd, "profitamt")));
            String overpluscount = (String) FormDataUtil.getProperty(fd, "overpluscount");
            overpluscount = StringUtils.isEmpty(overpluscount) ? "0" : overpluscount;
            dataRow.createCell(cellIndex ++).setCellValue(Integer.parseInt(overpluscount));
            Date nextRepaDate = (Date) FormDataUtil.getProperty(fd, "nextrepadate");
            String nDateStr = nextRepaDate == null ? "" : DateUtils.getStrFromDate(nextRepaDate);
            if (isOver(labels[i])) {
                nDateStr = "";
            }
            dataRow.createCell(cellIndex ++).setCellValue(nDateStr);
            dataRow.createCell(cellIndex ++).setCellValue(labels[i]);
        }
        autoSizeColumn(sheet, myProjectTitles);
        return workbook;
    }

    /**
     * 创建我的债权认购记录Excel
     * @param mw
     * @return
     */
    public HSSFWorkbook myCtHSSFWorkBook(MessageWrapper mw){
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        HSSFCellStyle cellStyle = createTitleCellStyle(workbook);
        buildTitleRow(sheet, myProjectTitles, cellStyle);
        List<FormData> dataList = (List<FormData>) mw.getBody();
        if (dataList  == null || dataList.isEmpty()) {
            return workbook;
        }
        String[] labels = (String[]) mw.getAnnex().get("apprstatelabel");
        int rowIndex = 1;
        for(int i = 0; i < dataList.size(); i ++) {
            int cellIndex = 0;
            Row dataRow = sheet.createRow(rowIndex ++);
            FormData fd = dataList.get(i);
            dataRow.createCell(cellIndex ++).setCellValue((String) FormDataUtil.getProperty(fd, "projecttitle"));
            dataRow.createCell(cellIndex ++).setCellValue(Integer.parseInt((String) FormDataUtil.getProperty(fd, "transfermoney")));
            dataRow.createCell(cellIndex ++).setCellValue(Double.parseDouble((String) FormDataUtil.getProperty(fd, "interate")));
            String add = (String) FormDataUtil.getProperty(fd, "inteaddrate");
            add = StringUtils.isEmpty((String) FormDataUtil.getProperty(fd, "intadd")) ? add : (String) FormDataUtil.getProperty(fd, "intadd");
            add = StringUtils.isEmpty(add) ? "0" : add;
            dataRow.createCell(cellIndex ++).setCellValue(Double.parseDouble(add));
            dataRow.createCell(cellIndex ++).setCellValue(Integer.parseInt((String) FormDataUtil.getProperty(fd, "tterm")));
            dataRow.createCell(cellIndex ++).setCellValue(Double.parseDouble((String) FormDataUtil.getProperty(fd, "profitamt")));
            String overpluscount = (String) FormDataUtil.getProperty(fd, "overpluscount");
            overpluscount = StringUtils.isEmpty(overpluscount) ? "0" : overpluscount;
            dataRow.createCell(cellIndex ++).setCellValue(Integer.parseInt(overpluscount));
            Date nextRepaDate = (Date) FormDataUtil.getProperty(fd, "nextrepadate");
            String nDateStr = nextRepaDate == null ? "" : DateUtils.getStrFromDate(nextRepaDate);
            if (isOver(labels[i])) {
                nDateStr = "";
            }
            dataRow.createCell(cellIndex ++).setCellValue(nDateStr);
            dataRow.createCell(cellIndex ++).setCellValue(labels[i]);
        }
        autoSizeColumn(sheet, myProjectTitles);
        return workbook;
    }

    /**
     * 创建标题样式
     * @param workbook
     * @return
     */
    private HSSFCellStyle createTitleCellStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 创建标题行
     * @param sheet
     * @param titles
     * @param cellStyle
     */
    private void buildTitleRow(Sheet sheet, String[] titles, HSSFCellStyle cellStyle) {
        Row titleRow = sheet.createRow(0);
        int cellIndex = 0;
        for (String title : titles) {
            Cell cell = titleRow.createCell(cellIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(title);
        }
    }

    /**
     * 设置单元格自动宽度
     * @param sheet
     * @param titles
     */
    private void autoSizeColumn(Sheet sheet, String[] titles) {
        for(int i = 0 ; i < titles.length; i ++) {
            sheet.autoSizeColumn(i);
        }
    }

    private boolean isOver(String apprstate) {
        for (String state : overApprStates) {
            if (state.equals(apprstate)) {
               return true;
            }
        }
        return false;
    }

}
