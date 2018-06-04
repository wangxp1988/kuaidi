package io.renren.modules.sys.service;

import java.util.List;
import java.util.Map;

import io.renren.common.utils.R;

public interface ExpDataProcessingService {

	R doSomething(Map<String, Object> params);

	List<Object> getDateList(Map<String, Object> params);

}
