// ISecurityCenter.aidl
package com.example.myapp;



interface ISecurityCenter {

    String encrypt(String content);
    String decrypt(String password);
}
