package com.javagpt.interviewspider.dto.CountryCareer;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * javagpt
 */
@Data
public class CountryCareerDTO {

	private Long amount;
	private Long applied;
	private Long appliedNum;
	private String applyInstruction;
	private String category;
	private String categoryCn;
	private Long chargeAmount;
	private String companyId;
	private CompanyInfo companyInfo;
	private String companyName;
	private ContactUser contactUser;
	private String contents;
	private Date createTime;
	private Long department;
	private String departmentCn;
	private List<DistrictList> districtList;
	private String education;
	private String educationCn;
	private Date endTime;
	private String experience;
	private String experienceCn;
	private String extend;
	private List<String> industry;
	private List<String> industryCn;
	private Boolean isApply;
	private Boolean isCharge;
	private Boolean isFavorite;
	private Boolean isGraduates;
	private Boolean isNegotiable;
	private String jobId;
	private String jobName;
	private List<String> major;
	private List<String> majorCn;
	private Long maxWage;
	private Long minWage;
	private Long months;
	private String nature;
	private String natureCn;
	private String notes;
	private Long offlineType;
	private String recruitmentType;
	private String recruitmentTypeCn;
	private Date refreshTime;
	private Date startTime;
	private Long status;
	private Long subsite;
	private String template;
	private Date updateTime;
	private String userId;
	private String wageUnit;
	private String wageUnitCn;
}