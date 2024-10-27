package com.example.mutiapptheme

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply saved theme before super.onCreate() to ensure it's set correctly
        applySavedTheme()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle window insets for edge-to-edge experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up theme buttons
        findViewById<Button>(R.id.buttonRed).setOnClickListener {
            setThemeAndRecreate("red")
        }
        findViewById<Button>(R.id.buttonGreen).setOnClickListener {
            setThemeAndRecreate("green")
        }
        findViewById<Button>(R.id.buttonBlue).setOnClickListener {
            setThemeAndRecreate("blue")
        }
        findViewById<Button>(R.id.buttonYellow).setOnClickListener {
            setThemeAndRecreate("yellow")
        }
    }

    private fun applySavedTheme() {
        val savedTheme = getSavedTheme()
        Log.d("ThemeDebug", "Applying theme: $savedTheme")  // Add logging here for verification

        // Load the saved theme and apply it before setContentView
        when (savedTheme) {
            "red" -> setTheme(R.style.Theme_App_Red)
            "green" -> setTheme(R.style.Theme_App_Green)
            "blue" -> setTheme(R.style.Theme_App_Blue)
            "yellow" -> setTheme(R.style.Theme_App_Yellow)
            else -> setTheme(R.style.Base_Theme_MutiAppTheme)
        }
    }

    private fun setThemeAndRecreate(theme: String) {
        saveTheme(theme)
        Log.d("ThemeDebug", "Setting theme to: $theme and recreating activity")
        recreate()  // Restart the activity to apply the theme
    }

    private fun saveTheme(theme: String) {
        val prefs = getSharedPreferences("theme_prefs", MODE_PRIVATE)
        prefs.edit().putString("selected_theme", theme).apply()
    }

    private fun getSavedTheme(): String? {
        val prefs = getSharedPreferences("theme_prefs", MODE_PRIVATE)
        return prefs.getString("selected_theme", "default")
    }
}
