/*
 * Copyright 2020 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.company.sample.screen.order;

import com.company.sample.entity.orders.Order;
import com.company.sample.service.CompositeJTAService;
import com.company.sample.service.orders.OrdersService;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.*;

import javax.inject.Inject;

@UiController("sample_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderBrowse extends StandardLookup<Order> {

    @Inject
    CompositeJTAService compositeJTAService;

    @Inject
    OrdersService ordersService;


    @Inject
    CollectionContainer<Order> ordersDc;

    @Inject
    DataLoader ordersDl;

    @Subscribe("ordersTable.invokeService")
    protected void onInvokeServiceActionPerformed(Action.ActionPerformedEvent event) {
        Order selected = ordersDc.getItemOrNull();
        if (selected != null) {
            compositeJTAService.updateCustomerAndOrder(selected);
        }
        ordersDl.load();
    }

    @Subscribe("ordersTable.updateOrder")
    protected void onUpdateOrderActionPerformed(Action.ActionPerformedEvent event) {
        Order selected = ordersDc.getItemOrNull();
        if (selected != null) {
            ordersService.saveOrder(selected);
        }
        ordersDl.load();
    }

}