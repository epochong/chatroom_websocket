package com.epochong.chatroom.dao;

import com.epochong.chatroom.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

/**
 * @author epochong
 * @date 2019/8/3 10:25
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class AccountDao  extends BaseDao{
    public User userLogin(String userName, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = getConnection();
            String sql = "select * from user where username = ? and" + " password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, DigestUtils.md5Hex(password));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserInfo(resultSet);
            }
        } catch (Exception e) {
            System.err.println("查询数据库用户信息出错");
            e.printStackTrace();
        } finally {
            close(connection, statement, resultSet);
        }
        return user;
    }

    public boolean userRegister(User user) {
        String userName = user.getUserName();
        String password = user.getPassword();
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSuccess = false;
        try {
            connection = getConnection();
            String sql = "insert into user(username,password) values(?,?)";
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,userName);
            statement.setString(2,DigestUtils.md5Hex(password));
            isSuccess = (statement.executeUpdate() == 1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,statement);
        }
        return isSuccess;
    }
    public User getUserInfo(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setPassword(resultSet.getString("password"));
        user.setUserName(resultSet.getString("username"));
        return user;
    }
}
