package hjx.com.searchpark;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import org.xutils.x;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横屏显示
        x.view().inject(this);
        MyApplication.addActivity(this);
    }
}
