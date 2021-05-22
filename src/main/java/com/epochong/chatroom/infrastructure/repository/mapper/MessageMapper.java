package com.epochong.chatroom.infrastructure.repository.mapper;

import com.epochong.chatroom.controller.assember.MessageAssembler;
import com.epochong.chatroom.domian.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangchong.epochong
 * @date 2021/5/18 11:02
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Slf4j
public class MessageMapper extends BaseMapper {

    public List<Message> queryByFromUserId(Message query) {
        log.info("queryByFromUserId(): params:{}", query);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Message> messageList = new ArrayList <>();
        try {
            connection = getConnection();
            String sql = "select * from message where from_user_id = ? order by create_time";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, query.getFromUserId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messageList.add(MessageAssembler.getMessage(resultSet));
            }
        } catch (Exception e) {
            log.error("数据库查询异常 error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            close(connection, statement, resultSet);
        }
        return messageList;
    }

    public List<Message> queryByToUserId(Message query) {
        log.info("queryByToUserId(): params:", query);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Message> messageList = new ArrayList <>();
        try {
            connection = getConnection();
            String sql = "select * from message where to_user_id = ? order by create_time";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, query.getToUserId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messageList.add(MessageAssembler.getMessage(resultSet));
            }
        } catch (Exception e) {
            log.error("数据库查询异常 error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            close(connection, statement, resultSet);
        }
        return messageList;
    }

    public boolean insertMessage(Message message) {
        log.info("insertMessage(): param:{}", message.toString());
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSuccess = false;
        try {
            connection = getConnection();
            String sql = "insert into message(type, from_user_id, from_user_name,to_user_id, to_user_name, content,create_time) values(?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, message.getType());
            statement.setInt(2, message.getFromUserId());
            statement.setString(3, message.getFromUserName());
            statement.setInt(4, message.getToUserId());
            statement.setString(5, message.getToUserName());
            statement.setString(6, message.getContent());
            statement.setLong(7, System.currentTimeMillis());
            isSuccess = (statement.executeUpdate() == 1);
        } catch (SQLException e) {
            log.error("insertMessage() error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            close(connection,statement);
        }
        return isSuccess;
    }

    public boolean updateUserName(Message message) {
        log.info("insertMessage(): param:{}", message.toString());
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSuccess = false;
        try {
            connection = getConnection();
            String sql = "update message set from_user_name = ?, to_user_name = ? where id = ?";
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, message.getFromUserName());
            statement.setString(2, message.getToUserName());
            statement.setLong(3, message.getId());
            isSuccess = (statement.executeUpdate() == 1);
        } catch (SQLException e) {
            log.error("insertMessage() error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            close(connection,statement);
        }
        return isSuccess;
    }


    public boolean updateType(Message message) {
        log.info("insertMessage(): param:{}", message.toString());
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSuccess = false;
        try {
            connection = getConnection();
            String sql = "update message set type = ? where id = ?";
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, message.getType());
            statement.setLong(2, message.getId());
            isSuccess = (statement.executeUpdate() == 1);
        } catch (SQLException e) {
            log.error("insertMessage() error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            close(connection,statement);
        }
        return isSuccess;
    }

}
