package com.epochong.chatroom.controller.assember;

import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.entity.Robot;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static Robot getRobot(ResultSet resultSet) throws SQLException {
        Robot robot = new Robot();
        robot.setId(resultSet.getInt("id"));
        robot.setFaqValid(resultSet.getInt("faq_valid"));
        robot.setFaq(resultSet.getString("faq"));
        robot.setAnswer(resultSet.getString("answer"));
        return robot;
    }

    public static Robot getRobot() {
        Robot robot = new Robot();
        robot.setAnswer("这个问题机器人还没学会，请人工回复");
        return robot;
    }

    public static RobotDto getRobotDto(Robot robot) {
        RobotDto dto = new RobotDto();
        dto.setFaq(robot.getFaq());
        dto.setAnswer(robot.getAnswer());
        return dto;
    }
}
