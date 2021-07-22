package com.zhousj.demo.repository;

import com.zhousj.common.ext.jpa.BaseRepository;
import com.zhousj.demo.entity.Agreement;
import org.springframework.stereotype.Component;

/**
 * @author zhousj
 * @date 2020/12/8
 */
@Component
public interface AgreementRepository extends BaseRepository<Agreement,String> {
}
