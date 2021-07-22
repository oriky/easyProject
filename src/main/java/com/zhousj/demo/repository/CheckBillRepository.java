package com.zhousj.demo.repository;

import com.zhousj.common.ext.jpa.BaseRepository;
import com.zhousj.demo.entity.CheckBill;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhousj
 * @date 2021/5/7
 */
@Repository
public interface CheckBillRepository extends BaseRepository<CheckBill, String> {

    /**
     * 查询测试
     *
     * @param extCustOrderId 号码
     * @return 结果集
     */
    List<CheckBill> findByExtCustOrderId(String extCustOrderId);
}
