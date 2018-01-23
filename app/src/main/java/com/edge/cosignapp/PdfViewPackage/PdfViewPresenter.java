package com.edge.cosignapp.PdfViewPackage;

import android.content.Context;

import java.io.File;

/**
 * Created by user1 on 2018-01-15.
 */

public class PdfViewPresenter implements PdfViewTask.PresenterBridge,PdfViewCallback {
    PdfViewModel pdfViewModel;
    Context context;
    PdfViewTask.View view;

    public PdfViewPresenter(Context context, PdfViewTask.View view) {
        this.context = context;
        this.view = view;
        view.setPresenterBridge(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void shareFile(File file) {
        pdfViewModel = new PdfViewModel(context,this);
        pdfViewModel.uploadPdf(file);
    }

    @Override
    public void deleteFile(File file) {
        pdfViewModel = new PdfViewModel(context,this);
        pdfViewModel.deleteFile(file);
    }

    @Override
    public void sendResult(int code) {
        view.sendResult(code);
    }
}
