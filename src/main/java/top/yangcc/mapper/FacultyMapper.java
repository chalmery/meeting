package top.yangcc.mapper;


import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;
import top.yangcc.entity.Faculty;


import java.util.HashMap;
import java.util.List;


/**
 * 院系相关的Mapper
 * @author yangcc
 */
@Repository
public interface FacultyMapper {

    /**
     * 查询全部的教学楼
     * @return 返回教学楼的信息
     */
    List<String> findFacultyNameForTeach();

    /**
     * 查询全部楼的名称
     * @return 返回全部楼信息
     */
    List<HashMap<String,String>> findAllName();


    /**
     * 条件查询
     * @param querySting 查询条件
     * @return 返回分页信息
     */
    Page<Faculty> findByCondition(String querySting);

    /**
     * 根据名称查询id
     * @param name 名称
     * @return id
     */
    Integer findIdByName(String name);

    /**
     * 根据id查询院系
     * @param id id
     * @return 院系对象
     */
    Faculty findById(Integer id);

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
