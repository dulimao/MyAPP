// IBookMananger.aidl 客户端调用服务端的接口
package com.example.myapp;

import com.example.myapp.Book;
import com.example.myapp.IBookArrivedListener;

interface IBookMananger {
    List<Book> getBookList();
    void addBook(in Book book);

    void registerListener(in IBookArrivedListener listener);
    void unregisterListener(in IBookArrivedListener listener);
}
