package com.example.myapplication;

import android.animation.LayoutTransition
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.os.UserHandle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.ProductsAdapter
import com.example.myapplication.databinding.AddProductActivityBinding
import com.example.myapplication.type.Product
import com.example.myapplication.type.ProductListAdapter
import java.io.LineNumberReader
import java.math.BigDecimal
import kotlin.random.Random

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding.ActivityProductListAdapterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductListAdapterBinding.infLate(layoutInfLater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val productList = mutableListOf<Product>()
        productList.add(product("Чашка", BigDecimal.valueOf(65.72)))
        productList.add(product("Ложка", BigDecimal.valueOf(32.10)))
        productList.add(Book("Словарь русского языка для 3 курса", BigDecimal.valueOf(165.72),64))
        binding.RecuclerProducts.layoutManager = LinearLayoutManager(this)
        binding.RecuclerProducts.adapter = ProductsAdapter(productList)

        binding.btAddProduct.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Добавление пользователя")
            val dialogAdd = AddProductActivityBinding.inflate(layoutInflater)
            dialog.setView(dialogAdd.root)
            dialogAdd.editBookPage.visibility = View.GONE
            dialogAdd.checkBoxIsBook.setOnClickListener { _, b ->
                dialogAdd.editBookPage.visibility = if (b) View.VISIBLE else View.GONE
            }

            dialog.setPositiveButton("Add"){
                 _, _, ->
                if(dialogAdd.checkBoxIsBook.isChecked){
                    productList.add(
                        Book(
                            dialogAdd.editProductName.text.toString(),
                            dialogAdd.editProductPrice.text.toString().toBigDecimal(),
                            dialogAdd.editBookPage.text.toString().toInt()
                        )
                    )
                }else{
                    productList.add(
                        Product(
                            dialogAdd.editProductName.text.toString(),
                            dialogAdd.editProductPrice.text.toString().toBigDecimal()
                        )
                    )
                }
                binding.RecuclerProducts.adapter?.notifyItemInserted(productList.lastIndex)
            }
            dialog.show()
        }
    }
}
