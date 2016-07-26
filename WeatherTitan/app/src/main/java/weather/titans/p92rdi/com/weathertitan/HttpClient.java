package weather.titans.p92rdi.com.weathertitan;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?id=";
    private static String API_KEY = "&appid=6400cc1cfebfb4e0cab17b0eb34472da";

    public String getWeatherData(String cityId) {
        HttpURLConnection mConnection = null ;
        InputStream mInputStream = null;

        try {
            mConnection = (HttpURLConnection) (new URL(BASE_URL + cityId + API_KEY)).openConnection();
            mConnection.setRequestMethod("GET");
            mConnection.setDoInput(true);
            mConnection.setDoOutput(true);
            mConnection.connect();

            StringBuilder mStringBuilder = new StringBuilder();
            mInputStream = mConnection.getInputStream();
            BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
            String mNewLine = null;
            while ( (mNewLine = mBufferedReader.readLine()) != null )
                mStringBuilder.append(mNewLine).append("\n");

            mInputStream.close();
            mConnection.disconnect();
            return mStringBuilder.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { mInputStream.close(); } catch (Throwable ignored) {}
            try { mConnection.disconnect(); } catch (Throwable ignored) {}
        }

        return null;

    }
}