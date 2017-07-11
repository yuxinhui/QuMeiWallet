package com.gdyd.qmwallet.talk;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.talk.model.ContactBean;

import java.util.ArrayList;



/**
 * Created by YoKey on 16/10/7.
 */
public class ContactAdapter extends BaseAdapter {

    private ArrayList<ContactBean> list;
    private LayoutInflater inflater;

    private Context  mContext;

    public ContactAdapter(ArrayList<ContactBean> list, Context context) {
        this.list = list;
        this.mContext = context;
        inflater=LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return list.size()>0?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViedHolder holder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_contact,viewGroup,false);
            holder= new ViedHolder();
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder= (ViedHolder) convertView.getTag();
        }

        String name = list.get(position).getName();

        if(TextUtils.isEmpty(name) || "null".equals(name)){
            name = "[未实名]";
        }
        holder.tv_name.setText(list.get(position).getPhoneNumber()+"   "+name);

        return convertView;
    }



    class ViedHolder{
        ImageView face;
        TextView tv_name;
    }


}
