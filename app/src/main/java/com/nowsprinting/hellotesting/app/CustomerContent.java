package com.nowsprinting.hellotesting.app;

import com.nowsprinting.hellotesting.app.models.Customer;

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
        ITEM_MAP.put(customer.getName(), customer);
    }

    //TODO: It's Dummy
    static {
        // Add 3 sample items.
        addItem(new Customer("Mike"));
        addItem(new Customer("Tom"));
    }
}
