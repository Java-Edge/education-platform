package com.javaedge.back.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class RecruitQO implements Serializable {

    /**
     * 招聘所属类型
     */
    private Integer type;

    /**
     * 公司编号
     */
    private Integer companyId;

    /**
     * 招聘类型 校招、实习、社招
     */
    private List<Integer> recruitType;

    /**
     * 工作城市
     */
    private String jobCity;

    /**
     * 职位号
     */
    private Integer careerJobId;

    /**
     * 职位名称
     */
    private String careerJobName;

    /**
     * 毕业年份
     */
    private List<Integer> graduateYear;

    /**
     * 招聘开始时间
     */
    private Date deliverBegin;

    /**
     * 招聘结束时间
     */
    private Date deliverEnd;

    /**
     * 薪资范围
     */
    private List<Integer> salaryRange;

    /**
     * 薪水类型
     */
    private Integer salaryType;

    /**
     * 薪资最小值
     */
    private Integer salaryMin;

    /**
     * 薪资最大值
     */
    private Integer salaryMax;

    /**
     *
     */
    private Integer salaryMonth;

    /**
     * 工作年限
     */
    private Integer workYearType;

    /**
     * 学历要求
     */
    private List<Integer> eduLevel;

    /**
     * 公司规模
     */
    private List<Integer> personScale;

    /**
     * 融资规模
     */
    private List<Integer> scaleTag;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 关键词拆分后的搜索 token（setKeyword 时自动生成）：按空格 + 中英文边界切分，
     * 统一小写去空格。让「ai应用开发」与「ai 应用开发」召回一致，Mapper 按此列表 AND 匹配。
     */
    private List<String> keywordTokens;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        this.keywordTokens = tokenize(keyword);
    }

    private static List<String> tokenize(String keyword) {
        List<String> tokens = new ArrayList<>();
        if (keyword == null || keyword.isBlank()) {
            return tokens;
        }
        for (String seg : keyword.trim().split("\\s+")) {
            for (String part : seg.split("(?<=[0-9a-zA-Z])(?=[\\u4e00-\\u9fa5])|(?<=[\\u4e00-\\u9fa5])(?=[0-9a-zA-Z])")) {
                String token = part.trim().toLowerCase().replace(" ", "");
                if (!token.isEmpty()) {
                    tokens.add(token);
                }
            }
        }
        return tokens;
    }
}