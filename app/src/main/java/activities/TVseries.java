package activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vac.player.player.R;

import adapters.MyViewPagerAdapter;
import fragments.Detail_fra;


public class TVseries extends FragmentActivity {

	private ViewPager viewPager;
	private LinearLayout moduleLinearlayout;
	private HorizontalScrollView hScrollView;
	private LinearLayout.LayoutParams indicateParams;
	private TextView indicateTextview;
	private MyViewPagerAdapter mAdaper;
	private FragmentManager fm;
    private PopupWindow popWindow;
    private ImageView flag_image;
    private TextView detail_textview_currentchannel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tvseries);
		initView();
		
		indicateParams = (LinearLayout.LayoutParams) indicateTextview.getLayoutParams();
		fm = getSupportFragmentManager();
		initFragment();
		 initModelEvent();

	}
	
	private void initView(){
		viewPager = (ViewPager)findViewById(R.id.tvseries_viewPager);
		moduleLinearlayout = (LinearLayout)findViewById(R.id.tvseries_linear_title);
		hScrollView = (HorizontalScrollView)findViewById(R.id.tvserise_hScroll_bar);
		indicateTextview = (TextView) findViewById(R.id.tvseries_tv_indicator);

        flag_image =(ImageView) findViewById(R.id.tvserise_image_flag);
        detail_textview_currentchannel = (TextView)findViewById(R.id.tvserises_text_curname);
	}
	
	private void initFragment(){
		ArrayList<Fragment> data = new ArrayList<Fragment>();
	
		for(int i = 0;i<9;i++){
			Detail_fra ts = new Detail_fra();
			Bundle bundle = new Bundle();
			bundle.putInt("position", i);
			ts.setArguments(bundle);
			data.add(ts);
		}
		mAdaper = new MyViewPagerAdapter(fm, data);
		viewPager.setAdapter(mAdaper);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				TextView moduleTv = (TextView) moduleLinearlayout.getChildAt(arg0);
				int sw = getResources().getDisplayMetrics().widthPixels;
				int offest = moduleTv.getLeft() - sw/2 + (moduleTv.getRight()-moduleTv.getLeft())/2;
				
				hScrollView.smoothScrollTo(offest, 0);
				Log.i("TAG", "(Integer)moduleTv.getTag()="+(Integer)moduleTv.getTag());
				for(int i=0;i< moduleLinearlayout.getChildCount();i++){
					TextView modelTv = (TextView) moduleLinearlayout.getChildAt(i);
					if((Integer)modelTv.getTag()==arg0){
						modelTv.setTextColor(getResources().getColor(R.color.home_blue));
					}else{
						modelTv.setTextColor(getResources().getColor(R.color.black));
					}
				}
			
				
			}
			
			@Override
			public void onPageScrolled(int position, float offest, int offestPixes) {
				if(offest == 0){
					indicateParams.setMargins(indicateTextview.getWidth() * position, 0, 0, 0);
					Log.i("TAG", "indicateTextview.getWidth() * position="+indicateTextview.getWidth() * position);
				}else{
					indicateParams.setMargins((int)(indicateTextview.getWidth()*(position + offest)),0,0,0);
					Log.i("TAG", "(int)(indicateTextview.getWidth()*(position + offest))="+(int)(indicateTextview.getWidth()*(position + offest)));
				}
				
				indicateTextview.setLayoutParams(indicateParams);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
	}
	
    private void initModelEvent(){
    	for(int i = 0; i < moduleLinearlayout.getChildCount(); i++){
    		TextView modelTv = (TextView) moduleLinearlayout.getChildAt(i);
    		modelTv.setTag(i);
    		modelTv.setClickable(true);
//    		modelTv.setTextColor(getResources().getColor(R.color.home_blue));
    		modelTv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int position = (Integer) v.getTag();
					selectModel(position);
					viewPager.setCurrentItem(position);
				}
			});
    	}
    }
    
    private void selectModel(int position){
    	TextView modelTv = (TextView) moduleLinearlayout.getChildAt(position);
    	int sw = getResources().getDisplayMetrics().widthPixels;
    	int offest = modelTv.getLeft() - sw/2 + (modelTv.getRight()-modelTv.getLeft())/2;
    	hScrollView.smoothScrollTo(offest, 0);
    }
    
    public void toBack(View view){
    	finish();
    }

    /**
     * @author yufengvac
     * @description 显示更多的频道
     */

    @SuppressLint("NewApi")
	public void toShowMoreChannel(View view){
        View popView = LayoutInflater.from(this).inflate(R.layout.menu_channel, null);
        popWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    TVseries.this.popWindow.dismiss();
                    return false;
                }
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
//		popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.more_bg));
        GridView gv = (GridView) popView.findViewById(R.id.menu_gv_channel);
        final List<String> channelList = new ArrayList<>();
        initChannelList(channelList);
        ArrayAdapter<String> mAdapter =new ArrayAdapter<String>(TVseries.this,android.R.layout.simple_list_item_1,channelList);
        gv.setAdapter(mAdapter);
        popWindow.showAsDropDown(findViewById(R.id.tvserise_bar), 0, 0, Gravity.RIGHT);

        flag_image.setBackgroundResource(R.drawable.other_site_arrow_selected);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                flag_image.setBackgroundResource(R.drawable.other_site_arrow_normal);
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedChannel = channelList.get(position);
                detail_textview_currentchannel.setText(selectedChannel);
                popWindow.dismiss();
            }
        });
	}

    private void initChannelList(List<String> list){
        list.add("电视剧");list.add("电影");list.add("综艺");list.add("动漫");
        list.add("音乐");list.add("咨询");list.add("会员");list.add("体育");
        list.add("教育");list.add("纪录片");list.add("原创");list.add("汽车");
        list.add("科技");list.add("游戏");list.add("生活");list.add("时尚");
        list.add("旅游");list.add("亲子");list.add("搞笑");list.add("财经");
        list.add("公益");list.add("少儿");list.add("拍客");list.add("自频道");
        list.add("特卖");list.add("品牌官网");list.add("精彩专题");list.add("整点档");
        list.add("直播");

    }
    
}
