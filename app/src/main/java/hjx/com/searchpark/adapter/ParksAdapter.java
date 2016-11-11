package hjx.com.searchpark.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hjx.com.searchpark.R;
import hjx.com.searchpark.Entity.ParkEntity;

/**
 * Created by cs001 on 2016/6/30.
 */
public class ParksAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ParkEntity> list;
    private LayoutInflater inflater;
    private LatLng latLng;
    private int checkPosition;


    public ParksAdapter(Context context, ArrayList<ParkEntity> list, LatLng latlng) {
        this.context = context;
        this.list = list;
        this.latLng = latlng;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.parks_lv_item_layout, null);
            holder.park_name_tv = (TextView) convertView.findViewById(R.id.detail_park_name_tv);
            holder.adres_tv = (TextView) convertView.findViewById(R.id.detail_jl_tv);
            holder.sum_num_tv = (TextView) convertView.findViewById(R.id.detail_sum_num_tv);
            holder.free_num_tv = (TextView) convertView.findViewById(R.id.detail_free_num_tv);
            holder.ccfl_tv = (TextView) convertView.findViewById(R.id.detail_ccfl_tv);
            holder.cwxx_tv = (TextView) convertView.findViewById(R.id.cwxx_tv);
            holder.picture_iv = (ImageView) convertView.findViewById(R.id.picture_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (checkPosition == position) {
            holder.park_name_tv.setTextColor(context.getResources().getColor(R.color.light_blue));
            holder.adres_tv.setTextColor(context.getResources().getColor(R.color.light_blue));
            holder.sum_num_tv.setTextColor(context.getResources().getColor(R.color.light_blue));
            holder.free_num_tv.setTextColor(context.getResources().getColor(R.color.light_blue));
            holder.ccfl_tv.setTextColor(context.getResources().getColor(R.color.light_blue));
            holder.cwxx_tv.setTextColor(context.getResources().getColor(R.color.light_blue));
        } else {
            holder.park_name_tv.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.adres_tv.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.sum_num_tv.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.free_num_tv.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.ccfl_tv.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.cwxx_tv.setTextColor(context.getResources().getColor(R.color.text_color));
        }
        ParkEntity park = list.get(position);
        LatLng curlatlng = new LatLng(park.getWD(), park.getJD());
        int distance = (int) AMapUtils.calculateLineDistance(latLng, curlatlng);
        park.setDistance(distance);
        holder.park_name_tv.setText("名称:" + park.getCCMC());
        holder.adres_tv.setText("距离:" + distance + "米");
        holder.sum_num_tv.setText("总车位:" + park.getZCW());
        holder.free_num_tv.setText("空车位:" + park.getKCW());
        holder.ccfl_tv.setText("车场类型:" + park.getCCFL() + "---" + park.getCCLX());
        Picasso.with(context).load("http://images.juheapi.com/park/" + park.getCCTP())
                .placeholder(new ColorDrawable(context.getResources().getColor(R.color.offline_down_size)))
                .error(new ColorDrawable(context.getResources().getColor(R.color.offline_down_size)))
                .into(holder.picture_iv);
        // x.image().bind(holder.picture_iv, "http://images.juheapi.com/park/" + park.getCCTP());
        return convertView;
    }


    public void setCheck(int position) {
        checkPosition = position;
        this.notifyDataSetChanged();
    }


    class ViewHolder {
        private TextView park_name_tv;
        private TextView adres_tv;
        private TextView sum_num_tv;
        private TextView free_num_tv;
        private TextView ccfl_tv;
        private TextView cwxx_tv;
        private ImageView picture_iv;
    }


}
