package com.example.foodsapp.view.category;

import com.example.foodsapp.Utils;
import com.example.foodsapp.model.Meals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPresenter {
    private CategoryView lisenter;

    public CategoryPresenter(CategoryView view) {
        this.lisenter = view;
    }

    void getMealByCategory(String category) {
        lisenter.showLoading();

        Call<Meals> mealsCall = Utils.getApi().getMealByCategory(category);
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
              lisenter.hideLoading();
              if(response.isSuccessful()&&response.body()!=null)
                  lisenter.setMeals(response.body().getMeals());
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
}
