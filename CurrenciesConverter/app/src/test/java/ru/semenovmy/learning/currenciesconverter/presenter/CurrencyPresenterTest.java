package ru.semenovmy.learning.currenciesconverter.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ru.semenovmy.learning.currenciesconverter.data.CurrencyRepository;
import ru.semenovmy.learning.currenciesconverter.data.model.Currency;
import ru.semenovmy.learning.currenciesconverter.view.ICurrencyView;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyPresenterTest {

    @Mock
    private CurrencyRepository mCurrencyRepository;
    @Mock
    private ICurrencyView mICurrencyView;
    private CurrencyPresenter mMainPresenter;

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    public void setUp() {
        mMainPresenter = new CurrencyPresenter(mICurrencyView, mCurrencyRepository);
    }

    /**
     * Тестирование асинхронного метода получения данных в презентере.
     */
    @Test
    public void testLoadDataAsync() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                CurrencyRepository.OnLoadingFinishListener onLoadingFinishListener =
                        (CurrencyRepository.OnLoadingFinishListener) invocation.getArguments()[0];

                onLoadingFinishListener.onFinish(createTestData());

                return null;
            }
        }).when(mCurrencyRepository).loadDataAsync(Mockito.any(CurrencyRepository.OnLoadingFinishListener.class));

        mMainPresenter.loadCurrency();

        verify(mICurrencyView).getCurrencies(createTestData());
    }

    private List<Currency> createTestData() {
        List<Currency> testData = new ArrayList<>();
        testData.add(new Currency("0", "Sberbank", 100, "1", new BigDecimal("45.678")));
        testData.add(new Currency("1", "Sberbank1", 101, "3", new BigDecimal("345.678")));

        return testData;
    }
}