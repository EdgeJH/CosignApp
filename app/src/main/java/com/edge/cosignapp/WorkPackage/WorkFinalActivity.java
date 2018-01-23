package com.edge.cosignapp.WorkPackage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edge.cosignapp.CosignApp;
import com.edge.cosignapp.Data.WorkContrackData;
import com.edge.cosignapp.PdfViewPackage.PdfVIewActivity;
import com.edge.cosignapp.R;
import com.edge.cosignapp.Utils.LoadingProgress;
import com.edge.cosignapp.Utils.PdfCallback;
import com.edge.cosignapp.Utils.PdfCreator;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by user1 on 2017-12-22.
 */

public class WorkFinalActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener,SignaturePad.OnSignedListener,PdfCallback {
    SignaturePad signaturePad;
    LinearLayout getContDate;
    TextView contYear,contMonth,contDay;
    RelativeLayout back,next,bin;
    WorkContrackData contrackData;
    Bitmap signature;
    boolean isSigned =false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_final_main);
        initView();
        CosignApp.activities.add(this);
    }

    private void initView(){
        signaturePad = findViewById(R.id.signature_pad);
        getContDate = findViewById(R.id.get_cont_date);
        contYear = findViewById(R.id.contract_year);
        contMonth = findViewById(R.id.contract_month);
        contDay = findViewById(R.id.contract_day);
        back =findViewById(R.id.back);
        next =findViewById(R.id.next);
        bin = findViewById(R.id.bin);

        getContDate.setOnClickListener(this);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        bin.setOnClickListener(this);
        signaturePad.setOnSignedListener(this);
    }
    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        contYear.setText(String.valueOf(year));
        contMonth.setText(String.valueOf(month+1));
        contDay.setText(String.valueOf(dayOfMonth));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.get_cont_date:
                showDateDialog();
                break;
            case R.id.bin:
                signaturePad.clear();
                break;
            case R.id.next:
                if (setData()){
                    LoadingProgress.showDialog(this);
                    createPdf();
                }
                break;
        }
    }

    @Override
    public void onStartSigning() {
        isSigned =true;
    }

    @Override
    public void onSigned() {

    }

    @Override
    public void onClear() {

    }

    private boolean setData(){
        contrackData = (WorkContrackData) getIntent().getSerializableExtra("workData");
        String conDate = contYear.getText().toString()+"년 "+contMonth.getText().toString() + "월 "+contDay.getText().toString()+"일";
        signature  = signaturePad.getTransparentSignatureBitmap();
        if (!isEmpty(conDate,signature)&&isSigned){
            contrackData.setContDate(conDate);
            contrackData.setSignature(signature);
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmpty(String conDate,Bitmap signature){
        if (!TextUtils.isEmpty(conDate)&& signature!=null){
            return false;
        } else {
            return true;
        }
    }
    private void createPdf(){
        PdfCreator pdfCreator = new PdfCreator(getApplicationContext(),this);
        try {
            pdfCreator.createWorkPdf(contrackData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void result(int code, String filePath) {
        Intent intent = new Intent(this, PdfVIewActivity.class);
        intent.putExtra("path",filePath);
        startActivity(intent);
    }
}
