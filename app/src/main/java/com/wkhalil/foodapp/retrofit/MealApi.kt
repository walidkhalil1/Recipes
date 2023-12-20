package com.wkhalil.foodapp.retrofit

import com.wkhalil.foodapp.pojo.CategoryList
import com.wkhalil.foodapp.pojo.MealsByCategoryList
import com.wkhalil.foodapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id: String) : Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName : String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName : String) : Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery: String): Call<MealList>
}