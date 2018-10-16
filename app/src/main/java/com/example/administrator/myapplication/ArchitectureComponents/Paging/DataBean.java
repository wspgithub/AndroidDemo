package com.example.administrator.myapplication.ArchitectureComponents.Paging;

import android.text.TextUtils;

public class DataBean {
    public int id;
    public String content;

    public int getId() {
        return id;
    }

    public boolean equals(DataBean o) {
        return TextUtils.equals(content, o.content);
    }
}
