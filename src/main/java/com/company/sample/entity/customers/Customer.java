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

package com.company.sample.entity.customers;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.Store;
import io.jmix.data.entity.BaseLongIdEntity;
import org.apache.commons.lang3.RandomUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Store(name = "main")
@Table(name = "SAMPLE_CUSTOMER")
@Entity(name = "sample_Customer")
public class Customer extends BaseLongIdEntity {
    private static final long serialVersionUID = -5988481163391808691L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    @InstanceName
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_ORDER_DATE")
    private Date lastOrder;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(Date lastOrder) {
        this.lastOrder = lastOrder;
    }

    @PrePersist
    void prePersist() {
        if (getId() == null) {
            setId(RandomUtils.nextLong());
        }
    }
}