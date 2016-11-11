package hjx.com.searchpark;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import hjx.com.searchpark.Entity.ParkDetailEntity;
import hjx.com.searchpark.Entity.ParkEntity;
import hjx.com.searchpark.RequetEntity.ParkDetailRequest;
import hjx.com.searchpark.ResponseEntity.ParkDetailResponse;

public class ParkDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView detail_park_picture;
    private TextView detail_park_name_tv;
    private TextView detail_adres_tv;
    private TextView detail_jl_tv;
    private TextView detail_ccfl_tv;
    private TextView detail_yysj_tv;
    private TextView detail_sum_num_tv;
    private TextView detail_free_num_tv;
    private TextView detail_bt_price_tv;
    private TextView detail_ws_price_tv;
    private Button ljgh_btn;
    private Button dh_btn;
    private ParkEntity park;
    private LatLng latlng;
    private ImageView detail_back_btn;

    private ParkDetailEntity parkDetailEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_detail);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        park = (ParkEntity) bundle.getSerializable("park");
        latlng = bundle.getParcelable("latlng");
        initView();
        initData();
    }


    private void initView() {
        detail_park_picture = (ImageView) findViewById(R.id.detail_park_picture);
        detail_park_name_tv = (TextView) findViewById(R.id.detail_park_name_tv);
        detail_adres_tv = (TextView) findViewById(R.id.detail_adres_tv);
        detail_jl_tv = (TextView) findViewById(R.id.detail_jl_tv);
        detail_ccfl_tv = (TextView) findViewById(R.id.detail_ccfl_tv);
        detail_yysj_tv = (TextView) findViewById(R.id.detail_yysj_tv);
        detail_sum_num_tv = (TextView) findViewById(R.id.detail_sum_num_tv);
        detail_free_num_tv = (TextView) findViewById(R.id.detail_free_num_tv);
        detail_bt_price_tv = (TextView) findViewById(R.id.detail_bt_price_tv);
        detail_ws_price_tv = (TextView) findViewById(R.id.detail_ws_price_tv);
        detail_back_btn = (ImageView) findViewById(R.id.detail_back_btn);
        ljgh_btn = (Button) findViewById(R.id.ljgh_btn);
        dh_btn = (Button) findViewById(R.id.dh_btn);
        ljgh_btn.setOnClickListener(this);
        dh_btn.setOnClickListener(this);
        detail_back_btn.setOnClickListener(this);
    }

    private void initData() {
        getParkDetailRequest();


        x.image().bind(detail_park_picture, "http://images.juheapi.com/park/" + park.getCCTP());
        detail_park_name_tv.setText("名称:" + park.getCCMC());
        detail_adres_tv.setText("地址:" + park.getCCDZ());
        detail_jl_tv.setText("距离:" + park.getDistance() + "米");
        detail_ccfl_tv.setText("车场类型:" + park.getCCFL() + "---" + park.getCCLX());
        detail_sum_num_tv.setText("总车位:" + park.getZCW());
        detail_free_num_tv.setText("空车位:" + Html.fromHtml("<font color='#ff0000'>"+park.getKCW()+"</font>"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ljgh_btn:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("park", park);
//                this.setResult(Contacts.LJGH_CODE, intent);
                startActivity(intent);
                this.finish();
                break;
            case R.id.dh_btn:
                //导航
                Intent intent1 = new Intent(this, MultipleRoutePlanningActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("start", latlng);
                bundle.putParcelable("end", new LatLng(park.getWD(), park.getJD()));
                intent1.putExtra("bundle", bundle);
                startActivity(intent1);
                break;
            case R.id.detail_back_btn:
                this.finish();
                break;
        }
    }


    public void getParkDetailRequest() {
        ParkDetailRequest request = new ParkDetailRequest();
        request.setKey("a51a96110232b714945015216df436c6");
        request.setCCID(park.getCCID());
        String str = "CCID=" + request.getCCID() + "&key=" + request.getKey();
        RequestParams params = new RequestParams(URL.PARL_DETAIL + str);
        x.http().get(params, parkdetailcallback);


    }

    Callback.CommonCallback<String> parkdetailcallback = new Callback.CommonCallback<String>() {
        @Override
        public void onSuccess(String s) {
            Log.i("parkdetail", "onSuccess: "+s);
            ParkDetailResponse response = new Gson().fromJson(s, ParkDetailResponse.class);
            if (response.getError_code() == 0 && response.getResult().size() != 0) {
                parkDetailEntity = response.getResult().get(0);
                detail_yysj_tv.setText("营业时间：" + parkDetailEntity.getYYKSSJ() + "--" + parkDetailEntity.getYYJSSJ());
                detail_bt_price_tv.setText("白天价格：" + parkDetailEntity.getBTTCJG());
                detail_ws_price_tv.setText("晚上价格：" + parkDetailEntity.getWSTCJG());
            } else {
                Utils.showToast(ParkDetailActivity.this, "获取车场详情失败");
            }
        }

        @Override
        public void onError(Throwable throwable, boolean b) {
            Log.i("parkdetail", "onError: ");
        }

        @Override
        public void onCancelled(CancelledException e) {
            Log.i("parkdetail", "onCancelled: ");
        }

        @Override
        public void onFinished() {
            Log.i("parkdetail", "onFinished: ");
        }
    };
}

