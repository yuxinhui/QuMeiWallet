package com.gdyd.qmwallet.mine.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdyd.qmwallet.R;

import java.util.List;

/**
 * Created by zanzq on 2017/3/3.
 */

public class RVAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private List<GradeBean> list;

    public RVAdapter(Context context, List<GradeBean> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.recycler_item,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        GradeBean bean = list.get(position);
        myViewHolder.face.setImageResource(bean.getType());
        myViewHolder.content.setText(bean.getContent());
        myViewHolder.name.setText(bean.getName());
        boolean current = bean.isCurrent();
        myViewHolder.submit.setText(bean.getBName());
        if (current){
            myViewHolder.layout.setVisibility(View.VISIBLE);
          myViewHolder.submit.setEnabled(true);
        }else{
            myViewHolder.layout.setVisibility(View.GONE);
            myViewHolder.submit.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView face;
        final TextView name;
        private final RelativeLayout layout;
        private final TextView content;
        private final Button submit;

        public MyViewHolder(View itemView) {
            super(itemView);
            face = ((ImageView) itemView.findViewById(R.id.sj_icon));
            name = ((TextView) itemView.findViewById(R.id.text_dj));
            layout = (RelativeLayout) itemView.findViewById(R.id.layout_tip);
            content = ((TextView) itemView.findViewById(R.id.content));
            submit = ((Button) itemView.findViewById(R.id.submit));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
