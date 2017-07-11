package com.gdyd.qmwallet;


import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdyd.qmwallet.utils.Is;


/**
 * 页脚
 * @author hjh
 *
 */
public class mFooter extends Fragment implements OnClickListener {

	private PercentRelativeLayout layout_bottom_home;
	private PercentRelativeLayout layout_bottom_share;
	private PercentRelativeLayout layout_bottom_mine;
	private PercentRelativeLayout layout_bottom_trans;
	private PercentRelativeLayout layout_bottom_talk;

	private ImageView s_img_home;
	private ImageView s_img_share;
	private ImageView s_img_mine;
	private ImageView s_img_trans;
	private ImageView s_img_talk;

	private TextView s_text_home;
	private TextView s_text_share;
	private TextView s_text_mine;
	private TextView s_text_trans;
	private TextView s_img_trans_sum;
	private TextView s_text_talk;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_main_footer, container,false);
		layout_bottom_home = (PercentRelativeLayout) view
				.findViewById(R.id.layout_bottom_home);
		layout_bottom_share = (PercentRelativeLayout) view.findViewById(R.id.layout_bottom_share);
		layout_bottom_mine = (PercentRelativeLayout) view
				.findViewById(R.id.layout_bottom_mine);
		layout_bottom_trans = (PercentRelativeLayout) view
				.findViewById(R.id.layout_bottom_trans);
		layout_bottom_talk = (PercentRelativeLayout) view
				.findViewById(R.id.layout_bottom_talk);

		s_img_trans_sum = ((TextView) view.findViewById(R.id.s_img_trans_sum));
		s_img_home = (ImageView) view.findViewById(R.id.s_img_home);
		s_img_share = (ImageView) view.findViewById(R.id.s_img_share);
		s_img_mine = (ImageView) view.findViewById(R.id.s_img_mine);
		s_img_trans = (ImageView) view.findViewById(R.id.s_img_trans);
		s_img_talk = (ImageView) view.findViewById(R.id.s_img_talk);

		s_text_home = (TextView) view.findViewById(R.id.s_text_home);
		s_text_share = (TextView) view.findViewById(R.id.s_text_share);
		s_text_mine = (TextView) view.findViewById(R.id.s_text_mine);
		s_text_trans = (TextView) view.findViewById(R.id.s_text_trans);
		s_text_talk = (TextView) view.findViewById(R.id.s_text_talk);

//			   layout_bottom_share.setVisibility(View.GONE);
//			   RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels / 3, ViewGroup.LayoutParams.MATCH_PARENT);
//			   RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels / 3, ViewGroup.LayoutParams.MATCH_PARENT);
//			   RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels / 3, ViewGroup.LayoutParams.MATCH_PARENT);
//			   params2.leftMargin=getResources().getDisplayMetrics().widthPixels / 3;
//			   params3.leftMargin=getResources().getDisplayMetrics().widthPixels*2 / 3;
//			   layout_bottom_home.setLayoutParams(params1);
//			   layout_bottom_mine.setLayoutParams(params3);
//			   layout_bottom_trans.setLayoutParams(params2);


		initStyle(new ImageView[] {s_img_home, s_img_share,s_img_talk, s_img_trans, s_img_mine},
				new int[] { R.drawable.home_blue,
						R.drawable.share_blue,
						R.drawable.tab5_select,
						R.drawable.trans_blue,
						R.drawable.mine_blue }, new int[] {
						R.drawable.home,
						R.drawable.share,
						R.drawable.tab5,
						R.drawable.trans,
						R.drawable.mine }, new TextView[] {
						s_text_home, s_text_share,s_text_talk, s_text_trans, s_text_mine},
				R.color.blue, R.color.colorGray);

		layout_bottom_home.setOnClickListener(this);
		layout_bottom_share.setOnClickListener(this);
		layout_bottom_mine.setOnClickListener(this);
		layout_bottom_trans.setOnClickListener(this);
		layout_bottom_talk.setOnClickListener(this);
		return view;
	}

	private int pointer = -1;
	private ImageView[] images;
	private TextView[] texts;
	private int[] imagesOfFocus;
	private int[] imagesOfNoFocus;
	private int bcolor;
	private int wcolor;

	private void initStyle(ImageView[] image, int[] focus, int[] nofocus,
			TextView[] text, int bcolor, int wcolor) {
		if (Is.isNoEmptyAll(image, focus, nofocus, text, bcolor, wcolor)) {
			if (image.length == focus.length && image.length == nofocus.length
					&& image.length == text.length) {
				images = image;
				imagesOfFocus = focus;
				imagesOfNoFocus = nofocus;
				texts = text;
				this.bcolor = bcolor;
				this.wcolor = wcolor;
				pointer = 0;
			} else {
				pointer = -1;
			}
		}
	}
      public void  setSumInfo(String sum){
		  if (sum!=null&&!sum.equals("")&&!sum.trim().equals("0")){
			  s_img_trans_sum.setVisibility(View.VISIBLE);
			  s_img_trans_sum.setText(sum);
		  }else{
			  s_img_trans_sum.setVisibility(View.INVISIBLE);
		  }
	  }
	/**
	 * 改变样式,需先使用initStyle(ImageView[] image, int[] focus, int[] nofocus)
	 * 
	 * @param id
	 *            第几个文本框,0为只清除样式
	 */
	public void changeStyle(int id) {
		if (pointer >= 0) {
			if (id != pointer) {
				images[pointer].setImageResource(imagesOfNoFocus[pointer]);
				texts[pointer].setTextColor(getResources().getColor(wcolor));
				pointer = id;
				images[pointer].setImageResource(imagesOfFocus[pointer]);
				texts[pointer].setTextColor(getResources().getColor(bcolor));
			}
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.layout_bottom_home:
				// 服务中心
				changeStyle(0);
				if (getActivity() instanceof MainActivity) {
					((MainActivity) getActivity()).replaceFragment(0);
				}
				break;
			case R.id.layout_bottom_share:
				changeStyle(1);
				if (getActivity() instanceof MainActivity) {
					((MainActivity) getActivity()).replaceFragment(1);
				}
					break;

			case R.id.layout_bottom_trans:
						changeStyle(3);
						if (getActivity() instanceof MainActivity) {
							((MainActivity) getActivity()).replaceFragment(2);
						}
						break;

			case R.id.layout_bottom_mine:
				// 我的
				changeStyle(4);
				if (getActivity() instanceof MainActivity) {
					((MainActivity) getActivity()).replaceFragment(3);
				}
				break;
			case R.id.layout_bottom_talk:
				// 回话
				changeStyle(2);
				if (getActivity() instanceof MainActivity) {
					((MainActivity) getActivity()).replaceFragment(4);
				}
				break;


			}
		}
	}

