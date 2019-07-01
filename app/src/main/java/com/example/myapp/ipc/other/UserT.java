package com.example.myapp.ipc.other;

import android.os.Parcel;
import android.os.Parcelable;

public class UserT implements Parcelable {

    public int userId;
    public String name;

    public UserT userT;



    protected UserT(Parcel in) {
        userId = in.readInt();
        name = in.readString();
        userT = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    public static final Creator<UserT> CREATOR = new Creator<UserT>() {
        @Override
        public UserT createFromParcel(Parcel in) {
            return new UserT(in);
        }

        @Override
        public UserT[] newArray(int size) {
            return new UserT[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeParcelable(userT,0);

    }
}
