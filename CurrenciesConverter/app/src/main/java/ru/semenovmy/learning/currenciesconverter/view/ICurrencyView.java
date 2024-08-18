package ru.semenovmy.learning.currenciesconverter.view;

import java.util.List;
import ru.semenovmy.learning.currenciesconverter.data.model.Currency;

public interface ICurrencyView {
    void getCurrencies(List<Currency> modelList);
}
