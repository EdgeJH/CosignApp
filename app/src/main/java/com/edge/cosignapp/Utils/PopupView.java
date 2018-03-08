package com.edge.cosignapp.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * Created by user1 on 2018-01-13.
 */

public class PopupView implements View.OnClickListener{
    PopupWindow mPopupWindow;
    Context context;
    LayoutInflater layoutInflater;
    ViewGroup popupView;
    PopupClickListener popupClickListener;
    RelativeLayout pdfDel,pdfShare,pdfEmail;
    public PopupView(Context context,int layout) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        popupView = (ViewGroup) layoutInflater.inflate(layout, null);
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
        ViewGroup viewGroup = (ViewGroup) popupView.getChildAt(0);
        for (int i =0; i<viewGroup.getChildCount(); i++){
            View view = viewGroup.getChildAt(i);
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        popupClickListener.itemClick(v);
    }
}
