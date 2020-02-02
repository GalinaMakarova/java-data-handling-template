package com.epam.izh.rd.online.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        File inputFile = new File("src/main/resources/sensitive_data.txt");
        if (inputFile.exists() && inputFile.isFile()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                String fileLine = reader.readLine();
                Pattern pattern = Pattern.compile("([0-9]+)\\s([0-9]+)\\s([0-9]+)\\s([0-9]+)");
                if (!fileLine.isEmpty()) {
                    Matcher matcher = pattern.matcher(fileLine);
                    if (matcher.find()) {
                        fileLine = matcher.replaceAll("$1 **** **** $4");
                        reader.close();
                        return fileLine;
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Метод должен считывать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        File inputFile = new File("src/main/resources/sensitive_data.txt");
        if (inputFile.exists() && inputFile.isFile()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                String fileLine = reader.readLine();
                String patternString = "(\\$\\{payment_amount\\})";
                Pattern pattern = Pattern.compile(patternString);
                if (!fileLine.isEmpty()) {
                    Matcher matcher = pattern.matcher(fileLine);
                    if (matcher.find()) {
                        patternString = String.format("%.0f", paymentAmount);
                        fileLine = matcher.replaceAll(patternString);
                    }
                    patternString = "(\\$\\{balance\\})";
                    pattern = Pattern.compile(patternString);
                    matcher = pattern.matcher(fileLine);
                    if (matcher.find()) {
                        patternString = String.format("%.0f", balance);
                        fileLine = matcher.replaceAll(patternString);
                    }
                    reader.close();
                    return fileLine;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
