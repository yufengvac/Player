package activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
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
    
}
