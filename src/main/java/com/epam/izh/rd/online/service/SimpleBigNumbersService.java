package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     *
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        BigDecimal precisionNumber = BigDecimal.valueOf(a);
        precisionNumber = precisionNumber.divide(BigDecimal.valueOf(b), range, BigDecimal.ROUND_DOWN);
        return precisionNumber;
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        BigInteger number = BigInteger.valueOf(3);
        List<BigInteger> primeNumbersArray = new ArrayList<>();
        primeNumbersArray.add(BigInteger.valueOf(2));
        primeNumbersArray.add(BigInteger.valueOf(3));
        while (primeNumbersArray.size() != range + 1) {
            number = number.add(BigInteger.valueOf(1));
            BigInteger sqrtOfNumber = sqrt(number);
            boolean isSimple = true;
            for (BigInteger i = BigInteger.valueOf(2); i.compareTo(sqrtOfNumber) <= 0; i = i.add(BigInteger.valueOf(1))) {
                int modResult = number.mod(i).compareTo(BigInteger.valueOf(0));
                int compareResult = number.compareTo(sqrtOfNumber);
                if (modResult == 0 && compareResult != 0) {
                    isSimple = false;
                    break;
                }
            }
            if (isSimple) {
                primeNumbersArray.add(number);
            }
        }
        return primeNumbersArray.get(range);
    }

    private static BigInteger sqrt(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength() / 2);
        BigInteger div2 = div;
        for (; ; ) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }
}
