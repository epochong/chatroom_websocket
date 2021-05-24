package com.epochong.chatroom.application.query.service.impl;

import com.epochong.chatroom.application.query.service.UserQueryService;
import com.epochong.chatroom.controller.assember.UserAssembler;
import com.epochong.chatroom.controller.dto.UserDto;
import com.epochong.chatroom.domian.entity.User;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.exception.ResourceException;
import com.epochong.chatroom.infrastructure.repository.mapper.UserMapper;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.util.Objects;

/**
 * @author wangchong.epochong
 * @date 2021/5/11 9:18
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Slf4j
public class UserQueryServiceImpl implements UserQueryService {

    private UserMapper userMapper = new UserMapper();

    @Override
    public BaseResp userLogin(UserDto userDto) {
        BaseResp baseResp = null;
        try {
            User user = userMapper.userLogin(userDto);
            if (user == null) {
                return new BaseResp(ResourceException.ACCOUNT_NOT_CORRECT);
            }
            if (user.getUserType().equals(Constant.INT_TO_USER_TYPE) && userDto.getUserType().equals(Constant.INT_FROM_USER_TYPE)) {
                return new BaseResp(ResourceException.NOT_FROM_USER);
            }
            if (user.getUserType().equals(Constant.INT_FROM_USER_TYPE) && userDto.getUserType().equals(Constant.INT_TO_USER_TYPE)) {
                return new BaseResp(ResourceException.NOT_TO_USER);
            }
            baseResp = new BaseResp(ResourceException.SUCCESS);
            baseResp.setObject(user);
        } catch (Exception e) {
            log.error("error() :{}", ExceptionUtils.getStackTrace(e));
        }
        return baseResp;
    }

    @Override
    public BaseResp queryUserById(UserDto userDto) {
        BaseResp baseResp;
        try {
            baseResp = BaseResp.getSuccessResp();
            User user = userMapper.queryById(UserAssembler.getUser(userDto));
            if (Objects.isNull(user)) {
                baseResp = new BaseResp(ResourceException.HAVE_NO_ID_USER);
                return baseResp;
            }
            baseResp.setObject(user);
        } catch (Exception e) {
            log.error("error(): {}", ExceptionUtils.getStackTrace(e));
            return BaseResp.getFailResp();
        }
        return baseResp;
    }
}
