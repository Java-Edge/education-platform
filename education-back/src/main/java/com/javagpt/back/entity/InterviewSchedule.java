package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author robot
 * @since 2023-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InterviewSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer interviewerId;

    private Integer candidateId;

    private LocalDate interviewDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;


}
