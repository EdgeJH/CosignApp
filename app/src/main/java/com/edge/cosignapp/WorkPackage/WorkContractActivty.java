package com.edge.cosignapp.WorkPackage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.edge.cosignapp.CosignApp;
import com.edge.cosignapp.Data.BusinessData;
import com.edge.cosignapp.Data.PersonalData;
import com.edge.cosignapp.Data.WorkContrackData;
import com.edge.cosignapp.R;

import java.util.Calendar;

/**
 * Created by user1 on 2017-12-14.
 */

public class WorkContractActivty extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    LinearLayout getStartDate,getEndDate,getStartTime,getEndTime,getBreakStartTime,getBreakEndTime;
    TextView startYear,startMonth,startDay,endYear,endMonth,endDay,startHour,startMin,endHour,endMin,breakStartHour,breakStartMin,breakEndHour,breakEndMin;
    EditText ceo,worker,address,company;
    RelativeLayout back,next;
    WorkContrackData contrackData;
    boolean isStartDate=false;
    int timeCount= 0;
    boolean isBusiness= false;
    BusinessData businessData;
    PersonalData personalData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_contract_main);
        initView();
        getUserType();
        CosignApp.activities.add(this);
    }

    private void initView(){
        ceo = findViewById(R.id.ceo);
        worker = findViewById(R.id.worker);
        address  = findViewById(R.id.address);
        company = findViewById(R.id.company);
        getEndDate = findViewById(R.id.get_end_date);
        getStartDate = findViewById(R.id.get_start_date);
        startYear = findViewById(R.id.start_year);
        startMonth = findViewById(R.id.start_month);
        startDay = findViewById(R.id.start_day);
        endYear = findViewById(R.id.end_year);
        endMonth = findViewById(R.id.end_month);
        endDay = findViewById(R.id.end_day);
        getStartTime = findViewById(R.id.get_start_time);
        getEndTime = findViewById(R.id.get_end_time);
        getBreakStartTime = findViewById(R.id.get_break_start_time);
        getBreakEndTime =findViewById(R.id.get_break_end_time);
        startHour = findViewById(R.id.start_hour);
        startMin = findViewById(R.id.start_min);
        endHour = findViewById(R.id.end_hour);
        endMin = findViewById(R.id.end_min);
        breakStartHour = findViewById(R.id.break_start_hour);
        breakStartMin = findViewById(R.id.break_start_min);
        breakEndHour = findViewById(R.id.break_end_hour);
        breakEndMin =findViewById(R.id.break_end_min);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        getStartDate.setOnClickListener(this);
        getEndDate.setOnClickListener(this);
        getStartTime.setOnClickListener(this);
        getEndTime.setOnClickListener(this);
        getBreakEndTime.setOnClickListener(this);
        getBreakStartTime.setOnClickListener(this);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    private void getUserType(){
        isBusiness = getIntent().getBooleanExtra("userType",false);
        if (isBusiness){
            businessData = (BusinessData) getIntent().getSerializableExtra("userData");
        } else {
            personalData = (PersonalData) getIntent().getSerializableExtra("userData");
        }
        setUserData();
    }
    private void setUserData(){
        if (isBusiness){
            if (businessData!=null){
                ceo.setText(businessData.getCeo());
                address.setText(businessData.getAddress());
                company.setText(businessData.getCompany());
            }
        } else {
            if (personalData!=null){
                worker.setText(personalData.getName());
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_start_date:
                isStartDate = true;
                showDateDialog();
                break;
            case R.id.get_end_date:
                isStartDate= false;
                showDateDialog();
                break;
            case R.id.get_start_time:
                timeCount =0;
                showTimeDialog();
                break;
            case R.id.get_end_time:
                timeCount =1;
                showTimeDialog();
                break;
            case R.id.get_break_start_time:
                timeCount=2;
                showTimeDialog();
                break;
            case R.id.get_break_end_time:
                timeCount=3;
                showTimeDialog();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.next:
                setContractData();
                break;
        }
    }
    private void setContractData(){
        contrackData = new WorkContrackData();
        String ceoName =ceo.getText().toString();
        String add = address.getText().toString();
        String com = company.getText().toString();
        String wokerName = worker.getText().toString();
        String endDate =endYear.getText().toString()+"년 "+endMonth.getText().toString()+"월 "+ endDay.getText().toString()+"일";
        String startDate =startYear.getText().toString()+"년 "+startMonth.getText().toString()+"월 "+ startDay.getText().toString()+"일";
        String endTime =endHour.getText().toString()+"시 "+endMin.getText().toString()+"분";
        String startTime =startHour.getText().toString()+"시 "+startMin.getText().toString()+"분";
        String breakStartTime = breakStartHour.getText().toString()+"시 "+breakStartMin.getText().toString()+"분";
        String breaEndTime = breakEndHour.getText().toString()+"시 "+breakEndMin.getText().toString()+"분";
        if (isBusiness){
            if (businessData!=null){
                businessData.setCeo(ceoName);
                businessData.setCompany(com);
                businessData.setAddress(add);
            } else {
                businessData = new BusinessData(com,ceoName,add,"","","");
            }
            personalData = new PersonalData(wokerName,"","");
            contrackData.setPersonalData(personalData);
            contrackData.setBusinessData(businessData);
        } else {
            businessData =new BusinessData(com,ceoName,add,"","","");
            if (personalData!=null){
                personalData.setName(wokerName);
            } else {
                personalData = new PersonalData(wokerName,"","");
            }
            contrackData.setBusinessData(businessData);
            contrackData.setPersonalData(personalData);
        }
        contrackData.setBreakStartTime(breakStartTime);
        contrackData.setBreakEndTime(breaEndTime);
        contrackData.setEndDate(endDate);
        contrackData.setStartDate(startDate);
        contrackData.setEndTime(endTime);
        contrackData.setStartTime(startTime);

        emptyCheckIntent(startDate,endTime,startTime,ceoName,add,com);
    }
    private void emptyCheckIntent(String startDate,String endTime,String startTime,String ceo,String add,String com){
        if (!TextUtils.isEmpty(startDate)&& ! TextUtils.isEmpty(endTime)&&
                !TextUtils.isEmpty(startTime)&& ! TextUtils.isEmpty(ceo)
                && ! TextUtils.isEmpty(add)&& ! TextUtils.isEmpty(com)){
            Intent intent = new Intent(this,WorkStep2Activity.class);
            intent.putExtra("userType",isBusiness);
            intent.putExtra("workData",contrackData);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(),"빈 부분을 확인해 주세요", Toast.LENGTH_SHORT).show();
        }
    }
    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
       DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();


    }

    private void showTimeDialog(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,this,hour,minute,false);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String yearSt= String.valueOf(year);
        String monthSt = String.valueOf(month+1);
        String daySt = String.valueOf(dayOfMonth);
        setDateText(yearSt,monthSt,daySt);
    }
    private void setDateText(String year,String month,String day){
        if (isStartDate){
            startYear.setText(year);
            startMonth.setText(month);
            startDay.setText(day);
        } else {
            endYear.setText(year);
            endMonth.setText(month);
            endDay.setText(day);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hour= String.valueOf(hourOfDay);
        String min = String.valueOf(minute);
        setTimeText(hour,min);
    }
    private void setTimeText(String hour,String min){
        switch (timeCount){
            case 0:
                startHour.setText(hour);
                startMin.setText(min);
                break;
            case 1:
                endHour.setText(hour);
                endMin.setText(min);
                break;
            case 2:
                breakStartHour.setText(hour);
                breakStartMin.setText(min);
                break;
            case 3:
                breakEndHour.setText(hour);
                breakEndMin.setText(min);
                break;
        }
    }
}
