package ru.semenovmy.learning.currenciesconverter.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import ru.semenovmy.learning.currenciesconverter.R;
import ru.semenovmy.learning.currenciesconverter.data.CurrencyRepository;
import ru.semenovmy.learning.currenciesconverter.data.model.Currency;
import ru.semenovmy.learning.currenciesconverter.presenter.CurrencyPresenter;

public class MainActivity extends AppCompatActivity implements ICurrencyView {

    private Button mConvertButton;
    private CurrencyPresenter mCurrencyPresenter = new CurrencyPresenter(this, new CurrencyRepository());
    private CurrencyAdapter mCurrencyAdapter;
    private EditText mEditText;
    private List<Currency> mCurrencyList;
    private TextView mTotalSumText;
    private TextView mRate;
    private Spinner mSpinnerFrom;
    private Spinner mSpinnerTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTotalSumText = findViewById(R.id.total_sum_text);
        mRate = findViewById(R.id.rate);
        mSpinnerTo = findViewById(R.id.spinner_to);
        mEditText = findViewById(R.id.edit_text);
        mSpinnerFrom = findViewById(R.id.spinner_from);
        mSpinnerFrom.setAdapter(mCurrencyAdapter);
        mSpinnerFrom.setOnItemSelectedListener(new OnCurrencySelectedListener());
        mSpinnerTo.setOnItemSelectedListener(new OnCurrencySelectedListener());
        mConvertButton = findViewById(R.id.btn_convert);
        mCurrencyPresenter.loadCurrency();
        mConvertButton.setOnClickListener(v -> {
            mTotalSumText.setText(mCurrencyPresenter.loadConvertedValue(mSpinnerFrom.getSelectedItemPosition(),
                    mSpinnerTo.getSelectedItemPosition(),
                    Double.valueOf(mEditText.getText().toString())));
        });
    }

    public void getCurrencies(List<Currency> list) {
        mCurrencyList = list;
        mCurrencyAdapter = new CurrencyAdapter(mCurrencyList);
        mSpinnerFrom.setAdapter(mCurrencyAdapter);
        mSpinnerTo.setAdapter(mCurrencyAdapter);
    }

    private class OnCurrencySelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mRate.setText(mCurrencyPresenter.loadConversion(mSpinnerFrom.getSelectedItemPosition(),
                    mSpinnerTo.getSelectedItemPosition()));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
