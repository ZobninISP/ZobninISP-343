package com.example.myapplication.type;

class User {
    lateinit var login:String
    lateinit var password:String
    fun getInfo():String{
        return login + " - " + password
    }
}
