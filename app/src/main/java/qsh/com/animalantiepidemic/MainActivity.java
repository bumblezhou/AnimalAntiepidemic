package qsh.com.animalantiepidemic;

import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import qsh.com.animalantiepidemic.adapter.FarmerAdapter;
import qsh.com.animalantiepidemic.databinding.FragmentAboutBinding;
import qsh.com.animalantiepidemic.fragments.AboutFragment;
import qsh.com.animalantiepidemic.fragments.AntiepidemicFragment;
import qsh.com.animalantiepidemic.fragments.ChipsetFragment;
import qsh.com.animalantiepidemic.fragments.EartagFragment;
import qsh.com.animalantiepidemic.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments
    AboutFragment aboutFragment;
    AntiepidemicFragment antiepidemicFragment;
    ChipsetFragment chipFragment;
    EartagFragment eartagFragment;
    HomeFragment homeFragment;

    MenuItem prevMenuItem;

    private FarmerAdapter mAdapter;
    //private FragmentHomeBinding mBinding;
    private Animator mAnimator;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    setTitle(R.string.title_home);
                    return true;
                case R.id.navigation_eartag:
                    setTitle(R.string.title_eartag);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_chipset:
                    setTitle(R.string.title_chipset);
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_antiepidemic:
                    setTitle(R.string.title_antiepidemic);
                    viewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_about:
                    setTitle(R.string.title_about);
                    viewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }

    };

    private ViewPager.OnPageChangeListener mOnViewPageChangeListener
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            }
            else
            {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
            Log.d("page", "onPageSelected: "+position);
            bottomNavigationView.getMenu().getItem(position).setChecked(true);
            prevMenuItem = bottomNavigationView.getMenu().getItem(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        aboutFragment = new AboutFragment();
        antiepidemicFragment = new AntiepidemicFragment();
        chipFragment = new ChipsetFragment();
        eartagFragment = new EartagFragment();
        homeFragment = new HomeFragment();

        adapter.addFragment(homeFragment);
        adapter.addFragment(eartagFragment);
        adapter.addFragment(chipFragment);
        adapter.addFragment(antiepidemicFragment);
        adapter.addFragment(aboutFragment);

        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(mOnViewPageChangeListener);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setupViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
