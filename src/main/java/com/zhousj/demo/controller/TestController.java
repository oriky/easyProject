package com.zhousj.demo.controller;

import com.zhousj.common.pojo.Operator;
import com.zhousj.common.pojo.Query;
import com.zhousj.common.pojo.SpecificationHelper;
import com.zhousj.demo.entity.Agreement;
import com.zhousj.demo.entity.CheckBill;
import com.zhousj.demo.repository.AgreementRepository;
import com.zhousj.demo.repository.CheckBillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhousj
 * @date 2020/12/8
 */
@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Resource
    private AgreementRepository agreementRepository;

    @Resource
    private CheckBillRepository checkBillRepository;

    @GetMapping("/test")
    public Object test() {
        LOGGER.debug("==========================>");
        Map<String, Object> map = new HashMap<>(8);
        Optional<Agreement> optional = agreementRepository.findById("12364085027");
        Query query = Query.newQuery();
        query.addSearch("extCustOrderId","0745", Operator.EQUAL);
        query.addSearch("seq","0", Operator.GREAT_THAN_EQUAL);
        query.addOrder("broadAccount",true);
        query.addOrder("flag");
        Specification<CheckBill> specification = SpecificationHelper.buildSpecification0(query,CheckBill.class);
        List<CheckBill> all = checkBillRepository.findAll(specification);
        System.out.println(all);
        map.put("msg", "ok");
        map.put("data", optional);
        System.out.println();
        return map;
    }

}
