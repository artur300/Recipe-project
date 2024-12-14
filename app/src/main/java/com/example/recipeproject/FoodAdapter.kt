package com.example.menu

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.menu.databinding.ItemFoodCardBinding
import com.bumptech.glide.Glide

class FoodAdapter(
    private val foods: MutableList<Food>,
    private val onDeleteClick: (Food) -> Unit,
    private val onEditClick: (Food) -> Unit,
    private val onViewDetailsClick: (Food) -> Unit // Callback חדש
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(val binding: ItemFoodCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]
        holder.binding.foodName.text = food.name

        food.imageUri?.let {
            Glide.with(holder.itemView.context)
                .load(it)
                .placeholder(R.drawable.ic_launcher_foreground) // תמונה חלופית בזמן הטעינה
                .error(R.drawable.ic_launcher_background) // תמונה במקרה של שגיאה
                .into(holder.binding.foodImage)
        }

        holder.binding.btnEdit.setOnClickListener {
            onEditClick(food)
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick(food)
        }

        // טיפול בלחיצה על "View Details"
        holder.binding.btnViewDetails.setOnClickListener {
            onViewDetailsClick(food)
        }
    }

    override fun getItemCount(): Int = foods.size
}
