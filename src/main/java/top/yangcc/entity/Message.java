package top.yangcc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息Bean
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message  implements Serializable {
    /** 消息的id */
    private Integer id;
    /** 消息的发送时间 */
    private String sendTime;
    /** 消息的内容 */
    private String content;
    /** 收信人*/
    private User addressee;
    /** 此消息对应的会议*/
    private Meeting meeting;
    /** 是否已读 */
    private boolean read;

}
