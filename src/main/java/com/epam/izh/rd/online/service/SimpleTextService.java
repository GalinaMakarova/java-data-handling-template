package com.epam.izh.rd.online.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTextService implements TextService {

    /**
     * Реализовать функционал удаления строки из другой строки.
     * <p>
     * Например для базовой строки "Hello, hello, hello, how low?" и строки для удаления ", he"
     * метод вернет "Hellollollo, how low?"
     *
     * @param base   - базовая строка с текстом
     * @param remove - строка которую необходимо удалить
     */
    @Override
    public String removeString(String base, String remove) {
        if (!base.isEmpty()) {
            String patternString = "(" + remove + ")";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(base);
            if (matcher.find()) {
                patternString = matcher.replaceAll("");
                return patternString;
            } else {
                return base;
            }
        }
        return null;
    }

    /**
     * Реализовать функционал проверки на то, что строка заканчивается знаком вопроса.
     * <p>
     * Например для строки "Hello, hello, hello, how low?" метод вернет true
     * Например для строки "Hello, hello, hello!" метод вернет false
     */
    @Override
    public boolean isQuestionString(String text) {
        Pattern pattern = Pattern.compile(".+\\?$");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    /**
     * Реализовать функционал соединения переданных строк.
     * <p>
     * Например для параметров {"Smells", " ", "Like", " ", "Teen", " ", "Spirit"}
     * метод вернет "Smells Like Teen Spirit"
     */
    @Override
    public String concatenate(String... elements) {
        if (elements.length > 0) {
            StringBuilder resultString = new StringBuilder();
            for (String bufferedString : elements) {
                resultString.append(bufferedString);
            }
            return resultString.toString();
        }
        return null;
    }

    /**
     * Реализовать функционал изменения регистра в вид лесенки.
     * Возвращаемый текст должен начинаться с прописного регистра.
     * <p>
     * Например для строки "Load Up On Guns And Bring Your Friends"
     * метод вернет "lOaD Up oN GuNs aNd bRiNg yOuR FrIeNdS".
     */
    @Override
    public String toJumpCase(String text) {
        if (text.length() != 0) {
            char[] inputText = text.toCharArray();
            for (int i = 0; i < inputText.length; i++) {
                if ((i % 2) != 0) {
                    inputText[i] = Character.toUpperCase(inputText[i]);
                } else {
                    inputText[i] = Character.toLowerCase(inputText[i]);
                }
            }
            StringBuilder resultString = new StringBuilder();
            for (char c : inputText) {
                resultString.append(c);
            }
            return resultString.toString();
        }
        return "";
    }

    /**
     * Метод определяет, является ли строка палиндромом.
     * <p>
     * Палиндром - строка, которая одинаково читается слева направо и справа налево.
     * <p>
     * Например для строки "а роза упала на лапу Азора" вернется true, а для "я не палиндром" false
     */
    @Override
    public boolean isPalindrome(String string) {
        if (string.length() > 1) {
            StringBuilder reverseInputString = new StringBuilder(removeString(string.toLowerCase(), " "));
            reverseInputString.reverse();
            return string.toLowerCase().replaceAll("\\s","").equals(reverseInputString.toString());
        }
        return false;
    }
}

