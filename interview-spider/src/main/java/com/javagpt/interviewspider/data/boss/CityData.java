package com.javagpt.interviewspider.data.boss;

import lombok.Data;

import java.util.List;

/**
 * JavaGPT
 */
@Data
public class CityData {

	/**
	 * 城市编号
	 */
	private Long code;

	/**
	 * 城市名称
	 */
	private String name;

	/**
	 * 访问路径
	 */
	private String url;

	/**
	 * 子城市
	 */
	private List<CityData> subLevelModelList;
}
