package top.yangcc;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yangcc.entity.Role;
import top.yangcc.mapper.RoleMapper;

import java.util.List;

@SpringBootTest
public class MeetingApplicationTests {
    @Autowired
    private RoleMapper roleMapper;
    @Test
    void contextLoads() {
        // 通过分页助手进行查询
        PageHelper.startPage(1,10);
        Page<Role> byCondition = roleMapper.findByCondition("");
        PageInfo<Role> pageInfo = new PageInfo<>(byCondition);
        List<Role> list = pageInfo.getList();
        System.out.println(list);
    }

}
