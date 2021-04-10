package top.yangcc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 角色表
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    /**角色id*/
    private Integer id;
    /**角色名称*/
    private String name;
    /**角色的代号 admin user*/
    private String code;
    /**角色创建时间*/
    private String createTime;
    /**角色是否为管理员*/
    private boolean admin;
    /**角色描述*/
    private String info;
    /**角色对应的菜单id*/
    private List<Integer> menuList;
}
