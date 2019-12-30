package com.sbhandare.pawdopt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;

import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener,
                                                               FavoritesFragment.OnFragmentInteractionListener,
                                                               AccountFragment.OnFragmentInteractionListener {

    BubbleTabBar bubbleTabBar;
    private ViewPager viewPager;

    SearchFragment searchFragment;
    FavoritesFragment favoritesFragment;
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


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        searchFragment = new SearchFragment();
        favoritesFragment = new FavoritesFragment();
        accountFragment = new AccountFragment();
        adapter.addFragment(searchFragment);
        adapter.addFragment(favoritesFragment);
        adapter.addFragment(accountFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
    }
}
