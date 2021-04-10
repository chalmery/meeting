package top.yangcc.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户接收的User
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /** 用户id */
    private Integer id;
    /** 用户名称 */
    private String username;
    /** 用户密码 */
    private String password;
    /** 用户角色*/
    private String role;
    /** 用户对应的院系*/
    private String faculty;
}
