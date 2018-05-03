package com.example.administrator.myapplication;

/**
 * Created by Administrator on 2018/5/2.
 */

public class MainListItem {
    private String name = "";
    private Class aClass;


    public MainListItem(String name, Class aClass) {
        this.name = name;
        this.aClass = aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
