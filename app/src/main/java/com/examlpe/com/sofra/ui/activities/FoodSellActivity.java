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
import com.examlpe.com.sofra.ui.fragments.general.ContactUsFragment;
import com.examlpe.com.sofra.ui.fragments.general.LoginFragment;
import com.examlpe.com.sofra.ui.fragments.restaurant.OwnerSignFragment;
import com.examlpe.com.sofra.ui.fragments.general.AlarmsFragment;
import com.examlpe.com.sofra.R;
import com.examlpe.com.sofra.ui.fragments.restaurant.AddProductOfferFragment;
import com.examlpe.com.sofra.ui.fragments.restaurant.OwnerProductsFragment;
import com.examlpe.com.sofra.helper.MyClickListener;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManagerOwner;
import com.examlpe.com.sofra.ui.fragments.general.FoodMenuFragment;
import com.examlpe.com.sofra.data.local.sharedPreference.SharedPrefManager;
import com.examlpe.com.sofra.data.model.restaurant.RestaurantUser;
import com.examlpe.com.sofra.ui.fragments.general.NewOffersFragment;
import com.examlpe.com.sofra.ui.fragments.restaurant.OrdersFragment;
import com.examlpe.com.sofra.ui.fragments.general.RulesFragment;
import com.examlpe.com.sofra.ui.fragments.general.ShareAppFragment;
import com.examlpe.com.sofra.ui.fragments.restaurant.TipsFragment;

public class FoodSellActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView img_settings;
    private Toolbar toolbar;
    private TextView TextViewUserName;

    // index to identify current nav menu item
    public static int navItemIndex ;

    // tags used to attach the fragments
    public static final String TAG_HOME = "home";
    public static final String TAG_MY_PRODUCTS = "منتجاتي";
    public static final String TAG_NEW_ORDERS = "الطلبات المقدمه";
    public static final String TAG_ALARMS = "التنبيهات";
    public static final String TAG_TIPS = "العموله";
    public static final String TAG_MY_OFFERS = "عروضي";
    public static final String TAG_ABOUT_APP = "عن التطبيق";
    public static final String TAG_RULES = "الشروط و الأحكام";
    public static final String TAG_SHARE_APP = "شارك التطبيق";
    public static final String TAG_CONTACT_US = "تواصل معنا";
    public static final String TAG_FOOD_MENU = "قائمة الطعام";
    public static final String TAG_ADD_PRODUCT = "اضف منتج";
    public static final String TAG_LOGIN = "تسجيل الدخول";
    public static final String TAG_SIGN = "انشاء حساب جديد";


    public static String CURRENT_TAG = TAG_HOME;


    FoodMenuFragment homeFragment ;
    OwnerProductsFragment myProductsFragment;
    OrdersFragment ordersFragment;
    TipsFragment tipsFragment ;
    NewOffersFragment myOffersFragment ;
    AboutAppFragment aboutAppFragment;
    RulesFragment rulesFragment ;
    ShareAppFragment shareApp ;
    ContactUsFragment contactUs ;
    FoodMenuFragment foodMenuFragment;
    AddProductOfferFragment addProductFragment ;
    LoginFragment loginFragment ;
    OwnerSignFragment ownerSignFragment ;
    AlarmsFragment alarmsFragment ;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        setContentView(R.layout.activity_food_sell);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mHandler = new Handler();

        InitElements();
        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            setCurrentTag(TAG_HOME,0,1);

        }
    }
    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    public  void loadHomeFragment() {
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
        fragmentTransaction.commitAllowingStateLoss();


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                //food menu
                 homeFragment = new FoodMenuFragment();
                return homeFragment;
            case 1:
                 myProductsFragment = new OwnerProductsFragment();
                return myProductsFragment;

            case 2:
                ordersFragment = new OrdersFragment();
                return ordersFragment;

            case 3:
                // Alarms
                SharedPrefManager.getInstance(getApplicationContext()).setKeyLogin("ALARMS");
                alarmsFragment = new AlarmsFragment();
                return alarmsFragment;
            case 4:
                 tipsFragment = new TipsFragment();
                return tipsFragment;

            case 5:
                 myOffersFragment = new NewOffersFragment();
                return myOffersFragment;
            case 6:
                // about app Fragment
                 aboutAppFragment = new AboutAppFragment();
                return aboutAppFragment;

            case 7:
                // Rules Fragment

                 rulesFragment = new RulesFragment();
                return rulesFragment;

            case 8:
                // share app Fragment
                 shareApp = new ShareAppFragment();
                return shareApp;

            case 9:
                // contact us Fragment
                 contactUs = new ContactUsFragment();
                return contactUs;

            case 10:
                // food menu Fragment
                 foodMenuFragment = new FoodMenuFragment();
                return foodMenuFragment;
            case 11:
                // Add Product Offer Fragment
                 addProductFragment = new AddProductOfferFragment();
                return addProductFragment;
            case 12:
                // Add Product Offer Fragment
                 loginFragment = new LoginFragment();
                return loginFragment;
            case 13:
                // Add Product Offer Fragment
                 ownerSignFragment = new OwnerSignFragment();
                return ownerSignFragment;

              default:
                return new FoodMenuFragment();
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
                        setCurrentTag(TAG_HOME,0,0);
                        break;
                    case R.id.nav_my_products:
                        setCurrentTag(TAG_MY_PRODUCTS,1,0);
                        break;
                    case R.id.nav_new_orders:
                        setCurrentTag(TAG_NEW_ORDERS,2,0);
                        break;
                    case R.id.nav_alarms:
                        setCurrentTag(TAG_ALARMS,3,0);
                        break;
                    case R.id.nav_tips:
                        setCurrentTag(TAG_TIPS,4,0);
                        break;
                    case R.id.nav_my_offers:
                        setCurrentTag(TAG_MY_OFFERS,5,0);
                        break;
                    case R.id.nav_about_app:
                        setCurrentTag(TAG_ABOUT_APP,6,0);
                        break;
                    case R.id.nav_rules:
                        setCurrentTag(TAG_RULES,7,0);
                        break;
                    case R.id.nav_share_app:
                        setCurrentTag(TAG_SHARE_APP,8,0);

                        break;
                    case R.id.nav_contact_us:
                        setCurrentTag(TAG_CONTACT_US,9,0);
                        break;
                    case R.id.nav_food_menu:
                        setCurrentTag(TAG_FOOD_MENU,10,0);
                        break;
                    case R.id.nav_add_product:
                        setCurrentTag(TAG_ADD_PRODUCT,11,0);
                        break;
                    case R.id.nav_login:
                        setCurrentTag(TAG_LOGIN,12,0);
                        break;
                    case R.id.nav_sign:
                        setCurrentTag(TAG_SIGN,13,0);
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
            if (navItemIndex == 1 ||navItemIndex == 2 ||navItemIndex == 3 ||navItemIndex == 4 ||navItemIndex == 5||navItemIndex == 6||navItemIndex == 7||navItemIndex == 8 ||navItemIndex == 9||navItemIndex == 10||navItemIndex == 13   ) {
                setCurrentTag(TAG_HOME,0,1);
                return;
            }else if(navItemIndex == 10){
                String add = SharedPrefManager.getInstance(FoodSellActivity.this).getKeyAdd();

                if (add.equals("PRODUCT") ){
                    setCurrentTag(TAG_MY_PRODUCTS,1,1);
                }else if(add.equals("OFFER")){
                    setCurrentTag(TAG_MY_OFFERS,4,1);
                }
                return;
            }else if(navItemIndex == 11){
                String add = SharedPrefManager.getInstance(FoodSellActivity.this).getKeyAdd();
                if (add.equals("PRODUCT") ){
                    setCurrentTag(TAG_MY_PRODUCTS,1,1);
                }else if(add.equals("OFFER")){
                    setCurrentTag(TAG_MY_OFFERS,4,1);
                }
                return;
            }

        }
        startActivity(new Intent(FoodSellActivity.this, SplashActivity.class));

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_auth, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_back) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    //method to initial our views
    private void   InitElements() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        TextViewUserName = (TextView) navHeader.findViewById(R.id.TextViewUserName);
        img_settings = (ImageView) navHeader.findViewById(R.id.img_settings);
        img_settings.setOnClickListener(new MyClickListener(FoodSellActivity.this,"sell_settings","image"));

        displayName();

    }

    public void setCurrentTag(String currentTag,int navIndex,int type){
        if(type == 0){
            navItemIndex = navIndex;
            CURRENT_TAG = currentTag;
        }else if (type == 1){
            navItemIndex = navIndex;
            CURRENT_TAG = currentTag;
            loadHomeFragment();
        }
    }
   public void displayName(){
        //getting the orderSell key
        String orderSell = SharedPrefManager.getInstance(FoodSellActivity.this).getKeyOrderSell();

        //getting the current user
        RestaurantUser user = SharedPrefManagerOwner.getInstance(FoodSellActivity.this).getUser();

        if (orderSell.equals("SELL") ){
            if (!SharedPrefManagerOwner.getInstance(this).isLoggedIn()) {
                TextViewUserName.setText("تفضل بتسجيل الدخول");
            }else {
                TextViewUserName.setText(user.getName());
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //call super
        super.onActivityResult(requestCode, resultCode, data);
    }

}
