package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        Scanner in = new Scanner(System.in);
        while (true) {
            String s = in.nextLine();
            manager.runCommand(s);
        }
    }
}