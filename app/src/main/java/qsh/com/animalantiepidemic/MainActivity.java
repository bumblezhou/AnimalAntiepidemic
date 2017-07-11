package qsh.com.animalantiepidemic;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import qsh.com.animalantiepidemic.databinding.FragmentAboutBinding;
import qsh.com.animalantiepidemic.databinding.FragmentChipsetBinding;
import qsh.com.animalantiepidemic.databinding.FragmentEartagBinding;
import qsh.com.animalantiepidemic.fragments.AboutFragment;
import qsh.com.animalantiepidemic.fragments.AntiepidemicFragment;
import qsh.com.animalantiepidemic.fragments.ChipsetFragment;
import qsh.com.animalantiepidemic.fragments.EartagFragment;
import qsh.com.animalantiepidemic.fragments.HomeFragment;

import static qsh.com.animalantiepidemic.R.color.colorPrimaryDark;

public class MainActivity extends AppCompatActivity {

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
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_antiepidemic:
                    setTitle(R.string.title_antiepidemic);
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_about:
                    setTitle(R.string.title_about);
                    viewPager.setCurrentItem(3);
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
            } else {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
            Log.d("page", "onPageSelected: " + position);
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
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setupViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < bottomNavigationView.getMenu().size(); ++i) {
                    bottomNavigationView.getMenu().getItem(i).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                setTitle(R.string.title_home);
                viewPager.setCurrentItem(0);
            }
        });
        searchView.setOnQueryTextListener(homeFragment);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_user) {
            //Toast.makeText(getApplicationContext(), "你点击了添加畜主！", Toast.LENGTH_SHORT).show();
            this.homeFragment.openAddFarmerDialog();
            return true;
        }

        if (id == R.id.action_edit_user) {
            Toast.makeText(getApplicationContext(), "你点击了编辑畜主！", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
