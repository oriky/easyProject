package com.zhousj.common.ext.jpa;


import com.zhousj.common.ext.util.ListUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhousj
 * @date 2021/5/11
 */
@SuppressWarnings("unused")
public class SpecificationHelper {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 构建jpa精准查询Specification对象
     *
     * @param searchPojo 查询字段
     * @return Specification对象
     * @author zhousj
     */
    public static <E extends BaseEntity> Specification<E> buildSpecification(List<SearchPojo> searchPojo) {
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
    public static <E extends BaseEntity> Specification<E> buildSpecification(List<SearchPojo> searchPojo, String... orderField) {
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
    public static <E extends BaseEntity> Specification<E> buildSpecification(List<SearchPojo> searchPojo, boolean[] desc, String... orderField) {
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


    /**
     * 构建jpa高级查询Specification对象， 初版待后续完善
     *
     * @param query  查询对象，包含查询条件和排序字段
     * @param eClass 对应entity对象
     * @return Specification对象
     * @see Query 自定义查询对象，包含条件和排序
     */
    public static <E extends BaseEntity> Specification<E> buildSpecification0(Query query, Class<E> eClass) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<SearchPojo> searchPojo = query.getSearchPojo();
            if (ListUtil.notEmpty(searchPojo)) {
                List<Predicate> predicates = ListUtil.newArrayList(searchPojo.size());
                searchPojo.forEach(pojo -> {
                    Operator operator = pojo.getOperator();
                    buildConditions(operator, predicates, criteriaBuilder, root, pojo);
                });
                Predicate[] arr = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(arr));
            }
            List<FieldOrder> fieldOrderList = query.getFieldOrders();
            if (ListUtil.notEmpty(fieldOrderList)) {
                List<Order> list = ListUtil.newArrayList(fieldOrderList.size());
                fieldOrderList.forEach(order -> {
                    Order asc = criteriaBuilder.asc(root.get(order.getField()));
                    if (order.isDesc()) {
                        asc.reverse();
                    }
                    list.add(asc);
                });
                criteriaQuery.orderBy(list);
            }
            return criteriaQuery.getRestriction();
        };
    }

    public static <E extends BaseEntity> Specification<E> buildPojo(E e, Class<E> eClass) {
        Map<String, Object> value = fetchPojoValue(e);
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (value.size() > 0) {
                List<Predicate> predicates = ListUtil.newArrayList(value.size());
                value.forEach((k, v) -> predicates.add(criteriaBuilder.equal(root.get(k), v)));
                Predicate[] arr = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(arr));
            }
            return criteriaQuery.getRestriction();
        };
    }

    private static <E extends BaseEntity> Map<String, Object> fetchPojoValue(E e) {
        Class<? extends BaseEntity> eClass = e.getClass();
        Field[] fields = eClass.getDeclaredFields();
        PropertyDescriptor pd;
        Method readMethod;
        Object object;
        Map<String, Object> values = new HashMap<>(fields.length);
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            if (field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                pd = new PropertyDescriptor(field.getName(), eClass);
                readMethod = pd.getReadMethod();
                object = readMethod.invoke(e);
                if (object != null) {
                    values.put(field.getName(), object);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return values;
    }


    private static <E extends BaseEntity> void buildConditions(Operator operator, List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<E> root, SearchPojo pojo) {
        switch (operator) {
            case LESS_THAN:
                predicates.add(criteriaBuilder.lessThan(root.get(pojo.getOperCode()), pojo.getOperValue()));
                break;
            case LESS_THAN_EQUAL:
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(pojo.getOperCode()), pojo.getOperValue()));
                break;
            case GREAT_THAN:
                predicates.add(criteriaBuilder.greaterThan(root.get(pojo.getOperCode()), pojo.getOperValue()));
                break;
            case GREAT_THAN_EQUAL:
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(pojo.getOperCode()), pojo.getOperValue()));
                break;
            case IS_EMPTY:
                predicates.add(criteriaBuilder.isEmpty(root.get(pojo.getOperCode())));
                break;
            case IS_NOT_EMPTY:
                predicates.add(criteriaBuilder.isNotEmpty(root.get(pojo.getOperCode())));
                break;
            case IS_NULL:
                predicates.add(criteriaBuilder.isNull(root.get(pojo.getOperCode())));
                break;
            case IS_NOT_NULL:
                predicates.add(criteriaBuilder.isNotNull(root.get(pojo.getOperCode())));
                break;
            case LIKE:
                predicates.add(criteriaBuilder.like(root.get(pojo.getOperCode()), pojo.getOperValue()));
                break;
            default:
                predicates.add(criteriaBuilder.equal(root.get(pojo.getOperCode()), pojo.getOperValue()));
        }
    }
}
