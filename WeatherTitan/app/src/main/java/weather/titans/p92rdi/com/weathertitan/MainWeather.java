package weather.titans.p92rdi.com.weathertitan;

import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.widget.TextView;


public class MainWeather extends AppCompatActivity {

    private final String SAVECITYNAMEKEY = "CityName";

    private SharedPreferences mActualCityNameSharedPreferences;
    private String mActualCityNameString = "";
    private int mActualCityIDInt;

    HttpClient mClient;
    Weather mResultWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        mClient = new HttpClient();
        mActualCityNameSharedPreferences = getSharedPreferences(SAVECITYNAMEKEY, 0);

    }

    private void loadWeatherData() {
        String mCityNameString = mActualCityNameSharedPreferences.getString(SAVECITYNAMEKEY, "");
        if(!mCityNameString.equals("")) {
            getWeather(mCityNameString);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(SAVECITYNAMEKEY, mActualCityNameString);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        getWeather(savedInstanceState.getString(SAVECITYNAMEKEY));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        MenuItem menuItem1 = menu.findItem(R.id.menu_load_slot1);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        getWeather(query);
                        saveCityName(query);
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
        setTextViewText(R.id.cityTextView, weatherData.getmCity().concat(", " + weatherData.getmCountry()));
        setTextViewText(R.id.degreeTextView, String.valueOf(weatherData.getmTemperature()));
        setTextViewText(R.id.descriptionTextView, weatherData.getmDescription());
        setTextViewText(R.id.minDegTextView, String.valueOf(weatherData.getmTempMin()));
        setTextViewText(R.id.maxDegTextView, String.valueOf(weatherData.getmTempMax()));
        setTextViewText(R.id.windTextView, String.valueOf(weatherData.getmWind()));
        setTextViewText(R.id.rainTextView, String.valueOf(weatherData.getmRain()));
        setTextViewText(R.id.humidityTextView, String.valueOf(weatherData.getmHumidity()));
    }

    
    private void setTextViewText(int id, String value){
        TextView textView = (TextView) findViewById(id);
        textView.setText(value);
    }

    private void saveCityName(String query){
        SharedPreferences.Editor editor1 = mActualCityNameSharedPreferences.edit();
        editor1.putString(SAVECITYNAMEKEY, query);
        editor1.commit();
    }

    private void getWeather(String query) {
        mClient.getWeatherData(query);
        JsonParser mWeatherParser = new JsonParser(mClient.getWeatherData(query));
        mResultWeather = mWeatherParser.processWeatherFromJson();
        mActualCityNameString = mResultWeather.getmCity();
        assignWeatherValues(mResultWeather);
    }


}


