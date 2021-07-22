package com.zhousj.common.ext.util;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * list常用工具类
 *
 * @author zhousj
 * @date 2020-11-25
 */
@SuppressWarnings("unused")
public class ListUtil {

    /**
     * 构造arraylist
     */
    public static <T> ArrayList<T> newArrayList() {
        return newArrayList(10);
    }

    /**
     * 构造arraylist
     */
    public static <T> ArrayList<T> newArrayList(int caps) {
        return new ArrayList<>(caps);
    }

    /**
     * 构造arraylist
     */
    public static <T> ArrayList<T> newArrayList(Collection<T> collection) {
        return new ArrayList<>(collection);
    }


    /**
     * 构造linkedList
     */
    public static <T> LinkedList<T> newLinkedList() {
        return new LinkedList<>();
    }

    /**
     * 构造linkedList
     */
    public static <T> LinkedList<T> newLinkedList(Collection<T> collection) {
        return new LinkedList<>(collection);
    }

    /**
     * list空判断
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * list非空判断
     */
    public static <T> boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }


    /**
     * iterator 转换list
     */
    public static <T> List<T> toList(Iterable<T> iterator) {
        List<T> list = newArrayList();
        iterator.forEach(list::add);
        return list;
    }


    public static <E, T> List<T> mapToList(List<E> list, Function<E, T> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }

    /**
     * list多条件排序
     *
     * @param <E>      泛型，list中元素类型
     * @param <U>      泛型，由泛型<E>获取，需自身实现comparable接口
     * @param list     源list
     * @param function 排序条件数组
     * @author zhousj
     * @date 2020-11-25
     * @see Function#apply(Object)
     * @see Comparable#compareTo(Object)
     */
    @SafeVarargs
    public static <E, U extends Comparable<? super U>> void sort(List<E> list, Function<E, U>... function) {
        Comparator<E> comparator = null;
        for (Function<E, U> euFunction : function) {
            if (comparator == null) {
                comparator = Comparator.comparing(euFunction);
            } else {
                comparator.thenComparing(euFunction);
            }
        }
        list.sort(comparator);
    }


    /**
     * 多条件排序
     *
     * @param <E>      泛型，list中元素类型
     * @param <U>      泛型，由泛型<E>获取，需自身实现comparable接口
     * @param list     源list
     * @param asc      是否正序，bol类型数组，每个元素需对应function数组元素，true为正序，false为倒序
     * @param function 排序条件数组
     * @author zhousj
     * @date 2020-11-25
     * @see Function#apply(Object)
     * @see Comparable#compareTo(Object)
     */
    @SafeVarargs
    public static <E, U extends Comparable<? super U>> void sort(List<E> list, boolean[] asc, Function<E, U>... function) {
        Comparator<E> comparator = null;
        for (int i = 0; i < function.length; i++) {
            if (comparator == null) {
                comparator = Comparator.comparing(function[i]);
            } else {
                comparator.thenComparing(function[i]);
            }
            if (!asc[i]) {
                comparator.reversed();
            }
        }
        list.sort(comparator);
    }

    /**
     * list转换map
     *
     * @param list 原数组
     * @param key  function接口 {@link Function#apply(Object)}，自定义实现
     * @param val  function接口 {@link Function#apply(Object)}，自定义实现
     * @author zhousj
     * @date 2020-11-25
     */
    public static <E, K, V> Map<K, V> toMap(List<E> list, Function<E, K> key, Function<E, V> val) {
        return toMap(list, key, val, (oldVal, newVal) -> newVal);
    }


    /**
     * list转换map
     *
     * @param list  源list
     * @param key   function接口，自定义实现
     * @param val   function接口，自定义实现
     * @param merge binaryOperator接口 {@link BinaryOperator#apply(Object, Object)}，当key重复时val的取值
     * @author zhousj
     * @date 2020-11-25
     */
    public static <E, K, V> Map<K, V> toMap(List<E> list, Function<E, K> key, Function<E, V> val, BinaryOperator<V> merge) {
        return list.stream().collect(Collectors.toMap(key, val, merge));
    }


    /**
     * 拼接list元素某一个属性字符串
     *
     * @param list     源list
     * @param function 拼接具体属性
     * @param joinStr  属性之间拼接字符
     * @return 拼接后字符串
     * @author zhousj
     * @date 2020-11-25
     */
    public static <E> String join(List<E> list, Function<E, ? extends CharSequence> function, String joinStr) {
        return list.stream().map(function).collect(Collectors.joining(joinStr));
    }


    /**
     * 按list中元素某个字段分组
     *
     * @param list  源list
     * @param group 分组字段
     * @return 分组后map
     * @author zhousj
     * @date 2020-11-25
     */
    public static <E, K> Map<K, List<E>> groupBy(List<E> list, Function<E, K> group) {
        return list.stream().collect(Collectors.groupingBy(group));
    }

    /**
     * 按list中元素某个字段分组
     *
     * @param list  源list
     * @param group 分组字段
     * @param val   分组提取list元素的某个属性
     * @return 分组后map
     * @author zhousj
     * @date 2020-11-25
     */
    public static <E, K, T> Map<K, List<T>> groupBy(List<E> list, Function<E, K> group, Function<E, T> val) {
        return list.stream().collect(Collectors.groupingBy(group, Collectors.mapping(val,
                Collector.of(ArrayList::new, List::add, (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                }))));
    }


    public static <E> boolean equals(List<E> fList, List<E> sList) {
        if (fList.size() != sList.size()) {
            return false;
        }
        List<E> listF = newArrayList(fList);
        List<E> listS = newArrayList(sList);
        listF.removeAll(listS);
        return listF.size() == 0;
    }


    public static <E> List<List<E>> splitList(List<E> list, int maxSize) {
        int count = list.size() / maxSize;
        if (list.size() % maxSize != 0) {
            count++;
        }
        List<List<E>> result = new ArrayList<>(count);
        int index = 0;
        while (index < count) {
            List<E> temp = list.subList(index * maxSize, Math.min((index + 1) * maxSize, list.size()));
            result.add(temp);
            index++;
        }
        return result;
    }
}
