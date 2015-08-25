package com.vac.player.player;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import adapters.MyViewPagerAdapter;
import fragments.ChannelFragment;
import fragments.HomeFragment;
import fragments.SubscribeFragment;
import fragments.UserFragment;


public class MainActivity extends FragmentActivity implements OnCheckedChangeListener,
OnPageChangeListener,OnClickListener{

	private ViewPager viewPager;
	private List<Fragment> fraList = new ArrayList<Fragment>();
	private MyViewPagerAdapter mAdapter;
	private FragmentManager fm;
	private RadioGroup home_rg;
	private RadioButton home_rb,channle_rb,subscribe_rb,user_rb;
	private Button activity_more;
	private PopupWindow popWindow;
	private TextView home_title;
	private ImageView home_image;
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.activity_viewpager_viewPager);
        initFragments();
        
        initViews();
    }

	private void initViews() {
		home_rg = (RadioGroup) findViewById(R.id.activity_rg_navigation);
        home_rg.setOnCheckedChangeListener(this);
        
        home_rb = (RadioButton) findViewById(R.id.activity_rb_home);
        channle_rb = (RadioButton) findViewById(R.id.activity_rb_channel);
        subscribe_rb = (RadioButton) findViewById(R.id.activity_rb_subscribe);
        user_rb = (RadioButton) findViewById(R.id.activity_rb_user);
        
        activity_more = (Button) findViewById(R.id.activity_btn_more);
        activity_more.setOnClickListener(this);
        
        home_title = (TextView) findViewById(R.id.activity_home_title);
        
        home_image = (ImageView) findViewById(R.id.activity_home_title_image);
	}

    private void initFragments(){
    	HomeFragment hf = new HomeFragment();
    	ChannelFragment cf = new ChannelFragment();
    	SubscribeFragment sf = new SubscribeFragment();
    	UserFragment uf = new UserFragment();
    	fraList.add(hf);
    	fraList.add(cf);
    	fraList.add(sf);
    	fraList.add(uf);
    	 fm = getSupportFragmentManager();
    	mAdapter = new MyViewPagerAdapter(fm, fraList);
    	viewPager.setAdapter(mAdapter);
    	
    	viewPager.setOnPageChangeListener(this);
    }

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int position) {
		setAllCheckFalse();
		switch (position) {
		case R.id.activity_rb_home:
			viewPager.setCurrentItem(0, false);
			home_rb.setChecked(true);
			break;
		case R.id.activity_rb_channel:
			viewPager.setCurrentItem(1, false);
			channle_rb.setChecked(true);
			break;
		case R.id.activity_rb_subscribe:
			viewPager.setCurrentItem(2, false);
			subscribe_rb.setChecked(true);
			break;
		case R.id.activity_rb_user:
			viewPager.setCurrentItem(3, false);
			user_rb.setChecked(true);
			break;
			

		default:
			break;
		}
		showTitleInPosition(position);
	}

	private void setAllCheckFalse(){
		home_rb.setChecked(false);
		channle_rb.setChecked(false);
		subscribe_rb.setChecked(false);
		user_rb.setChecked(false);
	}

	@Override
	public void onPageScrollStateChanged(int position) {
	
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		setAllCheckFalse();
		switch (position) {
		case 0:
			home_rb.setChecked(true);
			break;
		case 1:
			channle_rb.setChecked(true);
			break;
		case 2:
			subscribe_rb.setChecked(true);
			break;
		case 3:
			user_rb.setChecked(true);
			break;
		default:
			break;
		}
		
		showTitleInPosition(position);
	}

	private void showTitleInPosition(int position){
		switch (position) {
		case 0:
			home_title.setVisibility(View.GONE);
			home_image.setVisibility(View.VISIBLE);
			break;
		case 1:
			home_title.setVisibility(View.VISIBLE);
			home_image.setVisibility(View.GONE);
			home_title.setText("频道");
			break;
		case 2:
			home_title.setVisibility(View.VISIBLE);
			home_image.setVisibility(View.GONE);
			home_title.setText("订阅");
			break;
		case 3:
			home_title.setVisibility(View.VISIBLE);
			home_image.setVisibility(View.GONE);
			home_title.setText("我的");
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onClick(View view) {
		int id = view.getId();
		switch (id) {
		case R.id.activity_btn_more:		//显示一个更多的菜单
			showMoreMenu(view);
			break;

		default:
			break;
		}
	}
	
	
	@SuppressLint("NewApi")
	private void showMoreMenu(View view){
		View popView = LayoutInflater.from(this).inflate(R.layout.home_more_menu, null);
		popWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		popWindow.setOutsideTouchable(true);
		popWindow.setFocusable(true);
		popWindow.setTouchInterceptor(new OnTouchListener() {

	            @Override
	            public boolean onTouch(View v, MotionEvent event) {
	            	if(event.getAction() ==MotionEvent.ACTION_OUTSIDE) {              
	            		MainActivity.this.popWindow.dismiss();
	            		return false;
	            	}
	            		return false;
	                // 这里如果返回true的话，touch事件将被拦截
	                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
	            }
	        });
//		popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.more_bg));
		popWindow.showAsDropDown(findViewById(R.id.activity_linear_titlebar), 0, 0, Gravity.RIGHT);
	}
}
