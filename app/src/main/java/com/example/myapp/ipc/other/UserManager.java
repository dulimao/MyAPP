package com.example.myapp.ipc.other;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//Serializable序列化和反序列化
public class UserManager {

    private void test() {
        User user = new User();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("cache.txt"));
            outputStream.writeObject(user);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    ObjectInputStream inputStream;

    {
        try {
            inputStream = new ObjectInputStream(new FileInputStream("cache.txt"));
            User user1 = (User) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
