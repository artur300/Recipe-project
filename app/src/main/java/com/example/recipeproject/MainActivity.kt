package com.example.recipeproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.menu.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, FoodListFragment())
                .commit()
        }
    }
}