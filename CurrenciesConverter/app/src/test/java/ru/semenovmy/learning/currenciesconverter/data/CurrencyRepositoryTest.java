package ru.semenovmy.learning.currenciesconverter.data;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import ru.semenovmy.learning.currenciesconverter.data.model.Currency;
import ru.semenovmy.learning.currenciesconverter.data.model.CurrencyData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CurrencyRepositoryTest {

    private CurrencyRepository mCurrencyRepository;

    @Before
    public void setUp() {
        mCurrencyRepository = new CurrencyRepository();
    }

    @Test
    public void testConvert() {
        // arrange
        String id = "id";
        int numCode = 15;
        String charCode = "charCode";
        long nominal = 10L;
        String name = "Австралийский доллар";
        BigDecimal value = BigDecimal.ONE;

        List<CurrencyData> input = Collections.singletonList(new CurrencyData(
                id,
                numCode,
                charCode,
                nominal,
                name,
                value
        ));

        List<Currency> expectedOutput = Collections.singletonList(new Currency(
                id,
                charCode,
                nominal,
                name,
                value
        ));

        // act
        List<Currency> output = mCurrencyRepository.convert(input);

        // assert
        assertThat(output, is(expectedOutput));
    }
}