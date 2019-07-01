package com.example.myapp.ipc.proxy_stub;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * 代理者
 */
public class PlayerProxy implements IPlayer{


    private IBinder iBinder;
    private String mStatus;

    public PlayerProxy(IBinder binder) {
        this.iBinder = binder;
    }

    @Override
    public void paly() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeString("playing");
        try {
            iBinder.transact(1,data,reply,0);
            mStatus = reply.readString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        data.writeString("stop");
        try {
            iBinder.transact(2,data,reply,1);
            mStatus = reply.readString();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getStatus() {
        return mStatus;
    }
}
