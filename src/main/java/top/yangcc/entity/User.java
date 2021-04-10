package top.yangcc.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 用户的实体类
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /** 用户id */
    private Integer id;
    /** 用户头像 */
    private String avatar;
    /** 用户名称 */
    private String username;
    /** 用户密码 */
    private String password;
    /** 是否是管理员 */
    private boolean admin;
    /** 用户角色*/
    private Role role;
    /** 用户对应的院系*/
   private Faculty faculty;

    public User(String avatar, String username, String password, boolean admin, Role role, Faculty faculty) {
        this.avatar = avatar;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.role = role;
        this.faculty = faculty;
    }

    public User(Integer id, String username, Role role, Faculty faculty) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.faculty = faculty;
    }

    public User(Integer id, String avatar, String username, Role role, Faculty faculty) {
        this.id = id;
        this.avatar = avatar;
        this.username = username;
        this.role = role;
        this.faculty = faculty;
    }
}
