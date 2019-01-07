package com.lotuss.ordersisprocessed.waiter.orders

import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.data.orders.Order
import com.lotuss.ordersisprocessed.waiter.WaiterActivity
import kotlinx.android.synthetic.main.orders_fragment.view.*
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.lotuss.ordersisprocessed.data.auth.UserManager


class FragmentOrders: Fragment(){

    private var orderList: MutableList<Order> = mutableListOf()
    private val database = FirebaseDatabase.getInstance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.orders_fragment, container, false)
        val progressBar: ProgressBar = view.progress_circular
        val recyclerView: RecyclerView = view.recycler_orders
        val adapter = OrdersAdapter(layoutInflater, orderList)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        getOrderList(progressBar, adapter)
        return view
    }

    private fun sendNotification(order: Order){
        val notificationManager: NotificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifyId = 0
        val id = context!!.getString(R.string.default_notification_channel_id)
        val title = context!!.getString(R.string.default_notification_channel_title)
        val intent: Intent
        val pendingIntent: PendingIntent
        val builder: NotificationCompat.Builder
        var notificationTitle = ""
        var notificationMessage = ""
        when (order.status){
            2 -> {
                notificationTitle = context!!.getString(R.string.order_is_done_title)
                notificationMessage = context!!.getString(R.string.order_is_done_message, order.id)
            }
            3 -> {
                notificationTitle = context!!.getString(R.string.order_is_cancelled_title)
                notificationMessage = context!!.getString(R.string.order_is_cancelled_message, order.id)
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance: Int = NotificationManager.IMPORTANCE_HIGH
            var mChannel: NotificationChannel? = notificationManager.getNotificationChannel(id)
            if (mChannel == null) {
                mChannel = NotificationChannel(id, title, importance)
                mChannel.enableVibration(true)
                mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                notificationManager.createNotificationChannel(mChannel)
            }
            builder = NotificationCompat.Builder(context!!, id)
            intent = Intent(context, WaiterActivity::class.java)
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            builder.setContentTitle(notificationTitle)
                    .setSmallIcon(R.drawable.splash_for_v23)
                    .setContentText(notificationMessage)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setVibrate(longArrayOf(100, 200, 400, 300, 500, 400, 300, 200, 400))
        }else{
            builder = NotificationCompat.Builder(context!!, id)
            intent = Intent(context, WaiterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            builder.setContentTitle(notificationTitle)
                    .setSmallIcon(R.drawable.splash_for_v23)
                    .setContentText(notificationMessage)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
        }
        val notification = builder.build()
        notificationManager.notify(notifyId, notification)
    }

    private fun getOrderList(progressBar: ProgressBar, adapter: OrdersAdapter){
        val orderReference = database.getReference("orders")
        orderReference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                progressBar.visibility = View.VISIBLE
                orderList.clear()
                for (ds in dataSnapshot.children) {
                    val order: Order = ds.getValue(Order::class.java)!!
                    if (order.waiter == UserManager.user.id) {
                        orderList.add(order)
                        adapter.notifyDataSetChanged()
                        if (!order.checkedByWaiter) {
                            if (order.status != 1)
                            sendNotification(order)
                            orderReference.child(order.id.toString()).child("checkedByWaiter").setValue(true)
                        }
                    }
                }
                progressBar.visibility = View.GONE
                adapter.notifyDataSetChanged()
            }
        })
    }
}