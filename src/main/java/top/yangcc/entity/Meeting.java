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
    /** 开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date start;
    /** 结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date end;
    /** 会议简介 */
    private String info;
    /** 会议状态 未进行，正在进行，已经结束 */
    private String status;
    /** 要参加的人员 */
    private List<User> members;
    /** 会议室 */
    private MeetingRoom meetingRoom;
    /** 操作时间 */
    private  Date operatingTime;
    /** 操作人员 */
    private String operator;
    /** 发起人 */
    private String sponsor;
    /** 审核状态 未审核 通过 未通过 */
    private String approvalStatus;
}
