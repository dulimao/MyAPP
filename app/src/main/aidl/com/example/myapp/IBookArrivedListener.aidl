// IBookArrivedListener.aidl 服务端调用客户端的接口
package com.example.myapp;

import com.example.myapp.Book;
interface IBookArrivedListener {
    void bookArrived(in Book book);
}
