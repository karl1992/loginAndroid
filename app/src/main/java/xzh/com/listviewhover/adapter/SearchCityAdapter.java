package xzh.com.listviewhover.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import xzh.com.listviewhover.model.CountryDetail;

/**
 * 模糊搜索adapter
 */
public class SearchCityAdapter extends BaseAdapter implements
        SectionIndexer, Filterable {
    /**
     * a-z排序
     */
    Comparator comparator = new Comparator<CountryDetail>() {
        @Override
        public int compare(CountryDetail lhs, CountryDetail rhs) {
            String a = lhs.NameEn;
            String b = rhs.NameEn;
            if(TextUtils.isEmpty(a) || TextUtils.isEmpty(b)){return 0;}
            return a.compareTo(b);
        }
    };
    private List<CountryDetail> allCity;
    private List<CountryDetail> filterCity;
    private Context context;
    private OnHideNodata noData;

    public SearchCityAdapter(List<CountryDetail> allCity,
                             Context context) {
        super();
        this.context = context;
        filterCity = new ArrayList<CountryDetail>();
        this.allCity = allCity;
        //排序
        Collections.sort(filterCity, comparator);
        filterCity.size();
    }

    @Override
    public int getCount() {
        if (filterCity == null) {
            return 0;
        }
        return filterCity.size();
    }

    @Override
    public Object getItem(int paramInt) {
        return filterCity.get(paramInt);
    }

    @Override
    public long getItemId(int paramInt) {
        return 0;
    }

    @Override
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        CityListViewHold holdView;
        if (paramView == null) {
            holdView = new CityListViewHold();
            paramView = holdView.init(context);
        } else {
            holdView = (CityListViewHold) paramView.getTag();
        }

        holdView.fillData(filterCity, paramInt);
        return paramView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(
                    CharSequence paramCharSequence) {
                FilterResults results = new FilterResults();
                //不区分大小写
                String filterChar = paramCharSequence.toString().toLowerCase();
                if (filterChar.length() > 0 && filterChar != null
                        && allCity.size() > 0) {
                    List<CountryDetail> filterList = new ArrayList<CountryDetail>();
                    for (CountryDetail model : allCity) {
                        //全部转换为小写匹配
                        if (model.NameEn.toLowerCase().indexOf(filterChar) > -1) {
                            filterList.add(model);
                        }
                    }
                    results.values = filterList;
                    results.count = filterList.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence paramCharSequence,
                                          FilterResults paramFilterResults) {
                filterCity = (ArrayList<CountryDetail>) paramFilterResults.values;
                if (paramFilterResults.count > 0) {
                    notifyDataSetChanged();
                } else {
                    noData.hide();
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int paramInt) {
        if (paramInt == allCity.size()) {
            return 0;
        }
        if (filterCity != null) {
            for (int i = 0; i < filterCity.size(); i++) {
                String str = filterCity.get(i).NameEn;
                char firstChar = str.toUpperCase().charAt(0);
                if (firstChar == paramInt) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int paramInt) {
        return 0;
    }

    public void setHideNoData(OnHideNodata noData) {
        this.noData = noData;
    }

    public interface OnHideNodata {

        void hide();
    }

}
