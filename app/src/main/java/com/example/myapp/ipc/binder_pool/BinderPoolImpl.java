package com.example.myapp.ipc.binder_pool;

import android.os.IBinder;
import android.os.RemoteException;

import com.example.myapp.IBinderPool;

public class BinderPoolImpl extends IBinderPool.Stub {
    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY_CENTER = 1;
    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BINDER_SECURITY_CENTER:
                binder = new SecurityCenterImpl();
                break;
            case BINDER_COMPUTE:
                binder = new ComputeImpl();
                break;
        }
        return binder;
    }
}
