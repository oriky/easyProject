package com.zhousj.demo.repository;

import com.zhousj.demo.entity.AgreementErr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @author zhousj
 * @date 2020/12/8
 */
@Component
public interface AgreementErrRepository extends JpaRepository<AgreementErr,String> {
}
