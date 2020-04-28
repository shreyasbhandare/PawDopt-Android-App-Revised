package com.sbhandare.pawdopt.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;
import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.R;
import com.sbhandare.pawdopt.Adapter.ViewPagerAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener,
                                                               FavoritesFragment.OnFragmentInteractionListener,
                                                               AccountFragment.OnFragmentInteractionListener,
                                                               FilterFragment.OnFragmentInteractionListener,
                                                               CategoryFilterFragment.OnFragmentInteractionListener,
                                                               DistanceFilterFragment.OnFragmentInteractionListener,
                                                               PetDetailsFragment.OnFragmentInteractionListener {

    BubbleTabBar bubbleTabBar;
    private ViewPager viewPager;

    SearchFragmentRoot searchFragmentRoot;
    FavoritesFragmentRoot favoritesFragmentRoot;
    AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bubbleTabBar = findViewById(R.id.bubbleTabBar);
        viewPager = findViewById(R.id.viewPager);

        setupViewPager(viewPager);
        bubbleTabBar.setupBubbleTabBar(viewPager);
        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                switch (i){
                    case R.id.search:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.favorite:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.account:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float MIN_SCALE = 0.65f;
                final float MIN_ALPHA = 0.3f;
                // do transformation here
                if (position <-1){  // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0);

                }
                else if (position <=1){ // [-1,1]

                    page.setScaleX(Math.max(MIN_SCALE,1-Math.abs(position)));
                    page.setScaleY(Math.max(MIN_SCALE,1-Math.abs(position)));
                    page.setAlpha(Math.max(MIN_ALPHA,1-Math.abs(position)));

                }
                else {  // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0);

                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        searchFragmentRoot = new SearchFragmentRoot();
        favoritesFragmentRoot = new FavoritesFragmentRoot();
        accountFragment = new AccountFragment();
        adapter.addFragment(searchFragmentRoot);
        adapter.addFragment(favoritesFragmentRoot);
        adapter.addFragment(accountFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
    }

    @Override
    public void onDistanceSelected(String distance) {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("searchFragment");
        Objects.requireNonNull(searchFragment).onDistanceSelected(distance);
    }

    @Override
    public void onCategorySelected(String category) {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("searchFragment");
        Objects.requireNonNull(searchFragment).onCategorySelected(category);
    }

    @Override
    public void onFavoriteAdded(Pet pet) {
        FavoritesFragment favoritesFragment = (FavoritesFragment) getSupportFragmentManager().findFragmentByTag("favoritesFragment");
        Objects.requireNonNull(favoritesFragment).onFavoriteAdded(pet);
    }
}
