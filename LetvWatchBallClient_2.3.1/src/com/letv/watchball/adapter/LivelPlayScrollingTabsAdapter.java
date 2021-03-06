package com.letv.watchball.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.letv.watchball.R;
import com.letv.watchball.utils.UIs;

public class LivelPlayScrollingTabsAdapter  extends BaseScrollingTabsAdapter{
public static String[] TEXTS ;
	
	public LivelPlayScrollingTabsAdapter(Context context) {
		super(context);
		TEXTS = context.getResources().getStringArray(R.array.liveplay_half_tabs);
	}

    @Override
    public View getView(int position) {
    	View tab = UIs.inflate(context, R.layout.detailplay_half_tab_item, null , false);
        final TextView text = (TextView) tab.findViewById(R.id.detailplay_half_tab_item_text);
        text.setText(TEXTS[position]);
        text.setHeight(UIs.dipToPx(33));//必须设置，否则大小不对
        
        tab.setTag(text);
       
        return tab;
    }
    
    public static void setTEXTS(String[] strs){
    	TEXTS = strs;
    }
    
    @Override
    public int getCount() {
    	return TEXTS.length;
    }

    @Override
	public void updateView(View selectedTab, View prevTab, int curPos, int prevPos) {
		if(selectedTab != null) {
			TextView text = (TextView) selectedTab.getTag();
			text.setTextColor(context.getResources().getColor(R.color.letv_color_ff00a0e9));
			text.setTextSize(14);
		}
		
		if(prevTab != null) {
			TextView text = (TextView) prevTab.getTag();
			text.setTextColor(context.getResources().getColor(R.color.letv_color_ff5c5c5c));
			text.setTextSize(13);
		}
	}
}
