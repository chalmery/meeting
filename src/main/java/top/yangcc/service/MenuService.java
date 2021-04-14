package top.yangcc.service;

import top.yangcc.entity.Menu;

import java.util.List;

/**
 * @author yangcc
 */
public interface MenuService {
    /**
     * 查询全部的菜单
      * @return list
     */
   List<Menu> findAllMenu();

    /**
     * 查询对应用户的菜单
     * @param username username
     * @return list
     */
    List<Menu> findByUsername(String username);
}
