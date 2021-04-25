package top.yangcc.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yangcc.entity.Meeting;
import top.yangcc.entity.MeetingRoom;

/**
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryMeetingPageBean {
    private Integer currentPage;
    private Integer pageSize;
    private Meeting meeting;
}
