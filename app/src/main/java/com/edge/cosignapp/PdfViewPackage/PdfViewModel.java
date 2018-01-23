package com.edge.cosignapp.PdfViewPackage;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user1 on 2018-01-15.
 */

public class PdfViewModel {
    private Context context;
    private StorageReference mStorageRef;
    private String logoUrl = "https://firebasestorage.googleapis.com/v0/b/cosignapp-f69df.appspot.com/o/" +
            "%E1%84%8F%E1%85%A9%E1%84%89%E1%85%A1%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%85%E1%85%A9%E1%84%80%E1%85%A92.png?" +
            "alt=media&token=b54bf491-613e-4979-b94c-9edce35cf0b5";
    private PdfViewCallback pdfViewCallback;

    public PdfViewModel(Context context, PdfViewCallback pdfViewCallback) {
        this.context = context;
        this.pdfViewCallback = pdfViewCallback;
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void uploadPdf(File file) {
        Uri uri = Uri.fromFile(file);
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String time = simpleDateFormat.format(date);
        StorageReference riversRef = mStorageRef.child("pdf/"+time+"/" + file.getName() + ".pdf");
        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        if (downloadUrl != null) {
                            shareKakao(downloadUrl.toString());
                        } else {
                            pdfViewCallback.sendResult(400);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pdfViewCallback.sendResult(400);
                    }
                });
    }

    private void shareKakao(String url) {
        LinkObject linkObject = LinkObject.newBuilder()
                .setWebUrl(url)
                .setMobileWebUrl(url)
                .build();
        ContentObject contentObject = ContentObject.newBuilder(
                "Cosign 근로계약서입니다."+"\n"+
                        "본 파일은 최대 20일 유지되며 그 이후론 다운받으실 수 없습니다.", logoUrl, linkObject)
                .build();
        ButtonObject downButton = new ButtonObject("다운로드 받기", linkObject);
        FeedTemplate params = FeedTemplate
                .newBuilder(contentObject)
                .addButton(downButton)
                .build();
        KakaoLinkService.getInstance().sendDefault(context, params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
                pdfViewCallback.sendResult(400);
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                pdfViewCallback.sendResult(200);
            }
        });
    }

    public void deleteFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
            pdfViewCallback.sendResult(200);
        } else {
            pdfViewCallback.sendResult(400);
        }
    }


}
