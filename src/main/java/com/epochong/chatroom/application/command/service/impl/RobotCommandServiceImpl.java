package com.epochong.chatroom.application.command.service.impl;

import com.epochong.chatroom.application.command.service.RobotCommandService;
import com.epochong.chatroom.controller.assember.RobotAssembler;
import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.entity.Robot;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.exception.ResourceException;
import com.epochong.chatroom.infrastructure.repository.dao.RobotDao;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 15:29
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class RobotCommandServiceImpl implements RobotCommandService {

    private RobotDao robotDao = new RobotDao();
    @Override
    public BaseResp insertRobotFaq(RobotDto robotDto) {
        if (robotDao.insertFaq(robotDto)) {
            return new BaseResp(ResourceException.SUCCESS);
        }
        return new BaseResp(ResourceException.SYSTEM_ERROR);
    }

    @Override
    public BaseResp updateById(RobotDto robotDto) {
        if (robotDao.updateById(RobotAssembler.getRobot(robotDto))) {
            return BaseResp.getSuccessResp();
        }
        return BaseResp.getFailResp();
    }

    @Override
    public BaseResp deleteById(RobotDto dto) {
        Robot robot = RobotAssembler.getRobot(dto);
        if (robotDao.deleteById(robot)) {
            return BaseResp.getSuccessResp();
        }
        return BaseResp.getFailResp();
    }
}
