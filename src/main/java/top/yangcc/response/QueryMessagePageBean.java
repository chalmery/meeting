package top.yangcc.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yangcc.entity.Meeting;
import top.yangcc.entity.Message;

/**
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryMessagePageBean {
    private Integer currentPage;
    private Integer pageSize;
    private Message message;
}
