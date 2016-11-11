package hjx.com.searchpark;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hjx.com.searchpark.naviutil.TTSController;

/**
 * 创建时间：15/12/7 18:11
 * 项目名称：newNaviDemo
 *
 * @author lingxiang.wang
 * @email lingxiang.wang@alibaba-inc.com
 * 类说明：
 */

public class MultipleRoutePlanningActivity extends BaseActivity implements AMapNaviListener {

    List<NaviLatLng> startList = new ArrayList<NaviLatLng>();
    List<NaviLatLng> endList = new ArrayList<NaviLatLng>();
    private MapView mapView;
    private AMap amap;
    private AMapNavi aMapNavi;
    private HashMap<Integer, RouteOverLay> routeOverlays = new HashMap<Integer, RouteOverLay>();
    private int routeIndex;
    private int[] routeIds;
    private TTSController ttsManager;
    private boolean mapClickEndReady;
    private Marker mStartMarker;
    private Marker mWayMarker;
    private Marker mEndMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_route_layout);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        amap = mapView.getMap();


        aMapNavi = AMapNavi.getInstance(getApplicationContext());
        aMapNavi.addAMapNaviListener(this);


        ttsManager = TTSController.getInstance(getApplicationContext());
        ttsManager.init();
        ttsManager.startSpeaking();

        // 初始化Marker添加到地图
        mStartMarker = amap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.start))));
        mWayMarker = amap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.way))));
        mEndMarker = amap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.end))));
        //获取数据
        initData();


    }

    /**
     * 获取起始点
     */
    private void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        LatLng start = bundle.getParcelable("start");
        LatLng end = bundle.getParcelable("end");
        NaviLatLng startLat = new NaviLatLng(start.latitude, start.longitude);
        NaviLatLng endLat = new NaviLatLng(end.latitude, end.longitude);
        startList.clear();
        startList.add(startLat);
        endList.clear();
        endList.add(endLat);
        aMapNavi.calculateDriveRoute(startList, endList, null, PathPlanningStrategy.DRIVING_SHORT_DISTANCE);
        aMapNavi.calculateDriveRoute(startList,endList,null,PathPlanningStrategy.DRIVING_DEFAULT);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        aMapNavi.stopNavi();
        ttsManager.destroy();
        aMapNavi.destroy();
    }



    @Override
    public void onCalculateMultipleRoutesSuccess(int[] routeIds) {

        //当且仅当，使用策略AMapNavi.DrivingMultipleRoutes时回调
        //单路径算路依然回调onCalculateRouteSuccess，不回调这个


        //你会获取路径ID数组
        this.routeIds = routeIds;
        for (int i = 0; i < routeIds.length; i++) {
            //你可以通过对应的路径ID获得一条道路路径AMapNaviPath
            AMapNaviPath path = (aMapNavi.getNaviPaths()).get(routeIds[i]);

            //你可以通过这个AMapNaviPath生成一个RouteOverLay用于加在地图上
            RouteOverLay routeOverLay = new RouteOverLay(amap, path, this);
            routeOverLay.setTrafficLine(true);
            routeOverLay.addToMap();

            routeOverlays.put(routeIds[i], routeOverLay);
        }
        routeOverlays.get(routeIds[0]).zoomToSpan();
    }


    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }




    public void goToEmulateActivity(View view) {
            //SimpleNaviActivity非常简单，就是startNavi而已（因为导航道路已在这个activity生成好）
            Intent intent = new Intent(this, SimpleNaviActivity.class);
            intent.putExtra("gps", false);
            startActivity(intent);

    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int type) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation location) {

    }

    @Override
    public void onGetNavigationText(int type, String text) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    /**
     * 算路成功开始导航
     */
    @Override
    public void onCalculateRouteSuccess() {
        Intent intent = new Intent(this, SimpleNaviActivity.class);
        intent.putExtra("gps", true);
        startActivity(intent);
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int wayID) {

    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }



    public void goToGPSActivity(View view) {
            //SimpleNaviActivity非常简单，就是startNavi而已（因为导航道路已在这个activity生成好）
            Intent intent = new Intent(this, SimpleNaviActivity.class);
            intent.putExtra("gps", true);
            startActivity(intent);
    }
}
