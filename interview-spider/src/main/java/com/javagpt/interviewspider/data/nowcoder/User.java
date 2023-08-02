package com.javagpt.interviewspider.data.nowcoder;

import lombok.Data;

import java.util.List;

/**
 * JavaGPT
 */
@Data
public class User {

    private String gender;
    private Long id;
    private List<Identity> identity;
    private Object introduction;
    private String largeHeaderUrl;
    private Long loginTime;
    private String mainHeaderUrl;
    private Object nickname;
    private Long status;
    private String tinnyHeaderUrl;


}
