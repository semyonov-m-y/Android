package ru.semenovmy.learning.currenciesconverter.presenter;

import java.lang.ref.WeakReference;
import ru.semenovmy.learning.currenciesconverter.data.ConversionRate;
import ru.semenovmy.learning.currenciesconverter.data.CurrencyConverter;
import ru.semenovmy.learning.currenciesconverter.data.CurrencyRepository;
import ru.semenovmy.learning.currenciesconverter.view.ICurrencyView;

public class CurrencyPresenter {

    private final WeakReference<ICurrencyView> mWeakReference;
    private CurrencyConverter mCurrencyConverter;
    private CurrencyRepository mCurrencyRepository;
    private ConversionRate mConversionRate;

    public CurrencyPresenter(ICurrencyView mainActivity, CurrencyRepository currencyRepository) {
        mWeakReference = new WeakReference<>(mainActivity);
        mCurrencyRepository = currencyRepository;
    }

    public void loadCurrency() {
        CurrencyRepository.OnLoadingFinishListener onLoadingFinishListener = currencyModels -> {
            if (mWeakReference.get() != null) {
                mConversionRate = new ConversionRate(currencyModels);
                mCurrencyConverter = new CurrencyConverter(currencyModels);
                mWeakReference.get().getCurrencies(currencyModels);
            }
        };

        mCurrencyRepository.loadDataAsync(onLoadingFinishListener);
    }

    public String loadConversion(int mSpinnerFrom, int mSpinnerTo) {
        return mConversionRate.getConversion(mSpinnerFrom, mSpinnerTo);
    }

    public String loadConvertedValue(int mSpinnerFrom, int mSpinnerTo, double mAmount) {
        return mCurrencyConverter.getResultOfConversion(mSpinnerFrom, mSpinnerTo, mAmount);
    }
}
