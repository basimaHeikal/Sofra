package com.examlpe.com.sofra.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.SplashBTOrder)
    Button SplashBTOrder;
    @BindView(R.id.SplashBTSell)
    Button SplashBTSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.SplashBTOrder)
    public void onSplashBTOrderClicked() {
        SharedPrefManager.getInstance(SplashActivity.this).setKeyOrderSell("ORDER");
        finish();
        startActivity(new Intent(SplashActivity.this, FoodOrderActivity.class));
    }

    @OnClick(R.id.SplashBTSell)
    public void onSplashBTSellClicked() {
        SharedPrefManager.getInstance(SplashActivity.this).setKeyOrderSell("SELL");
        finish();
        startActivity(new Intent(SplashActivity.this, FoodSellActivity.class));
    }

    private void start(String keyOrderSell){
        SharedPrefManager.getInstance(SplashActivity.this).setKeyOrderSell(keyOrderSell);
        finish();
        startActivity(new Intent(SplashActivity.this, FoodOrderActivity.class));
    }
}
