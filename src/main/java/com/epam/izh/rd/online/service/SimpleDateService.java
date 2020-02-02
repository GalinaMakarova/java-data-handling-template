package com.epam.izh.rd.online.service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class SimpleDateService implements DateService {

    /**
     * Метод парсит дату в строку
     *
     * @param localDate дата
     * @return строка с форматом день-месяц-год(01-01-1970)
     */
    @Override
    public String parseDate(LocalDate localDate) {
        Date inputDate = Date.valueOf(localDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(inputDate);
    }

    /**
     * Метод парсит строку в дату
     *
     * @param string строка в формате год-месяц-день часы:минуты (1970-01-01 00:00)
     * @return дата и время
     */
    @Override
    public LocalDateTime parseString(String string) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(string, dateTimeFormatter);
    }

    /**
     * Метод конвертирует дату в строку с заданным форматом
     *
     * @param localDate исходная дата
     * @param formatter формат даты
     * @return полученная строка
     */
    @Override
    public String convertToCustomFormat(LocalDate localDate, DateTimeFormatter formatter) {
        return formatter.format(localDate);
    }

    /**
     * Метод получает следующий високосный год
     *
     * @return високосный год
     */
    @Override
    public long getNextLeapYear() {
        if (getSecondsInYear(LocalDate.now().getYear()) != getSecondsInYear(2000)) {
            return LocalDate.now().getYear() + 1;
        } else
            return LocalDate.now().getYear();
    }

    /**
     * Метод считает число секунд в заданном году
     *
     * @return число секунд
     */
    @Override
    public long getSecondsInYear(int year) {
        Date startDate = new Date(0);
        startDate.setYear(year - 1900);
        Date endDate = new Date(0);
        endDate.setYear(year + 1 - 1900);
        return (endDate.getTime() - startDate.getTime()) / 1000;
    }


}
