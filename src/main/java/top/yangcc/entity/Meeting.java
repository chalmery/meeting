package top.yangcc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 会议的Bean
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {
    /** 会议id */
    private Integer id;
    /** 会议名称 */
    private String name;
    /** 发起人 */
    private String sponsor;
    /** 开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(timezone = "GMT+8",locale = "zh",pattern = "yyyy-MM-dd HH:mm")
    private Date start;
    /** 结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(timezone = "GMT+8",locale = "zh",pattern = "yyyy-MM-dd HH:mm")
    private Date end;
    /** 会议地点 */
    private String location;
    /** 会议室 */
    private String meetingRoom;
    /** 会议简介r */
    private String info;
    /** 要参加的人员 */
    private List<Integer> members;
    /** 操作时间 */
    private  Date operatingTime;
    /** 操作人员 */
    private String operator;
}
