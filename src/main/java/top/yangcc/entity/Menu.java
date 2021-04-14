package top.yangcc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单类
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    /** 菜单id */
    private Integer id;
    /** 父菜单的id */
    private Integer pid;
    /** 菜单名 */
    private String name;
    /** 菜单的icon */
    private String icon;
    /** 菜单的path */
    private String path;
    /** 菜单的子菜单 */
    private List<Menu> children = new ArrayList<>();
    /** 是否需要展示 */
    private boolean root;
}
