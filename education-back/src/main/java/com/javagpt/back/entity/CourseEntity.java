package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@TableName(value ="source_course")
@Data
public class CourseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String image;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private BigDecimal price;

    /**
     * 
     */
    private Date creatTime;

    /**
     * 
     */
    private String creator;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String updater;

    /**
     * 
     */
    private String remark;

    /**
     * 
     */
    private String sourceUrl;
    private Integer step;
    private Integer parentId;
    private Integer courseCatId;
    private Integer pageView;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 0: 视频  1:专栏
     */
    private Integer type;



    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CourseEntity other = (CourseEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getImage() == null ? other.getImage() == null : this.getImage().equals(other.getImage()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getCreatTime() == null ? other.getCreatTime() == null : this.getCreatTime().equals(other.getCreatTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdater() == null ? other.getUpdater() == null : this.getUpdater().equals(other.getUpdater()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getStep() == null ? other.getStep() == null : this.getStep().equals(other.getStep()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getPageView() == null ? other.getPageView() == null : this.getPageView().equals(other.getPageView()))
            && (this.getCourseCatId() == null ? other.getCourseCatId() == null : this.getCourseCatId().equals(other.getCourseCatId()))
            && (this.getSourceUrl() == null ? other.getSourceUrl() == null : this.getSourceUrl().equals(other.getSourceUrl()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getImage() == null) ? 0 : getImage().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getCreatTime() == null) ? 0 : getCreatTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdater() == null) ? 0 : getUpdater().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getStep() == null) ? 0 : getStep().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getCourseCatId() == null) ? 0 : getCourseCatId().hashCode());
        result = prime * result + ((getPageView() == null) ? 0 : getPageView().hashCode());
        result = prime * result + ((getSourceUrl() == null) ? 0 : getSourceUrl().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", image=").append(image);
        sb.append(", description=").append(description);
        sb.append(", price=").append(price);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", creator=").append(creator);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updater=").append(updater);
        sb.append(", remark=").append(remark);
        sb.append(", step=").append(step);
        sb.append(", parentId=").append(parentId);
        sb.append(", courseCatId=").append(courseCatId);
        sb.append(", sourceUrl=").append(sourceUrl);
        sb.append(", pageView=").append(pageView);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}