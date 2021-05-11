package top.yangcc.service.impl;

import cn.dev33.satoken.stp.StpUtil;
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
import top.yangcc.mapper.MeetingMapper;
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
    private static final String OFF_LINE = "离线";

    private AdminMapper adminMapper;

    private RoleMapper roleMapper;

    private FacultyMapper facultyMapper;

    private MeetingMapper meetingMapper;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper, RoleMapper roleMapper, FacultyMapper facultyMapper, MeetingMapper meetingMapper) {
        this.adminMapper = adminMapper;
        this.roleMapper = roleMapper;
        this.facultyMapper = facultyMapper;
        this.meetingMapper = meetingMapper;
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
            User user = new User(avatarName, username, encrypt, true,OFF_LINE, role1, faculty1);
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
            User user = new User("header.jpg", username, encrypt, true,OFF_LINE,role1,faculty1);
            //插入数据库
            adminMapper.add(user);
        }
    }

    /**修改用户*/
    @Override
    public void edit(Integer id,String username,String password, String faculty, String role) {
        //查询role,faculty id
        Integer facultyId = facultyMapper.findIdByName(faculty);
        Integer roleId = roleMapper.findIdByCode(role);
        if (facultyId !=null && roleId !=null) {
            Faculty faculty1 = new Faculty();
            faculty1.setId(facultyId);
            Role role1 = new Role();
            role1.setId(roleId);
            //如果密码为空,表示不重置密码
            if (password==null || password.length()==0){
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setRole(role1);
                user.setFaculty(faculty1);
                adminMapper.edit(user);
            }
            //否则重置密码
            else {
                //加密
                String encrypt = Md5Utils.encrypt(password);
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(encrypt);
                user.setRole(role1);
                user.setFaculty(faculty1);
                adminMapper.edit(user);
            }
        }
    }

    /**修改用户*/
    @Override
    public void editAdnUpload(Integer id, MultipartFile avatar, String username,String password,  String faculty, String role) throws IOException {
        //查询role,faculty id
        Integer facultyId = facultyMapper.findIdByName(faculty);
        Integer roleId = roleMapper.findIdByCode(role);
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
            //如果修改了密码
            if (password==null || password.length()==0){
                User user = new User();
                user.setId(id);
                user.setAvatar(avatarName);
                user.setUsername(username);
                user.setRole(role1);
                user.setFaculty(faculty1);
                adminMapper.edit(user);
            }else{
                //加密
                String encrypt = Md5Utils.encrypt(password);
                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setAvatar(avatarName);
                user.setPassword(encrypt);
                user.setRole(role1);
                user.setFaculty(faculty1);
                adminMapper.edit(user);
            }
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

    /**踢人下线*/
    @Override
    public void offline(Integer id) {
        String username = adminMapper.findUsernameById(id);
        //下线
        StpUtil.logoutByLoginId(username);
        //修改数据库
        adminMapper.offline(id);
    }

    /**账号封禁*/
    @Override
    public void ban(Integer id, Integer banTime, boolean deleteMeetingApply, boolean forever) {
        //查询此用户对应的用户名
        String username = adminMapper.findUsernameById(id);
        //踢下线
        StpUtil.logoutByLoginId(username);
        //删除申请并且永久封禁
        if (deleteMeetingApply && forever){
            //查询此用户对应的所有的待审核会议id
            List<Integer> ids = meetingMapper.findMeetingApplyAllForUserId(id);
            for (Integer meetingId : ids) {
                //删除会议申请
                meetingMapper.deleteMeetingApplyAllForMeetingId(id);
            }
            //永久封禁
            StpUtil.disable(username, -1);
        }
        //删除申请并且不永久封禁
        if (deleteMeetingApply && !forever){
            //查询此用户对应的所有的待审核会议id
            List<Integer> ids =meetingMapper.findMeetingApplyAllForUserId(id);
            for (Integer meetingId : ids) {
                //删除会议申请
                meetingMapper.deleteMeetingApplyAllForMeetingId(id);
            }
            //指定时间封禁
            StpUtil.disable(username,banTime*86400);
        }
        //不删除申请且不永久封禁
        if (!deleteMeetingApply && !forever){
            //指定时间封禁
            StpUtil.disable(username,banTime*86400);
        }
        //不删除永久封禁
        if (!deleteMeetingApply && forever){
            //永久封禁
            StpUtil.disable(username, -1);
        }
        //修改数据库
        adminMapper.ban(id);
    }

    @Override
    public void unBan(String username) {
        //解封禁
        StpUtil.untieDisable(username);
        //设置用户状态
        adminMapper.unBan(username);
    }
}
