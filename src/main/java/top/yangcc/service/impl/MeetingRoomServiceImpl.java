package top.yangcc.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryMeetingRoomPageBean;
import top.yangcc.response.QueryPageBean;
import top.yangcc.mapper.FacultyMapper;
import top.yangcc.mapper.MeetingRoomMapper;
import top.yangcc.entity.MeetingRoom;
import top.yangcc.service.MeetingRoomService;
import top.yangcc.utils.QiNiuUtil;

import java.io.IOException;
import java.util.*;

/**
 * 会议室相关的服务层实现类
 * @author yangcc
 */
@Service
@Transactional
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private MeetingRoomMapper meetingRoomMapper;

    private FacultyMapper facultyMapper;

    @Autowired
    public MeetingRoomServiceImpl(MeetingRoomMapper meetingRoomMapper, FacultyMapper facultyMapper) {
        this.meetingRoomMapper = meetingRoomMapper;
        this.facultyMapper = facultyMapper;
    }

    /**
     * 文件上传+新建会议室
     * @param file 文件
     * @param meetingRoom meetingRoom对象
     */
    @Override
    public void upload(MultipartFile file, MeetingRoom meetingRoom) throws IOException {
        //拿到图片的类型
        String split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        // 通过UUID生成文件名防止重复
        String fileName = UUID.randomUUID() + "." + split;
        //然后上传七牛云
        QiNiuUtil.upload(file.getBytes(), fileName);
        // 通过拿到的会议室信息进行数据库插入
        meetingRoom.setImg(fileName);
        meetingRoomMapper.add(meetingRoom);
    }

    /**
     * 删除会议室
     * @param id 会议室id
     */
    @Override
    public void delete(Integer id) {
        // 先查询图片名称,删除图片
        String img = meetingRoomMapper.findImgById(id);
        QiNiuUtil.delete(img);
        meetingRoomMapper.delete(id);

    }

    /**
     * 更新会议
     * @param meetingRoom 会议室对象
     */
    @Override
    public void edit(MeetingRoom meetingRoom) {
        meetingRoomMapper.edit(meetingRoom);
    }

    /**
     * 更新会议室信息并且修改会议室图片
     * @param file 会议室的图片
     * @param meetingRoom 会议室信息
     * @exception IOException 异常
     */
    @Override
    public void editAndUpload(MultipartFile file, MeetingRoom meetingRoom) throws IOException {
        //拿到图片的类型
        String split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        // 通过UUID生成文件名防止重复
        String fileName = UUID.randomUUID() + "." + split;
        //然后上传七牛云
        QiNiuUtil.upload(file.getBytes(), fileName);
        // 新的图片名称
        meetingRoom.setImg(fileName);
        meetingRoomMapper.edit(meetingRoom);
    }

    /**
     * 根据院系，查询会议室
     * @param faculty 院系
     * @return 会议室名称
     */
    @Override
    public List<HashMap<String,String>> findByFaculty(String faculty) {
        return meetingRoomMapper.findByFaculty(faculty);
    }

    /**
     * 分页查询
     * @param queryPageBean 查询条件等
     * @return 返回分页信息
     */
    @Override
    public PageResult findPage(QueryMeetingRoomPageBean queryPageBean) {
        //  当前页码，每页显示条数 查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        MeetingRoom meetingRoom = queryPageBean.getMeetingRoom();
        // 使用分页助手查询
        PageHelper.startPage(currentPage,pageSize);
        Page<MeetingRoom> meetingRooms = meetingRoomMapper.findByCondition(meetingRoom);
        PageInfo<MeetingRoom> pageInfo = new PageInfo<>(meetingRooms);
        List<MeetingRoom> list = pageInfo.getList();
        long total = pageInfo.getTotal();

        return new PageResult(total,list);
    }
}
