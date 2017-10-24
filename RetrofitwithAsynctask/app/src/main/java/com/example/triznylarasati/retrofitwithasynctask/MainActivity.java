package com.example.triznylarasati.retrofitwithasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static com.example.triznylarasati.retrofitwithasynctask.R.id.rv_product;

public class MainActivity extends AppCompatActivity {
    ProgressBar progress_bar;
    AsynctaskRunner asynctask_runner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisiasi progress bar untuk meng-handle UI process
        progress_bar = (ProgressBar) findViewById(R.id.progressBar);
        progress_bar.setVisibility(View.VISIBLE);

        //inisiasi recyclerview dan layoutmanager nya
        final RecyclerView rv_product = (RecyclerView) findViewById(R.id.rv_product);
        rv_product.setLayoutManager(new LinearLayoutManager(this));

        asynctask_runner = new AsynctaskRunner();
        asynctask_runner.execute();
    }
}

class AsynctaskRunner extends AsyncTask<Call, Object, String> {
    private MyAdapter mAdapter;
    private int counter = 0;
    ProgressDialog progress_dialog;
    ProgressBar progress_bar;

    @Override
    protected void onPreExecute() {
        //this for init progress dialog
        progress_dialog.setIndeterminate(true);
        progress_dialog.setMessage("Loading...");
        progress_dialog.show();
    }

    @Override
    protected String doInBackground(Call... params) {
        //inisiasi gson object untuk mengubah data json menjadi java
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        //inisiasi retrofit untuk meman
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //inisiasi user_api dan dipanggil pakai retrofit
        UserApi user_api = retrofit.create(UserApi.class);

        try {
            //implement interface to get all user
            Call<Users> call = user_api.getUsers();
            Response<Users> response = call.execute();
            return response.body().toString();
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
                    if (progress_dialog.isShowing())
                        progress_dialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) { //kalau get api tdk memberi kembalian lagi, maka selesai
                    if (progress_dialog.isShowing())
                        progress_dialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
            return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        mAdapter.add(values[0]);
        counter ++;
        Integer current_status = (int) ((counter / (float) users.length) * 100);
        progress_bar.setProgress(current_status);

        //set progress only working for horizontal loading
        progress_dialog.setProgress(current_status);

        //setmessage will not working when using horizontal loading
        progress_dialog.setMessage(String.valueOf(current_status)+ "%");
    }

    @Override
    protected void onPostExecute(List<Users.UserItem> usersReturn) {

        //hide top progress bar
        progress_bar.setVisibility(View.INVISIBLE);

        //remove progress dialog
        progress_dialog.dismiss();

        //masukkin list user nya satu persatu
        for(int i=0;i<users.size();i++){
            //Log.e("data", String.format("data %s ",users.get(i).getEmail()));
            mAdapter.add(users.get(i));
        }
    }
}
