package ru.semenovmy.learning.currenciesconverter.data;

import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.semenovmy.learning.currenciesconverter.data.model.Currency;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CurrencyConverterTest {

    private CurrencyConverter mCurrencyConverter;
    private String mCharCode1 = "charCode1";
    private BigDecimal mValue1 = BigDecimal.ONE;
    private String mCharCode2 = "charCode2";
    private BigDecimal mValue2 = BigDecimal.TEN;
    private List<Currency> mCurrencies;

    @Before
    public void setUp() {
        String id1 = "id1";
        long nominal1 = 10L;
        String name1 = "name1";
        String id2 = "id2";
        long nominal2 = 20L;
        String name2 = "name2";
        mCurrencies = Arrays.asList(
                new Currency(
                        id1,
                        mCharCode1,
                        nominal1,
                        name1,
                        mValue1
                ),
                new Currency(
                        id2,
                        mCharCode2,
                        nominal2,
                        name2,
                        mValue2
                )
        );
        mCurrencyConverter = new CurrencyConverter(mCurrencies);
    }

    @Test
    public void testGetConversion() {
        testGetResultOfConversion(null, 0, 0, 10,"Итого: 10.00 charCode1");
        testGetResultOfConversion(new ArrayList<>(), 0, 0, 0,"Итого: 0.00 charCode1");
        testGetResultOfConversion(mCurrencies, 0, 1, 2,"Итого: 0.40 charCode2");
    }

    private void testGetResultOfConversion(List<Currency> currencies, int fromCurrencyWithIndex,
                                   int toCurrencyWithIndex, double mAmount, String expectedOutput) {
        // act
        String output = mCurrencyConverter.getResultOfConversion(fromCurrencyWithIndex, toCurrencyWithIndex, mAmount);

        // assert
        assertThat(output, is(expectedOutput));
    }
}