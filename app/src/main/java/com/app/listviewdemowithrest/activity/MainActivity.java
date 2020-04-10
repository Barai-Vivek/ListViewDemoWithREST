package com.app.listviewdemowithrest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.listviewdemowithrest.R;
import com.app.listviewdemowithrest.adapters.CustomAdapter;
import com.app.listviewdemowithrest.api.AllApiCalls;
import com.app.listviewdemowithrest.api.ApiCallResponse;
import com.app.listviewdemowithrest.models.MainResponse;
import com.app.listviewdemowithrest.models.Row;
import com.app.listviewdemowithrest.utility.InternetReachability;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressBar pgrsBar;
    private CustomAdapter adapter;
    private AllApiCalls allApiCalls;
    private Activity activity;
    private SwipeRefreshLayout swipeToRefresh;
    private Context context;
    private TextView txtErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = MainActivity.this;


        getSupportActionBar().setTitle(getResources().getString(R.string.loading));
        //display back icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialize controls
        initialization();

        //make api call
        apiCalls();

        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiCalls();
            }
        });

    }

    //initialize controls
    public void initialization() {
        allApiCalls = AllApiCalls.singleInstance(context);
        listView = findViewById(R.id.listView);
        pgrsBar = findViewById(R.id.pgrsBar);
        swipeToRefresh = findViewById(R.id.swipeToRefresh);
        txtErr = findViewById(R.id.txtErr);
    }


    public void apiCalls() {
        //add a path to api call and get response in sucess method
        if (InternetReachability.hasConnection(context)) {
            txtErr.setVisibility(View.GONE);
            if (!swipeToRefresh.isRefreshing())
                pgrsBar.setVisibility(View.VISIBLE);
            swipeToRefresh.setEnabled(false);
            hideErrorUI();
            allApiCalls.mainResonseApiCall(activity, "s/2iodh4vg0eortkl/facts.json", new ApiCallResponse() {
                @Override
                public void onSuccess(MainResponse mainResponse) {
                    hideLoaders();
                    if (mainResponse != null) {
                        if (mainResponse.getTitle() != null && !mainResponse.getTitle().trim().isEmpty()) {
                            getSupportActionBar().setTitle(mainResponse.getTitle());
                        }
                        if (mainResponse.getRows() != null && mainResponse.getRows().size() > 0) {
                            fillData(mainResponse.getRows());
                        }
                    } else {
                        showErrorUI();
                    }
                }

                @Override
                public void onError(String err) {
                    Toast.makeText(context, err + "", Toast.LENGTH_SHORT).show();
                    hideLoaders();
                    showErrorUI();
                }
            });
        } else {
            hideLoaders();
            showErrorUI();
            InternetReachability.showConnectionErrorMessage(context);
        }
    }

    public void fillData(final List<Row> rowList) {
        adapter = new CustomAdapter(rowList, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Row dataModel = rowList.get(position);
            }
        });
    }

    //hide progress bar and swiperefresh
    public void hideLoaders() {
        swipeToRefresh.setEnabled(true);
        pgrsBar.setVisibility(View.GONE);
        swipeToRefresh.setRefreshing(false);
    }

    //show error UI on api error
    public void showErrorUI() {
        listView.setVisibility(View.GONE);
        //error UI
        getSupportActionBar().setTitle("Error");
        txtErr.setVisibility(View.VISIBLE);
    }

    //hide error UI View on api succes
    public void hideErrorUI() {
        listView.setVisibility(View.VISIBLE);
        //error UI
        getSupportActionBar().setTitle(getResources().getString(R.string.loading));
        txtErr.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //back icon click
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
