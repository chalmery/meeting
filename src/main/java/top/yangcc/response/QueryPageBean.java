package top.yangcc.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 前台的分页查询封装的Bean
 *    当前页码
 *    每页显示记录数
 *    查询条件
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPageBean implements Serializable {
    private Integer currentPage;
    private Integer pageSize;
    private String  queryString;
}
