package com.example.recipeapp.ui.recipeFragments.RecipeStepsFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.Repository
import com.example.recipeapp.api.model.Step
import com.example.recipeapp.room_DB.model.CookedRecipe
import com.example.recipeapp.room_DB.model.FavoriteRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeStepsViewModel(private val recipeId: Int, private val repository: Repository): ViewModel() {

    val cookedRecipes: Flow<List<CookedRecipe>> = repository.getAllCookedRecipes()
    val favRecipes: Flow<List<FavoriteRecipe>> = repository.getAllFavoriteRecipes()

    suspend fun getSteps(): List<Step>{
        return withContext(Dispatchers.IO) {
            repository.getSteps(recipeId = recipeId)
        }
    }

    fun addToCookedRecipes(cookedRecipe: CookedRecipe){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertCookedRecipe(cookedRecipe)
            cookedRecipes.collect { recipes ->
                repository.updateCookedRecipesInFirestore(recipes)
            }

            repository.deleteFavoriteRecipe(FavoriteRecipe(cookedRecipe.id , cookedRecipe.title , cookedRecipe.image , cookedRecipe.readyInMinutes , cookedRecipe.servings , cookedRecipe.likes , cookedRecipe.healthScore , createdAt = System.currentTimeMillis()))
            favRecipes.collect { recipes ->
                repository.updateFavRecipesInFirestore(recipes)
            }
        }
    }

    fun removeFromFavRecipes(cookedRecipe: CookedRecipe){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFavoriteRecipe(FavoriteRecipe(cookedRecipe.id , cookedRecipe.title , cookedRecipe.image , cookedRecipe.readyInMinutes , cookedRecipe.servings , cookedRecipe.likes , cookedRecipe.healthScore , createdAt = System.currentTimeMillis()))
            favRecipes.collect { recipes ->
                repository.updateFavRecipesInFirestore(recipes)
            }
        }
    }

}