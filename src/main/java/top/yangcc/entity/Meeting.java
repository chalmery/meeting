package top.yangcc.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 会议的Bean
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting  implements Serializable {
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
    private  String operatingTime;
    /** 操作人员 */
    private User operator;
    /** 发起人 */
    private User sponsor;
    /** 发起时间*/
    private  String sponsorTime;
    /** 审核状态 未审核 通过 未通过 */
    private String approvalStatus;
    /**会议室是否冲突*/
    private boolean conflictMeetingRoom;
    /**用户是否冲突*/
    private boolean conflictUser;
}
