package sadad.com.jibonomy.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sadad.com.jibonomy.biz.dto.AccountsRequest;
import sadad.com.jibonomy.biz.dto.Post;
import sadad.com.jibonomy.biz.dto.Result;

public interface APIService {
 
    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title,@Field("body") String body,@Field("userId") long userId);

//    @POST("account/v1/customer/individual/accounts-info")
//    Call<Result> getAccountsSer(@Header("Authorization") String auth , @Body AccountsRequest body);

    @POST("getAccounts.php")
    @FormUrlEncoded
    Call<Result> getAccounts(@Field("token") String token);

    @POST("getTransactions.php")
    @FormUrlEncoded
    Call<Result> getTransactions(@Field("token") String token,@Field("account") String account);

    @GET("getBalance.php")
    @FormUrlEncoded
    Call<Result> getBalance(@Field("token") String token,@Field("account") String account);

}