package top.yangcc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.yangcc.entity.Faculty;
import top.yangcc.entity.Role;
import top.yangcc.entity.User;
import top.yangcc.mapper.AdminMapper;
import top.yangcc.mapper.FacultyMapper;
import top.yangcc.mapper.RoleMapper;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryUserPageBean;
import top.yangcc.service.AdminService;
import top.yangcc.utils.Md5Utils;
import top.yangcc.utils.QiNiuUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author yangcc
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private AdminMapper adminMapper;

    private RoleMapper roleMapper;

    private FacultyMapper facultyMapper;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper, RoleMapper roleMapper, FacultyMapper facultyMapper) {
        this.adminMapper = adminMapper;
        this.roleMapper = roleMapper;
        this.facultyMapper = facultyMapper;
    }

    /**
     * 查询所有的管理员用户信息
     */
    @Override
    public PageResult findAdminByCondition(QueryUserPageBean queryUserPageBean) {
        // 通过分页助手进行查询
        PageHelper.startPage(queryUserPageBean.getCurrentPage(),queryUserPageBean.getPageSize());
        Page<User> byCondition = adminMapper.findAdminByCondition(queryUserPageBean.getUser());
        PageInfo<User> pageInfo = new PageInfo<>(byCondition);
        //拿到分页后的role
        List<User> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    /**查询用户是否存在*/
    @Override
    public Integer findCountByUsername(String username) {
        //查询用户是否已存在
        return adminMapper.findCountByUsername(username);
    }


    /**
     *新建admin用户
     */
    @Override
    public void add(MultipartFile avatar, String username, String password, String faculty, String role) throws IOException {
        //查询role,faculty id
        Integer facultyId = facultyMapper.findIdByName(faculty);
        Integer roleId = roleMapper.findIdByCode(role);
        if (facultyId !=null && roleId !=null) {
            //拿到图片的后缀
            String split = Objects.requireNonNull(avatar.getOriginalFilename()).split("\\.")[1];
            //上传图片
            String avatarName = UUID.randomUUID() + "." + split;
            QiNiuUtil.upload(avatar.getBytes(), avatarName);
            //密码加密
            String encrypt = Md5Utils.encrypt(password);
            //准备插入
            Faculty faculty1 = new Faculty();
            faculty1.setId(facultyId);
            Role role1 = new Role();
            role1.setId(roleId);
            User user = new User(avatarName, username, encrypt, true, role1, faculty1);
            //插入数据库
            adminMapper.add(user);
        }
    }

    /** 新建admin用户,不带头像 */
    @Override
    public void addForNoAvatar(String username, String password, String faculty, String role) {
        //查询role,faculty id
        Integer facultyId = facultyMapper.findIdByName(faculty);
        Integer roleId = roleMapper.findIdByCode(role);
        if (facultyId !=null && roleId !=null){
            //密码加密
            String encrypt = Md5Utils.encrypt(password);
            //准备插入
            Faculty faculty1 = new Faculty();
            faculty1.setId(facultyId);
            Role role1 = new Role();
            role1.setId(roleId);
            User user = new User("header.jpg", username, encrypt, true,role1,faculty1);
            //插入数据库
            adminMapper.add(user);
        }
    }

    /**修改用户*/
    @Override
    public void edit(Integer id,String username,String password, String faculty, String role) {
        System.out.println(faculty);
        System.out.println(role);
        //查询role,faculty id
        Integer facultyId = facultyMapper.findIdByName(faculty);
        Integer roleId = roleMapper.findIdByName(role);
        if (facultyId !=null && roleId !=null) {
            Faculty faculty1 = new Faculty();
            faculty1.setId(facultyId);
            Role role1 = new Role();
            role1.setId(roleId);
            System.out.println(facultyId);
            System.out.println(roleId);
            //如果密码为空,表示不重置密码
            if (password==null || password.length()==0){
                adminMapper.edit(new User(id, username, role1, faculty1));
            }
            //否则重置密码
            else {
                //加密
                String encrypt = Md5Utils.encrypt(password);
                adminMapper.edit(new User(id, username,encrypt, role1, faculty1));
            }
        }
    }

    /**修改用户*/
    @Override
    public void editAdnUpload(Integer id, MultipartFile avatar, String username,String password,  String faculty, String role) throws IOException {
        //查询role,faculty id
        Integer facultyId = facultyMapper.findIdByName(faculty);
        Integer roleId = roleMapper.findIdByName(role);
        if (facultyId !=null && roleId !=null){
            //查询老的头像
            String oldAvatar = adminMapper.findAvatarById(id);
            //如果老头像不是默认头像则删除
            if (!Objects.equals(oldAvatar,"header.jpg")){
                QiNiuUtil.delete(oldAvatar);
            }
            //拿到图片的后缀
            String split = Objects.requireNonNull(avatar.getOriginalFilename()).split("\\.")[1];
            //上传图片
            String avatarName = UUID.randomUUID() + "." + split;
            QiNiuUtil.upload(avatar.getBytes(), avatarName);
            //准备插入
            Faculty faculty1 = new Faculty();
            faculty1.setId(facultyId);
            Role role1 = new Role();
            role1.setId(roleId);
            User user = new User(id,avatarName, username,role1,faculty1);
            //update
            adminMapper.edit(user);
        }

    }


    @Override
    public void delete(Integer id) {
        //查询老的头像
        String oldAvatar = adminMapper.findAvatarById(id);
        //如果老头像不是默认头像则删除
        if (!Objects.equals(oldAvatar,"header.jpg")){
            QiNiuUtil.delete(oldAvatar);
        }
        //然后删除这个用户
        adminMapper.delete(id);
    }
}
