package org.example;

import java.util.EmptyStackException;
import java.util.Stack;

public class Manager {

    Database database = new Database();
    public void runCommand(String command) {
        String result = getResult(command);
        if (result != null) {
            System.out.println(result);
        }
    }

    public String getResult(String command) {
        String[] args = command.split(" ");
        switch (Command.valueOf(args[0].toUpperCase())) {
            case SET -> {
                database.set(args[1], args[2]);
            }
            case GET -> {
                String result = database.get(args[1]);
                return result == null ? "NULL" : result;
            }
            case COUNT -> {
                Integer result = database.getCount(args[1]);
                return String.valueOf(result == null ? 0 : result);
            }
            case DELETE -> {
                database.delete(args[1]);
            }
            case BEGIN -> {
                database.begin();
            }
            case COMMIT -> {
                database.commit();
            }
            case ROLLBACK -> {
                try {
                    Stack<String> transaction = database.rollBack();
                    while(!transaction.isEmpty()) {
                        getResult(transaction.pop());
                    }

                } catch (EmptyStackException e) {
                    return "TRANSACTION NOT FOUND";
                }
            }
            case END -> System.exit(0);
        }
        return null;
    }

}
