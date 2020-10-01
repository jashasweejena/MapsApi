package com.example.mapsassignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var TAG = this.javaClass.simpleName
    var editText: EditText? = null
    var button: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        button!!.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(this@MainActivity, ResultsActivity::class.java)
            intent.putExtra("location", editText!!.getText().toString())
            startActivity(intent)
        })
    }
}