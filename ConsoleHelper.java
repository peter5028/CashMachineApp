package com.codegym.task.task26.task2613;

import com.codegym.task.task26.task2613.exception.InterruptedOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {

    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + ".resources.common");
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptedOperationException {
        try {
            String text = bis.readLine();
            if ("exit".equals(text.toLowerCase())) {
                throw new InterruptedOperationException();
            }

            return text;
        } catch (IOException ignored) { //suppose it will never occur
        }
        return null;
    }

    public static String requestCurrencyCode() throws InterruptedOperationException {
        while (true) {
            ConsoleHelper.writeMessage(res.getString("choose.currency.code"));
            String currencyCode = ConsoleHelper.readString();
            if (currencyCode == null || currencyCode.trim().length() != 3) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
                continue;
            }
            return currencyCode.trim().toUpperCase();
        }
    }

    public static String[] getTwoValidNumbers(String currencyCode) throws InterruptedOperationException {
        while (true) {
            ConsoleHelper.writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
            String s = ConsoleHelper.readString();
            String[] split = null;
            if (s == null || (split = s.split(" ")).length != 2) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            } else {
                try {
                    if (Integer.parseInt(split[0]) <= 0 || Integer.parseInt(split[1]) <= 0)
                        ConsoleHelper.writeMessage(res.getString("invalid.data"));
                } catch (NumberFormatException e) {
                    ConsoleHelper.writeMessage(res.getString("invalid.data"));
                    continue;
                }
                return split;
            }
        }
    }

    public static Operation requestOperation() throws InterruptedOperationException {
        while (true) {
            ConsoleHelper.writeMessage(res.getString("choose.operation"));
            ConsoleHelper.writeMessage("\t 1 - " + res.getString("operation.INFO"));
            ConsoleHelper.writeMessage("\t 2 - " + res.getString("operation.DEPOSIT"));
            ConsoleHelper.writeMessage("\t 3 - " + res.getString("operation.WITHDRAW"));
            ConsoleHelper.writeMessage("\t 4 - " + res.getString("operation.EXIT"));
            Integer i = Integer.parseInt(ConsoleHelper.readString().trim());
            try {
                return Operation.getAllowableOperationByOrdinal(i);
            } catch (IllegalArgumentException e) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            }
        }
    }

    public static void printExitMessage() {
        ConsoleHelper.writeMessage(res.getString("the.end"));
    }

}
