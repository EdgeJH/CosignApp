package com.edge.cosignapp.MainPackage;

import android.content.Context;

import com.edge.cosignapp.Data.BusinessData;
import com.edge.cosignapp.Data.PersonalData;

/**
 * Created by user1 on 2017-12-14.
 */

public class MainPresenter implements MainTask.PresenterBridge,MainCallback {

    MainTask.View view;
    MainModel mainModel;
    Context context;

    public MainPresenter(MainTask.View view, Context context) {
        this.view = view;
        this.context = context;
        view.setPresenterBridge(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void savePersonalData(PersonalData personalData) {
        mainModel = new MainModel(context,this);
        mainModel.savePersonalData(personalData);
    }

    @Override
    public void saveBusinessData(BusinessData businessData) {
        mainModel = new MainModel(context,this);
        mainModel.saveBusinessData(businessData);
    }
    @Override
    public void sendResult(int code) {
        view.showResult(code);
    }

}
