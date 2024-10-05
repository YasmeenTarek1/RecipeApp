package com.example.recipeapp.ui.recipeFragments.RecipeStepsFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Repository
import com.example.recipeapp.api.model.Step
import com.example.recipeapp.room_DB.model.CookedRecipe
import com.example.recipeapp.room_DB.model.FavoriteRecipe
import kotlinx.coroutines.launch

class RecipeStepsViewModel(private val recipeId: Int, private val repository: Repository): ViewModel() {

    suspend fun getSteps(): List<Step>{
        return repository.getSteps(recipeId = recipeId)
    }

    fun addToCookedRecipes(cookedRecipe: CookedRecipe){
        viewModelScope.launch{
            repository.insertCookedRecipe(cookedRecipe)
            repository.deleteFavoriteRecipe(FavoriteRecipe(cookedRecipe.id , cookedRecipe.title , cookedRecipe.image , cookedRecipe.readyInMinutes , cookedRecipe.servings , cookedRecipe.likes , cookedRecipe.healthScore ,             createdAt = System.currentTimeMillis()))
        }
    }

}