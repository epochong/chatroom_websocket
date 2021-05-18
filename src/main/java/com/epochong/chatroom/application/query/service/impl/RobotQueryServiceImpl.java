package com.epochong.chatroom.application.query.service.impl;

import com.epochong.chatroom.application.query.rpc.AipNlpQueryService;
import com.epochong.chatroom.application.query.rpc.impl.AipNlpQueryServiceImpl;
import com.epochong.chatroom.application.query.service.RobotQueryService;
import com.epochong.chatroom.controller.assember.RobotAssembler;
import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.entity.Robot;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.exception.ResourceException;
import com.epochong.chatroom.infrastructure.repository.dao.RobotDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author wangchong.epochong
 * @date 2021/5/17 15:54
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
@Slf4j
public class RobotQueryServiceImpl implements RobotQueryService {
    private RobotDao robotDao = new RobotDao();
    private AipNlpQueryService aipNlpQueryService = new AipNlpQueryServiceImpl();
    @Override
    public BaseResp queryAnswer(RobotDto dto) {
        BaseResp baseResp = new BaseResp(ResourceException.ROBOT_LAST_MESSAGE);
        try {
            if (StringUtils.isEmpty(dto.getFaq())) {
                return baseResp;
            }
            // 分词
            List <String> stringSplit = aipNlpQueryService.getStringSplit(dto.getFaq());
            log.info("queryAnswer() 分词原始字符串:{}", dto.getFaq());
            log.info("queryAnswer() 分词结果:{}", stringSplit);
            if (CollectionUtils.isEmpty(stringSplit)) {
                return baseResp;
            }
            dto.setKeyWords(stringSplit);
            Robot robot = robotDao.queryAnswer(dto);
            RobotDto robotDto = null;
            if (Objects.nonNull(robot)) {
                robotDto = RobotAssembler.getRobotDto(robot);
            }
            // 问题不为空就不是兜底方案
            if (Objects.nonNull(robotDto.getFaq())) {
                baseResp.setCode(ResourceException.SUCCESS.getCode());
            }
            baseResp.setMessage(robotDto.getAnswer());
        } catch (Exception e) {
            log.error("error:{}", ExceptionUtils.getStackTrace(e));
        }
        return baseResp;
    }
}
