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
import com.company.sample.service.ComplexService;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;

import javax.inject.Inject;
import java.util.Set;

@UiController("sample_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderBrowse extends StandardLookup<Order> {

    @Inject
    ComplexService complexService;

    @Inject
    GroupTable<Order> ordersTable;

    @Subscribe("ordersTable.invokeService")
    protected void onInvokeServiceActionPerformed(Action.ActionPerformedEvent event) {
        Set<Order> selected = ordersTable.getSelected();
        if (selected != null && selected.size() > 0) {
            complexService.updateCustomerAndOrder(selected.iterator().next());
        }
    }


}