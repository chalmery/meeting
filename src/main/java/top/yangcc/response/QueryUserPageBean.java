package top.yangcc.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yangcc.request.User;

/**
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserPageBean {
    private Integer currentPage;
    private Integer pageSize;
    private User user;
}
