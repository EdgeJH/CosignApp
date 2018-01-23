package com.edge.cosignapp.WorkPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.edge.cosignapp.CosignApp;
import com.edge.cosignapp.Data.WorkContrackData;
import com.edge.cosignapp.R;

import me.omidh.liquidradiobutton.LiquidRadioButton;

/**
 * Created by user1 on 2017-12-19.
 */

public class WorkStep2Activity extends AppCompatActivity implements View.OnClickListener {
    WorkContrackData contrackData;
    Spinner getWorkDay,getHoliday,isBonus,payWay;
    EditText payEdit,bonusEdit,infoEdit;
    RelativeLayout back,next;
    LiquidRadioButton payMethod1,payMethod2;
    boolean isBusiness =false;
    boolean payMethod=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_step2_main);
        getData();
        initView();
        CosignApp.activities.add(this);
    }

    private void initView(){
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        getWorkDay = findViewById(R.id.get_work_day);
        getHoliday = findViewById(R.id.get_holiday);
        isBonus = findViewById(R.id.isbonus);
        payWay = findViewById(R.id.pay_way);
        payEdit = findViewById(R.id.pay);
        bonusEdit = findViewById(R.id.bonus);
        infoEdit =findViewById(R.id.info);
        payMethod1 = findViewById(R.id.pay_method_1);
        payMethod2 = findViewById(R.id.pay_method_2);
        payMethod1.setOnClickListener(this);
        payMethod2.setOnClickListener(this);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    private void getData(){
        contrackData = (WorkContrackData) getIntent().getSerializableExtra("workData");
        isBusiness = getIntent().getBooleanExtra("userType",false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.next:
                if (setData()){
                    Intent intent = new Intent(this,WorkFinalActivity.class);
                    intent.putExtra("workData",contrackData);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"빈 부분을 확인해 주세요",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.pay_method_1:
                payMethod =true;
                payMethod1.setChecked(true);
                payMethod2.setChecked(false);
                break;
            case R.id.pay_method_2:
                payMethod =false;
                payMethod2.setChecked(true);
                payMethod1.setChecked(false);
                break;
        }
    }

    private boolean setData(){
        String bonus = bonusEdit.getText().toString();
        String pay = payEdit.getText().toString();
        String method="";
        if (payMethod){
            method = "근로자에게 직접지금";
        } else {
            method = "근로자명의 통장에 입금";
        }
        String workday = (String) getWorkDay.getItemAtPosition(getWorkDay.getSelectedItemPosition());
        String holiday = (String) getHoliday.getItemAtPosition(getHoliday.getSelectedItemPosition());
        String payWaySt  = (String) payWay.getItemAtPosition(payWay.getSelectedItemPosition());
        String info = infoEdit.getText().toString();
        if (!isEmpty(workday,holiday,payWaySt,info)){
            contrackData.setPayWay(payWaySt);
            contrackData.setInfo(info);
            contrackData.setBonus(bonus);
            contrackData.setPay(pay);
            contrackData.setPayMethod(method);
            contrackData.setWorkDay(workday);
            contrackData.setHoliday(holiday);
            return true;
        }else {
            return false;
        }
    }

    private boolean isEmpty(String workday,String holiday,String payWaySt,String info){
        if (!TextUtils.isEmpty(workday)&& !TextUtils.isEmpty(payWaySt)&&
                !TextUtils.isEmpty(holiday)&& !TextUtils.isEmpty(info)){
            return false;
        } else {
            return true;
        }
    }




}
