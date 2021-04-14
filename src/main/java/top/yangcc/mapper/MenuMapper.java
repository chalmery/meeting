package top.yangcc.mapper;

import org.springframework.stereotype.Repository;
import top.yangcc.entity.Menu;

import java.util.List;

/**
 * @author yangcc
 */
@Repository
public interface MenuMapper {
    /**
     * 查询全部的菜单
     * @return list
     */
   List<Menu> findAllMenu();

    /**
     * 查询菜单根据用户名
     * @param username  username
     * @return list
     */
    List<Menu> findByUsername(String username);
}
