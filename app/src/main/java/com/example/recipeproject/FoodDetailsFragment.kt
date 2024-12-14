package com.example.menu

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.menu.databinding.FragmentFoodDetailsBinding
import com.bumptech.glide.Glide


class FoodDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFoodDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodDetailsBinding.inflate(inflater, container, false)

        // קבלת הנתונים מה-Bundle
        val foodName = arguments?.getString("food_name") ?: "No Name"
        val foodImageUri = arguments?.getString("food_image_uri")
        val foodDescription = arguments?.getString("food_description") ?: "No Description" // תיאור

        // הצגת הנתונים ב-UI
        binding.foodName.text = foodName
        binding.foodDescription.setText(foodDescription) // הצגת התיאור
        foodImageUri?.let {
            // שימוש ב-Glide לטעינת תמונה בפרטי המנה
            Glide.with(requireContext())
                .load(it)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(binding.foodImage)
        }

        return binding.root
    }
}
