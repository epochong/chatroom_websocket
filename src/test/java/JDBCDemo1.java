import com.epochong.chatroom.infrastructure.repository.utils.JdbcUtils;
import com.epochong.chatroom.infrastructure.repository.utils.JdbcUtils1;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;

/**
 * @author epochong
 * @date 2019/7/27 10:53
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class JDBCDemo1 {
    @Test
    //查询所有
    public void test() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        //存在sql注入问题
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user ");
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            System.out.println(id + username + password);
        }
        JdbcUtils.close(connection,statement,resultSet);
        Assert.assertNotNull(resultSet);
    }

    @Test
    public void test0() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        //存在sql注入问题
        Statement statement = connection.createStatement();
        String username = "zs";
        String password = "123";
        ResultSet resultSet = statement.executeQuery("select * from user where username = ' " + username + "' and password = '" + password + "'");
        if (resultSet.next()) {
            System.out.println(username + "登录成功");
        }
        JdbcUtils.close(connection,statement,resultSet);
        Assert.assertNotNull(resultSet);
    }
    @Test
    public void test1() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        //预编译sql类,会使用占位符?占位用户名密码等需要外部传入的字段
        PreparedStatement statement = connection.prepareStatement("select * from user where username = ? and password = ?");
        String username = "zs";
        String password = "123";
        statement.setString(1,username);
        statement.setString(2,password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println(username + "登录成功");
        }
        JdbcUtils.close(connection,statement,resultSet);
    }
    @Test
    public void selectTest() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        connection = JdbcUtils.getConnection();
        String sql = "select * from user";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id= resultSet.getInt("id");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                System.out.println(id + userName + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection,statement,resultSet);
        }

    }
    @Test
    public void testQuery() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils1.getConnection();
            String sql = "select * from user where id = ? and username = ?";
            //产生预编译sql对象
            statement = connection.prepareStatement(sql);
            statement.setInt(1,1);
            statement.setString(2,"zs");
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                System.out.println(id + username + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection,statement,resultSet);
        }
    }
    @Test
    public void testInsert() {
        Connection connection = null;
        PreparedStatement statement = null;
        connection = JdbcUtils.getConnection();
        String sql = "insert into user(username, password) values (?,?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,"java7");
            statement.setString(2,DigestUtils.md5Hex("java7"));
            int lines = statement.executeUpdate();
            Assert.assertEquals(1,lines);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection,statement);
        }
    }
}
