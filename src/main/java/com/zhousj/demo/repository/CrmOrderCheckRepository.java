package com.zhousj.demo.repository;

import com.zhousj.demo.entity.CrmOrderCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhousj
 * @date 2020/12/8
 */
@Component
public interface CrmOrderCheckRepository extends JpaRepository<CrmOrderCheck,String> , JpaSpecificationExecutor<CrmOrderCheck> {


    /**
     * 通过deal_flag,deal_time查询
     * @param dealFlag 稽核标志，操作符 =
     * @param dealTime 稽核时间，操作符 >=
     * @return CrmOderCheck集合
     * */
    List<CrmOrderCheck> findByDealFlagAndDealTimeIsGreaterThanEqual(String dealFlag, String dealTime);
}
