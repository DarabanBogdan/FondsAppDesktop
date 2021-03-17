package main.java.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.Utils.Config;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    private static Retrofit retrofit=null;

    public static Retrofit getClient(){

        Config cfg=new Config();

        if(retrofit==null){


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit= new Retrofit.Builder()
                    .baseUrl(cfg.getProperty("baseUrl"))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

        }
            return  retrofit;
    }
}
