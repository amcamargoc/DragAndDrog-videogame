package angelcotes.viewmovil;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import angelcotes.fragments.HelpOne;
import angelcotes.fragments.HelpTwo;

public class Help extends AppCompatActivity {

    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);




        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Establish ViewPage which will be replace by a fragment
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Add each fragment in the adapter. The second attribute is the tab title.
        // For this app it is not necessary, so i sent a empty string
        adapter.addFragment(new HelpOne(), "");
        adapter.addFragment(new HelpTwo(), "");
        viewPager.setAdapter(adapter);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().hide();
    }


    // Change tab
    public void next(View view) {
        viewPager.setCurrentItem(1);
    }

    public void backToMenu(View view) {
            this.finish();
    }


    // Class used for activitytab swipe
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}