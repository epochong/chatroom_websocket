package com.epochong.chatroom.controller.assember;

import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.controller.vo.RobotVo;
import com.epochong.chatroom.domian.entity.Robot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 15:26
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class RobotAssembler {
    public static RobotDto getRobot(Robot robot) {
        RobotDto dto = new RobotDto();
        dto.setFaq(robot.getFaq());
        dto.setAnswer(robot.getAnswer());
        dto.setFaqValid(robot.getFaqValid());
        return dto;
    }

    public static Robot getRobot(RobotDto robotDto) {
        Robot robot = new Robot();
        if (Objects.nonNull(robotDto.getId())) {
            robot.setId(robotDto.getId());
        }
        if (Objects.nonNull(robotDto.getFaq())) {
            robot.setFaq(robotDto.getFaq());
        }
        if (Objects.nonNull(robotDto.getAnswer())) {
            robot.setAnswer(robotDto.getAnswer());
        }
        if (Objects.nonNull(robotDto.getFaqValid())) {
            robot.setFaqValid(robotDto.getFaqValid());
        }
        return robot;
    }

    public static Robot getRobot(ResultSet resultSet) throws SQLException {
        Robot robot = new Robot();
        robot.setId(resultSet.getInt("id"));
        robot.setFaqValid(resultSet.getInt("faq_valid"));
        robot.setFaq(resultSet.getString("faq"));
        robot.setAnswer(resultSet.getString("answer"));
        robot.setMatches(resultSet.getInt("matches"));
        return robot;
    }

    public static Robot getRobot() {
        Robot robot = new Robot();
        robot.setAnswer("这个问题机器人还没学会，请人工回复");
        return robot;
    }

    public static RobotDto getRobotDto(Robot robot) {
        RobotDto dto = new RobotDto();
        dto.setId(robot.getId());
        dto.setFaqValid(robot.getFaqValid());
        dto.setFaq(robot.getFaq());
        dto.setAnswer(robot.getAnswer());
        return dto;
    }
    public static RobotDto getRobotDto(RobotVo robot) {
        RobotDto dto = new RobotDto();
        dto.setId(robot.getId());
        dto.setFaqValid(robot.getFaqValid());
        dto.setFaq(robot.getFaq());
        dto.setAnswer(robot.getAnswer());
        return dto;
    }

}
