package com.edge.cosignapp.MainPackage;

import com.edge.cosignapp.BasePresenter;
import com.edge.cosignapp.BaseView;
import com.edge.cosignapp.Data.BusinessData;
import com.edge.cosignapp.Data.PersonalData;

/**
 * Created by user1 on 2017-12-14.
 */

public interface MainTask {
    interface PresenterBridge extends BasePresenter{
        void savePersonalData(PersonalData personalData);
        void saveBusinessData(BusinessData businessData);
    }
    interface View extends BaseView<PresenterBridge>{
        void showResult(int code);
    }
}
