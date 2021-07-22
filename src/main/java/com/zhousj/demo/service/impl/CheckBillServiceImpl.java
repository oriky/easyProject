package com.zhousj.demo.service.impl;

import com.zhousj.common.ext.jpa.BaseRepository;
import com.zhousj.common.ext.jpa.BaseServiceImpl;
import com.zhousj.demo.entity.CheckBill;
import com.zhousj.demo.repository.CheckBillRepository;
import com.zhousj.demo.service.CheckBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhousj
 * @date 2021/5/7
 */
@Service
public class CheckBillServiceImpl extends BaseServiceImpl<CheckBill,String> implements CheckBillService {

    private CheckBillRepository checkBillRepository;

    @Autowired
    public void setCheckBillRepository(CheckBillRepository checkBillRepository) {
        this.checkBillRepository = checkBillRepository;
    }

    @Override
    protected BaseRepository<CheckBill, String> getBaseRepository() {
        return checkBillRepository;
    }
}
