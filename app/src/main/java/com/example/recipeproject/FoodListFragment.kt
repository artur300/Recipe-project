package com.example.recipeproject

import com.example.menu.utils.AnimationUtils
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.activity.result.contract.ActivityResultContracts
import com.example.menu.databinding.FragmentFoodListBinding
import com.bumptech.glide.Glide
import com.example.menu.R

class FoodListFragment : Fragment() {

    private lateinit var binding: FragmentFoodListBinding
    private lateinit var adapter: FoodAdapter
    private val foodList = mutableListOf<Food>()
    private var selectedImageUri: Uri? = null
    private var imagePreview: ImageView? = null




    //-------------------------do not change this-----------------------------------------------------------------
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            imagePreview?.let {
                Glide.with(requireContext())
                    .load(uri)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(it)
            }
        } else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
        }
    }
    //-------------------------------------------------------------------------------------------




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(inflater, container, false)

        adapter = FoodAdapter(
            foodList,
            onDeleteClick = { food -> removeFood(food) },
            onEditClick = { food -> showEditFoodDialog(food) },
            onViewDetailsClick = { food -> navigateToFoodDetails(food) }
        )

        binding.recyclerView.layoutManager = GridLayoutManager(context, 1)
        binding.recyclerView.adapter = adapter

        binding.menuIcon.setOnClickListener {
            AnimationUtils.scaleView(binding.menuIcon)
            showAddFoodDialog()
        }
        return binding.root
    }



    private fun addNewFood(name: String, imageUri: String, description: String, authorName: String,
                           ingredients : String) {
        val newFood = Food(
            name = name,
            imageUri = imageUri,
            description = description,
            authorName = authorName,
            ingredients=ingredients
        )
        foodList.add(newFood)
        adapter.notifyItemInserted(foodList.size - 1)
    }



    private fun removeFood(food: Food) {
        val position = foodList.indexOf(food)
        if (position != -1) {
            foodList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }




    private fun showAddFoodDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_food_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        imagePreview = dialogView.findViewById(R.id.food_image_preview)
        val pickImageButton = dialogView.findViewById<Button>(R.id.btn_pick_image)
        val nameInput = dialogView.findViewById<EditText>(R.id.food_name_input)
        val descriptionInput = dialogView.findViewById<EditText>(R.id.food_description_input)
        val authorNameInput=dialogView.findViewById<EditText>(R.id.author_name_input)
        val ingredientsInput = dialogView.findViewById<EditText>(R.id.ingredients_description_input)



        pickImageButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        dialogView.findViewById<View>(R.id.btn_add_food).setOnClickListener {
            val name = nameInput.text.toString()
            val description = descriptionInput.text.toString()
            val authorName=authorNameInput.text.toString()
            val ingredients= ingredientsInput.text.toString()


            if (name.isNotBlank() && selectedImageUri != null) {
                addNewFood(name, selectedImageUri.toString(), description,authorName,ingredients)
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }




    private fun showEditFoodDialog(food: Food) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_food_dialog, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        imagePreview = dialogView.findViewById(R.id.food_image_preview)
        val nameInput = dialogView.findViewById<EditText>(R.id.food_name_input)
        val authorNameInput=dialogView.findViewById<EditText>(R.id.author_name_input)
        val descriptionInput = dialogView.findViewById<EditText>(R.id.food_description_input)
        val pickImageButton = dialogView.findViewById<Button>(R.id.btn_pick_image)
        val ingredientsInput = dialogView.findViewById<EditText>(R.id.ingredients_description_input)


        ingredientsInput.setText(food.ingredients)
        authorNameInput.setText(food.authorName)
        nameInput.setText(food.name)
        descriptionInput.setText(food.description)


        //-------------------------do not change this-----
        food.imageUri?.let { uri ->
            Glide.with(requireContext())
                .load(uri)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(imagePreview!!)
        }
        //-------------------------------------------------


        pickImageButton.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        dialogView.findViewById<View>(R.id.btn_add_food).setOnClickListener {
            val newName = nameInput.text.toString()
            val newDescription = descriptionInput.text.toString()
            val newAuthorName=authorNameInput.text.toString()
            val newingredients = ingredientsInput.text.toString()


            if (newName.isNotBlank() && selectedImageUri != null) {
                food.name = newName
                food.description = newDescription
                food.ingredients=newingredients
                food.authorName=newAuthorName
                food.imageUri = selectedImageUri.toString()
                adapter.notifyItemChanged(foodList.indexOf(food))
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun navigateToFoodDetails(food: Food) {
        val bundle = Bundle().apply {
            putString("food_name", food.name)
            putString("food_image_uri", food.imageUri)
            putString("food_description", food.description)
            putString("food_authorName", food.authorName)
            putString("food_ingredients", food.ingredients)

        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.main, FoodDetailsFragment::class.java, bundle)
            .addToBackStack(null)
            .commit()
    }
}
