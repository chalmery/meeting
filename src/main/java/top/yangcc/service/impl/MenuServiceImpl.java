package top.yangcc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yangcc.entity.Menu;
import top.yangcc.entity.Role;
import top.yangcc.mapper.MenuMapper;
import top.yangcc.service.MenuService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangcc
 */
@Service
public class MenuServiceImpl  implements MenuService {

    private MenuMapper menuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    /**获取全部的菜单*/
    @Override
    public List<Menu> findAllMenu() {
        List<Menu> menuList = menuMapper.findAllMenu();
        //存储父节点
        ArrayList<Menu> parent = new ArrayList<>();
        //遍历
        for (Menu menu : menuList) {
            //如果是父节点,存储到list
            if (menu.getPid()==null){
                parent.add(menu);
            }
        }
        //遍历父节点
        for (Menu menu1 : parent) {
            //遍历
            for (Menu menu : menuList) {
                //如果有对应的子节点,加入子节点
                if (menu1.getId().equals(menu.getPid())){
                    menu1.getChildren().add(menu);
                }
            }
        }
        //遍历父节点
        for (Menu menu1 : parent) {
            //遍历子节点
            for (Menu child : menu1.getChildren()) {
                //遍历
                for (Menu menu : menuList) {
                    //如果有对应的子节点,加入子节点
                    if (child.getId().equals(menu.getPid())){
                        child.getChildren().add(menu);
                    }
                }
            }
        }
        return parent;
    }

    /**获取此用户对应的菜单*/
    @Override
    public List<Menu> findByUsername(String username) {
        List<Menu> menuList = menuMapper.findByUsername(username);
        //存储父节点
        ArrayList<Menu> parent = new ArrayList<>();
        //遍历
        for (Menu menu : menuList) {
            //如果是父节点,存储到list
            if (menu.getPid()==null){
                parent.add(menu);
            }
        }
        //遍历父节点
        for (Menu menu1 : parent) {
            //遍历
            for (Menu menu : menuList) {
                //如果有对应的子节点,加入子节点
                if (menu1.getId().equals(menu.getPid())){
                    menu1.getChildren().add(menu);
                }
            }
        }
        return parent;
    }




}
