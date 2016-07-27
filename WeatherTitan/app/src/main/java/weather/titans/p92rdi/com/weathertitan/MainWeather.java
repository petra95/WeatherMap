package weather.titans.p92rdi.com.weathertitan;

import android.graphics.Bitmap;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainWeather extends AppCompatActivity {

    private Menu menu;
    private final String SAVECITYNAMEKEY = "CityName";
    private final String SAVESLOT1CITYID = "Slot1CityID";
    private final String SAVESLOT2CITYID = "Slot2CityID";
    private final String SAVESLOT3CITYID = "Slot3CityID";
    TableLayout mDataTableLayout;
    
    private SharedPreferences mSlot1CityNameSharedPreference;
    private SharedPreferences mSlot2CityNameSharedPreference;
    private SharedPreferences mSlot3CityNameSharedPreference;
    private String mActualCityNameString = "";
    private String mSlot1TitleString = "";
    private String mSlot2TitleString = "";
    private String mSlot3TitleString = "";

    HttpClient mClient;
    Weather mResultWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        mClient = new HttpClient();

        mDataTableLayout = (TableLayout) findViewById(R.id.dataTableLayout);
        mSlot1CityNameSharedPreference = getSharedPreferences(SAVESLOT1CITYID, 0);
        mSlot2CityNameSharedPreference = getSharedPreferences(SAVESLOT2CITYID,0 );
        mSlot3CityNameSharedPreference = getSharedPreferences(SAVESLOT3CITYID,0 );

        startActivity();
    }

    private void startActivity() {
        mActualCityNameString = mSlot1CityNameSharedPreference.getString(SAVESLOT1CITYID, "");
        getWeather(mActualCityNameString);
        mSlot1TitleString = mSlot1CityNameSharedPreference.getString(SAVESLOT1CITYID, "");
        mSlot2TitleString = mSlot2CityNameSharedPreference.getString(SAVESLOT2CITYID, "");
        mSlot3TitleString = mSlot3CityNameSharedPreference.getString(SAVESLOT3CITYID, "");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(SAVECITYNAMEKEY, mActualCityNameString);
        savedInstanceState.putString(SAVESLOT1CITYID, mSlot1TitleString);
        savedInstanceState.putString(SAVESLOT2CITYID, mSlot2TitleString);
        savedInstanceState.putString(SAVESLOT3CITYID, mSlot3TitleString);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        mSlot1TitleString = savedInstanceState.getString(SAVESLOT1CITYID);
        mSlot2TitleString = savedInstanceState.getString(SAVESLOT2CITYID);
        mSlot3TitleString = savedInstanceState.getString(SAVESLOT3CITYID);
        getWeather(savedInstanceState.getString(SAVECITYNAMEKEY));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        MenuItem slot1MenuItem = menu.findItem(R.id.menu_load_slot1);
        if(!mSlot1TitleString.equals("")){
            slot1MenuItem.setTitle(mSlot1TitleString);
        }

        MenuItem slot2MenuItem = menu.findItem(R.id.menu_load_slot2);
        if(!mSlot2TitleString.equals("")){
            slot2MenuItem.setTitle(mSlot2TitleString);
        }

        MenuItem slot3MenuItem = menu.findItem(R.id.menu_load_slot3);
        if(!mSlot3TitleString.equals("")){
            slot3MenuItem.setTitle(mSlot3TitleString);
        }


        searchView.setOnQueryTextListener (
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        getWeather(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                }
        );
        return true;
    }

    private void assignWeatherValues(Weather weatherData) {
        if (weatherData.getmIcon() != null) {
            ImageView mImageView = (ImageView) findViewById(R.id.weatherImageView);
            mImageView.setImageBitmap(weatherData.getmIcon());
        }
        setTextViewText(R.id.cityTextView, weatherData.getmCity().concat(" " + weatherData.getmCountry()));
        setTextViewText(R.id.degreeTextView, String.valueOf(weatherData.getmTemperature() - 273) + " C°");
        setTextViewText(R.id.descriptionTextView, weatherData.getmDescription());
        setTextViewText(R.id.minDegTextView, String.valueOf(weatherData.getmTempMin() - 273) + " C°");
        setTextViewText(R.id.maxDegTextView, String.valueOf(weatherData.getmTempMax() - 273) + " C°");
        setTextViewText(R.id.windTextView, String.valueOf(weatherData.getmWind()) + " m/s");
        setTextViewText(R.id.humidityTextView, String.valueOf(weatherData.getmHumidity()) + "%");
    }

    private void setTextViewText(int id, String value) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(value);
    }

    private void saveCityName(SharedPreferences actualSharedPreferenc,String query, String keyString){
        SharedPreferences.Editor editor1 = actualSharedPreferenc.edit();
        editor1.putString(keyString, query);
        editor1.commit();
    }

    private void getWeather(String query) {
        final String mFinalQuery = query;
        Thread mNetworkThread = new Thread(new Runnable() {
            public void run() {
                JsonParser mWeatherParser = new JsonParser(mClient.getWeatherData(mFinalQuery));
                mResultWeather = mWeatherParser.processWeatherFromJson();
                Bitmap test = mClient.getImage(mResultWeather.getmIconCode());
                mResultWeather.setmIcon(test);
            }
        });
        mNetworkThread.start();
        try {
            mNetworkThread.join();
        } catch (InterruptedException e) {
        }
        mActualCityNameString = mResultWeather.getmCity();
        assignWeatherValues(mResultWeather);
        mDataTableLayout.setVisibility(View.VISIBLE);
    }

    public void loadCityWeather(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menu_load_slot1:
                mActualCityNameString = mSlot1CityNameSharedPreference.getString(SAVESLOT1CITYID, "");
                getWeather(mActualCityNameString);
                menuItem.setTitle(mActualCityNameString);
                break;
            case R.id.menu_load_slot2:
                mActualCityNameString = mSlot2CityNameSharedPreference.getString(SAVESLOT2CITYID, "");
                getWeather(mActualCityNameString);
                menuItem.setTitle(mActualCityNameString);
                break;
            case R.id.menu_load_slot3:
                mActualCityNameString = mSlot3CityNameSharedPreference.getString(SAVESLOT3CITYID, "");
                getWeather(mActualCityNameString);
                menuItem.setTitle(mActualCityNameString);
                break;
        }

    }

    public void saveCityWeather(MenuItem menuItem){
        if(!mActualCityNameString.equals("")) {

            switch(menuItem.getItemId()){
                case R.id.menu_save_slot1:
                    saveCityName(mSlot1CityNameSharedPreference, mActualCityNameString, SAVESLOT1CITYID);
                    menu.findItem(R.id.menu_load_slot1).setTitle(mActualCityNameString);
                    mSlot1TitleString = mActualCityNameString;
                    break;
                case R.id.menu_save_slot2:
                    saveCityName(mSlot2CityNameSharedPreference, mActualCityNameString, SAVESLOT2CITYID);
                    menu.findItem(R.id.menu_load_slot2).setTitle(mActualCityNameString);
                    mSlot2TitleString = mActualCityNameString;
                    break;
                case R.id.menu_save_slot3:
                    saveCityName(mSlot3CityNameSharedPreference, mActualCityNameString, SAVESLOT3CITYID);
                    menu.findItem(R.id.menu_load_slot3).setTitle(mActualCityNameString);
                    mSlot3TitleString = mActualCityNameString;
                    break;
            }
        }

    }


}


