package com.company.research_message_queue.controller;

import com.company.research_message_queue.roleConfig.Sender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-09 20:54:44
 */
@RestController
public class StudentController {
    @Resource
    private Sender sender;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String send(String msg) {
        sender.sendMessage("Li.Wen.Bo_queue", msg);
        return "消息：" + msg + ",已发送";
    }


}
