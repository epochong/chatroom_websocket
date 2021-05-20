package com.epochong.chatroom.application.query.service.impl;

import com.epochong.chatroom.application.query.service.rpc.AipNlpQueryService;
import com.epochong.chatroom.application.query.service.rpc.impl.AipNlpQueryServiceImpl;
import com.epochong.chatroom.application.query.service.RobotQueryService;
import com.epochong.chatroom.controller.assember.RobotAssembler;
import com.epochong.chatroom.controller.dto.RobotDto;
import com.epochong.chatroom.domian.entity.Robot;
import com.epochong.chatroom.domian.value.BaseResp;
import com.epochong.chatroom.exception.ResourceException;
import com.epochong.chatroom.infrastructure.repository.mapper.RobotMapper;
import com.epochong.chatroom.infrastructure.repository.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

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
    private RobotMapper robotMapper = new RobotMapper();
    private AipNlpQueryService aipNlpQueryService = new AipNlpQueryServiceImpl();


    @Override
    public BaseResp queryAnswer(RobotDto dto) {
        BaseResp baseResp = new BaseResp(ResourceException.ROBOT_LAST_MESSAGE);
        try {
            if (StringUtils.isEmpty(dto.getFaq())) {
                return baseResp;
            }
            // 分词
            log.info("queryAnswer() 分词原始字符串:{}", dto.getFaq());
            List <String> stringSplit = aipNlpQueryService.getStringSplit(dto.getFaq());
            log.info("queryAnswer() 分词结果:{}", stringSplit);
            dto.setKeyWords(stringSplit);
            Robot robot = robotMapper.queryAnswer(dto);
            RobotDto robotDto = null;
            if (Objects.nonNull(robot)) {
                robotDto = RobotAssembler.getRobotDto(robot);
            }
            // 问题为空就是兜底方案
            if (Objects.isNull(robotDto.getFaq())) {
                baseResp.setMessage(robotDto.getAnswer());
                return baseResp;
            }
            // 问题不为空，说明匹配到了这个问题
            baseResp = BaseResp.getSuccessResp();
            baseResp.setMessage(Constant.ROBOT_MAYBE_ANSWER + robotDto.getFaq() + Constant.NEW_LINE
                    + Constant.ROBOT_MAYBE_ANSWER2 + robotDto.getAnswer());
        } catch (Exception e) {
            log.error("error:{}", ExceptionUtils.getStackTrace(e));
        }
        return baseResp;
    }

    @Override
    public BaseResp queryAnswerById(RobotDto dto) {
        Robot robot = RobotAssembler.getRobot(dto);
        BaseResp baseResp = BaseResp.getSuccessResp();
        baseResp.setObject(robotMapper.queryRobotById(robot));
        return baseResp;
    }

    @Override
    public List <Robot> getAll() {
        return robotMapper.getAll();
    }

    @Override
    public List <Robot> searchByFaq(RobotDto dto) {
        return robotMapper.searchByFaq(dto);
    }


}
