package weather.titans.p92rdi.com.weathertitan;

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

    HttpClient mClient;
    Weather mResultWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        mClient = new HttpClient();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        final TableLayout mDataTableLayout = (TableLayout) findViewById(R.id.dataTableLayout);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        final String mFinalQuery = query;
                        Thread mNetworkThread = new Thread(new Runnable() {
                            public void run() {
                                JsonParser mWeatherParser = new JsonParser(mClient.getWeatherData(mFinalQuery));
                                mResultWeather = mWeatherParser.processWeatherFromJson();
                            }
                        });
                        mNetworkThread.start();
                        try {
                            mNetworkThread.join();
                        } catch (InterruptedException e) {
                        }
                        assignWeatherValues(mResultWeather);
                        mDataTableLayout.setVisibility(View.VISIBLE);

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
        setTextViewText(R.id.cityTextView, weatherData.getmCity().concat(" " + weatherData.getmCountry()));
        setTextViewText(R.id.degreeTextView, String.valueOf(weatherData.getmTemperature() - 273) + " C°");
        setTextViewText(R.id.descriptionTextView, weatherData.getmDescription());
        setTextViewText(R.id.minDegTextView, String.valueOf(weatherData.getmTempMin() - 273) + " C°");
        setTextViewText(R.id.maxDegTextView, String.valueOf(weatherData.getmTempMax() - 273) + " C°");
        setTextViewText(R.id.windTextView, String.valueOf(weatherData.getmWind()) + " m/s");
        setTextViewText(R.id.humidityTextView, String.valueOf(weatherData.getmHumidity())+ "%");
        ImageView mImageView = (ImageView) findViewById(R.id.weatherImageView);
    }

    private void setTextViewText(int id, String value) {
        TextView textView = (TextView) findViewById(id);
        textView.setText(value);
    }
}


