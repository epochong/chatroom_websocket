package com.epochong.chatroom.application.command.service.impl;

import com.epochong.chatroom.application.command.service.RobotCommandService;
import com.epochong.chatroom.controller.assember.RobotAssembler;
import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.entity.Robot;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.exception.ResourceException;
import com.epochong.chatroom.infrastructure.repository.mapper.RobotMapper;

import java.util.Objects;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 15:29
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class RobotCommandServiceImpl implements RobotCommandService {

    private RobotMapper robotMapper = new RobotMapper();
    @Override
    public BaseResp insertRobotFaq(RobotDto dto) {
        Robot robot = robotMapper.queryRobotByFaq(RobotAssembler.getRobot(dto));
        // 如果数据库中有这个问题，则应该是更新问题
        if (Objects.nonNull(robot)) {
            robot.setAnswer(dto.getAnswer());
            robot.setFaqValid(1);
            robotMapper.updateById(robot);
            return BaseResp.getSuccessResp();
        }
        if (robotMapper.insertFaq(dto)) {
            return new BaseResp(ResourceException.SUCCESS);
        }
        return new BaseResp(ResourceException.SYSTEM_ERROR);
    }

    @Override
    public BaseResp updateById(RobotDto robotDto) {
        if (robotMapper.updateById(RobotAssembler.getRobot(robotDto))) {
            return BaseResp.getSuccessResp();
        }
        return BaseResp.getFailResp();
    }

    @Override
    public BaseResp deleteById(RobotDto dto) {
        Robot robot = RobotAssembler.getRobot(dto);
        if (robotMapper.deleteById(robot)) {
            return BaseResp.getSuccessResp();
        }
        return BaseResp.getFailResp();
    }
}
