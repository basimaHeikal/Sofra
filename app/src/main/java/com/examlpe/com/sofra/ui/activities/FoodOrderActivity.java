package com.examlpe.com.sofra.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.examlpe.com.sofra.ui.fragments.general.AboutAppFragment;
import com.examlpe.com.sofra.ui.fragments.general.AlarmsFragment;
import com.examlpe.com.sofra.ui.fragments.client.ClientSettingsFragment;
import com.examlpe.com.sofra.ui.fragments.client.ClientSignFragment;
import com.examlpe.com.sofra.ui.fragments.general.ContactUsFragment;
import com.examlpe.com.sofra.ui.fragments.general.LoginFragment;
import com.examlpe.com.sofra.ui.fragments.client.CartFragment;
import com.examlpe.com.sofra.ui.fragments.client.OrderDetailsFragment;
import com.examlpe.com.sofra.ui.fragments.client.SandwichFragment;
import com.examlpe.com.sofra.helper.MyClickListener;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerUser;
import com.examlpe.com.sofra.ui.fragments.general.FoodMenuFragment;
import com.examlpe.com.sofra.ui.fragments.client.HomeFragment;
import com.examlpe.com.sofra.ui.fragments.client.MyOrdersFragment;
import com.examlpe.com.sofra.data.model.client.ClientUser;
import com.examlpe.com.sofra.ui.fragments.general.NewOffersFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.R;
import com.examlpe.com.sofra.ui.fragments.general.RulesFragment;
import com.examlpe.com.sofra.ui.fragments.general.ShareAppFragment;

public class FoodOrderActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView img_settings;
    private Toolbar toolbar;
    private TextView TextViewUserName;

    // index to identify current nav menu item
    public static int navItemIndex;

    // tags used to attach the fragments
    public static final String TAG_HOME = "home";
    public static final String TAG_MY_ORDERS = "طلباتي";
    public static final String TAG_ALARMS = "التنبيهات";
    public static final String TAG_NEW_OFFERS = "جديد العروض";
    public static final String TAG_ABOUT_APP = "عن التطبيق";
    public static final String TAG_RULES = "الشروط و الأحكام";
    public static final String TAG_SHARE_APP = "شارك التطبيق";
    public static final String TAG_CONTACT_US = "تواصل معنا";
    public static final String TAG_FOOD_MENU = "قائمة الطعام";
    public static final String TAG_SANDWICH = "ساندوتش";
    public static final String TAG_CART = "سلة التسوق";
    public static final String TAG_LOGIN = "تسجيل الدخول";
    public static final String TAG_CLIENT_SIGN = "حساب جديد";
    public static final String TAG_ORDER_DETAILS = "تفاصيل الطلب";
    public static final String TAG_CLIENT_SETTINGS = "اعدادات";

    public static String CURRENT_TAG = TAG_HOME;

    public static int item_id;
    public static String item_name;
    public static String item_description;
    public static String item_price;
    public static String item_preparing_time;
    public static String item_photo;


    HomeFragment homeFragment;
    MyOrdersFragment myOrdersFragment;
    AlarmsFragment alarmsFragment;
    NewOffersFragment newOffersFragment;
    AboutAppFragment aboutAppFragment;
    RulesFragment rulesFragment;
    ShareAppFragment shareApp;
    ContactUsFragment contactUs;
    FoodMenuFragment foodMenuFragment;
    SandwichFragment sandwichFragment;
    CartFragment cartFragment;
    LoginFragment loginFragment;
    ClientSignFragment clientSignFragment;
    OrderDetailsFragment orderDetailsFragment;
    ClientSettingsFragment clientSettingsFragment;


    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        setContentView(R.layout.activity_food_order);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mHandler = new Handler();

        InitElements();
        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            setCurrentTag(TAG_HOME, 0, 1);
        }
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    public void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // update the main content by replacing fragments
        Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commit();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // food order ==> restaurants menu
                homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // my orders fragment
                myOrdersFragment = new MyOrdersFragment();
                return myOrdersFragment;

            case 2:
                // Alarms
                SharedPrefManager.getInstance(getApplicationContext()).setKeyLogin("ALARMS");
                alarmsFragment = new AlarmsFragment();
                return alarmsFragment;
            case 3:
                // new offers Fragment
                newOffersFragment = new NewOffersFragment();
                return newOffersFragment;

            case 4:
                // about app Fragment
                aboutAppFragment = new AboutAppFragment();
                return aboutAppFragment;

            case 5:
                // Rules Fragment
                rulesFragment = new RulesFragment();
                return rulesFragment;

            case 6:
                // share app Fragment
                shareApp = new ShareAppFragment();
                return shareApp;

            case 7:
                // contact us Fragment
                contactUs = new ContactUsFragment();
                return contactUs;

            case 8:
                // food menu Fragment
                foodMenuFragment = new FoodMenuFragment();
                return foodMenuFragment;
            case 9:
                SharedPrefManager.getInstance(FoodOrderActivity.this).setKeyTotal("SAN");

                // food menu Fragment
                sandwichFragment = new SandwichFragment(item_id, item_name, item_description, item_price, item_preparing_time, item_photo);
                return sandwichFragment;

            case 10:

                cartFragment = new CartFragment();
                return cartFragment;

            case 11:
                // food menu Fragment
                loginFragment = new LoginFragment();
                return loginFragment;

            case 12:
                // food menu Fragment
                clientSignFragment = new ClientSignFragment();
                return clientSignFragment;
            case 13:
                // food menu Fragment
                orderDetailsFragment = new OrderDetailsFragment();
                return orderDetailsFragment;
            case 14:
                // client settings Fragment
                clientSettingsFragment = new ClientSettingsFragment();
                return clientSettingsFragment;
            default:
                return new HomeFragment();
        }
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        setCurrentTag(TAG_HOME, 0, 0);
                        break;
                    case R.id.nav_my_orders:
                        setCurrentTag(TAG_MY_ORDERS, 1, 0);
                        break;
                    case R.id.nav_alarms:
                        setCurrentTag(TAG_ALARMS, 2, 0);
                        break;
                    case R.id.nav_offers:
                        setCurrentTag(TAG_NEW_OFFERS, 3, 0);
                        break;
                    case R.id.nav_about_app:
                        setCurrentTag(TAG_ABOUT_APP, 4, 0);
                        break;
                    case R.id.nav_rules:
                        setCurrentTag(TAG_RULES, 5, 0);
                        break;
                    case R.id.nav_share_app:
                        setCurrentTag(TAG_SHARE_APP, 6, 0);
                        break;
                    case R.id.nav_contact_us:
                        setCurrentTag(TAG_CONTACT_US, 7, 0);
                        break;
                    case R.id.nav_food_menu:
                        setCurrentTag(TAG_FOOD_MENU, 8, 0);
                        break;
                    case R.id.nav_sandwich:
                        setCurrentTag(TAG_SANDWICH, 9, 0);
                        break;
                    case R.id.nav_cart:
                        setCurrentTag(TAG_CART, 10, 0);
                        break;
                    case R.id.nav_login:
                        setCurrentTag(TAG_LOGIN, 11, 0);
                        break;
                    case R.id.nav_sign:
                        setCurrentTag(TAG_CLIENT_SIGN, 12, 0);
                        break;
                    case R.id.nav_order_details:
                        setCurrentTag(TAG_ORDER_DETAILS, 13, 0);
                        break;
                    case R.id.nav_client_settings:
                        setCurrentTag(TAG_CLIENT_SETTINGS, 14, 0);
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.mipmap.nav);
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex == 1 || navItemIndex == 2 || navItemIndex == 3 || navItemIndex == 4 || navItemIndex == 5 || navItemIndex == 6 || navItemIndex == 7 || navItemIndex == 8) {
                setCurrentTag(TAG_HOME, 0, 1);
                return;
            }
            if (navItemIndex == 9) {
                setCurrentTag(TAG_FOOD_MENU, 8, 1);
                return;
            }
            if (navItemIndex == 10) {
                String add = SharedPrefManager.getInstance(FoodOrderActivity.this).getKeyTotal();

                if (add.equals("CART") ){
                    setCurrentTag(TAG_HOME, 0, 1);

                }else if (add.equals("SAN") ){
                    setCurrentTag(TAG_SANDWICH, 9, 1);
                }

                return;
            }
            if (navItemIndex == 11) {
                setCurrentTag(TAG_HOME, 0, 1);
                return;
            }
            if (navItemIndex == 12) {
                setCurrentTag(TAG_LOGIN, 11, 1);
                return;
            }
            if (navItemIndex == 14) {
                setCurrentTag(TAG_HOME, 0, 1);
                return;
            }
            if (navItemIndex == 13) {
                setCurrentTag(TAG_CART, 10, 1);
                return;
            }
        }
        startActivity(new Intent(FoodOrderActivity.this, SplashActivity.class));

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (navItemIndex == 8) {
            getMenuInflater().inflate(R.menu.order, menu);
        } else if (navItemIndex == 11 || navItemIndex == 12) {
            getMenuInflater().inflate(R.menu.user_auth, menu);
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.menu_cart) {
            SharedPrefManager.getInstance(FoodOrderActivity.this).setKeyTotal("CART");
            setCurrentTag(TAG_CART, 10, 1);
        }


        if (id == R.id.menu_back) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    //method to initial our views
    private void InitElements() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        TextViewUserName = (TextView) navHeader.findViewById(R.id.TextViewUserName);
        img_settings = (ImageView) navHeader.findViewById(R.id.img_settings);
        img_settings.setOnClickListener(new MyClickListener(FoodOrderActivity.this, "order_settings", "image"));


        displayName();


/*
        String photo = String.valueOf(user.getImage());

        //loading the user profile photo
        Glide.with(MainActivity.this)
                .load(photo)
                .into(imgProfile);*/

    }

    public void displayName() {
        //getting the orderSell key
        String orderSell = SharedPrefManager.getInstance(FoodOrderActivity.this).getKeyOrderSell();

        //getting the current user
        ClientUser user = SharedPrefManagerUser.getInstance(FoodOrderActivity.this).getUser();

        if (orderSell.equals("ORDER")) {
            if (!SharedPrefManagerUser.getInstance(this).isLoggedIn()) {
                TextViewUserName.setText("تفضل بتسجيل الدخول");
            } else {
                TextViewUserName.setText(user.getName());
            }
        }
    }

    public void setCurrentTag(String currentTag, int navIndex, int type) {
        if (type == 0) {
            navItemIndex = navIndex;
            CURRENT_TAG = currentTag;
        } else if (type == 1) {
            navItemIndex = navIndex;
            CURRENT_TAG = currentTag;
            loadHomeFragment();
        }
    }
}
