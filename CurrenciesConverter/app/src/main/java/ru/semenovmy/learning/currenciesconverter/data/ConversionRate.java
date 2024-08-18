package ru.semenovmy.learning.currenciesconverter.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import ru.semenovmy.learning.currenciesconverter.data.model.Currency;

public class ConversionRate {

    private List<Currency> mListOfCurrency;

    public ConversionRate(List<Currency> listOfCurrency) {
        this.mListOfCurrency = listOfCurrency;
    }

    public String getConversion(int mSpinnerFromPosition, int mSpinnerToPosition) {
        Currency fromCurrency = mListOfCurrency.get(mSpinnerFromPosition);
        Currency toCurrency = mListOfCurrency.get(mSpinnerToPosition);

        BigDecimal rate = fromCurrency.getValue()
                .multiply(new BigDecimal(toCurrency.getNominal()))
                .divide(toCurrency.getValue(), 2, RoundingMode.HALF_UP)
                .divide(new BigDecimal(fromCurrency.getNominal()), 2, RoundingMode.HALF_UP);

        return "По курсу: " + rate + " " + fromCurrency.getCharCode() + "/" + toCurrency.getCharCode();
    }
}
