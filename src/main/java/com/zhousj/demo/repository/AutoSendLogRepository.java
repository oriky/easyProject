package com.zhousj.demo.repository;

import com.zhousj.demo.entity.AutoSendLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @author zhousj
 * @date 2020/12/9
 */
@Component
public interface AutoSendLogRepository extends JpaRepository<AutoSendLog,String> {
}
