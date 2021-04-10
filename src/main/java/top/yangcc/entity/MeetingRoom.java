package top.yangcc.entity;

import lombok.*;

/**
 * 会议室Bean
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRoom {
    /** 表的id */
    private Integer id;
    /** 图片的路径 */
    private String img;
    /** 会议室的地点 */
    private String room;
    /** 会议室可容纳的人数*/
    private Integer capacity;
    /** 会议室的状态，可用，使用中，维修中 */
    private String status;
    /** 会议室描述信息 */
    private String info;
    /** 会议室所属的系楼信息 */
    private Faculty faculty;

    public MeetingRoom(String room, Integer capacity, String status, String info, Faculty faculty) {
        this.room = room;
        this.capacity = capacity;
        this.status = status;
        this.info = info;
        this.faculty = faculty;
    }
}
