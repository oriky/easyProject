package com.zhousj.demo.task;

import com.zhousj.demo.repository.AutoSendLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableScheduling
@Async
public class AlarmTaskFiveresend {


    @Resource
    private AutoSendLogRepository autoSendLogRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(AlarmTaskFiveresend.class);

    public void autoSend() {

    }

}
