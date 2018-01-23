package com.edge.cosignapp.PdfViewPackage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edge.cosignapp.CosignApp;
import com.edge.cosignapp.MainPackage.MainActivity;
import com.edge.cosignapp.R;
import com.edge.cosignapp.Utils.LoadingProgress;
import com.edge.cosignapp.Utils.PopupClickListener;
import com.edge.cosignapp.Utils.SharePopup;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.File;

public class PdfVIewActivity extends AppCompatActivity implements OnLoadCompleteListener,View.OnClickListener ,PopupClickListener,PdfViewTask.View{
    PDFView pdfView;
    RelativeLayout shareBt,back;
    SharePopup sharePopup;
    PdfViewTask.PresenterBridge presenterBridge;
    PdfViewPresenter presenter;
    File file;
    TextView goMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        presenter = new PdfViewPresenter(this,this);
        initView();
        setPdfView();
    }

    private void initView(){
        pdfView = findViewById(R.id.pdf_viewer);
        shareBt = findViewById(R.id.share_bt);
        goMain = findViewById(R.id.go_main);
        back =findViewById(R.id.back);
        goMain.setOnClickListener(this);
        sharePopup = new SharePopup(getApplicationContext());
        sharePopup.setPopupClickListener(this);
        shareBt.setOnClickListener(this);
    }

    private void setPdfView(){
        String path = getIntent().getStringExtra("path");
        file = new File(path);

        pdfView.fromFile(file)
                .defaultPage(0)
                .enableDoubletap(true)
                .enableSwipe(true)
                .onLoad(this)
                .load();
    }

    @Override
    public void loadComplete(int nbPages) {
        LoadingProgress.dismissDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_bt:
                sharePopup.showPopup(v);
                break;
            case R.id.go_main:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finishStack();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void itemClick(View view) {
        switch (view.getId()){
            case R.id.pdf_share:
                if (file!=null){
                    LoadingProgress.showDialog(this);
                    presenterBridge.shareFile(file);
                }
                break;
            case R.id.pdf_del:
                if (file!=null){
                    presenterBridge.deleteFile(file);
                }
                break;
            case R.id.pdf_email:
                if (file!=null){
                    Uri uri = Uri.fromFile(file);
                    composeEmail(uri);
                }
                break;
        }
    }

    @Override
    public void setPresenterBridge(PdfViewTask.PresenterBridge presenterBridge) {
        this.presenterBridge =presenterBridge;
    }

    @Override
    public void sendResult(int code) {
        switch (code){
            case 200:
                LoadingProgress.dismissDialog();

                break;
            case 400:
                Toast.makeText(getApplicationContext(),"파일 공유에 실패하였습니다",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void finishStack(){
        finish();
        for (AppCompatActivity activity : CosignApp.activities){
            activity.finish();
        }
    }
    public void composeEmail(Uri path) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_STREAM, path);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
