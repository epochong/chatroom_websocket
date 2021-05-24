package com.epochong.chatroom.controller.assember;

import com.epochong.chatroom.controller.dto.UserDto;
import com.epochong.chatroom.controller.vo.LoginVo;
import com.epochong.chatroom.domian.entity.User;
import com.epochong.chatroom.domian.value.BaseResp;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

/**
 * @author wangchong.epochong
 * @date 2021/5/8 22:17
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class UserAssembler {

    public static UserDto getLoginDto(LoginVo vo) {
        UserDto dto = new UserDto();
        if (StringUtils.isNotEmpty(vo.getUsername())) {
            dto.setUsername(vo.getUsername());
        }
        if (StringUtils.isNotEmpty(vo.getPassword())) {
            dto.setPassword(vo.getPassword());
        }
        if (Objects.nonNull(vo.getUserType())) {
            dto.setUserType(vo.getUserType());
        }
        return dto;
    }

    public static String getWriterStr(BaseResp baseResp) {
        return "<script>\n" +
                "    alert(\"" + baseResp.getMessage() + "\");\n" +
                "    window.location.href = \"/index.html\" ;\n" +
                "</script>";
    }

    public static User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setPassword(resultSet.getString("password"));
        user.setUserName(resultSet.getString("username"));
        user.setUserType(resultSet.getInt("user_type"));
        user.setCity(resultSet.getString("city"));
        return user;
    }

    public static User getUser(Map<String, String> urlParams) {
        User user = new User();
        user.setId(Integer.parseInt(urlParams.get("id")));
        user.setUserName(urlParams.get("username"));
        user.setUserType(Integer.parseInt(urlParams.get("userType")));
        user.setCity(urlParams.get("city"));
        return user;
    }

    public static User getUser(UserDto userDto) {
        User user = new User();
        if (Objects.nonNull(userDto.getId())) {
            user.setId(userDto.getId());
        }
        if (Objects.nonNull(userDto.getUsername())) {
            user.setUserName(userDto.getUsername());
        }
        if (Objects.nonNull(userDto.getUserType())) {
            user.setUserType(userDto.getUserType());
        }
        if (Objects.nonNull(userDto.getPassword())) {
            user.setPassword(userDto.getPassword());
        }
        return user;
    }


    public static String verifyParams(LoginVo vo) {
        if (StringUtils.isEmpty(vo.getUsername()) || StringUtils.isEmpty(vo.getPassword())) {
            return "    <script>\n" +
                    "        alert(\"用户名或密码为空!\");\n" +
                    "        window.location.href = \"/index.html\";\n" +
                    "    </script>";
        }
        return null;
    }
}
