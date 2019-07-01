package com.example.myapp.ipc.proxy_stub;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * 存根
 */
public abstract class PlayerStub extends Binder implements IPlayer {

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        reply.writeString(data.readString() + "mp3");
        if (code == 1) {
            this.paly();
        } else if (code == 2) {
            this.stop();
        }
        return true;
    }


}
