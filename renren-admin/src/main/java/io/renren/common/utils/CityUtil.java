package io.renren.common.utils;

public class CityUtil {

	public static String getCity(String city) {
		switch (city) {
			case "北京市":
				return "北京";
			case "广西省":
				return "广西壮族自治区";
			case "上海市":
				return "上海";
			case "天津市":
				return "天津";
			case "重庆市":
				return "重庆";
			default:
				return city;
			}
	}
}
