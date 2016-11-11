package hjx.com.searchpark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import hjx.com.searchpark.Entity.ParkEntity;
import hjx.com.searchpark.RequetEntity.NearParkRequest;
import hjx.com.searchpark.ResponseEntity.NearParkResponse;
import hjx.com.searchpark.adapter.ParksAdapter;
import hjx.com.searchpark.popupwindows.ParksListPopupWindow;
import hjx.com.searchpark.route.AMapUtil;
import hjx.com.searchpark.route.DriveRouteColorfulOverLay;

public class MainActivity extends BaseActivity implements LocationSource,
        AMapLocationListener,
        AMap.OnMarkerClickListener,
        AMap.InfoWindowAdapter,
        AMap.OnInfoWindowClickListener,
        RouteSearch.OnRouteSearchListener {

    private MapView mMapView;
    private AMap aMap;

    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private OnLocationChangedListener mListener;
    private LatLng curlatlng;
    private ParksListPopupWindow pupop;
    private ListView park_lv;
    private ImageView parks_iv;
    String nearpark = "nearpark";
    ArrayList<ParkEntity> parks = new ArrayList<>();
    ArrayList<Marker> markers = new ArrayList<>();
    private ParkEntity clickPark;
    private FrameLayout parks_frame_layout;
    private CameraUpdate update;
    private ParksAdapter adapter;
    private RouteSearch mRouteSearch;
    private ProgressDialog progDialog;
    private RelativeLayout mBottomLayout;
    private TextView mRotueTimeDes;
    private TextView mRouteDetailDes;
    private long mExitTime;
    private int cur_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        initView();
        aMap = mMapView.getMap();
        //设置地图的缩放级别

        setUpMap();
        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                aMap.moveCamera(CameraUpdateFactory.zoomIn());
                aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
            }
        });


        ImageView btn = (ImageView) findViewById(R.id.location_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationClient.startLocation();
                aMap.clear();
            }
        });
    }

    //初始化控件
    private void initView() {
        parks_iv = (ImageView) findViewById(R.id.parks_iv);
        park_lv = (ListView) findViewById(R.id.main_park_lv);
        parks_frame_layout = (FrameLayout) findViewById(R.id.parks_frame_layout);
        mBottomLayout = (RelativeLayout) findViewById(R.id.route_layout);
        mRotueTimeDes = (TextView) findViewById(R.id.time_tv);
        mRouteDetailDes = (TextView) findViewById(R.id.money_tv);
        parks_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curlatlng != null) {
                    Intent intent = new Intent(MainActivity.this, AllParksActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("parks", parks);
                    bundle.putParcelable("latlng", curlatlng);
                    intent.putExtra("bunder", bundle);
                    startActivityForResult(intent, 1);
                }else{
                    Utils.showToast(MainActivity.this,"正在定位...");
                }
            }
        });
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        //设置popupwindow
        aMap.setInfoWindowAdapter(this);
        aMap.setOnInfoWindowClickListener(this);
        /**设置定位图标*/
        MyLocationStyle locationStyle = new MyLocationStyle();
        locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.point));
//        locationStyle.strokeColor(getResources().getColor(R.color.light_blue));
//        locationStyle.strokeWidth(2);
        aMap.setMyLocationStyle(locationStyle);


        //设置地图表面控件的位置
        UiSettings settings = aMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);


        //aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        //aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);

        //设置marker点击事件
        aMap.setOnMarkerClickListener(this);

        //路径规划相关

        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (locationClient == null) {
            locationClient = new AMapLocationClient(this);
            locationOption = new AMapLocationClientOption();
            //设置定位监听
            locationClient.setLocationListener(this);
            //设置为高精度定位模式
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            locationOption.setOnceLocation(true);
            //设置定位参数
            locationClient.setLocationOption(locationOption);
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            locationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.i("tag", "onLocationChanged: ");
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                Log.i("TAG", "定位成功");
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                //当前经纬度
                curlatlng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                //获取附近停车场信息
                getNearPark(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                //绘制marker
                Log.i("PARK", "onLocationChanged:------> " + aMapLocation.getLatitude() + "   " + aMapLocation.getLongitude());
                // mListener.onLocationChanged(aLocation);//这行代码就是显示系统默认图标，现在注释掉
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }


    private void getNearPark(final double latitude, final double longitude) {
        final NearParkRequest request = new NearParkRequest();
        request.setJD((float) longitude);
        request.setKey("a51a96110232b714945015216df436c6");
        request.setWD((float) latitude);
        request.setSDXX(1);
        request.setJLCX(3000);
        request.setJLPX(1);
        String url = "key=" +request.getKey()+
                "&JD=" + request.getJD() +
                "&WD=" + request.getWD() +
                "&SDXX=" + request.getSDXX() +
                "&TCCFL=" + request.getTCCFL() +
                "&TCCLX=" + request.getTCCLX() +
                "&JLCX=" + request.getJLCX() +
                "&JLPX=" + request.getJLPX();
        RequestParams params = new RequestParams(URL.GET_NEAR_PARK + url);
        params.setConnectTimeout(3000);
        //params.addParameter("", new Gson().toJson(request));
        Log.i("PARK", "request------>" +URL.GET_NEAR_PARK + url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.i(nearpark, "onSuccess:-------- " + s);
                NearParkResponse response = new Gson().fromJson(s, NearParkResponse.class);
                parks = (ArrayList<ParkEntity>) response.getResult();
                if (response.getError_code() == 0 && parks.size() != 0) {
                    DrawMrakers(parks);
                } else {
                    Utils.showToast(MainActivity.this, "抱歉,暂时无法获取附近的车位信息");
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i(nearpark, "onError:-------- " + throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {
                Log.i(nearpark, "onCancelled:-------- ");
            }

            @Override
            public void onFinished() {
                if(parks == null || parks.size() == 0){
                    Utils.showToast(MainActivity.this,"获取数据异常");
                }
            }
        });
    }

    private void DrawMrakers(List<ParkEntity> list) {
        //绘制marker
        for (int i = 0; i < list.size(); i++) {
            ParkEntity entity = list.get(i);
            Marker marker = aMap.addMarker(new MarkerOptions()
                    .position(new LatLng(entity.getWD(), entity.getJD()))
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.marker_other_highlight)))
                    .draggable(true));
            if (i == cur_position) {
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.poi_marker_1)));
            }

            //必须设置title，否则popupwindow不显示
            //marker.setTitle("");
            markers.add(marker);
        }
        if (curlatlng != null) {
            //新建parks列表popupwindow
            adapter = new ParksAdapter(this, parks, curlatlng);
            //popupwindow方案
//             pupop = new ParksListPopupWindow(this, listener, adapter);
//             pupop.showAtLocation(this.findViewById(R.id.map), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            //下方列表方案
            park_lv.setAdapter(adapter);
            adapter.setCheck(cur_position);
            park_lv.setSelection(cur_position);
            park_lv.setOnItemClickListener(listener);
            if (parks_frame_layout.getVisibility() == View.GONE) {
                parks_frame_layout.setVisibility(View.VISIBLE);
            }

            //返回一个CameraUpdate对象，只改变地图可视区域中心点，地图缩放级别不变。
            CameraUpdate update = CameraUpdateFactory.changeLatLng(curlatlng);
            aMap.moveCamera(update);

            //将视图往上移动
            update = CameraUpdateFactory.scrollBy(0, Utils.dip2px(this, 150));
            aMap.moveCamera(update);
        } else {
            Utils.showToast(this, "获取当前位置失败");
            return;
        }
    }


    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            adapter.setCheck(position);
            cur_position = position;
            Log.i("setCheckMarker", "position=======" + position);

            setCheckMarker(position);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("onItemClick", "onItemClick: ");
                    Intent intent = new Intent(MainActivity.this, ParkDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("park", parks.get(position));
                    bundle.putParcelable("latlng", curlatlng);
                    intent.putExtra("bundle", bundle);
                    startActivityForResult(intent, Contacts.REQUEST_LJGH);
//            if (pupop != null && pupop.isShowing()) {
//                pupop.dismiss();
//            }
                }
            }, 500);

        }
    };


    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("location", "onMarkerClick:-----> " + marker.getGeoPoint().x);
        int position = markers.indexOf(marker);
        clickPark = parks.get(position);
        Log.i("onclick", "URL--->" + "http://images.juheapi.com/park/" + clickPark.getCCTP());
        CameraUpdate move = CameraUpdateFactory.changeLatLng(marker.getPosition());
        aMap.moveCamera(move);
        if (update == null) {
            update = CameraUpdateFactory.scrollBy(0, Utils.dip2px(this, 150));
        }
        //设置选中的marker
        setCheckMarker(position);
        aMap.moveCamera(update);
        adapter.setCheck(position);
        park_lv.setSelection(position);
        //显示popupwindow
        marker.showInfoWindow();
        return true;
    }

    /**
     * 设置选中的marker
     *
     * @param position
     */
    private void setCheckMarker(int position) {
        Log.i("setCheckMarker", "setCheckMarker: ");
        for (int i = 0; i < markers.size(); i++) {
            if (position == i) {
                markers.get(i).setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.poi_marker_1)));
            } else {
                markers.get(i).setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.marker_other_highlight)));
            }
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        Log.i("location", "getInfoWindow: ");
        View infoWindow = getLayoutInflater().inflate(
                R.layout.infowindowlayout, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Log.i("location", "getInfoContents: ");
        View infoWindow = getLayoutInflater().inflate(
                R.layout.infowindowlayout, null);
        render(marker, infoWindow);
        return infoWindow;
    }


    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        //名称
        TextView name_tv = ((TextView) view.findViewById(R.id.info_park_name_tv));
        //距离
        TextView distance_tv = (TextView) view.findViewById(R.id.info_jl_tv);
        //总车位
        TextView sum_num_tv = (TextView) view.findViewById(R.id.info_sum_num_tv);
        //空车位
        TextView free_sum_tv = (TextView) view.findViewById(R.id.info_free_num_tv);
        //车场分类
        TextView ccfl_tv = (TextView) view.findViewById(R.id.info_ccfl_tv);
        name_tv.setText("车场名称:" + clickPark.getCCMC());
        LatLng latlng = new LatLng(clickPark.getWD(), clickPark.getJD());
        int distance = (int) AMapUtils.calculateLineDistance(latlng, curlatlng);
        distance_tv.setText("车场距离:" + distance + "米");
        sum_num_tv.setText("车位总数量:" + clickPark.getZCW());
        free_sum_tv.setText("空车位:" + clickPark.getKCW());
        ccfl_tv.setText("车场类型:" + clickPark.getCCFL() + "---" + clickPark.getCCLX());
    }

    /**
     * popupwindow点击事件
     *
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(MainActivity.this, ParkDetailActivity.class);
        intent.putExtra("park", clickPark);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Contacts.LJGH_CODE) {
//            ParkEntity park = (ParkEntity) data.getSerializableExtra("park");
//            LatLonPoint startPoint = new LatLonPoint(curlatlng.latitude, curlatlng.longitude);
//            LatLonPoint endPoint = new LatLonPoint(park.getWD(), park.getJD());
//            showProgressDialog();
//            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
//            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
//                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
//            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent!=null){
            Log.i("onNewIntent", "onNewIntent: ");
            ParkEntity park = (ParkEntity) intent.getSerializableExtra("park");
            if(curlatlng==null){
                return;
            }
            LatLonPoint startPoint = new LatLonPoint(curlatlng.latitude, curlatlng.longitude);
            if(park !=null){
                LatLonPoint endPoint = new LatLonPoint(park.getWD(), park.getJD());
                showProgressDialog();
                RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
                RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
                        null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
                mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
            }

        }

    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    DriveRouteResult mDriveRouteResult;

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int errorCode) {
        dissmissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == 1000) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {
                    mDriveRouteResult = driveRouteResult;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);

                    DriveRouteColorfulOverLay drivingRouteOverlay = new DriveRouteColorfulOverLay(
                            aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    mBottomLayout.setVisibility(View.VISIBLE);
                    parks_frame_layout.setVisibility(View.GONE);
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    mRotueTimeDes.setText(des);
                    mRouteDetailDes.setVisibility(View.VISIBLE);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    mRouteDetailDes.setText("打车约" + taxiCost + "元");
                    mBottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this,
                                    DriveRouteDetailActivity.class);
                            intent.putExtra("drive_path", drivePath);
                            intent.putExtra("drive_result",
                                    mDriveRouteResult);
                            startActivity(intent);
                        }
                    });
                } else if (driveRouteResult != null && driveRouteResult.getPaths() == null) {
                    Utils.showToast(MainActivity.this, "对不起，没有搜索到相关数据！");
                }

            } else {
                Utils.showToast(MainActivity.this, "对不起，没有搜索到相关数据！");
            }
        } else {
            Utils.showToast(this.getApplicationContext(), errorCode + "");
        }


    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }


    @Override
    public void onBackPressed() {
        if (mBottomLayout.getVisibility() == View.VISIBLE) {
            mBottomLayout.setVisibility(View.GONE);
            parks_frame_layout.setVisibility(View.VISIBLE);
            aMap.clear();
            locationClient.startLocation();
            markers.clear();
            DrawMrakers(parks);

            Log.i("marker", "marker.size====" + markers.size());
            return;
        }


        if ((System.currentTimeMillis() - mExitTime) > 2000) {//
            // 如果两次按键时间间隔大于2000毫秒，则不退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();// 更新mExitTime
        } else {
            MyApplication.finishAllActivity();
            System.exit(0);// 否则退出程序
        }
    }
}
