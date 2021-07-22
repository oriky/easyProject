package com.zhousj.common.ext.jpa;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * @author zhousj
 * @date 2021/4/27
 */
@SuppressWarnings("unused")
public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable> {

    private Type[] types;

    /**
     * 获取查询基础Repository，由子类实现
     *
     * @return BaseRepository接口
     */
    protected abstract BaseRepository<T, ID> getBaseRepository();

    public void save(T t) {
        getBaseRepository().save(t);
    }

    public void update(T t) {
        getBaseRepository().save(t);
    }

    public void delete(T t) {
        getBaseRepository().delete(t);
    }

    public Optional<T> findById(ID id) {
        return getBaseRepository().findById(id);
    }

    public List<T> findAll() {
        return getBaseRepository().findAll();
    }

    public List<T> findAll(Sort sort) {
        return getBaseRepository().findAll(sort);
    }

    public List<T> findAllById(Iterable<ID> ids) {
        return getBaseRepository().findAllById(ids);
    }

    public <S extends T> List<S> saveAll(Iterable<S> records) {
        return getBaseRepository().saveAll(records);
    }

    public void flush() {
        getBaseRepository().flush();
    }

    public <S extends T> S saveAndFlush(S record) {
        return getBaseRepository().saveAndFlush(record);
    }

    public void deleteInBatch(Iterable<T> records) {
        getBaseRepository().deleteInBatch(records);
    }

    public void deleteAllInBatch() {
        getBaseRepository().deleteAllInBatch();
    }

    public T getOne(ID id) {
        return getBaseRepository().getOne(id);
    }

    public <S extends T> List<S> findAll(Example<S> example) {
        return getBaseRepository().findAll(example);
    }

    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return getBaseRepository().findAll(example, sort);
    }

    public Optional<T> findOne(Specification<T> specification) {
        return getBaseRepository().findOne(specification);
    }

    public List<T> findAll(Specification<T> specification) {
        return getBaseRepository().findAll(specification);
    }

    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return getBaseRepository().findAll(specification, pageable);
    }

    public List<T> findAll(Specification<T> specification, Sort sort) {
        return getBaseRepository().findAll(specification, sort);
    }

    public long count(Specification<T> specification) {
        return getBaseRepository().count();
    }

    public long count() {
        return getBaseRepository().count();
    }

    public void deleteById(ID id) {
        getBaseRepository().deleteById(id);
    }


    public List<T> findByQuery(Query query) {
        Specification<T> specification = SpecificationHelper.buildSpecification0(query, findCurrentClassEntity());
        return findAll(specification);
    }

    public Page<T> findByQuery(Query query, Pageable pageable) {
        Specification<T> specification = SpecificationHelper.buildSpecification0(query, findCurrentClassEntity());
        return findAll(specification, pageable);
    }


    /**
     * 获取当前service实现类的泛型
     */
    public Type[] getTypes() {
        if (types != null) {
            return types;
        }
        Class<?> aClass = this.getClass();
        Type type = aClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            this.types = parameterizedType.getActualTypeArguments();
        }
        return types;
    }


    /**
     * 获取当前service实现类泛型对应的baseEntity
     */
    @SuppressWarnings("unchecked")
    public Class<T> findCurrentClassEntity() {
        Type[] types = getTypes();
        if (types == null || types.length == 0) {
            throw new IllegalArgumentException("获取泛型异常.");
        }
        return (Class<T>) types[0];
    }


}
