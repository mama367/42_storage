package com.mbc.day03.domain;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDAO {
    List<OrderDTO> orderList(int no);

    void addOrder(OrderDTO orderDTO);
}
