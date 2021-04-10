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
}
