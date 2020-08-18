package com.example.foodsapp.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.foodsapp.R;
import com.example.foodsapp.adapter.RecyclerViewHomeAdapter;
import com.example.foodsapp.adapter.ViewPagerHeaderAdapter;
import com.example.foodsapp.model.Categories;
import com.example.foodsapp.model.Meals;
import com.example.foodsapp.view.category.CategoryActivity;
import com.example.foodsapp.view.detail.DetailsActivity;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.foodsapp.view.category.CategoryFragment.EXTRA_NAME_MEAL;


public class HomeActivity extends AppCompatActivity implements HomeView, RecyclerViewHomeAdapter.ClickListener {
    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";
    List<Categories.Category> category;
    @BindView(R.id.viewPagerHeader)
    ViewPager viewPagerMeal;
    @BindView(R.id.recyclerCategory)
    RecyclerView recyclerViewCategory;

    HomePresenter homePresenter;
    @BindView(R.id.search)
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        homePresenter = new HomePresenter(this);
        homePresenter.getMeals();
        homePresenter.getCategories();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               homePresenter.getFilterMeals(s+"");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void showLoading() {
        findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        findViewById(R.id.shimmerMeal).setVisibility(View.INVISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.INVISIBLE);
    }

    @Override
    public void setMeal(final List<Meals.Meal> meals) {
        ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(meals, this);
        viewPagerMeal.setAdapter(headerAdapter);
        viewPagerMeal.setPadding(20, 0, 150, 0);
        headerAdapter.notifyDataSetChanged();
        headerAdapter.setOnItemClickListener(new ViewPagerHeaderAdapter.ClickListener() {
            @Override
            public void onClick(int position) {
               String name_meal = meals.get(position).getStrMeal();
               startActivity(new Intent(getApplicationContext(), DetailsActivity.class).putExtra(EXTRA_NAME_MEAL,name_meal));
            }
        });
    }

    @Override
    public void setFilterMeals(List<Meals.Meal> meals) {
        if(meals.size()>0) {
            ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(meals, this);
            viewPagerMeal.setAdapter(headerAdapter);
            viewPagerMeal.setPadding(20, 0, 150, 0);
            headerAdapter.notifyDataSetChanged();
            headerAdapter.setOnItemClickListener(new ViewPagerHeaderAdapter.ClickListener() {
                @Override
                public void onClick(int position) {
                    String name_meal = meals.get(position).getStrMeal();
                    startActivity(new Intent(getApplicationContext(), DetailsActivity.class).putExtra(EXTRA_NAME_MEAL, name_meal));
                }
            });
        }
    }

    @Override
    public void setCategories(List<Categories.Category> categories) {
        category = categories;
        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(categories, this);
        recyclerViewCategory.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        homeAdapter.setOnItemClickListener(this);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorLoading(String error) {
        Log.i("myResponse", error);

    }


    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
        intent.putExtra(EXTRA_POSITION, position);
        startActivity(intent);
    }
}
