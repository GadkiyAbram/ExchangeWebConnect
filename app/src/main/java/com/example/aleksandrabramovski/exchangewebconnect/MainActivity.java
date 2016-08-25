package com.example.aleksandrabramovski.exchangewebconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView mtv1, mtv2, mtvDate;
    String mCurrentDate;

    private String url1 = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    private String url2;
    private HandleXML obj;
    public Button mButtonShowWeather;
    public Button mButtonCurrency;
    public EditText mEditTextInputValue;
    public TextView mTextViewOutput;
    public String resultCurrency;
    public float resTemp;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonShowWeather = (Button)findViewById(R.id.button);
        mButtonCurrency = (Button)findViewById(R.id.buttonChange);
        mEditTextInputValue = (EditText)findViewById(R.id.editTextInputValue);
        mTextViewOutput = (TextView)findViewById(R.id.textViewResultCurrencyExchange);


        SimpleDateFormat mDate = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        mCurrentDate = mDate.format(calendar.getTime());
        TextView mTextViewCurrentDate = (TextView)findViewById(R.id.textViewDate);
        mTextViewCurrentDate.setText("Today is: " + mCurrentDate);
        url2 = mCurrentDate;

        mtv1 = (TextView)findViewById(R.id.textViewIndex);
        mtv2 = (TextView)findViewById(R.id.textViewCurrency);

//        String finalUrl = url1 + url2;
//        mtv2.setText(finalUrl);
//
//        obj = new HandleXML(finalUrl);
//        obj.fetchXML();
//        resultCurrency = obj.getCountry();
//
//        while (obj.parsingComplete) ;
//        mtv1.setText("Index: " + obj.getLocation());
//        mtv2.setText("Rate: " + obj.getCountry());


        mButtonShowWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalUrl = url1 + url2;
                mtv2.setText(finalUrl);

                obj = new HandleXML(finalUrl);
                obj.fetchXML();
//                resultCurrency = obj.getCountry().toString();

                while (obj.parsingComplete) ;
                mtv1.setText("Index: " + obj.getLocation());
                mtv2.setText("Rate: " + obj.getCountry());

            }
        });
    }


    private float convertRubToUSD(float currencyUSD){
        resTemp = Float.parseFloat(obj.getCountry());
        return (float) (currencyUSD / resTemp);
    }

    private float convertUSDToRub(float currencyRub){
        resTemp = Float.parseFloat(obj.getCountry());
        return (float) (currencyRub * resTemp);
    }


    public void onClickChange(View view) {
        RadioButton mRadioButtonChange = (RadioButton) findViewById(R.id.radioButtonRUBtoUSD);
        if (mtv1.getText().length() == 0 || mtv2.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Press Get Rate", Toast.LENGTH_SHORT).show();
            return;
        } else {
            float inputValue = Float.parseFloat(mEditTextInputValue.getText().toString());
            if (mRadioButtonChange.isChecked()) {
                mTextViewOutput.setText("In US Dollar: " + String.valueOf(convertRubToUSD(inputValue)));
        } else {
                mTextViewOutput.setText("In Russian Roubles: " + String.valueOf(convertUSDToRub(inputValue)));
        }
    }
    }

}