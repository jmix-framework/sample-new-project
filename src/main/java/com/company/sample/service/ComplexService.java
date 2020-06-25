package com.company.sample.service;

import com.company.sample.entity.customers.Customer;
import com.company.sample.entity.orders.Order;
import com.company.sample.service.customers.CustomersService;
import com.company.sample.service.orders.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComplexService {

    private final Logger logger = LoggerFactory.getLogger(ComplexService.class);

    final CustomersService customersService;

    final OrdersService ordersService;

    public ComplexService(CustomersService customersService, OrdersService ordersService) {
        this.customersService = customersService;
        this.ordersService = ordersService;
    }

    @Transactional
    public void updateCustomerAndOrder(Order order) {
        Customer customer = customersService.updateCustomerById(order.getCustomerId());
        Order updatedOrder = ordersService.saveOrder(order);
        logger.info(customer+" "+order);
        if (updatedOrder.getNumber().contains("8")) {
            throw new RuntimeException("Test Exception");
        }
    }

}
