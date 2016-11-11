package hjx.com.searchpark;

import android.app.Activity;
import android.app.Application;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by cs001 on 2016/6/21.
 */
public class MyApplication extends Application {

    public static ArrayList<Activity> allActivitys = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        x.Ext.init(this);
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        Activity removeAcy = null;
        for (Activity activity : allActivitys) {
            if (activity.getClass().equals(cls)) {
                if (activity != null) {
                    removeAcy = activity;
                }
            }
        }
        removeAcy.finish();
        removeAcy = null;
    }

    public static void addActivity(Activity activity) {
        allActivitys.add(activity);
    }

    public static void finishAllActivity() {
        for (int i = 0; i < allActivitys.size(); i++) {
            allActivitys.get(i).finish();
        }
    }
}
