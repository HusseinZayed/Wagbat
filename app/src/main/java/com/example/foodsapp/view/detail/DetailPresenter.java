package com.example.foodsapp.view.detail;
import com.example.foodsapp.Utils;
import com.example.foodsapp.model.Meals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {

    private DetailView lisenter;

    public DetailPresenter(DetailView lisenter) {
        this.lisenter = lisenter;
    }

    void getMealByName(String mealName) {

        lisenter.showLoading();
        Call<Meals> mealsCall = Utils.getApi().getMealByName(mealName);
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                lisenter.hideLoading();
                if(response.isSuccessful()&&response!=null)
                    lisenter.setMeal(response.body().getMeals().get(0));
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
