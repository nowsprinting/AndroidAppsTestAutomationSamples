/*
 * Copyright 2014 Koji Hasegawa
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

package com.nowsprinting.hellotesting.app;

import com.nowsprinting.hellotesting.app.models.Customer;
import com.nowsprinting.hellotesting.app.models.Gender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入力されている顧客ストア
 *
 * @author Koji Hasegawa
 */
public class CustomerContent {

    /**
     * An array of customers.
     */
    public static List<Customer> ITEMS = new ArrayList<Customer>();

    /**
     * A map of customers, by ID.
     */
    public static Map<String, Customer> ITEM_MAP = new HashMap<String, Customer>();

    private static void addItem(Customer customer) {
        ITEMS.add(customer);
        ITEM_MAP.put(customer.getId(), customer);
    }

    /**
     * 顧客を追加し、そのidを返す
     * @return 追加した顧客のid
     */
    public static String addCustomer(){
        Customer newCustomer = new Customer();
        addItem(newCustomer);
        return newCustomer.getId();
    }
}
