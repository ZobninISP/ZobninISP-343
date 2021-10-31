package com.example.myapplication

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
import com.example.myapplication.type.Product
import com.example.myapplication.type.ProductListAdapter
import java.math.BigDecimal
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var printUi: Boolean = false
    private fun stringPrint(string: String) {
        if (printUi)
            binding.ViewArea.text = string
        else {
            binding.ViewArea.text = ""
            Log.d("test", string)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.infLate(layoutInflater)
        setContentView(binding.root)
        binding.RadioGroupList.setOnCheckedChangeListener { _, Id ->
            printUi = Id == R.id.RadioUI
        }

        binding.SimpleArrayPrint.SetOnClickListener {
            var list = mutableListOf<Int>()
            for (i in 0 until (3..5).random()) {
                list.add(Random.nextInt(0, 100))
            }
            stringPrint(list.joinToSting())
        }

        binding.PrintObject.setOnClickListener {
            val user = User()
            user.login = "User"
            user.password = "pass123"
            stringPrint(user.login + " " + user.password)
        }

        binding.PrintObjectUsingMethod.setOnClickListener {
            val user = User()
            user.login = "Admin"
            user.password = "PassAdmin"
            stringPrint(user.getInfo())
        }

        binding.PrintObjectWithConstructor.setOnClickListener {
            val product = Product("Носок правый + левый в подарок", BigDecimal.valueOf(52.99))
            stringPrint(productGetInfo())
        }

        binding.InheritanceExample.SetOnClickListener {
            val book = Book("Война и мир 3-4 том", BigDecimal.valueOf(310.58),750)
            stringPrint(book.getInfo())
        }

        val ProductList = mutableListOf<Product>()
        ProductList.add(Product("Чашка", BigDecimal.valueOf(65.70)))
        ProductList.add(Product("Ложка", BigDecimal.valueOf(30.20)))
        ProductList.add(Book("Словарь русского языка для 3 курса", BigDecimal.valueOf(163,50)))

        binding.ProductsList.setOnClickListener {
            stringPrint(ProductList.joinToString("/n") { p -> p.getInfo() } )
        }

        binding.AddProduct.SetOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Добавление пользователя")
            val dialogAdd = AddProductActivityBinding.inflate(layoutInflater)
            dialogAdd.editBookPage.visibility = View.GONE
            dialogAdd.checkBoxIsBook.setOnCheckedChangeListener { _, b ->
                dialogAdd.editBookPage.visibility = if (b) View.VISIBLE else View.GONE
            }

            dialog.setPositiveButton("Add") { _, _, ->
                if (dialogAdd.checkBoxIsBook.isChecked) {
                    ProductList.add(
                        Book(
                            dialogAdd.editProduckName.text.toString(),
                            dialogAdd.editProduckPrice.text.toString().toBigDecimal(),
                            dialogAdd.editBookPage.text.toString().toInt
                        )
                    )
                } else {
                    ProductList.add(
                        Product(
                            dialogAdd.editProduckName.text.toString(),
                            dialogAdd.editProduckName.text.toString().toBigDecimal()
                        )
                    )
                }
            }
            dialog.show()
        }
        binding.btap.setOnClickListener {
            val ActivityProducts = Intent(this, ProductListAdapter::class.java)
        }
    }
}
