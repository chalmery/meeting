package top.yangcc.service;



import top.yangcc.entity.Faculty;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryPageBean;
import top.yangcc.response.Result;

import java.util.HashMap;
import java.util.List;

/**
 * 教学楼相关的Service
 * @author yangcc
 */
public interface FacultyService {

    /**
     * 查询全部的教学楼
     * @return 返回教学楼的数组
     */
    List<String>  findFacultyNameForTeach();

    /**
     * 查询全部的楼
     * @return 返回数组
     */
    List<Faculty> findAll();

    /**
     * 分页查询
     * @param queryPageBean 查询条件等
     * @return 返回分页信息
     */
    PageResult findPage(QueryPageBean queryPageBean);

    /**
     * 新增院系
     * @param faculty 院系
     */
    void add(Faculty faculty);

    /**
     * 修改院系
     * @param faculty 院系
     */
    void edit(Faculty faculty);

    /**
     * 删除院系
     * @param id 院系id
     */
    void delete(Integer id);
}
