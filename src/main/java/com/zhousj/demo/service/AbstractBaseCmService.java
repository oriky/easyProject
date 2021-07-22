package com.zhousj.demo.service;


import com.zhousj.common.ext.jpa.BaseEntity;
import com.zhousj.demo.entity.CrmOrderCheck;
import com.zhousj.demo.pojo.AdvanceSearchPojo;
import com.zhousj.demo.util.ListUtil;
import com.zhousj.demo.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * 本项目JPA service实现类的增强类
 *
 * @author zhousj
 */
@SuppressWarnings({"unused"})
public abstract class AbstractBaseCmService<E extends BaseEntity> {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * 构建jpa精准查询Specification对象
     *
     * @param searchPojo 查询字段
     * @return Specification对象
     * @author zhousj
     */
    public Specification<E> buildSpecification(List<AdvanceSearchPojo> searchPojo) {
        return buildSpecification(searchPojo, null, (String[]) null);
    }


    /**
     * 构建jpa精准查询Specification对象
     *
     * @param searchPojo 查询字段
     * @param orderField 排序字段
     * @return Specification对象
     * @author zhousj
     */
    public Specification<E> buildSpecification(List<AdvanceSearchPojo> searchPojo, String... orderField) {
        return buildSpecification(searchPojo, null, orderField);
    }


    /**
     * 构建jpa精准查询Specification对象
     *
     * @param searchPojo 查询字段
     * @param desc       是否降序
     * @param orderField 排序字段
     * @return Specification对象
     * @author zhousj
     */
    public Specification<E> buildSpecification(List<AdvanceSearchPojo> searchPojo, boolean[] desc, @NotNull String... orderField) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (ListUtil.notEmpty(searchPojo)) {
                List<Predicate> predicates = ListUtil.newArrayList(searchPojo.size());
                searchPojo.forEach(pojo -> predicates.add(criteriaBuilder.equal(root.get(pojo.getOperCode()), pojo.getOperValue())));
                Predicate[] arr = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(arr));
            }
            if (!Objects.isNull(orderField) && orderField.length > 0) {
                List<Order> list = ListUtil.newArrayList(orderField.length);
                for (int i = 0; i < orderField.length; i++) {
                    Order asc = criteriaBuilder.asc(root.get(orderField[i]));
                    if (!Objects.isNull(desc) && desc[i]) {
                        asc.reverse();
                    }
                    list.add(asc);
                }
                criteriaQuery.orderBy(list);
            }
            return criteriaQuery.getRestriction();
        };
    }


    public Specification<E> buildPrivateSpecification(CrmOrderCheck check) {
        return buildPrivateSpecification(check, null);
    }


    public Specification<E> buildPrivateSpecification(CrmOrderCheck check, String orderBy) {
        return buildPrivateSpecification(check, orderBy, true);
    }

    /**
     * 自动重发接口查询，查询字段 deal_flag，deal_time >= 当前时间
     */
    public Specification<E> buildPrivateSpecification(CrmOrderCheck check, String orderBy, boolean asc) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = ListUtil.newArrayList();
            predicates.add(criteriaBuilder.equal(root.get("dealFlag"), check.getDealFlag()));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dealTime"), LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)) + " 00:00:00"));
            Predicate[] arr = new Predicate[predicates.size()];
            criteriaQuery.where(predicates.toArray(arr));
            if (StringUtil.nonNull(orderBy)) {
                Order ascOrder = criteriaBuilder.asc(root.get(orderBy));
                criteriaQuery.orderBy(asc ? ascOrder : ascOrder.reverse());
            }
            return criteriaQuery.getRestriction();
        };
    }
}
