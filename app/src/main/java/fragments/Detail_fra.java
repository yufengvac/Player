package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vac.player.player.R;


public class Detail_fra extends Fragment {

	private TextView tv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tvserise_fra, null);
		tv = (TextView) view.findViewById(R.id.tvserise_fra_textView);
		Bundle bundle = getArguments();
		int position = bundle.getInt("position");
		switch (position) {
		case 0:
				tv.setText("第一页");
			break;
		case 1:
			tv.setText("第一页");
			break;
		case 2:
			tv.setText("第二页");
			break;
		case 3:
			tv.setText("第三页");
			break;
		case 4:
			tv.setText("第四页");
			break;
		case 5:
			tv.setText("第五页");
			break;		
		case 6:
			tv.setText("第六页");
		   break;
		case 7:
			tv.setText("第七页");
			   break;
		case 8:
			tv.setText("第八页");
			   break;
		case 9:
			tv.setText("第九页");
			   break;
		default:
			break;
		}
		return view;
	}
}
