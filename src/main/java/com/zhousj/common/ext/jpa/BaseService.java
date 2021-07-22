package com.zhousj.common.ext.jpa;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author zhousj
 * @date 2020-12-8
 */
@SuppressWarnings("unused")
public interface BaseService<T extends BaseEntity, ID extends Serializable> {


    /**
     * 新增对象
     *
     * @param t 继承baseEntity对象
     */
    void save(T t);

    /**
     * 修改对象
     *
     * @param t 继承baseEntity对象
     */
    void update(T t);

    /**
     * 删除对象
     *
     * @param t 继承baseEntity对象
     */
    void delete(T t);

    /**
     * 通过id查询对象
     *
     * @param id 对象主键
     * @return optional容器对象
     */
    Optional<T> findById(ID id);

    /**
     * 查询所有行数
     *
     * @return 结果集
     */
    List<T> findAll();

    /**
     * 排序查询所有行数
     *
     * @param sort 排序
     * @return 结果集
     */
    List<T> findAll(Sort sort);

    /**
     * 通过id查询所有行数
     *
     * @param ids Iterable接口实现类，如ArrayList集合
     * @return 结果集
     */
    List<T> findAllById(Iterable<ID> ids);

    /**
     * 保存所有记录
     *
     * @param records 保存对象集合
     * @return 保存成功对象集合
     */
    <S extends T> List<S> saveAll(Iterable<S> records);

    /**
     * 刷新缓存
     */
    void flush();

    /**
     * 保存并刷新缓存
     *
     * @param record 保存对象
     * @return 当前对象
     */
    <S extends T> S saveAndFlush(S record);

    /**
     * 批量删除并刷新缓存
     *
     * @param records 批量删除对象
     */
    void deleteInBatch(Iterable<T> records);

    /**
     * 删除全部记录并刷新缓存
     */
    void deleteAllInBatch();


    /**
     * 通过id查询一条记录
     *
     * @param id 数据id
     * @return 结果对象
     */
    T getOne(ID id);


    /**
     * 通过example对象查询所有数据
     *
     * @param example example查询对象
     * @return 结果集
     * @see Example
     */
    <S extends T> List<S> findAll(Example<S> example);


    /**
     * 通过example对象查询所有数据并排序
     *
     * @param example example查询对象
     * @param sort    排序
     * @return 结果集
     * @see Example
     * @see Sort
     */
    <S extends T> List<S> findAll(Example<S> example, Sort sort);


    /**
     * 通过specification对象查询一条记录
     *
     * @param specification jpa高级查询对象
     * @return optional容器对象
     * @see Specification
     */
    Optional<T> findOne(@Nullable Specification<T> specification);


    /**
     * 通过specification对象查询所有记录
     *
     * @param specification jpa高级查询对象
     * @return 结果集
     * @see Specification
     */
    List<T> findAll(@Nullable Specification<T> specification);


    /**
     * 通过specification对象和pageable对象分页查询
     *
     * @param specification jpa高级查询对象
     * @param pageable      分页对象
     * @return 分页Page对象
     */
    Page<T> findAll(@Nullable Specification<T> specification, Pageable pageable);


    /**
     * 通过specification对象查询所有行数并排序
     *
     * @param specification jpa高级查询对象
     * @param sort          排序对象
     * @return 结果集
     */
    List<T> findAll(@Nullable Specification<T> specification, Sort sort);


    /**
     * 通过specification对象查询所有行数
     *
     * @param specification jpa高级查询对象
     * @return 结果集
     */
    long count(@Nullable Specification<T> specification);

    /**
     * 获取所有行数
     *
     * @return 行数
     */
    long count();

    /**
     * 通过id删除
     *
     * @param id 记录id
     */
    void deleteById(ID id);

    /**
     * 复杂查询获取结果集
     * @param query 查询条件{@link Query}
     * @return 结果集
     * */
    List<T> findByQuery(Query query);

    /**
     * 分页复杂查询获取结果集
     * @param query 查询条件{@link Query}
     * @param pageable 分页信息
     * @return 分页结果集
     * */
    Page<T> findByQuery(Query query, Pageable pageable);

}
