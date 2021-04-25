package top.yangcc.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.yangcc.entity.Faculty;
import top.yangcc.entity.Role;

import java.util.List;

/**
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConflictUser {
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
    /** 用户冲突的会议列表 */
    private List<Integer> meetingIds;
}
