package hjx.com.searchpark.popupwindows;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import hjx.com.searchpark.R;
import hjx.com.searchpark.adapter.ParksAdapter;

/**
 * Created by cs001 on 2016/6/30.
 */
public class ParksListPopupWindow extends PopupWindow {

    private final View contentView;
    private Context context;
    private AdapterView.OnItemClickListener listener;
    private LayoutInflater inflater;
    private ParksAdapter adapter;
    private ListView lv;

    public ParksListPopupWindow(Context context, AdapterView.OnItemClickListener listener, ParksAdapter adapter) {
        this.context = context;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
        this.adapter = adapter;
        contentView = inflater.inflate(R.layout.parks_lv_layout, null);
        lv = (ListView) contentView.findViewById(R.id.parks_lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(listener);

        this.setContentView(contentView);//给PopupWinsows设置布局文件
        this.setWidth(ViewPager.LayoutParams.MATCH_PARENT);//设置宽度
        this.setHeight(400);//设置高度
        this.setFocusable(true);//可获取焦点状态
        this.setAnimationStyle(R.style.mypopwindow_anim_style);//设置动画样式
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.popup_bg));
        this.setBackgroundDrawable(dw);//设置背景
        this.setOutsideTouchable(true);

    }
}
