package com.example.myapplication.type.Products;

import java.math.BigDecimal

class Book(name:String, price:BigDecimal, var pageCount: Int)
    : Product(name, price) {
    override fun getInfo(): String {
        return super.getInfo() + "pages: "+pageCount
    }
}
