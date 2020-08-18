package com.example.foodsapp.view.home;

import com.example.foodsapp.model.Categories;
import com.example.foodsapp.model.Meals;

import java.util.List;

public interface HomeView {

     void showLoading();
     void hideLoading();
     void setMeal(List<Meals.Meal> meals);
     void setFilterMeals(List<Meals.Meal> meals);
     void setCategories(List<Categories.Category> categories);
     void onErrorLoading(String error);


}
