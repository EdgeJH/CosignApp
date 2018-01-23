package com.edge.cosignapp.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.edge.cosignapp.R;

/**
 * Created by user1 on 2018-01-13.
 */

public class SharePopup implements View.OnClickListener{
    PopupWindow mPopupWindow;
    Context context;
    LayoutInflater layoutInflater;
    View popupView;
    PopupClickListener popupClickListener;
    RelativeLayout pdfDel,pdfShare,pdfEmail;
    public SharePopup(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        popupView = layoutInflater.inflate(R.layout.popup_share, null);
    }

    public void showPopup( View button){
        mPopupWindow = new PopupWindow(popupView,
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        // Removes defaultfilter background.
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mPopupWindow.setAnimationStyle(-1); // 애니메이션 설정(-1:설정, 0:설정안함)
        mPopupWindow.showAsDropDown(button, 0, 0);
    }

    public void setPopupClickListener(PopupClickListener popupClickListener) {
        this.popupClickListener = popupClickListener;
        initButton();
    }
    private void initButton(){
        pdfEmail = popupView.findViewById(R.id.pdf_email);
        pdfDel = popupView.findViewById(R.id.pdf_del);
        pdfShare = popupView.findViewById(R.id.pdf_share);
        pdfDel.setOnClickListener(this);
        pdfEmail.setOnClickListener(this);
        pdfShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        popupClickListener.itemClick(v);
    }
}
