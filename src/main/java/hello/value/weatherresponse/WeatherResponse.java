package hello.value.weatherresponse;

//                "firstcity": "Milano, IT",
//                "secondcity": "Venezia, IT",
//                "firsttemp": 29.0,
//                "secondtemp": 31.0
//              }


public class WeatherResponse {
    private String firstcity;
    private String secondcity;
    private double firsttemp;
    private double secondtemp;

    public String getFirstcity() {
        return firstcity;
    }

    public void setFirstcity(String firstcity) {
        this.firstcity = firstcity;
    }

    public String getSecondcity() {
        return secondcity;
    }

    public void setSecondcity(String secondcity) {
        this.secondcity = secondcity;
    }

    public double getFirsttemp() {
        return firsttemp;
    }

    public void setFirsttemp(double firsttemp) {
        this.firsttemp = firsttemp;
    }

    public double getSecondtemp() {
        return secondtemp;
    }

    public void setSecondtemp(double secondtemp) {
        this.secondtemp = secondtemp;
    }
}
