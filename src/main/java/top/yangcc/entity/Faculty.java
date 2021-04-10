package top.yangcc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * 学院的Bean
 * @author yangcc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faculty implements Serializable {
    /** 院系id*/
    private Integer id;
    /** 院系名称*/
    private String name;
    /** 院系地点*/
    private String location;
    /** 院系是否是教学楼*/
    private Boolean teach;
}
