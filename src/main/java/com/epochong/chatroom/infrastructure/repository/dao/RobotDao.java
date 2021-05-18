package com.epochong.chatroom.infrastructure.repository.dao;

import com.epochong.chatroom.controller.assember.RobotAssembler;
import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.entity.Robot;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wangchong.epochong
 * @date 2021/5/17 15:30
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Slf4j
public class RobotDao extends BaseDao {

    public boolean insertFaq(RobotDto robotDto) {
        log.info("insertFaq(): param:{}", robotDto.toString());
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSuccess = false;
        try {
            connection = getConnection();
            String sql = "insert into robot_faq(faq_valid,faq,answer) values(?,?,?)";
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, robotDto.getFaqValid());
            statement.setString(2, robotDto.getFaq());
            statement.setString(3, robotDto.getAnswer());
            isSuccess = (statement.executeUpdate() == 1);
        } catch (SQLException e) {
            log.error("insertFaq() error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            close(connection,statement);
        }
        return isSuccess;
    }


    public Robot queryAnswer(RobotDto dto) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Robot robot = null;
        try {
            connection = getConnection();
            String sql = "select * from robot_faq where faq like ?";
            statement = connection.prepareStatement(sql);
            // 完全匹配
            resultSet = querySqlResult(statement, dto.getFaq());
            if (resultSet.next()) {
                robot = RobotAssembler.getRobot(resultSet);
                return robot;
            }
            // 整句话模糊匹配如果包含该问题子串匹配度最高
            resultSet = querySqlResult(statement, "%" + dto.getFaq() + "%");
            if (resultSet.next()) {
                robot = RobotAssembler.getRobot(resultSet);
                return robot;
            }
            // 使用分词选出匹配程度最高的问题
            Map<Integer, Integer> idCountsMap = new HashMap <>();
            for (String keyWord : dto.getKeyWords()) {
                resultSet = querySqlResult(statement, "%" + keyWord + "%");
                while (resultSet.next()) {
                    robot = RobotAssembler.getRobot(resultSet);
                    if (idCountsMap.containsKey(robot.getId())) {
                        idCountsMap.put(robot.getId(), idCountsMap.get(robot.getId()) + 1);
                    } else {
                        idCountsMap.put(robot.getId(), 1);
                    }
                }
            }
            log.info("问题匹配结果：{}", idCountsMap);
            if (!CollectionUtils.isEmpty(idCountsMap)) {
                Map.Entry <Integer, Integer> maxTimes = idCountsMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get();
                if (maxTimes.getValue() * Constant.ONE_DOUBLE >= dto.getKeyWords().size() * Constant.MOST_LIKE_PERCENT) {
                    log.info("所有分词匹配次数大于等于该问题描述的80%，查询问题匹配程度最高的问题：问题Id:{},次数:{}",  maxTimes.getKey(), maxTimes.getValue());
                    Robot queryById = new Robot();
                    queryById.setId(maxTimes.getKey());
                    return queryRobotById(queryById);
                }
            }
            // 兜底回复
            robot = RobotAssembler.getRobot();
        } catch (Exception e) {
            log.error("数据库查询异常 error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            close(connection, statement, resultSet);
        }
        return robot;
    }


    public Robot queryRobotById(Robot queryRobot) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Robot robot = null;
        try {
            connection = getConnection();
            String sql = "select * from robot_faq where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, queryRobot.getId());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return RobotAssembler.getRobot(resultSet);
            }
        } catch (Exception e) {
            log.error("数据库查询异常 error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            close(connection, statement, resultSet);
        }
        return robot;
    }


    private ResultSet querySqlResult(PreparedStatement statement, String word) throws SQLException {
        statement.setString(1, word);
        ResultSet resultSet = statement.executeQuery();
        return resultSet;
    }
}
