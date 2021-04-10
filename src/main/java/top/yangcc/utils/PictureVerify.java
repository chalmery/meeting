package top.yangcc.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * 文件是否是图片的校验类
 * @author yangcc
 */
public class PictureVerify {

    /**是否是图片*/
    public static boolean isPicture(MultipartFile file){
        String filename = file.getOriginalFilename();
        //拿到图片的后缀
        String split = Objects.requireNonNull(filename.split("\\.")[1]);
        return Objects.equals(split, "png") || Objects.equals(split, "jpg") || Objects.equals(split, "jpeg");
    }

    /**是否是图片,以及是否为空*/
    public static boolean isPictureAndIsNull(MultipartFile file){
        if (file==null){
            return false;
        }
        String filename = file.getOriginalFilename();
        //拿到图片的后缀
        String split = Objects.requireNonNull(filename.split("\\.")[1]);
        return Objects.equals(split, "png") || Objects.equals(split, "jpg") || Objects.equals(split, "jpeg");
    }

}
