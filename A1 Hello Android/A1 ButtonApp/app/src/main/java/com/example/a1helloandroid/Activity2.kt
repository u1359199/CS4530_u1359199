package com.example.a1helloandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a1helloandroid.databinding.Activity2Binding

/**
 * Activity2: Displays the text of the button that was clicked in MainActivity.
 * Includes a return button to go back to MainActivity.
 */
class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Set second activity layout objects to the binding variable
        val binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the return button to go back to MainActivity
        binding.returnToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Retrieve the button text passed from MainActivity and display it
        val buttonText = intent.extras?.getString("button_text")
        binding.btnText.text = buttonText
    }
}