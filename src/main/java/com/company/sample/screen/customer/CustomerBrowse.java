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

package com.company.sample.screen.customer;

import com.company.sample.entity.customers.Customer;
import com.company.sample.service.customers.CustomersService;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;

import javax.inject.Inject;
import java.util.Set;

@UiController("sample_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
@LoadDataBeforeShow
public class CustomerBrowse extends StandardLookup<Customer> {

    @Inject
    CustomersService customersService;

    @Inject
    GroupTable<Customer> customersTable;

    @Inject
    Notifications notifications;

    @Subscribe("customersTable.updateCustomer")
    protected void onUpdateCustomerActionPerformed(Action.ActionPerformedEvent event) {
        Set<Customer> selected = customersTable.getSelected();
        if (selected != null && selected.size() > 0) {
            Customer customer = customersService.updateCustomerById(selected.iterator().next().getId());
            notifications.create().withCaption(customer.getLastOrder().toString()).show();
        }
    }

}