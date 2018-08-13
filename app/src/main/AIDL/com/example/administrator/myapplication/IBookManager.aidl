// IBookManager.aidl
package com.example.administrator.myapplication;
import com.example.administrator.myapplication.AIDLTest.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
