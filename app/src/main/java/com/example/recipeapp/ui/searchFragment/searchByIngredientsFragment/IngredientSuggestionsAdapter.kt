package com.example.recipeapp.ui.searchFragment.searchByNameFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.api.model.Ingredient
import com.example.recipeapp.databinding.IngredientSuggestionItemBinding


class IngredientSuggestionsAdapter(private val onOptionClick: (String) -> Unit) : RecyclerView.Adapter<IngredientSuggestionsAdapter.IngredientSuggestionsViewHolder>() {

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientSuggestionsViewHolder {
        return IngredientSuggestionsViewHolder(IngredientSuggestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: IngredientSuggestionsViewHolder, position: Int) {
        val option = differ.currentList[position]
        holder.itemBinding.suggestionTextView.text = option.name
        holder.itemView.setOnClickListener{
            onOptionClick(option.name)
        }
    }

    inner class IngredientSuggestionsViewHolder(val itemBinding: IngredientSuggestionItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
