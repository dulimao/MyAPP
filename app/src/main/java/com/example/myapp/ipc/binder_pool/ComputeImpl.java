package com.example.myapp.ipc.binder_pool;

import android.os.RemoteException;

import com.example.myapp.ICompute;

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
