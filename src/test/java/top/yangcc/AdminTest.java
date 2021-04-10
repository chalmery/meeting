package top.yangcc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.yangcc.entity.Faculty;
import top.yangcc.entity.Role;
import top.yangcc.entity.User;
import top.yangcc.mapper.AdminMapper;

/**
 * @author yangcc
 */
public class AdminTest extends MeetingApplicationTests {
    @Autowired
    private AdminMapper adminMapper;
    @Test
    public void test(){
        Role role = new Role();
        role.setId(1);
        Faculty faculty = new Faculty();
        faculty.setId(1);
        User user = new User("avatar.jpg", "111", "111", true, role, faculty);
        adminMapper.add(user);
    }
}
