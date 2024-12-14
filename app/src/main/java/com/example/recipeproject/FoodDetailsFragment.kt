package com.example.recipeproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.menu.databinding.FragmentFoodDetailsBinding
import com.bumptech.glide.Glide
import com.example.menu.R


class FoodDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFoodDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodDetailsBinding.inflate(inflater, container, false)

        // קבלת הנתונים מה-Bundle
        val foodName = arguments?.getString("food_name") ?: "No Name"
        val foodImageUri = arguments?.getString("food_image_uri")
        val foodDescription = arguments?.getString("food_description") ?: "No Description"
        val foodIngredients = arguments?.getString("food_ingredients") ?: "No Description"
        val foodAuthorName=arguments?.getString("food_authorName")?: "No name"

        // הצגת הנתונים ב-UI
        binding.authorName.text=foodAuthorName
        binding.foodName.text = foodName
        binding.foodDescription.setText(foodDescription)
        binding.ingredients.setText(foodIngredients)


        //-------------------------do not change this-----
        foodImageUri?.let {
            Glide.with(requireContext())
                .load(it)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(binding.foodImage)
        }
        //--------------------------------------------------

        return binding.root
    }
}
