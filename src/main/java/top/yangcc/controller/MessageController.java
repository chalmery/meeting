package top.yangcc.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.yangcc.entity.Message;
import top.yangcc.request.User;
import top.yangcc.response.PageResult;
import top.yangcc.response.QueryMessagePageBean;
import top.yangcc.response.QueryUserPageBean;
import top.yangcc.response.Result;
import top.yangcc.service.AdminService;
import top.yangcc.service.MessageService;
import top.yangcc.utils.PictureVerify;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author yangcc
 */
@RestController
@SaCheckLogin
@RequestMapping("/message")
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /** 分页查询 */
    @RequestMapping("/findPage")
    @SaCheckPermission("message")
    public Result findPage(@RequestBody QueryMessagePageBean queryMessagePageBean){
        try {
            PageResult pageResult = messageService.findPage(queryMessagePageBean);
            return Result.success(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询消息列表失败");
        }
    }

    /**查询消息详情*/
    @RequestMapping("/findDetail/{id}")
    @SaCheckPermission("message")
    public Result findDetail(@PathVariable Integer id){
        try {
            Map<String,Object> map  = messageService.findDetail(id);
            return Result.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询消息列表失败");
        }
    }

    /**修改*/
    @RequestMapping("/edit/{id}")
    @SaCheckPermission("message")
    public Result edit(@PathVariable Integer id,@RequestBody String content){
        try {
            messageService.edit(id,content);
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询消息列表失败");
        }
    }

    /**查询消息列表*/
    @RequestMapping("/findPageByUser")
    @SaCheckPermission("myMessage")
    public Result findPageByUser(@RequestBody QueryMessagePageBean queryMessagePageBean){
        try {
            PageResult pageResult = messageService.findPageByUser(queryMessagePageBean);
            return Result.success(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询消息列表失败");
        }
    }


    /**修改消息为已读*/
    @RequestMapping("/haveRead/{id}")
    public void haveRead(@PathVariable Integer id){
        try {
            messageService.haveRead(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**查看未读消息*/
    @RequestMapping("/findAllReadMessage/{username}")
    public Result findAllReadMessage(@PathVariable String username){
        try {
           List<Message> list =   messageService.findAllReadMessage(username);
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询消息列表失败");
        }
    }

    /**将此用户的消息全部设置为已读*/
    @RequestMapping("/haveReadAll/{username}")
    public void haveReadAll(@PathVariable String username){
        try {
            messageService.haveReadAll(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
