package sadad.com.jibonomy.utils;

import sadad.com.jibonomy.services.APIService;
import sadad.com.jibonomy.services.RetrofitClient;

public class ApiUtils {
 
    private ApiUtils() {}
 
    public static final String BASE_URL = "http://192.168.25.135/jibonomy/";
 
    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}