package com.lotuss.ordersisprocessed.waiter.menu


import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.lotuss.ordersisprocessed.R
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.lotuss.ordersisprocessed.data.orders.OrderManager
import com.lotuss.ordersisprocessed.data.auth.UserManager
import kotlinx.android.synthetic.main.send_dialog.view.*
import java.util.*


const val PEPPER: Int = 0
const val SUGAR: Int = 1

class OrderDialog: DialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val v: View = inflater.inflate(R.layout.send_dialog, null)
        val orderFoodAdapter = OrderFoodAdapter(inflater, OrderManager.order.orderList)
        val recycler = v.food_recycler
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler.adapter = orderFoodAdapter
        val cancelBtn: Button = v.cancel
        val sendBtn: Button = v.send
        val editNotes: EditText = v.notes
        cancelBtn.setOnClickListener{
            dismiss()
        }
        sendBtn.setOnClickListener{
            OrderManager.order.id = (System.currentTimeMillis()%100000L).toInt()
            OrderManager.order.date = Calendar.getInstance().time
            OrderManager.order.waiter = UserManager.user.id
            OrderManager.order.desc = editNotes.text.toString()
            setOrderToDatabase(OrderManager.order.id)
            Toast.makeText(activity, R.string.send_order, Toast.LENGTH_LONG).show()
            dismiss()
        }
        v.pepper.setOnClickListener{
            printNote(editNotes, PEPPER)
        }
        v.sugar.setOnClickListener{
            printNote(editNotes, SUGAR)
        }
        return v
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        OrderManager.order.orderList.clear()
    }

    private fun printNote(editText: EditText, id: Int){
        when(id){
            PEPPER -> editText.append(editText.context.resources.getString(R.string.pepper) + " ")
            SUGAR -> editText.append(editText.context.resources.getString(R.string.sugar) + " ")
        }
    }

    private fun setOrderToDatabase(id: Int){
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("orders")
        reference.child(id.toString()).setValue(OrderManager.order)
    }
}