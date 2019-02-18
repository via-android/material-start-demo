package com.morepranit.materialstartdemo1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Adapter;

import com.google.android.material.tabs.TabLayout;
import com.morepranit.materialstartdemo1.fragments.MGridFragment;
import com.morepranit.materialstartdemo1.fragments.MListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabs = findViewById(R.id.tabs);
        /*tabs.addTab(tabs.newTab().setText("List"));
        tabs.addTab(tabs.newTab().setText("Grid"));
        tabs.addTab(tabs.newTab().setText("Misc"));*/

        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        adapter.addFragment(new MListFragment(), "List");
        adapter.addFragment(new MGridFragment(), "Grid");
        viewPager.setAdapter(adapter);
    }

    class PageAdapter extends FragmentPagerAdapter {
        List<String> titleList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        public PageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            titleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
