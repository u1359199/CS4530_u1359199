package com.example.a1helloandroid

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a1helloandroid.databinding.ActivityMainBinding

/**
 * MainActivity: Displays five buttons on the screen.
 * When a button is clicked, it opens Activity2 and passes the button's text.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set main activity layout objects to the binding variable
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set the click on listener for all buttons
        setBtnClickListener(binding.marioBtn)
        setBtnClickListener(binding.luigiBtn)
        setBtnClickListener(binding.linkBtn)
        setBtnClickListener(binding.scorpionBtn)
        setBtnClickListener(binding.subzeroBtn)
    }

    /**
     * Sets the clickOn listener to a Button that launches Activity2.
     *
     * When the button is clicked, Activity2 is started and receives the
     * text displayed on the button.
     *
     * @param button The Button to set the click on listener.
     */
    fun setBtnClickListener(button: Button) {
        button.setOnClickListener {
            // Create an Intent to start Activity2
            val intent = Intent(this, Activity2::class.java)

            // Pass the button's text to Activity2
            intent.putExtra("button_text", button.text.toString())

            // Start Activity2
            startActivity(intent)
        }
    }
}

