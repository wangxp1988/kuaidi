package io.renren.modules.sys.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.service.ExpReceivablesService;

@RestController
@RequestMapping("sys/receivables")
public class ExpReceivablesController {

	@Value("${filepath.excleSavePath}")
	private String diskDirPath;
	
	@Autowired
	private ExpReceivablesService expReceivablesService;
	/**
	 * 应收账款汇总
	 * @param params
	 * @return
	 */
	@RequestMapping("list")
	@RequiresPermissions("sys:receivables:list")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = expReceivablesService.queryPage(params);
		return R.ok().put("page", page);
	}
	
	@RequestMapping("listexport")
	@RequiresPermissions("sys:receivables:list")
	public void receivablesListExport(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params) {
		params.put("response", response);
		 expReceivablesService.receivablesListExport(params);
	}
	
	
	
	/**
	 * 参数 开始时间，结束时间 ，客户类型  ，结果类型（只查借款，全查）
	 * @param params
	 * @return
	 */
	@RequestMapping("billlist")
	@RequiresPermissions("sys:receivables:billlist")
	public R receivableslist(@RequestParam Map<String, Object> params) {
		PageUtils page = expReceivablesService.queryReceivablesPage(params);
		return R.ok().put("page", page);
	}
	
	/**
	 * 参数 开始时间，结束时间 ，客户类型  ，结果类型（只查借款，全查）
	 * @param params
	 * @return
	 */
	@RequestMapping("export")
	@RequiresPermissions("sys:receivables:export")
	public R receivablesExport(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params) {
		params.put("diskDirPath", diskDirPath);
		String fileName=expReceivablesService.receivablesExport(params);
		return R.ok().put("fileName", fileName);
		
	}
	
	
	@RequestMapping("downzip")
	public void downZip(HttpServletRequest request,HttpServletResponse response,@RequestParam String fileName) {
		 //定义输出流，以便打开保存对话框______________________begin 
		try {
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
		// 取得输出流    
	    response.reset();// 清空输出流    
	    response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1")); 
	  // 设定输出文件头    
	    response.setContentType("application/octet-stream");
	    //response.setContentType("application/msexcel");// 定义输出类型   
	    //定义输出流，以便打开保存对话框_______________________end 
	    File zip =new File(diskDirPath+File.separator+fileName);
	    FileInputStream inStream = new FileInputStream(zip);  
        byte[] buf = new byte[4096];  
        int readLength;  
        while (((readLength = inStream.read(buf)) != -1)) {  
        	os.write(buf, 0, readLength);  
        } 
        inStream.close(); 
        os.flush();  
        os.close(); 
        response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
