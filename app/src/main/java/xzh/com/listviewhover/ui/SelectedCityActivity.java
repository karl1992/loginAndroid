package xzh.com.listviewhover.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import xzh.com.listviewhover.R;
import xzh.com.listviewhover.adapter.CityListAdapter;
import xzh.com.listviewhover.adapter.SearchCityAdapter;
import xzh.com.listviewhover.model.CountryDetail;
import xzh.com.listviewhover.model.CountryListModel;
import xzh.com.listviewhover.utils.Constant;
import xzh.com.listviewhover.utils.JsonUtil;
import xzh.com.listviewhover.view.SideBar;

/**
 * 注册选择国家和地区
 *
 */

public class SelectedCityActivity extends Activity implements OnItemClickListener {
    @InjectView(R.id.seacher_edit)
    EditText seacher_edit;
    @InjectView(R.id.nodata_layout)
    LinearLayout nodata_layout;
    //    @InjectView(R.id.list_sidebar)
//    SideBar list_sidebar;
    @InjectView(R.id.all_city_listview)
    StickyListHeadersListView all_city_listview;
    @InjectView(R.id.search_city_listview)
    ListView search_city_listview;

    private CountryListModel countryModel;
    private List<CountryDetail> models;
    private CityListAdapter cityListAdapter;
    private SearchCityAdapter searchCityAdapter;
    private int type = 1;
    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city_layout);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        initParam();
        initView();
        getCountryData();
    }

    private void initParam() {

    }

    private void getCountryData() {
        initCountryData();
    }

    //获取国家数据
    private void initCountryData() {
        String url = Constant.GET_COUNTRY_DATA;
        RequestParams params = new RequestParams();
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                countryModel = JsonUtil.parseJson(content, CountryListModel.class);
                if (countryModel != null) {
                    models = countryModel.Result;
                    bindCity(models);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Toast.makeText(SelectedCityActivity.this, content, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        setHintText();
        seacher_edit.addTextChangedListener(textWatcher);
        all_city_listview.setOnItemClickListener(this);
        search_city_listview.setOnItemClickListener(this);
//        list_sidebar.setListView(all_city_listview);
    }

    private void setHintText() {
        if (type == 1) {
            seacher_edit.setHint("搜索国家");
        } else if (type == 2) {
            seacher_edit.setHint("搜索州/省");
        } else {
            seacher_edit.setHint("搜索城市");
        }
    }

    @OnClick({R.id.iv_close, R.id.btn_fresh})
    public void cityClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_fresh:
                getCountryData();
                break;
        }
    }


    private void bindCity(List<CountryDetail> listModel) {
        if (listModel != null && listModel.size() > 0) {
            cityListAdapter = new CityListAdapter(models, this);
            all_city_listview.setAdapter(cityListAdapter);
        }
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(null==models){
              return;
            }
            String search = seacher_edit.getText().toString().trim();
            searchCityAdapter = new SearchCityAdapter(models, SelectedCityActivity.this);
            searchCityAdapter.setHideNoData(hideData);
            search_city_listview.setAdapter(searchCityAdapter);
            searchCityAdapter.getFilter().filter(search);
            if (models.size() < 1 || TextUtils.isEmpty(search)) {
                all_city_listview.setVisibility(View.VISIBLE);
                search_city_listview.setVisibility(View.GONE);
//                list_sidebar.setVisibility(View.VISIBLE);
                nodata_layout.setVisibility(View.GONE);
            } else {
                all_city_listview.setVisibility(View.GONE);
                search_city_listview.setVisibility(View.VISIBLE);
//                list_sidebar.setVisibility(View.GONE);
                nodata_layout.setVisibility(View.GONE);
            }
        }
    };

    SearchCityAdapter.OnHideNodata hideData = new SearchCityAdapter.OnHideNodata() {
        @Override
        public void hide() {
            if (search_city_listview.getVisibility() == View.VISIBLE)
                nodata_layout.setVisibility(View.VISIBLE);
        }
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }


    @Override
    public void onDestroy() {
//        if (list_sidebar != null) {
//            list_sidebar.removeTextDialog();
//        }
        finish();
        super.onDestroy();
    }

}
