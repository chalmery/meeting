package top.yangcc.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 查询的分页数据
 *   总共有多少数据
 *   当前页数据
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {
    private Long total;
    private List rows;
}
