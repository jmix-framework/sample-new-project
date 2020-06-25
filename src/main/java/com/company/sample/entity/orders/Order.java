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

package com.company.sample.entity.orders;

import com.company.sample.entity.customers.Customer;
import io.jmix.core.entity.annotation.SystemLevel;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.ModelProperty;
import io.jmix.core.metamodel.annotation.Store;
import io.jmix.data.entity.BaseLongIdEntity;
import org.apache.commons.lang3.RandomUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Store(name = "orders")
@Table(name = "SAMPLE_ORDER")
@Entity(name = "sample_Order")
public class Order extends BaseLongIdEntity {
    private static final long serialVersionUID = -8651639140562527033L;

    @Column(name = "NUMBER_")
    @InstanceName
    private String number;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    private Date date;

    @SystemLevel
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @NotNull
    @Transient
    @ModelProperty(related = "customerId", mandatory = true)
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customerId = customer.getId();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @PrePersist
    void prePersist() {
        if (getId() == null) {
            setId(RandomUtils.nextLong());
        }
    }

}