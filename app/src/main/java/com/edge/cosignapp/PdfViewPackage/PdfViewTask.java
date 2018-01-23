package com.edge.cosignapp.PdfViewPackage;

import com.edge.cosignapp.BasePresenter;
import com.edge.cosignapp.BaseView;

import java.io.File;

/**
 * Created by user1 on 2018-01-15.
 */

public interface PdfViewTask {
    interface PresenterBridge extends BasePresenter{
        void shareFile(File file);
        void deleteFile(File file);
    }

    interface View extends BaseView<PresenterBridge>{
        void sendResult(int code);
    }
}
