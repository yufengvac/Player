package fragments;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.vac.player.player.R;

import activities.DetailShow;
import adapters.MyShowAdapter;
import entity.Show;
import utils.HttpEngine;

public class Detail_fra extends Fragment implements DetailShow.rechooseshowListener {

	private GridView showGridview;
	private List<Show> showList;
	private MyShowAdapter mAdapter;
	private ProgressDialog pd;

	private DetailShow.rechooseshowListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tvserise_fra, null);
		showGridview = (GridView) view.findViewById(R.id.detail_gv_show);

		pd = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
		pd.setMessage("正在加载中...");
		pd.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				mAdapter.notifyDataSetChanged();
			}
		});

		Bundle bundle = getArguments();
		int position = bundle.getInt("position");
		switch (position) {
			case 0:
//			tv.setText("第一页");
				showTVSesrise("电视剧");
				break;
			case 1:
//			tv.setText("第一页");
				break;
			case 2:
//			tv.setText("第二页");
				break;
			case 3:
//			tv.setText("第三页");
				break;
			case 4:
//			tv.setText("第四页");
				break;
			case 5:
//			tv.setText("第五页");
				break;
			case 6:
//			tv.setText("第六页");
				break;
			case 7:
//			tv.setText("第七页");
				break;
			case 8:
//			tv.setText("第八页");
				break;
			case 9:
//			tv.setText("第九页");
				break;
			default:
				break;
		}
		return view;
	}

	/**
	 * 所有的电视剧
	 */
	private void showTVSesrise(String name){
		showList = HttpEngine.getShowsByCategotyByAsync(getActivity(), name, pd);
		for(Show s:showList){
			Log.i("TAG", s.toString());
		}
		mAdapter = new MyShowAdapter(getActivity(), showList);
		showGridview.setAdapter(mAdapter);
	}


	@Override
	public void rechooseshow(String name){
		showTVSesrise(name);
	}

}
