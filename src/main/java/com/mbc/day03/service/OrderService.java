package com.mbc.day03.service;

import com.mbc.day03.domain.OrderDAO;
import com.mbc.day03.domain.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    public List<OrderDTO> orderList(int no){ return orderDAO.orderList(no); }

    public void addOrder(OrderDTO orderDTO){
        orderDAO.addOrder(orderDTO);
    }
}
