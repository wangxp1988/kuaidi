package io.renren.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/***
 * @author lsf
 */
public class ExportExcelBatch {
	/***************************************************************************
	 * @param fileName
	 *            EXCEL文件名称
	 * @param listTitle
	 *            EXCEL文件第一行列标题集合
	 * @param list
	 *            EXCEL文件正文数据集合
	 * @return
	 */
	public final static String exportExcel(String fileName, String[] Title,
			List<Map<String, Object>> list, String path,String userName,Map<String, Object> params) {
		String result = "系统提示：Excel文件导出成功！";
		String file = path + File.separator + fileName + ".xls";
		String exportType=params.get("exportType").toString();
		// 以下开始输出到EXCEL
		try {
			/*
			 * // 定义输出流，以便打开保存对话框______________________begin OutputStream os =
			 * response.getOutputStream();// 取得输出流 response.reset();// 清空输出流
			 * response.setHeader("Content-disposition", "attachment; filename=" + new
			 * String(fileName.getBytes("GB2312"), "ISO8859-1")); // 设定输出文件头
			 * response.setContentType("application/msexcel");// 定义输出类型 //
			 * 定义输出流，以便打开保存对话框_______________________end
			 */

			FileOutputStream o = new FileOutputStream(file);

			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(o);

			/** **********创建工作表************ */
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行
			// 用于正文居右
			WritableCellFormat wcf_right = new WritableCellFormat(NormalFont);
			wcf_right.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wcf_right.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_right.setAlignment(Alignment.RIGHT); // 文字水平对齐
			wcf_right.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL开头大标题，暂时省略********************* */
			sheet.mergeCells(1, 0, 7, 0);
			sheet.addCell(new Label(1, 0, "运费明细表", wcf_center));
			/** ***************以下是EXCEL第二行行列标题********************* */
			sheet.addCell(new Label(1, 1, "客户名称:", wcf_right));
			sheet.addCell(new Label(2, 1, userName, wcf_left));
			sheet.addCell(new Label(3, 1, "", wcf_left));
			sheet.addCell(new Label(4, 1, "时段:", wcf_right));
			sheet.addCell(new Label(5, 1,params.get("start_dates")+"至"+params.get("end_dates"), wcf_left));
			
			/** ***************以下是EXCEL第三行列标题********************* */
			for (int i = 0; i < Title.length; i++) {
				sheet.addCell(new Label(i + 1, 2, Title[i], wcf_center));
			}
			/** ***************以下是EXCEL正文数据********************* */
			int i = 3;
			BigDecimal debtorSums=new BigDecimal(0);
			BigDecimal lenderSums=new BigDecimal(0);
			for (Map<String, Object> map : list) {
				
				if (null != map.get("createDate") && "" != map.get("createDate")) {
					sheet.addCell(new Label(1, i, map.get("createDate").toString(), wcf_left));
					sheet.setColumnView(1, 15);
				}
				if (null != map.get("voucherRemark") && "" != map.get("voucherRemark")) {
					sheet.addCell(new Label(2, i, map.get("voucherRemark").toString(), wcf_left));
					sheet.setColumnView(2, 35);
				}
				if (null != map.get("debtorSum") && "" != map.get("debtorSum")) {
					BigDecimal debtorSum=new BigDecimal(map.get("debtorSum").toString());
					debtorSums=debtorSums.add(debtorSum);
					sheet.setColumnView(3, 10);
					sheet.addCell(new Label(3, i, map.get("debtorSum").toString(), wcf_left));
				}
				if(exportType.equals("1")) {
					if (null != map.get("lenderSum") && "" != map.get("lenderSum")) {
						sheet.setColumnView(4, 10);
						BigDecimal lenderSum=new BigDecimal(map.get("lenderSum").toString());
						lenderSums=lenderSums.add(lenderSum);
						sheet.addCell(new Label(4, i, map.get("lenderSum").toString(), wcf_left));
					}
				}else {
					  sheet.addCell(new Label(4, i,"", wcf_left));
				}
				
				if (null != map.get("voucherCode") && "" != map.get("voucherCode")) {
					sheet.setColumnView(5, 25);
					sheet.addCell(new Label(5, i, map.get("voucherCode").toString(), wcf_left));
				}
				i++;
			}
			// 添加小计
			sheet.addCell(new Label(1, i, "", wcf_left));
			sheet.addCell(new Label(2, i, "小计：", wcf_right));
			/*if(exportType.equals("1")) {
			  sheet.addCell(new Label(3, i, initialBalance.toString(), wcf_left));
			}else {
		      sheet.addCell(new Label(3, i, "", wcf_left));
			}*/
			sheet.addCell(new Label(3, i, debtorSums.toString(), wcf_left));
			if(exportType.equals("1")) {
			 sheet.addCell(new Label(4, i, lenderSums.toString(), wcf_left));
			}else {
			 sheet.addCell(new Label(5, i,"", wcf_left));
			}
			/*if(exportType.equals("1")) {
			   sheet.addCell(new Label(6, i, endingBalance.toString(), wcf_left));
			}else {
				sheet.addCell(new Label(6, i,"", wcf_left));
			}*/
			sheet.addCell(new Label(5, i, "", wcf_left));

			i++;
			// 制表
			sheet.addCell(new Label(1, i, "制表：", wcf_right));
			sheet.addCell(new Label(2, i, "老板", wcf_left));
		 
			sheet.addCell(new Label(3, i, " ", wcf_left));
			sheet.addCell(new Label(4, i, "制表日期：", wcf_right));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			sheet.addCell(new Label(5, i, sdf.format(new Date()), wcf_left));

			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();
			o.close();

		} catch (Exception e) {
			result = "系统提示：Excel文件导出失败，原因：" + e.toString();
			System.out.println(result);
			e.printStackTrace();
		} 
		return file;
	}

	// 压缩文件
	public static void ZipFiles(java.io.File[] srcfile, java.io.File zipfile) {
		byte[] buf = new byte[1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
			for (int i = 0; i < srcfile.length; i++) {
				FileInputStream in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}