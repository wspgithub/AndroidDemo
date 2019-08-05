package com.example.administrator.myapplication.AIDLTest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/8/13.
 */

public class Animal implements Parcelable {

    private int bookID;
    private String bookName;


    public Animal(int bookID, String bookName) {
        this.bookID = bookID;
        this.bookName = bookName;
    }

    protected Animal(Parcel in) {
        bookID = in.readInt();
        bookName = in.readString();
    }

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookID);
        dest.writeString(bookName);
    }


    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
