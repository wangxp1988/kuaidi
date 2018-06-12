package io.renren.modules.sys.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import io.renren.common.utils.PageUtils;

public interface ExpReceivablesService {

	PageUtils queryPage(Map<String, Object> params);
    //
	PageUtils queryReceivablesPage(Map<String, Object> params);
	//批量导出
	void receivablesExport(HttpServletResponse response,Map<String, Object> params, String diskDirPath);

}
