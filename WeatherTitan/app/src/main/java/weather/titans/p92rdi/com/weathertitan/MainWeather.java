package weather.titans.p92rdi.com.weathertitan;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.widget.TextView;


public class MainWeather extends AppCompatActivity {

    HttpClient mClient;
    Weather mResultWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        TextView mResultTextView;
        
        mClient = new HttpClient();

        /*SharedPreferences sharedPreferences = getSharedPreferences(MTEXTVALUEKEYSTRING, 0);
        final EditText editText = (EditText) findViewById(R.id.searchEditText);
        editText.setText(sharedPreferences.getString("valueText", ""));*/

    }
   /* @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences(MTEXTVALUEKEYSTRING,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        EditText editText = (EditText) findViewById(R.id.searchEditText);
        editor.putString("valueText", editText.getText().toString());

        editor.commit();
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mClient.getWeatherData(query);
                        JsonParser mWeatherParser = new JsonParser(mClient.getWeatherData(query));
                        mResultWeather = mWeatherParser.processWeatherFromJson();
                        assignWeatherValues(mResultWeather);
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

}


