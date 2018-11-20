package com.lotuss.ordersisprocessed

import android.app.ProgressDialog
import android.content.Context

class ProgressDialogCaller{
    companion object {
        private var progressDialog: ProgressDialog? = null

        fun showProgressDialog(context: Context) {
            if (progressDialog == null) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setMessage(context.getString(R.string.loading))
                progressDialog!!.isIndeterminate = true
            }

            progressDialog!!.show()
        }

        fun hideProgressDialog() {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }
}