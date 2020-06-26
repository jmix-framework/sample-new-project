package com.company.sample.service.orders;

import com.company.sample.entity.orders.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Service
public class OrdersService {

    //TODO in this configuration, entities are not saved

    //To make it work, we need to get entity manager like this storeAwareLocator.getEntityManager(storeName);
    //But if we use JTA transaction, calling this service from CompositeJTAService
    //This service causes exception in PersistenceSupport.getInstanceContainerResourceHolder


    @PersistenceContext(unitName = "orders")
    EntityManager entityManager;

/*
    @Autowired
    StoreAwareLocator storeAwareLocator;
*/

    @Transactional("ordersTransactionManager")
    public Order saveOrder(Order order) {
        order.setOrderDate(new Date());
        order.setNumber((Integer.parseInt(order.getNumber())+1)+"");
        return entityManager.merge(order);
    }

/*
    @Transactional("ordersTransactionManager")
    public Order saveOrder(Order order) {
        order.setOrderDate(new Date());
        order.setNumber((Integer.parseInt(order.getNumber())+1)+"");
        return storeAwareLocator.getEntityManager("orders").merge(order);
    }
*/


}
