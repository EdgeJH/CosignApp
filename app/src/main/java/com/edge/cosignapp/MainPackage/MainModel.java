package com.edge.cosignapp.MainPackage;

import android.content.Context;

import com.edge.cosignapp.Data.BusinessData;
import com.edge.cosignapp.Data.Code;
import com.edge.cosignapp.Data.PersonalData;
import com.edge.cosignapp.Utils.SharedPreference;

/**
 * Created by user1 on 2017-12-14.
 */

public class MainModel {
    Context context;
    MainCallback callback;
    SharedPreference sharedPreference = new SharedPreference();
    public MainModel(Context context, MainCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void savePersonalData(PersonalData personalData){
        sharedPreference.put(context,"personal",personalData);
        callback.sendResult(Code.SUCCESS);
    }
    public void saveBusinessData(BusinessData businessData){
        sharedPreference.put(context,"business",businessData);
        callback.sendResult(Code.SUCCESS);
    }
}
