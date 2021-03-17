package main.java.Retrofit;

import main.java.Domain.Account;
import main.java.Domain.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiInterface {

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("User/login")
    Call<String>Login(@Body User user);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("User")
    Call<Void>SignUp(@Body User user);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("Account")
    Call<Void>AddAccount(@Body Account account, @Header("Authorization") String authHeader);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("User/AllAccounts")
    Call<List<Account>>GetAllAccount(@Header("Authorization") String authHeader);
}
