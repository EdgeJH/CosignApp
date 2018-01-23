package com.edge.cosignapp.MainPackage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edge.cosignapp.CustomViewPager.CardItem;
import com.edge.cosignapp.CustomViewPager.CardPagerAdapter;
import com.edge.cosignapp.CustomViewPager.ImageClickCallback;
import com.edge.cosignapp.CustomViewPager.ShadowTransformer;
import com.edge.cosignapp.Data.BusinessData;
import com.edge.cosignapp.Data.Code;
import com.edge.cosignapp.Data.PersonalData;
import com.edge.cosignapp.R;
import com.edge.cosignapp.Utils.SharedPreference;
import com.edge.cosignapp.WorkPackage.WorkContractActivty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener ,MainTask.View,ImageClickCallback{
    /*    GoogleApiClient mGoogleApiClient;
        private static final String TAG = "11111";
        private static final int REQUEST_CODE_CAPTURE_IMAGE = 1;
        private static final int REQUEST_CODE_CREATOR = 2;
        private static final int REQUEST_CODE_RESOLUTION = 3;
        EditText editText;
        Button button;
        String txt;*/
    BottomSheetBehavior bottomSheetBehavior;
    TextView logo,title,info,settingText,name;
    ImageView settingArrow,editImage,bottomBack;
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    RelativeLayout personal,company,edit;
    LinearLayout bottomSheet,settingTab,comNum,manager,ceo;
    EditText nameEdit,numberEdit,addressEdit,comNumEdit,managerEdit,ceoEdit;
    boolean editable= false;
    MainTask.PresenterBridge presenterBridge;
    MainPresenter presenter;
    boolean isBusiness= false;
    int peekHeight;
    BusinessData businessData;
    PersonalData personalData;
    SharedPreference sharedPreference = new SharedPreference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter= new MainPresenter(this,getApplicationContext());
        initView();
        initSetting();
        setViewPager();
        setBottomSheetBehavior();

    }

    private void initSetting(){
        title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/chosunilbo.ttf"));
        logo.setTypeface(Typeface.createFromAsset(getAssets(),"Playball-Regular.ttf"));
        personal.setOnClickListener(this);
        company.setOnClickListener(this);
        edit.setOnClickListener(this);
        title.setText(R.string.title_1);
        info.setText(R.string.info_1);
        numberEdit.setEnabled(false);
        nameEdit.setEnabled(false);
        settingTab.setOnClickListener(this);
        addressEdit.setEnabled(false);
    }

    private void setUserData(){
        if (isBusiness){
            businessData = sharedPreference.getValue(getApplicationContext(),"business",BusinessData.class);
            if (businessData!=null){
                nameEdit.setText(businessData.getCompany());
                ceoEdit.setText(businessData.getCeo());
                comNumEdit.setText(businessData.getCompanyNum());
                numberEdit.setText(businessData.getPhoneNumber());
                addressEdit.setText(businessData.getAddress());
                managerEdit.setText(businessData.getManager());
            }
        } else {
            personalData = sharedPreference.getValue(getApplicationContext(),"personal",PersonalData.class);
            if (personalData!=null){
                nameEdit.setText(personalData.getName());
                numberEdit.setText(personalData.getPhoneNumber());
                addressEdit.setText(personalData.getAddress());
            }
        }
    }
    private void initView(){
        mViewPager = findViewById(R.id.viewpager);
        logo = findViewById(R.id.logo);
        title = findViewById(R.id.title);
        info = findViewById(R.id.info);
        name = findViewById(R.id.name);
        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomBack = findViewById(R.id.bottom_back);
        settingTab = findViewById(R.id.setting_tab);
        settingArrow = findViewById(R.id.settiong_arrow);
        settingText = findViewById(R.id.setting_text);
        personal = findViewById(R.id.personal);
        company = findViewById(R.id.company);
        edit = findViewById(R.id.edit);
        editImage = findViewById(R.id.edit_image);
        nameEdit =  findViewById(R.id.name_edit);
        numberEdit= findViewById(R.id.number_edit);
        addressEdit = findViewById(R.id.address_edit);
        managerEdit = findViewById(R.id.manager);
        manager = findViewById(R.id.man);
        comNum = findViewById(R.id.com_num);
        comNumEdit = findViewById(R.id.company_num);
        ceoEdit = findViewById(R.id.ceo_name);
        ceo = findViewById(R.id.ceo);
    }


    private void setViewPager(){
        mCardAdapter = new CardPagerAdapter(this,this);
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.drawable.work));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.drawable.freelancer1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.drawable.model));
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mCardShadowTransformer.enableScaling(true);
        mViewPager.addOnPageChangeListener(this);
    }
    private void setBottomSheetBehavior(){
        personal.setSelected(true);
        isBusiness =sharedPreference.getValue(this,"isBusiness",false);
        if (isBusiness){
            personal.setSelected(false);
            company.setSelected(true);
        } else {
            personal.setSelected(true);
            company.setSelected(false);
        }
        changeBottomSheet();
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        peekHeight = bottomSheetBehavior.getPeekHeight();
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                bottomBack.setAlpha(slideOffset);
                settingArrow.setRotation(180-(180*slideOffset));
                if (slideOffset>0.2){
                    settingText.setTextColor(Color.WHITE);
                    settingArrow.setImageResource(R.drawable.arrow_white);
                    bottomBack.setVisibility(View.VISIBLE);
                } else if (slideOffset<=0.2&&slideOffset>0){
                    settingArrow.setImageResource(R.drawable.arrow_black);
                    settingText.setTextColor(Color.BLACK);
                }else {
                    bottomBack.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setEditable(){
        if (!editable){
            numberEdit.setEnabled(true);
            nameEdit.setEnabled(true);
            addressEdit.setEnabled(true);
            nameEdit.setFocusable(true);
            nameEdit.setFocusableInTouchMode(true);
            nameEdit.requestFocus();
            editable=true;
            editImage.setImageResource(R.drawable.success);
        } else {
            numberEdit.setEnabled(false);
            nameEdit.setEnabled(false);
            addressEdit.setEnabled(false);
            editable=false;
            editImage.setImageResource(R.drawable.pen);
            saveData();
        }
    }

    private void saveData(){
        String  company= nameEdit.getText().toString();
        String address = addressEdit.getText().toString();
        String phoneNumber = numberEdit.getText().toString();
        String managerSt = managerEdit.getText().toString();
        String comNumSt = comNumEdit.getText().toString();
        String ceo = ceoEdit.getText().toString();
        if (isBusiness){
            businessData = new BusinessData(company,ceo,address,phoneNumber,managerSt,comNumSt);
            presenterBridge.saveBusinessData(businessData);
        } else {
            personalData = new PersonalData(company,address,phoneNumber);
            presenterBridge.savePersonalData(personalData);
        }
    }

    private void changeBottomSheet(){
        if (isBusiness){
            ceo.setVisibility(View.VISIBLE);
            comNum.setVisibility(View.VISIBLE);
            manager.setVisibility(View.VISIBLE);
            name.setText("상호명");

        }else {
            name.setText("이름");
            ceo.setVisibility(View.GONE);
            comNum.setVisibility(View.GONE);
            manager.setVisibility(View.GONE);
        }


        setUserData();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                title.setText(R.string.title_1);
                info.setText(R.string.info_1);
                break;
            case 1:
                title.setText(R.string.title_2);
                info.setText(R.string.info_2);
                break;
            case 2:
                title.setText(R.string.title_3);
                info.setText(R.string.info_3);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.personal :
                personal.setSelected(true);
                company.setSelected(false);
                isBusiness = false;
                sharedPreference.put(this,"isBusiness",false);
                changeBottomSheet();
                break;
            case R.id.company :
                personal.setSelected(false);
                company.setSelected(true);
                isBusiness =true;
                sharedPreference.put(this,"isBusiness",true);
                changeBottomSheet();
                break;
            case R.id.edit:
                setEditable();
                break;
            case R.id.setting_tab:
                if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
        }
    }

    @Override
    public void setPresenterBridge(MainTask.PresenterBridge presenterBridge) {
        this.presenterBridge = presenterBridge;
    }

    @Override
    public void showResult(int code) {
        if (code== Code.SUCCESS){
            Toast.makeText(getApplicationContext(),"변경 되었습니다", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void imageClick(CardItem item, View view) {
        switch (item.getTitle()){
            case R.string.title_1:
                Intent intent = new Intent(this, WorkContractActivty.class);
                intent.putExtra("userType",isBusiness);
                if (isBusiness){
                    intent.putExtra("userData",businessData);
                } else {
                    intent.putExtra("userData",personalData);
                }
                startActivity(intent);
                break;
            case R.string.title_2:
                Intent intent2 = new Intent();
                startActivity(intent2);
                break;
            case R.string.title_3:
                Intent intent3 = new Intent();
                startActivity(intent3);
                break;
        }
    }
}
