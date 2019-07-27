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
    public void test() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from user");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            System.out.println(id + username + password);
        }
        JdbcUtils.close(connection,statement,resultSet);

    }
}
