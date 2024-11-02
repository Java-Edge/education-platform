package com.javagpt.back.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BasActivity implements Serializable {
    /**
     * 活动主键
     */
    private Long id;

    /**
     * 小程序用户唯一id
     */
    private String userId;

    /**
     * 海报列表(多张海报用,隔开)
     */
    private String postersList;

    /**
     * 活动主题
     */
    private String activityTheme;

    /**
     * 活动地址(待定-因为要选择地图上的地址，实际开发确认下)
     */
    private String activityAddress;

    /**
     * 区
     */
    private String district;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 支付类型的活动结束标志 支付类型的  0-默认 1-待结算 2-结算完成
     */
    private Integer isPayEnd;

    /**
     * 0、免费 1、在线支付 2、现场支付
     */
    private Integer costType;

    /**
     * 活动单人应支付金额(type为0的时候值为0.00)
     */
    private BigDecimal activityAmount;

    /**
     * 活动负责人联系方式
     */
    private String contact;

    /**
     * 活动一级分类
     */
    private String primaryClassification;

    /**
     * 活动二级分类
     */
    private String secondaryClassification;

    /**
     * 活动内容
     */
    private String activityContent;

    /**
     * 活动内容附带图片列表(多张用,隔开)
     */
    private String activityContentPicture;

    /**
     * 活动已经报名人数
     */
    private Integer alreadyJoin;

    /**
     * 活动期望报名人数
     */
    private Integer expectNumber;

    /**
     * 活动报名截止时间
     */
    private Date registrationDeadline;

    /**
     * 活动报名所需问题(多个问题需要用,隔开)
     */
    private String registrationReqQuestions;

    /**
     * 1、公开 0、不公开
     */
    private Integer whetherPublic;

    /**
     * 0 不热门 1-热门
     */
    private Integer isHot;

    /**
     * 活动一级分类id
     */
    private Integer primaryId;

    /**
     * 活动二级分类id
     */
    private Integer secondaryId;

    /**
     * 维度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 活动所在城市
     */
    private String activityCity;

    /**
     * 群二维码
     */
    private String erCodeUrl;

    /**
     * 圈子id
     */
    private Long circleId;

    /**
     * 圈子名称
     */
    private String circleName;

    /**
     * 平台
     */
    private Integer platform;

    /**
     * 逻辑删除标识：0-未删除；1-已删除
     */
    private Integer isDeleted;

    /**
     * 创建人工号
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人昵称
     */
    private String createUserName;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 修改人工号
     */
    private String modifyUser;

    /**
     * 修改人昵称
     */
    private String modifyUserName;

    /**
     * 活动编号
     */
    private String activityNo;


    private String location;

    public BasActivity() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private static final long serialVersionUID = 1L;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPostersList() {
        return postersList;
    }

    public void setPostersList(String postersList) {
        this.postersList = postersList == null ? null : postersList.trim();
    }

    public String getActivityTheme() {
        return activityTheme;
    }

    public void setActivityTheme(String activityTheme) {
        this.activityTheme = activityTheme == null ? null : activityTheme.trim();
    }

    public String getActivityAddress() {
        return activityAddress;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress == null ? null : activityAddress.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsPayEnd() {
        return isPayEnd;
    }

    public void setIsPayEnd(Integer isPayEnd) {
        this.isPayEnd = isPayEnd;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    public BigDecimal getActivityAmount() {
        return activityAmount;
    }

    public void setActivityAmount(BigDecimal activityAmount) {
        this.activityAmount = activityAmount;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getPrimaryClassification() {
        return primaryClassification;
    }

    public void setPrimaryClassification(String primaryClassification) {
        this.primaryClassification = primaryClassification == null ? null : primaryClassification.trim();
    }

    public String getSecondaryClassification() {
        return secondaryClassification;
    }

    public void setSecondaryClassification(String secondaryClassification) {
        this.secondaryClassification = secondaryClassification == null ? null : secondaryClassification.trim();
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent == null ? null : activityContent.trim();
    }

    public String getActivityContentPicture() {
        return activityContentPicture;
    }

    public void setActivityContentPicture(String activityContentPicture) {
        this.activityContentPicture = activityContentPicture == null ? null : activityContentPicture.trim();
    }

    public Integer getAlreadyJoin() {
        return alreadyJoin;
    }

    public void setAlreadyJoin(Integer alreadyJoin) {
        this.alreadyJoin = alreadyJoin;
    }

    public Integer getExpectNumber() {
        return expectNumber;
    }

    public void setExpectNumber(Integer expectNumber) {
        this.expectNumber = expectNumber;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(Date registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getRegistrationReqQuestions() {
        return registrationReqQuestions;
    }

    public void setRegistrationReqQuestions(String registrationReqQuestions) {
        this.registrationReqQuestions = registrationReqQuestions == null ? null : registrationReqQuestions.trim();
    }

    public Integer getWhetherPublic() {
        return whetherPublic;
    }

    public void setWhetherPublic(Integer whetherPublic) {
        this.whetherPublic = whetherPublic;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public Integer getSecondaryId() {
        return secondaryId;
    }

    public void setSecondaryId(Integer secondaryId) {
        this.secondaryId = secondaryId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getActivityCity() {
        return activityCity;
    }

    public void setActivityCity(String activityCity) {
        this.activityCity = activityCity == null ? null : activityCity.trim();
    }

    public String getErCodeUrl() {
        return erCodeUrl;
    }

    public void setErCodeUrl(String erCodeUrl) {
        this.erCodeUrl = erCodeUrl == null ? null : erCodeUrl.trim();
    }

    public Long getCircleId() {
        return circleId;
    }

    public void setCircleId(Long circleId) {
        this.circleId = circleId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName == null ? null : circleName.trim();
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

    public String getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo == null ? null : activityNo.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", postersList=").append(postersList);
        sb.append(", activityTheme=").append(activityTheme);
        sb.append(", activityAddress=").append(activityAddress);
        sb.append(", district=").append(district);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", isPayEnd=").append(isPayEnd);
        sb.append(", costType=").append(costType);
        sb.append(", activityAmount=").append(activityAmount);
        sb.append(", contact=").append(contact);
        sb.append(", primaryClassification=").append(primaryClassification);
        sb.append(", secondaryClassification=").append(secondaryClassification);
        sb.append(", activityContent=").append(activityContent);
        sb.append(", activityContentPicture=").append(activityContentPicture);
        sb.append(", alreadyJoin=").append(alreadyJoin);
        sb.append(", expectNumber=").append(expectNumber);
        sb.append(", registrationDeadline=").append(registrationDeadline);
        sb.append(", registrationReqQuestions=").append(registrationReqQuestions);
        sb.append(", whetherPublic=").append(whetherPublic);
        sb.append(", isHot=").append(isHot);
        sb.append(", primaryId=").append(primaryId);
        sb.append(", secondaryId=").append(secondaryId);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", activityCity=").append(activityCity);
        sb.append(", erCodeUrl=").append(erCodeUrl);
        sb.append(", circleId=").append(circleId);
        sb.append(", circleName=").append(circleName);
        sb.append(", platform=").append(platform);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUserName=").append(createUserName);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", modifyUser=").append(modifyUser);
        sb.append(", modifyUserName=").append(modifyUserName);
        sb.append(", activityNo=").append(activityNo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}