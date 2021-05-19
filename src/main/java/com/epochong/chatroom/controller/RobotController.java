package com.epochong.chatroom.controller;

import com.epochong.chatroom.application.command.service.RobotCommandService;
import com.epochong.chatroom.application.command.service.impl.RobotCommandServiceImpl;
import com.epochong.chatroom.application.query.service.RobotQueryService;
import com.epochong.chatroom.application.query.service.impl.RobotQueryServiceImpl;
import com.epochong.chatroom.controller.assember.RobotAssembler;
import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.entity.Robot;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author wangchong.epochong
 * @date 2021/5/18 18:15
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Slf4j
@Controller
@RequestMapping("/robot")
public class RobotController {
    private RobotQueryService robotQueryService = new RobotQueryServiceImpl();
    private RobotCommandService robotCommandService = new RobotCommandServiceImpl();

    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list", robotQueryService.getAll());
        map.put("keyWords", "");
        return "robot_list";
    }

    @RequestMapping(value = "/search", params = "keyWords")
    public String search(String keyWords, Map<String,Object> map){
        RobotDto robotDto = new RobotDto();
        robotDto.setFaq(keyWords);
        map.put("list", robotQueryService.searchByFaq(robotDto));
        map.put("keyWords", keyWords);
        return "robot_list";
    }

    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("robot",new Robot());
        return "robot_add";
    }

    @RequestMapping(value = "/to_update",params = "id")
    public String toUpdate(String id, Map<String,Object> map){
        RobotDto robotDto = new RobotDto();
        robotDto.setId(Integer.parseInt(id));
        Robot robot = (Robot)robotQueryService.queryAnswerById(robotDto).getObject();
        map.put("robot", robot);
        map.put("valid", robot.getFaqValid() == 1 ? "checked" : "");
        return "robot_update";
    }

    @RequestMapping("/update")
    public String update(Robot robot){
        log.info("update(): params:{}", robot);
        BaseResp baseResp = robotCommandService.updateById(RobotAssembler.getRobotDto(robot));
        if (baseResp.getCode() == Constant.SUCCESS) {
            return "redirect:list";
        }
        return "";
    }

    @RequestMapping("/add")
    public String add(Robot robot){
        robotCommandService.insertRobotFaq(RobotAssembler.getRobotDto(robot));
        return "redirect:list";
    }

    @RequestMapping(value = "/remove",params = "id")
    public String remove(String id){
        RobotDto robotDto = new RobotDto();
        robotDto.setId(Integer.parseInt(id));
        robotCommandService.deleteById(robotDto);
        return "redirect:list";
    }

}
