package com.epochong.chatroom.infrastructure.repository.dao;

import com.epochong.chatroom.controller.assember.UserAssembler;
import com.epochong.chatroom.controller.dto.LoginDto;
import com.epochong.chatroom.domian.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.sql.*;

/**
 * @author epochong
 * @date 2019/8/3 10:25
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Slf4j
public class AccountDao  extends BaseDao {
    public User userLogin(LoginDto loginDto) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            String sql = "select * from user where username = ? and" + " password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, loginDto.getUsername());
            statement.setString(2, DigestUtils.md5Hex(loginDto.getPassword()));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = UserAssembler.getUser(resultSet);
            }
        } catch (Exception e) {
            log.error("数据库查询异常 error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            close(connection, statement, resultSet);
        }
        return user;
    }

    public boolean userRegister(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSuccess = false;
        try {
            connection = getConnection();
            String sql = "insert into user(username,password,user_type,city) values(?,?,?)";
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, DigestUtils.md5Hex(user.getPassword()));
            statement.setInt(3, user.getUserType());
            //statement.setString(4, user.getCity());
            isSuccess = (statement.executeUpdate() == 1);
        } catch (SQLException e) {
            log.error("insertFaq() error:{}", ExceptionUtils.getStackTrace(e));
        } finally {
            close(connection,statement);
        }
        return isSuccess;
    }

}
