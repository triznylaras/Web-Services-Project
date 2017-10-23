package com.example.triznylarasati.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    private MyAdapter mAdapter;
    ProgressBar progress_bar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisiasi progress bar untuk meng-handle UI process
        progress_bar = (ProgressBar) findViewById(R.id.progressBar);
        progress_bar.setVisibility(View.VISIBLE);

        //inisiasi recyclerview dan layoutmanager nya
        final RecyclerView rv_product = (RecyclerView) findViewById(R.id.rv_product);
        rv_product.setLayoutManager(new LinearLayoutManager(this));

        //inisiasi gson object untuk mengubah data json menjadi java
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //inisiasi user_api dan dipanggil pakai retrofit
        UserApi user_api = retrofit.create(UserApi.class);

        //this for init progress dialog
        final ProgressDialog progress_dialog = new ProgressDialog(this);
        progress_dialog.setIndeterminate(true);
        progress_dialog.setMessage("Loading...");
        progress_dialog.show();

        //implement interface to get all user
        Call<Users> call = user_api.getUsers();
        call.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Response<Users> response, Retrofit retrofit) {
                List<Users.UserItem> users = response.body().getUsers(); //siapin list users dan ambil response body text nya

                //setup adapter buat masukin data dari recycler view
                mAdapter = new MyAdapter(getApplicationContext()); //siapin layout nya
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);

                rv_product.setLayoutManager(llm);
                rv_product.setAdapter(mAdapter);

                //masukkin list user nya satu persatu
                for(int i=0;i<users.size();i++){
                    //Log.e("data", String.format("data %s ",users.get(i).getEmail()));
                    mAdapter.add(users.get(i));
                    if (progress_dialog.isShowing())
                        progress_dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) { //kalau get api tdk memberi kembalian lagi, maka selesai
                if (progress_dialog.isShowing())
                    progress_dialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}