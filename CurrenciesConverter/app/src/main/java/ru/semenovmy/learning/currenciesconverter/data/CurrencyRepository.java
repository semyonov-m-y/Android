package ru.semenovmy.learning.currenciesconverter.data;

import android.os.AsyncTask;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import ru.semenovmy.learning.currenciesconverter.data.model.CurrenciesData;
import ru.semenovmy.learning.currenciesconverter.data.model.Currency;
import ru.semenovmy.learning.currenciesconverter.data.model.CurrencyData;

public class CurrencyRepository {

    private static HttpURLConnection mConnection;
    private static Strategy mAnnotationStrategy = new AnnotationStrategy();
    private static Serializer mSerializer = new Persister(mAnnotationStrategy);
    private static CurrenciesData mCurrencies;

    public void loadDataAsync(OnLoadingFinishListener onLoadingFinishListener) {
        LoadCurrency loadingCurrencyAsyncTask = new LoadCurrency(onLoadingFinishListener);
        loadingCurrencyAsyncTask.execute();
    }

    private static class LoadCurrency extends AsyncTask<Void, Void, List<CurrencyData>> {

        private final OnLoadingFinishListener mOnLoadingFinishListener;

        LoadCurrency(OnLoadingFinishListener onLoadingFinishListener) {
            mOnLoadingFinishListener = onLoadingFinishListener;
        }

        @Override
        protected List<CurrencyData> doInBackground(Void... voids) {
            return loadFromWeb();
        }

        @Override
        protected void onPostExecute(List<CurrencyData> currencyData) {
            mOnLoadingFinishListener.onFinish(convert(currencyData));
        }
    }

    private static List<CurrencyData> loadFromWeb() {
        try {
            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
            mConnection = (HttpURLConnection) url.openConnection();
            mConnection.setRequestMethod("GET");
            InputStream inputStream = null;
            inputStream = mConnection.getInputStream();
            mCurrencies = mSerializer.read(CurrenciesData.class, inputStream);
        } finally {
            mConnection.disconnect();
            return new ArrayList<>(mCurrencies.getCurrencies());
        }
    }

    public static List<Currency> convert(List<CurrencyData> currencies) {

        List<Currency> result = new ArrayList<>();

        for (CurrencyData currency : currencies) {
            result.add(new Currency(
                    currency.getId(),
                    currency.getCharCode(),
                    currency.getNominal(),
                    currency.getName(),
                    currency.getValue()
            ));
        }

        return result;
    }

    public interface OnLoadingFinishListener {
        void onFinish(List<Currency> currencyModels);
    }
}
