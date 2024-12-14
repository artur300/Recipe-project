package com.example.recipeproject

data class Food(
    var name: String,
    var imageUri: String?,
    var authorName: String = "", // Author's name
    var description: String = "", // Description of the food
    var ingredients: String = "", // Ingredients list
    var vegan: Boolean = false // Whether the food is vegan
)


