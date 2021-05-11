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
import top.yangcc.entity.Meeting;
import top.yangcc.entity.Role;
import top.yangcc.entity.User;

import top.yangcc.mapper.FacultyMapper;
import top.yangcc.mapper.MeetingMapper;
import top.yangcc.mapper.RoleMapper;
import top.yangcc.mapper.UserMapper;
import top.yangcc.response.ConflictUser;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryUserPageBean;
import top.yangcc.service.UserService;
import top.yangcc.utils.Md5Utils;
import top.yangcc.utils.QiNiuUtil;

import java.io.IOException;
import java.util.*;

/**
 * @author yangcc
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final String OFF_LINE = "离线";

    private UserMapper userMapper;

    private RoleMapper roleMapper;

    private FacultyMapper facultyMapper;

    private MeetingMapper meetingMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper, FacultyMapper facultyMapper, MeetingMapper meetingMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.facultyMapper = facultyMapper;
        this.meetingMapper = meetingMapper;
    }

    /**
     * 查询用户信息
     */
    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 查询用户头像
     * @param username username
     * @return avatar
     */
    @Override
    public String findAvatarByUsername(String username) {
        return userMapper.findAvatarByUsername(username);
    }

    /**
     * 查询所有的管理员用户信息
     */
    @Override
    public PageResult findUserByCondition(QueryUserPageBean queryUserPageBean) {
        // 通过分页助手进行查询
        PageHelper.startPage(queryUserPageBean.getCurrentPage(),queryUserPageBean.getPageSize());
        Page<User> byCondition = userMapper.findUserByCondition(queryUserPageBean.getUser());
        PageInfo<User> pageInfo = new PageInfo<>(byCondition);
        //拿到分页后的role
        List<User> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new PageResult(total,list);
    }

    /**查询全部的用户信息*/
    @Override
    public List<User> findByFacultyId(Integer id) {
        //根据院系id,查询该院系是否为教学楼
        boolean isTeach= facultyMapper.findTeachById(id);
        if (isTeach){
            return userMapper.findByFacultyId(id);
        }
        else{
           return userMapper.findAll();
        }
    }

    /**查询用户是否存在*/
    @Override
    public Integer findCountByUsername(String username) {
        //查询用户是否已存在
        return userMapper.findCountByUsername(username);
    }

    /**
     * 查询对应的院系是否还有用户
     * @param id id
     * @return count user
     */
    @Override
    public Integer findUserCountByFacultyId(Integer id) {
        return userMapper.findUserCountByFacultyId(id);
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
            User user = new User(avatarName, username, encrypt, true, OFF_LINE,role1, faculty1);
            //插入数据库
            userMapper.add(user);
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
            userMapper.add(user);
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
                userMapper.edit(user);
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
                userMapper.edit(user);
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
            String oldAvatar = userMapper.findAvatarById(id);
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
                userMapper.edit(user);
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
                userMapper.edit(user);
            }
        }

    }


    @Override
    public void delete(Integer id) {
        //查询老的头像
        String oldAvatar = userMapper.findAvatarById(id);
        //如果老头像不是默认头像则删除
        if (!Objects.equals(oldAvatar,"header.jpg")){
            QiNiuUtil.delete(oldAvatar);
        }
        //然后删除这个用户
        userMapper.delete(id);
    }

    /**修改信息*/
    @Override
    public void userEdit(User user) {
        //如果没有密码表示不重置密码
        if (user.getPassword() != null && user.getPassword().length() != 0) {
            String encrypt = Md5Utils.encrypt(user.getPassword());
            user.setPassword(encrypt);
        }
        userMapper.userEdit(user);
    }

    /**修改信息*/
    @Override
    public void userEditAvatar(MultipartFile avatar,Integer id) throws IOException {
        //查询老的头像
        String oldAvatar = userMapper.findAvatarById(id);
        //如果老头像不是默认头像则删除
        if (!Objects.equals(oldAvatar,"header.jpg")){
            QiNiuUtil.delete(oldAvatar);
        }
        //拿到图片的后缀
        String split = Objects.requireNonNull(avatar.getOriginalFilename()).split("\\.")[1];
        //上传图片
        String avatarName = UUID.randomUUID() + "." + split;
        QiNiuUtil.upload(avatar.getBytes(), avatarName);
        //修改
        User user = new User();
        user.setId(id);
        user.setAvatar(avatarName);
        userMapper.userEditAvatar(user);
    }

    /**查询用户信息,根据会议id*/
    @Override
    public List<User> findByMeetingId(Integer id) {
        return userMapper.findByMeetingId(id);
    }

    /**查询会议冲突的用户*/
    @Override
    public List<ConflictUser> findByConflict(Integer id) {
        //查询此会议对应的用户
        List<User> users = userMapper.findByMeetingId(id);
        //查询此id对应的会议
        Meeting meeting = meetingMapper.findById(id);
        //构造集合
        List<ConflictUser> list =new ArrayList<>();
        //会议人员是否冲突
        for (User user : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("user_id",user.getId());
            map.put("meeting_id",meeting.getId());
            map.put("start",meeting.getStart());
            map.put("end",meeting.getEnd());
            //查询冲突的会议Id
            List<Integer> meetingIds=  userMapper.findByConflict(map);
            if (meetingIds !=null && meetingIds.size()>0){
                list.add(new ConflictUser(user.getId(),user.getAvatar(),user.getUsername(),null,false,user.getRole(),user.getFaculty(),meetingIds));
            }
        }
        return list;
    }


    /**踢人下线*/
    @Override
    public void offline(Integer id) {
        String username = userMapper.findUsernameById(id);
        //下线
        StpUtil.logoutByLoginId(username);
        //修改数据库
        userMapper.offline(id);
    }

    /**账号封禁*/
    @Override
    public void ban(Integer id, Integer banTime, boolean deleteMeetingApply, boolean forever) {
        //查询此用户对应的用户名
        String username = userMapper.findUsernameById(id);
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
        userMapper.ban(id);
    }

    @Override
    public void unBan(String username) {
        //解封禁
        StpUtil.untieDisable(username);
        //设置用户状态
        userMapper.unBan(username);
    }
}
