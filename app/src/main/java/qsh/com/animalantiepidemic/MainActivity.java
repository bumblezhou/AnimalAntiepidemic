package qsh.com.animalantiepidemic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import me.dm7.barcodescanner.zbar.ZBarScannerView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import qsh.com.animalantiepidemic.adapter.ViewPagerAdapter;
import qsh.com.animalantiepidemic.fragments.AboutFragment;
import qsh.com.animalantiepidemic.fragments.AntiepidemicFragment;
import qsh.com.animalantiepidemic.fragments.ChipsetFragment;
import qsh.com.animalantiepidemic.fragments.EartagFragment;
import qsh.com.animalantiepidemic.fragments.HomeFragment;
import qsh.com.animalantiepidemic.fragments.ZBarScanQrcodeFragment;
import qsh.com.animalantiepidemic.fragments.ZXingScanQrcodeFragment;
import qsh.com.animalantiepidemic.localstate.DataHolder;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, ZBarScannerView.ResultHandler {

    BottomNavigationView bottomNavigationView;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments
    AboutFragment aboutFragment;
    AntiepidemicFragment antiepidemicFragment;
    ChipsetFragment chipFragment;
    EartagFragment eartagFragment;
    HomeFragment homeFragment;
    ZXingScanQrcodeFragment zXingScanQrcodeFragment;
    ZBarScanQrcodeFragment zBarScanQrcodeFragment;

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

    public void switchToScanQrcodeFragment(){
        Handler uiHandler = new Handler();
        uiHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                if(DataHolder.TO_SCAN_CONTENT_INTO_CONTROL_INDEX == 0){
                    setTitle(R.string.action_scan_start_eartag);
                } else {
                    setTitle(R.string.action_scan_end_eartag);
                }
                viewPager.setCurrentItem(5);
            }
        });
    }

    public void switchToEartagFragment(){
        Handler uiHandler = new Handler();
        uiHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                setTitle(R.string.title_eartag);
                viewPager.setCurrentItem(1);
            }
        });
    }

    private ViewPager.OnPageChangeListener mOnViewPageChangeListener
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //Log.d("fragment", "页面滑动了,滑动位置：" + position);
            if(position >= 5 && !DataHolder.IS_OPEN_SCAN_CAMERA){
                backToHomepage();
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
            //如果选中的页面索引大于4,则直接跳转到页面1.
            Log.d("page", "onPageSelected: " + position);
            if(position > 4){
                position = 1;
            }
            bottomNavigationView.getMenu().getItem(position).setChecked(true);
            prevMenuItem = bottomNavigationView.getMenu().getItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        if(aboutFragment == null){
            aboutFragment = new AboutFragment();
        }

        if(antiepidemicFragment == null){
            antiepidemicFragment = new AntiepidemicFragment();
        }

        if(chipFragment == null){
            chipFragment = new ChipsetFragment();
        }

        if(eartagFragment == null){
            eartagFragment = new EartagFragment();
        }

        if(homeFragment == null){
            homeFragment = new HomeFragment();
        }

        //if(zXingScanQrcodeFragment == null){
        //    zXingScanQrcodeFragment = new ZXingScanQrcodeFragment();
        //}
        if(zBarScanQrcodeFragment == null){
            zBarScanQrcodeFragment = new ZBarScanQrcodeFragment();
        }

        adapter.addFragment(homeFragment);
        adapter.addFragment(eartagFragment);
        adapter.addFragment(chipFragment);
        adapter.addFragment(antiepidemicFragment);
        adapter.addFragment(aboutFragment);
        //adapter.addFragment(zXingScanQrcodeFragment);
        adapter.addFragment(zBarScanQrcodeFragment);

        viewPager.setAdapter(adapter);
        Log.d("fragment", "total fragment size:" + viewPager.getScrollBarSize());
    }

    private void backToHomepage(){
        Handler uiHandler = new Handler();
        uiHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                setTitle(R.string.title_home);
                viewPager.setCurrentItem(0);
            }
        });
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
                backToHomepage();
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
        if (id == R.id.action_add_farmer) {
            //Toast.makeText(getApplicationContext(), "你点击了添加畜主！", Toast.LENGTH_SHORT).show();
            backToHomepage();
            this.homeFragment.openAddFarmerDialog();
            return true;
        }

        if (id == R.id.action_edit_farmer) {
            //Toast.makeText(getApplicationContext(), "你点击了编辑畜主！", Toast.LENGTH_SHORT).show();
            backToHomepage();
            this.homeFragment.openEidtFarmerDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleResult(com.google.zxing.Result result) {
        if(result == null){
            return;
        }
        DataHolder.IS_OPEN_SCAN_CAMERA = false;
        Log.d("qrscan", "scan result:" + result.getText());
        //Toast.makeText(this, "Content:" + result.getText() + " Format:" + result.getBarcodeFormat().toString(), Toast.LENGTH_LONG).show();
        DataHolder.setScanedResult(result.getText());
        switchToEartagFragment();
    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {
        if(result == null){
            return;
        }
        DataHolder.IS_OPEN_SCAN_CAMERA = false;
        Log.d("qrscan", "scan result:" + result.getContents());
        //Toast.makeText(this, "Content:" + result.getText() + " Format:" + result.getBarcodeFormat().toString(), Toast.LENGTH_LONG).show();
        DataHolder.setScanedResult(result.getContents());
        switchToEartagFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == Static.BARCODE_SCAN_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
                String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
                DataHolder.IS_OPEN_SCAN_CAMERA = false;
                Log.d("qrscan", "scan result:" + result);
                //Toast.makeText(this, "Content:" + result.getText() + " Format:" + result.getBarcodeFormat().toString(), Toast.LENGTH_LONG).show();
                DataHolder.setScanedResult(result);
            }
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            // your code
//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(intent);
//            setContentView(R.layout.activity_login);
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
}
