package com.gdyd.qmwallet.talk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.talk.model.ContactBean;

import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * Created by fx-168 on 17/7/7.
 */

public class ContactAdapter2 extends IndexableAdapter<ContactBean>{


    private LayoutInflater mInflater;

    public ContactAdapter2(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_contact, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, ContactBean entity) {
        ContentVH vh = (ContentVH) holder;
        //vh.tvName.setText(entity.getName());

        String name =  entity.getName();

        if(TextUtils.isEmpty(name)){
            name = "[未实名]";
        }
        vh.tvName.setText(entity.getPhoneNumber()+"   "+ name);

    }


    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tvName;

        public ContentVH(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);

        }
    }


}
