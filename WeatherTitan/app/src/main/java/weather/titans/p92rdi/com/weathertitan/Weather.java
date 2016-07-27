package weather.titans.p92rdi.com.weathertitan;

public class Weather {
    private String mCity;
    private String mCountry;
    private String mDescription;
    private int mTemperature;
    private int mTempMin;
    private int mTempMax;
    private String mIcon;
    private int mHumidity;
    private int mWind;

    public Weather() {

        mCity = "Unknown Settlement!";
        mCountry = "";
        mDescription = "dkanrgneq orgnoe GN O QERTGH NOENAhg";
        mTemperature = 0;
        mTempMin = 0;
        mTempMax = 0;
        mIcon = "";
        mHumidity = 0;
        mWind = 0;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmTemperature() {
        return mTemperature;
    }

    public void setmTemperature(int mTemperature) {
        this.mTemperature = mTemperature;
    }

    public int getmTempMin() {
        return mTempMin;
    }

    public void setmTempMin(int mTempMin) {
        this.mTempMin = mTempMin;
    }

    public int getmTempMax() {
        return mTempMax;
    }

    public void setmTempMax(int mTempMax) {
        this.mTempMax = mTempMax;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public int getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(int mHumidity) {
        this.mHumidity = mHumidity;
    }

    public int getmWind() {
        return mWind;
    }

    public void setmWind(int mWind) {
        this.mWind = mWind;
    }
}
