package com.example.konekdatabase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class ManageFakultasActivity : AppCompatActivity() {

    lateinit var i:Intent
    lateinit var add:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_fakultas)

        add=findViewById(R.id.btnCreate)
        i=intent

        if (i.getStringExtra("EditMode").equals("1")){
            onEditMode()
        }
    }

    add.setOnClickListener{
        onCreate()
    }
}
private fun onEditMode(){
    TODO("not implemented")
    txt_kodefakultas.setText(i.getStringExtra("txt_kodefakultas"))
    txt_namafakultas.setText(i.getStringExtra("txt_namafakultas"))

    btnCreate.visibility=View.GONE
    btnUpdate.visibility=View.VISIBLE
    btnDelete.visibility=View.VISIBLE
}

private fun onCreate(){
    val loading=ProgressDialog(this)
    loading.setMessage("Menambahkan Data...")
    loading.show()

    AndroidNetworking.post(ApiEndPoint.CREATE)
        .addBodyParameter("kode fakultas",txt_kodefakultas.text.toString())
        .addBodyParameter("nama fakultas",txt_namafakultas.text.toString())
        .setPriority(Priority.MEDIUM)
        .build()
        .getAsJSONObject(object :JSONObjectRequestListener{
            override fun onResponse(response: JSONObject?) {
                loading.dismiss()
                Toast.makeText(applicationContext,response?.getString("Message"),Toast.LENGTH_SHORT).show()

                if (response?.getString("Message")?.contains("Successfully")!!){
                    this@ManageFakultasActivity.finish()
                }
            }

            override fun onError(anError: ANError?) {
                loading.dismiss()
                Log.d("ONERROR",anError?.errorDetail?.toString())

                Toast.makeText(applicationContext,"Connection failure",Toast.LENGTH_SHORT).show()
            }
        })
}
