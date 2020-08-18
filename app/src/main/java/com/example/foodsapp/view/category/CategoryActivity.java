package com.example.foodsapp.view.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.example.foodsapp.R;
import com.example.foodsapp.adapter.ViewPagerCategoryAdapter;
import com.example.foodsapp.model.Categories;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.foodsapp.view.home.HomeActivity.EXTRA_CATEGORY;
import static com.example.foodsapp.view.home.HomeActivity.EXTRA_POSITION;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);
        initActionBar();
        fillViewPager();

    }

    private void fillViewPager() {
        Intent intent = getIntent();
        List<Categories.Category> category = (List<Categories.Category>) intent.getSerializableExtra(EXTRA_CATEGORY);
        int postion = intent.getIntExtra(EXTRA_POSITION,0);
        ViewPagerCategoryAdapter adapter = new ViewPagerCategoryAdapter(getSupportFragmentManager(),category);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(postion,true);
        adapter.notifyDataSetChanged();
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}