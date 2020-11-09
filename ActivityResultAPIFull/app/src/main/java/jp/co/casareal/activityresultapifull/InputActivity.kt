package jp.co.casareal.activityresultapifull

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)


        findViewById<Button>(R.id.btnFinish).setOnClickListener {

            val inputText = findViewById<EditText>(R.id.editText).text.toString()

            val resultIntent = Intent()

            resultIntent.putExtra("message", inputText)

            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        }
    }
}