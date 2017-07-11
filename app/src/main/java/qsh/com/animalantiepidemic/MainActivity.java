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
            openAddFarmerDialog();
            return true;
        }

        if (id == R.id.action_edit_user) {
            Toast.makeText(getApplicationContext(), "你点击了编辑畜主！", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openAddFarmerDialog(){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.create_farmer_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("提交",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                //result.setText(userInput.getText());
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("添加畜主");
        alertDialog.setIcon(R.mipmap.ic_launcher_round);

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(colorPrimaryDark));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(colorPrimaryDark));
            }
        });

        Spinner spinner = (Spinner) promptsView.findViewById(R.id.txt_farmer_breed_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.breed_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(spinner == null){
            Log.d("system", "未能找到对话框中的spinner widget.");
        }
        if(adapter == null){
            Log.d("system", "未能成功创建spinner适配器.");
        }
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // show it
        alertDialog.show();
    }
}
