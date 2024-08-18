package ru.semenovmy.learning.currenciesconverter.data;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.semenovmy.learning.currenciesconverter.data.model.Currency;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ConversionRateTest {

    private ConversionRate mConversionRate;
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

        mConversionRate = new ConversionRate(mCurrencies);
    }

    @Test
    public void testGetConversion() {
        testGetConversion(null, 0, 0, "По курсу: 1.00 charCode1/charCode1");
        testGetConversion(new ArrayList<>(), 0, 1, "По курсу: 0.20 charCode1/charCode2");
        testGetConversion(mCurrencies, 1, 0, "По курсу: 5.00 charCode2/charCode1");
    }

    private void testGetConversion(List<Currency> currencies, int fromCurrencyWithIndex,
                                   int toCurrencyWithIndex, String expectedOutput) {
        // act
        String output = mConversionRate.getConversion(fromCurrencyWithIndex, toCurrencyWithIndex);

        // assert
        assertThat(output, is(expectedOutput));
    }
}