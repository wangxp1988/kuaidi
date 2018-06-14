package io.renren.modules.sys.service;

import java.util.Map;


import io.renren.common.utils.PageUtils;

public interface ExpReceivablesService {

	PageUtils queryPage(Map<String, Object> params);
    //
	PageUtils queryReceivablesPage(Map<String, Object> params);
	//批量导出
	String receivablesExport(Map<String, Object> params);
	void expotslist(Map<String, Object> params);

}
