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
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findAllMenu() {
        List<Menu> menuList = menuMapper.findAllMenu();
        //存储父节点
        ArrayList<Menu> parent = new ArrayList<>();
        for (Menu menu : menuList) {
            //如果是父节点
            if (menu.getPid()==null){
                parent.add(menu);
            }
        }
        //遍历子节点
        for (Menu menu1 : parent) {
            for (Menu menu : menuList) {
                if (menu1.getId().equals(menu.getPid())){
                    menu1.getChildren().add(menu);
                }
            }
        }
        return parent;
    }
}
