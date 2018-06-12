package io.renren.modules.sys.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.ExportExcelBatch;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.ExpCustomerDao;
import io.renren.modules.sys.dao.ExpVoucherDao;
import io.renren.modules.sys.entity.ExpCustomerEntity;
import io.renren.modules.sys.entity.ExpVoucherEntity;
import io.renren.modules.sys.service.ExpCustomerService;
import io.renren.modules.sys.service.ExpReceivablesService;

@Service("expReceivablesService")
public class ExpReceivablesServiceImpl extends ServiceImpl<ExpVoucherDao, ExpVoucherEntity> implements ExpReceivablesService  {

	@Autowired
	private ExpVoucherDao expVoucherDao;
	@Autowired
	private ExpCustomerService expCustomerService;
	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtils queryPage(Map<String, Object> params) {
			 int count=this.expVoucherDao.selectReceivablesCount(params);
			 Query query=new Query<Map<String, Object>>(params);
			 params.put("currPage", (query.getCurrPage()-1)*query.getLimit());
			 params.put("limit", query.getLimit());
			 List<Map<String, Object>> list=this.expVoucherDao.selectReceivables(params);
		     return  new PageUtils(list, count, query.getLimit(), query.getCurrPage());
	}
	@Override
	/**
	 * 账单分页
	 */
	public PageUtils queryReceivablesPage(Map<String, Object> params) {
		 int count=this.expVoucherDao.selectReceivablesByCodesCount(params);
		 Query query=new Query<Map<String, Object>>(params);
		 params.put("currPage", (query.getCurrPage()-1)*query.getLimit());
		 params.put("limit", query.getLimit());
		 List<Map<String, Object>> list=this.expVoucherDao.selectReceivablesByCodes(params);
	     return  new PageUtils(list, count, query.getLimit(), query.getCurrPage());
	}
	@Override
	/**
	 * 批量导出
	 */
	public void receivablesExport(HttpServletResponse response,Map<String, Object> params, String diskDirPath) {
		 List<ExpCustomerEntity> customerCode= expCustomerService.selectCustomerByType(params);
		 String[] Title={"日期","凭证摘要","初期余额","借方金额","贷方金额","期末余额","凭证号码"};
		 File zip = new File(diskDirPath+ File.separator +"("+params.get("start_dates")+"-"+params.get("start_dates")+")运费表"+ ".zip");// 压缩文件  
		 List<String> listFile=new ArrayList<String>();
		 customerCode.forEach(item->{
			 String fileName=item.getName()+"应付运费"+"("+params.get("start_dates")+"-"+params.get("start_dates")+")运费表";
			 params.put("customerCode", item.getCode());
			 List<Map<String, Object>> list=expVoucherDao.selectReceivablesByCode(params);
			 ExportExcelBatch.exportExcel(response, fileName, Title, list, diskDirPath);
			 listFile.add(fileName);
		 });
		
		 File srcfile[] = new File[listFile.size()];  
	        for (int i = 0, n1 = listFile.size(); i < n1; i++) {  
	            srcfile[i] = new File(listFile.get(i));  
	        }  
	    ExportExcelBatch.ZipFiles(srcfile, zip); 
		
	}

}
