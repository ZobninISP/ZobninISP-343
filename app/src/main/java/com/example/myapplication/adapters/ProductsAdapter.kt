package com.example.myapplication.adapters

import android.animation.LayoutTransition
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.os.UserHandle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.AddProductActivityBinding
import com.example.myapplication.databinding.ProductItemBinding
import com.example.myapplication.type.Product
import com.example.myapplication.type.ProductListAdapter
import com.example.myapplication.type.Products.Book
import java.math.BigDecimal
import kotlin.random.Random

class ProductsAdapter(val list:MutableList<Product>):RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ProductItemBinding):RecyclerView.ViewHolder(binding.root){
        lateinit var product: Product
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.context)))
        holder.binding.btDelete.setOnClickListener {
            val dialog = AlertDialog.Builder(parent.context)
            val index = list.indexOf(holder.product)
            dialog.setTitle("You want remove this product")
            dialog.setPositiveButton("Yes") { _, _ ->
                list.remove(holder.product)
                this.notifyItemRemoved(index)
            }
            dialog.setNegativeButton("No") { dialogInterface: DialogInterface, _ ->
                dialogInterface.cancel()
            }
            dialog.show()
        }

        holder.binding.btChange.setOnClickListener {
            val dialog = AlertDialog.Builder(parent.context)
            val editBinding = AddProductActivityBinding.inflate(LayoutInflater.from(parent.context))
            val index = list.indexOf(holder.product)
            editBinding.editProductName.setText(holder.product.Name)
            editBinding.editProductPrice.setText(holder.product.Price.toString())
            if(holder.product is Book) {
                editBinding.checkBoxIsBook.isChecked = true
                editBinding.editBookPage.setText((holder.product as Book).pageCount.toString())
                }
            dialog.setView(editBinding.root)

            dialog.setPositiveButton("Save"){ _, _ ->
                holder.product.Name = editBinding.editProductName.text.toString()
                holder.product.Price = editBinding.editProductPrice.text.toString().toBigDecimal()
                if(editBinding.checkBoxIsBook.isChecked){
                    val book = holder.product as Book
                    book.pageCount = editBinding.editBookPage.text.toString().toInt()
                }

                notifyItemChanged(index)
            }
            dialog.setNegativeButton("Cancel"){ dialogInterface: DialogInterface, _ ->
                dialogInterface.cancel()
            }
            dialog.show()
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.TextName.text = list[position].Name
        holder.binding.TextPrice.text = list[position].Price.toString()
        holder.product = list[position]
    }

    override fun getItemCount(): Int {
        return  list.size
    }
}