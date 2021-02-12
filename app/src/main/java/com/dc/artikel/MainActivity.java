package com.dc.artikel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dc.artikel.Adapter.Adapter;
import com.dc.artikel.Model.Artikel;
import com.dc.artikel.Model.Headline;
import com.dc.artikel.Rest.ApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText edt_search;
    Button btn_search;
    final String API_KEY = "57da071c73b54c83b681dd34f221744a";
    Adapter adapter;
    List<Artikel> artikels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.rv_list);

        edt_search = findViewById(R.id.edt_search);
        btn_search = findViewById(R.id.button_search);

        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        final String country = getCountry();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retriveJSON("", country, API_KEY);
            }
        });

        retriveJSON("", country, API_KEY);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edt_search.getText().toString().equals("")) {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retriveJSON(edt_search.getText().toString(), country, API_KEY);
                        }
                    });

                    retriveJSON(edt_search.getText().toString(), country, API_KEY);
                } else {
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retriveJSON("", country, API_KEY);
                        }
                    });

                    retriveJSON("", country, API_KEY);

                }

            }
        });
    }

    public void retriveJSON(String query, String country, String apiKey) {
        swipeRefreshLayout.setRefreshing(true);
        Call<Headline> call;

        if (!edt_search.getText().toString().equals("")) {
            call = ApiClient.getInstance().getApi().getSpecificData(query, apiKey);
        } else {
            call = ApiClient.getInstance().getApi().getHeadlines(country, apiKey);
        }

        call.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    swipeRefreshLayout.setRefreshing(false);
                    artikels.clear();
                    artikels = response.body().getArticles();
                    adapter = new Adapter(MainActivity.this, artikels);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headline> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();

        return country.toLowerCase();
    }
}
