package main.java.Service;

import main.java.Domain.Account;
import main.java.Domain.User;
import main.java.Retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private User user;
    private ApiInterface apiInterface;



    Iterable<Account> temp;
    private int code=400;

    public Service(User user, ApiInterface apiInterface) {
        this.user=user;
        this.apiInterface=apiInterface;
    }
    public int AddAccount(Account account){


        code=400;
        Call<Void> call=apiInterface.AddAccount(account, user.getToken());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                 code=response.code();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                code=400;
            }
        });

        return code;

    }

    public Iterable<Account> findAllAccounts() throws IOException {


        temp=new ArrayList<>();

        code=400;
        Call<List<Account>> call=apiInterface.GetAllAccount(user.getToken());
        System.out.println(user.getToken());


        temp=call.execute().body();

        return temp;
    }
}
