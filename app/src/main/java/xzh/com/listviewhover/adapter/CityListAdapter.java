package xzh.com.listviewhover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import xzh.com.listviewhover.R;
import xzh.com.listviewhover.model.CountryDetail;

public class CityListAdapter extends BaseAdapter implements SectionIndexer,StickyListHeadersAdapter {
    private List<CountryDetail> cityList;
    private Context context;
    private TreeSet<Character> characters = new TreeSet<Character>();

    public CityListAdapter(List<CountryDetail> cityList,
                           Context context) {
        this.cityList = cityList;
        this.context = context;
        //排序
        Collections.sort(cityList, comparator);
    }

    @Override
    public int getCount() {
        if (cityList == null) {
            return 0;
        }
        return cityList.size();
    }

    @Override
    public CountryDetail getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityListViewHold viewHolder;
        if (convertView == null) {
            viewHolder = new CityListViewHold();
            convertView = viewHolder.init(context);
        } else {
            viewHolder = (CityListViewHold) convertView.getTag();
        }
        viewHolder.fillData(cityList, position);
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int section) {
        if (section == cityList.size()) {
            return 0;
        }
        if (cityList != null) {
            for (int i = 0; i < cityList.size(); i++) {
                String l = cityList.get(i).NameEn;
                char firstChar = l.toUpperCase().charAt(0);
                characters.add(firstChar);
                if (firstChar == section) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    /**
     * a-z排序
     */
    Comparator comparator = new Comparator<CountryDetail>() {
        @Override
        public int compare(CountryDetail lhs, CountryDetail rhs) {
            String a = lhs.NameEn;
            String b = rhs.NameEn;
            return a.compareTo(b);
        }
    };



    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.head_item, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.head_name);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        CountryDetail detail=getItem(position);
            String headerText = detail.NameEn.charAt(0)+"";
            holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    class HeaderViewHolder {
        TextView text;
    }
}
