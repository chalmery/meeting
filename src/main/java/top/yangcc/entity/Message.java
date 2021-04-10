package top.yangcc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 消息Bean
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    /** 消息的id */
    private Integer id;
    /** 消息的发送时间 */
    private Date sendTime;
    /** 消息的内容 */
    private String content;
    /** 收信人的id*/
    private Integer userId;
}
