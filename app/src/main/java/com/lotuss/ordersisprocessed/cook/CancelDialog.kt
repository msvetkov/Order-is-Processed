package com.lotuss.ordersisprocessed.cook

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.FirebaseDatabase
import com.lotuss.ordersisprocessed.R
import kotlinx.android.synthetic.main.cancel_dialog.view.*

class CancelDialog: DialogFragment(){

    var orderId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val v: View = inflater.inflate(R.layout.cancel_dialog, null)
        v.yes_btn.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("orders")
            reference.child(orderId.toString()).child("checkedByWaiter").setValue(false)
            reference.child(orderId.toString()).child("status").setValue(3)
            dismiss()
        }
        v.no_btn.setOnClickListener {
            dismiss()
        }
        return v
    }
}