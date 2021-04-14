package top.yangcc.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.yangcc.entity.Faculty;
import top.yangcc.mapper.FacultyMapper;

import top.yangcc.mapper.UserMapper;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryPageBean;
import top.yangcc.service.FacultyService;


import java.util.List;

/**
 * @author yangcc
 */
@Service
@Transactional
public class FacultyServiceImpl implements FacultyService {

    private FacultyMapper facultyMapper;

    private UserMapper userMapper;

    @Autowired
    public FacultyServiceImpl(FacultyMapper facultyMapper, UserMapper userMapper) {
        this.facultyMapper = facultyMapper;
        this.userMapper = userMapper;
    }

    /**
     * 查询全部的教学楼
     * @return 返回教学楼的数组
     */
    @Override
    public List<String> findFacultyNameForTeach() {
        return facultyMapper.findFacultyNameForTeach();
    }

    /**
     * 查询全部的楼
     * @return 返回楼的数组
     */
    @Override
    public List<Faculty> findAll() {
        return facultyMapper.findAll();
    }

    /**
     * 分页查询
     * @return 分页数据
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //  当前页码，每页显示条数 查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Page<Faculty> faculties = facultyMapper.findByCondition(queryString);
        PageInfo<Faculty> pageInfo = new PageInfo<>(faculties);
        List<Faculty> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    /**
     * 新增
     * @param faculty 院系
     */
    @Override
    public void add(Faculty faculty) {
        facultyMapper.add(faculty);
    }

    /**
     * 修改
     * @param faculty 院系
     */
    @Override
    public void edit(Faculty faculty) {
        facultyMapper.edit(faculty);
    }

    /**
     * 删除
     * @param id 院系id
     */
    @Override
    public void delete(Integer id) {
        facultyMapper.delete(id);
    }
}