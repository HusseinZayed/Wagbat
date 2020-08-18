package com.example.foodsapp.view.detail;

import com.example.foodsapp.model.Meals;

public interface DetailView {

    void showLoading();
    void hideLoading();
    void setMeal(Meals.Meal meal);
    void onErrorLoading(String error);
}
