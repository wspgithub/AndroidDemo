package com.example.administrator.myapplication.RecyclerViewDragTest;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;


/**
 * 首页设备长按对话框
 *
 * @author wuxq
 */
public class HomeDevMoreDialog extends Dialog implements OnClickListener {

    private LinearLayout llDevDrag;
    private LinearLayout llDevDetail;
    private TextView tvLine;

    public HomeDevMoreDialog(Context context) {
        super(context, R.style.CustomDialog);
        setContentView(R.layout.pad_home_dev_longclick_dialog);
        setCanceledOnTouchOutside(true);

        llDevDrag = findViewById(R.id.llDevDrag);
        llDevDetail = findViewById(R.id.llDevDetail);
        tvLine = findViewById(R.id.tvLine);
        llDevDetail.setOnClickListener(this);
        llDevDrag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        if (vid == R.id.llDevDrag) {
            // 设备拖动
            this.dismiss();
            if (mListener != null) {
                mListener.onDevDrag();
            }
        } else if (vid == R.id.llDevDetail) {
            // 设备详情
            this.dismiss();
            if (mListener != null) {
                mListener.onDevDetail();
            }
        }
    }

    /**
     * 排序设置隐藏
     */
    public void setDevDragHide(){
        llDevDrag.setVisibility(View.GONE);
        tvLine.setVisibility(View.GONE);
    }

    private OnHomeDevLongClickListener mListener;
    public interface OnHomeDevLongClickListener{
        /**
         * 设备拖动
         */
        void onDevDrag();

        /**
         * 设备详情
         */
        void onDevDetail();
    }

    public void setOnHomeDevLongClickListener(OnHomeDevLongClickListener listener){
        this.mListener = listener;
    }
}

