
package com.example.foodsapp.view.home;

import androidx.annotation.NonNull;

import com.example.foodsapp.Utils;
import com.example.foodsapp.model.Categories;
import com.example.foodsapp.model.Meals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class HomePresenter {

    private HomeView lisenter;

    public HomePresenter(HomeView lisenter) {
        this.lisenter = lisenter;
    }

    void getMeals() {

        lisenter.showLoading();

        Call<Meals> mealsCall = Utils.getApi().getMeal();
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call,@NonNull Response<Meals> response) {
                lisenter.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    lisenter.setMeal(response.body().getMeals());
                }
                else {
                    lisenter.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                lisenter.onErrorLoading(t.getMessage());
                lisenter.hideLoading();
            }
        });
    }

    void getFilterMeals(String mealName) {

        lisenter.showLoading();
        Call<Meals> mealsCall = Utils.getApi().getMealByName(mealName);
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                lisenter.hideLoading();

                 if (response.isSuccessful() && response != null)
                    lisenter.setFilterMeals(response.body().getMeals());
                 else
                    lisenter.onErrorLoading(response.message());
            }

            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                lisenter.hideLoading();
                lisenter.onErrorLoading(t.getMessage());
            }
        });
    }
    void getCategories() {
        lisenter.showLoading();

        Call<Categories> categoriesCall =  Utils.getApi().getCategories();
        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(@NonNull Call<Categories> call,@NonNull Response<Categories> response) {
                lisenter.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    lisenter.setCategories(response.body().getCategories());
                }
                else {
                     lisenter.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                lisenter.onErrorLoading(t.getMessage());
                lisenter.hideLoading();
            }
        });
    }
}
