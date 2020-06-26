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
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.*;

import javax.inject.Inject;

@UiController("sample_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
@LoadDataBeforeShow
public class CustomerBrowse extends StandardLookup<Customer> {

    @Inject
    CustomersService customersService;

    @Inject
    CollectionContainer<Customer> customersDc;

    @Inject
    DataLoader customersDl;

    @Inject
    Notifications notifications;

    @Subscribe("customersTable.updateCustomer")
    protected void onUpdateCustomerActionPerformed(Action.ActionPerformedEvent event) {
        Customer selected = customersDc.getItemOrNull();
        if (selected != null) {
            Customer customer = customersService.updateCustomerById(selected.getId());
            customersDl.load();
            notifications.create().withCaption(customer.getLastOrder().toString()).show();
        }
    }

}