package com.gdyd.qmwallet.home.model;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.mine.model.GradeBean;

import java.util.List;

/**
 * Created by zanzq on 2017/3/5.
 * 升级适配器
 */

public class VpAdapter extends PagerAdapter {
    private Context context;
    private List<GradeBean> list;
    private LayoutInflater inflater;
    private TextView content2;
    private ImageView mq;

    public VpAdapter(Context context, List<GradeBean> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView face;
        TextView name;
        RelativeLayout layout;
        TextView content;
        Button submit;
     View  itemView=inflater.inflate(R.layout.recycler_item,container,false);
        face = ((ImageView) itemView.findViewById(R.id.sj_icon));
        name = ((TextView) itemView.findViewById(R.id.text_dj));
        layout = (RelativeLayout) itemView.findViewById(R.id.layout_tip);
        content = ((TextView) itemView.findViewById(R.id.content));
        submit = ((Button) itemView.findViewById(R.id.submit));
        content2 = ((TextView) itemView.findViewById(R.id.content2));
        mq = ((ImageView) itemView.findViewById(R.id.mq));
        final GradeBean bean = list.get(position);
        int levelvalue = bean.getLevelvalue();
        face.setImageResource(bean.getType());
        content2.setText(bean.getTitle());
        content.setText(bean.getContent());
        switch (bean.getLevel()){
            case 1:
                content.setLineSpacing(2,1.4f);
                break;
            case 2:
                content.setLineSpacing(2,1.3f);
                break;
            case 3:
                content.setLineSpacing(2,1.3f);
                break;
            case 4:
                content.setLineSpacing(2,1.3f);
                break;
            case 5:
                content.setLineSpacing(2,0.9f);
                content.setTextSize(12);
                break;
            case 6:
                content.setLineSpacing(2,0.9f);
                content.setTextSize(11);
                break;
        }
        name.setText(bean.getName());
        boolean current = bean.isCurrent();
        submit.setText(bean.getBName());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onUpgradeClick.onClick(bean.getLevel());
            }
        });
        if (!current){
            submit.setEnabled(true);
        }else{
            submit.setEnabled(false);
        }
            if (bean.getLevel()>3){
                if (bean.getLevel()<=levelvalue){
                    submit.setEnabled(false);
                }
                }



        if (bean.getLevel()==levelvalue){
          //  layout.setVisibility(View.VISIBLE);
            mq.setVisibility(View.VISIBLE);
        }else{
         //   layout.setVisibility(View.GONE);
            mq.setVisibility(View.GONE);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    public interface OnUpgradeClick{
        void onClick(int grade);
    }
    private OnUpgradeClick onUpgradeClick;

    public void setOnUpgradeClick(OnUpgradeClick onUpgradeClick) {
        this.onUpgradeClick = onUpgradeClick;
    }
}
