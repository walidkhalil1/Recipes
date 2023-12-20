package com.wkhalil.foodapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.wkhalil.foodapp.HomeFragment
import com.wkhalil.foodapp.R
import com.wkhalil.foodapp.ViewModel.MealViewModel
import com.wkhalil.foodapp.ViewModel.MealViewModelFactory
import com.wkhalil.foodapp.databinding.ActivityMealBinding
import com.wkhalil.foodapp.db.MealDatabase
import com.wkhalil.foodapp.pojo.Meal

class MealActivity : AppCompatActivity() {

    private  lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var youtubeLink : String
    private lateinit var mealMvvm : MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        //mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setMealInformationInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)

        observeMealDetailsLiveData()

        onYoutubeImageClick()

        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.fabMeal.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.ivMealYtb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave :Meal? =null
    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(meal: Meal) {
                onResponseCase()
                mealToSave = meal

                binding.tvMealCategory.text = "Category : ${meal.strCategory}"
                binding.tvMealArea.text = "Area : ${meal.strArea}"
                binding.tvMealInstuctions1.text = meal.strInstructions
                youtubeLink = meal.strYoutube!!
            }

        })
    }

    private fun setMealInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.ivMeal)

        binding.ctblMeal.title = mealName
        binding.ctblMeal.setCollapsedTitleTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        binding.ctblMeal.setExpandedTitleColor(ContextCompat.getColor(applicationContext, R.color.white))
    }

    private fun getMealInformationFromIntent() {
        var intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    fun loadingCase(){
        binding.lpiMeal.visibility = View.VISIBLE
        binding.fabMeal.visibility = View.INVISIBLE
        binding.tvMealInstructions.visibility = View.INVISIBLE
        binding.tvMealCategory.visibility = View.INVISIBLE
        binding.tvMealArea.visibility = View.INVISIBLE
        binding.ivMeal.visibility = View.INVISIBLE
    }

    fun onResponseCase(){
        binding.lpiMeal.visibility = View.INVISIBLE
        binding.fabMeal.visibility = View.VISIBLE
        binding.tvMealInstructions.visibility = View.VISIBLE
        binding.tvMealCategory.visibility = View.VISIBLE
        binding.tvMealArea.visibility = View.VISIBLE
        binding.ivMeal.visibility = View.VISIBLE
    }
}