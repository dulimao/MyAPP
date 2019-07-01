// IBinderPool.aidl
package com.example.myapp;

//Binder连接池接口

interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
