package hjx.com.searchpark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;

import hjx.com.searchpark.Entity.ParkEntity;
import hjx.com.searchpark.adapter.ParksAdapter;

public class AllParksActivity extends BaseActivity {
    private ArrayList<ParkEntity> parks;
    private LatLng latlng;
    private Bundle bundle;
    private ListView all_parks_lv;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_parks);
        bundle = getIntent().getBundleExtra("bunder");
        initView();
    }

    private void initView() {
        back_btn = (ImageView) findViewById(R.id.back_btn);
        all_parks_lv = (ListView) findViewById(R.id.all_parks_lv);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllParksActivity.this.finish();
            }
        });
        if (bundle == null) {
            Utils.showToast(this, "数据异常");
            finish();
        }
        parks = (ArrayList<ParkEntity>) bundle.getSerializable("parks");
        latlng = bundle.getParcelable("latlng");
        if (parks != null && latlng != null) {
            ParksAdapter adapter = new ParksAdapter(this, parks, latlng);
            all_parks_lv.setAdapter(adapter);
        }
        all_parks_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllParksActivity.this, ParkDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("park", parks.get(position));
                bundle.putParcelable("latlng", latlng);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }
}
