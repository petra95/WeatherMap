package weather.titans.p92rdi.com.weathertitan;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;


public class MainWeather extends AppCompatActivity {

    private final String MTEXTVALUEKEYSTRING = "MyTextKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

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

}


