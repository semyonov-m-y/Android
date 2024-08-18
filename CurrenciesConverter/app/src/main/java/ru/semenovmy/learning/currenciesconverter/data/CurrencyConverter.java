package ru.semenovmy.learning.currenciesconverter.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import ru.semenovmy.learning.currenciesconverter.data.model.Currency;

public class CurrencyConverter {

    private List<Currency> mListOfCurrency;

    public CurrencyConverter(List<Currency> listOfCurrency) {
        this.mListOfCurrency = listOfCurrency;
    }

    public String getResultOfConversion(int mSpinnerFromPosition, int mSpinnerToPosition, double mAmount) {
        Currency fromCurrency = mListOfCurrency.get(mSpinnerFromPosition);
        Currency toCurrency = mListOfCurrency.get(mSpinnerToPosition);

        BigDecimal result = BigDecimal.valueOf(mAmount)
                .multiply(fromCurrency.getValue())
                .multiply(new BigDecimal(toCurrency.getNominal()))
                .divide(toCurrency.getValue(), 2, RoundingMode.HALF_UP)
                .divide(new BigDecimal(fromCurrency.getNominal()), 2, RoundingMode.HALF_UP);

        return "Итого: " + result + " " + toCurrency.getCharCode();
    }
}
