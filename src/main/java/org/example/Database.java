package org.example;

import java.util.*;

public class Database {
    private HashMap<String, String> values = new HashMap<>();
    private HashMap<String, Integer> counts = new HashMap<>();

    private Stack<Stack<String>> transactions = new Stack<>();

    public String get(String name) {
        return values.get(name);
    }

    public void set(String name, String value) {
        // Add inverse operation to transactions if one exists
        if (transactions.size() > 0) {
            String currentValue = values.get(name);
            if (currentValue == null) {
                transactions.lastElement().push("DELETE " + name);
            } else {
                transactions.lastElement().push("SET " + name + " " + currentValue);
            }
        }

        subtractExistingCount(name);
        values.put(name, value);
        addAndIncrementCount(value);
    }

    // Add the value to our datastore and increment its count
    private void addAndIncrementCount(String value) {
        Integer existing = counts.get(value);
        if (existing == null) {
            counts.put(value, 1);
        } else {
            counts.put(value, existing + 1);
        }
    }

    private void subtractExistingCount(String name) {
        String existing = values.get(name);
        if (existing != null) {
            int count = counts.get(existing);
            counts.put(existing, count - 1);
        }
    }

    public Integer getCount(String value) {
        return counts.get(value);
    }

    public void delete(String name) {
        // Add inverse operation to transactions if one exists
        if (transactions.size() > 0) {
            transactions.lastElement().push("SET " + name + " " + values.get(name));
        }

        subtractExistingCount(name);
        values.remove(name);
    }

    public void begin() {
        transactions.push(new Stack<>());
    }

    public void commit() {
        transactions = new Stack<>();
    }

    public Stack<String> rollBack() {
        return transactions.pop();
    }
}
