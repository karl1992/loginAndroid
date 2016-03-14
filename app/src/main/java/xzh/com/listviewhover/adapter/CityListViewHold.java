package xzh.com.listviewhover.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.listviewhover.R;
import xzh.com.listviewhover.model.CountryDetail;

/**
 * 注册选择国家/地区
 */
public class CityListViewHold {

    @InjectView(R.id.city_name)
    TextView cityName;
    @InjectView(R.id.city_num)
    TextView cityNum;

    private Context context;

    public View init(Context context) {
       this.context=context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.choose_city_list_item, null);
        ButterKnife.inject(this, view);
        view.setTag(this);
        return view;
    }

    public void fillData(List<CountryDetail> models,
                         int position) {
        if (models == null) {
            return;
        }
        bindData(models, position);
    }

    private void bindData(List<CountryDetail> models, int position) {
        CountryDetail model = models.get(position);
        if (model==null)
        return;
        String name = model.NameEn;
        String pinyin = model.NameEn;
        String num=model.Code;
        String curChar = pinyin.substring(0, 1).toUpperCase();
        cityNum.setText("+"+num);
        cityName.setText(name);
        if (position != 0) {
            CountryDetail preModel = models
                    .get(position - 1);
            String prePinyin = preModel.NameEn;
            String preChar = prePinyin.substring(0, 1).toUpperCase();
        }else {

        }
    }
}
