package com.example.foodsapp.view.category;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodsapp.R;
import com.example.foodsapp.Utils;
import com.example.foodsapp.adapter.RecyclerViewMealByCategory;
import com.example.foodsapp.model.Meals;
import com.example.foodsapp.view.detail.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.foodsapp.adapter.ViewPagerCategoryAdapter.EXTRA_DATA_DESC;
import static com.example.foodsapp.adapter.ViewPagerCategoryAdapter.EXTRA_DATA_IMAGE;
import static com.example.foodsapp.adapter.ViewPagerCategoryAdapter.EXTRA_DATA_NAME;


public class CategoryFragment extends Fragment implements CategoryView {

    public static final String EXTRA_NAME_MEAL = "nameMale";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imageCategory)
    ImageView imageCategory;
    @BindView(R.id.imageCategoryBg)
    ImageView imageCategoryBg;
    @BindView(R.id.textCategory)
    TextView textCategory;
    AlertDialog.Builder descDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            textCategory.setText(getArguments().getString(EXTRA_DATA_DESC));
            Picasso.get().load(getArguments().getString(EXTRA_DATA_IMAGE)).into(imageCategory);
            Picasso.get().load(getArguments().getString(EXTRA_DATA_IMAGE)).into(imageCategoryBg);
        }

        descDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString(EXTRA_DATA_NAME))
                .setMessage(getArguments().getString(EXTRA_DATA_DESC));

        CategoryPresenter presenter = new CategoryPresenter(this);
        presenter.getMealByCategory(getArguments().getString(EXTRA_DATA_NAME));
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMeals(List<Meals.Meal> meals) {
        RecyclerViewMealByCategory adapter = new RecyclerViewMealByCategory(getActivity(), meals);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new RecyclerViewMealByCategory.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String nameMeal = meals.get(position).getStrMeal();
                startActivity(new Intent(getActivity(), DetailsActivity.class).putExtra(EXTRA_NAME_MEAL,nameMeal));
            }
        });

    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Error ", message);
    }

    @OnClick(R.id.cardCategory)
    public void onClick() {
        descDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        descDialog.show();
    }
}
