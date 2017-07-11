package com.gdyd.qmwallet.home.view;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.BaseFragment;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.home.PlayActivity;
import com.gdyd.qmwallet.home.model.MemberDetailsBean;
import com.gdyd.qmwallet.home.model.VideoBean;
import com.gdyd.qmwallet.home.presenter.VideoPresenter;
import com.gdyd.qmwallet.mine.model.PlaceBean;
import com.gdyd.qmwallet.utils.EncryptionHelper;
import com.gdyd.qmwallet.utils.Is;
import com.gdyd.qmwallet.utils.T;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by zanzq on 2017/4/5.
 */

public class VideoFragment extends BaseFragment implements IVideoView {
    private int page=1;
    private final int pageSize = 30;
    private View view;
    private VideoPresenter videoPresenter=new VideoPresenter(this);
    private PullToRefreshListView listview;
    private TextView count;
    private int type;
    private SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    private String lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
     private ArrayList<VideoBean.DataBean.Video> videoList =new ArrayList<>();
    private VideoAdapter adapter;
    public static VideoFragment getInstance(int type) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        type = arguments.getInt("type",1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.fm_puto_list, container, false);
        }

        listview = ((PullToRefreshListView) view.findViewById(R.id.listView));
        count = ((TextView) view.findViewById(R.id.count));
        count.setVisibility(View.GONE);
        listview.setMode(PullToRefreshBase.Mode.BOTH);// 两端刷新
        initPull();
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                long transTyp = EncryptionHelper.getDate();
                String transTy="1059"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                videoPresenter.getVideoInfo(new PlaceBean("1059",transKe,pageSize,page,type,transTyp,APPConfig.AgentID));
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                lastUpdateTime = dateFormat2.format(new Date(System.currentTimeMillis()));
                initPull();
                long transTyp = EncryptionHelper.getDate();
                String transTy="1059"+transTyp+"";
                String transKe = EncryptionHelper.md5(transTy);
                videoPresenter.getVideoInfo(new PlaceBean("1059",transKe,pageSize,page,type,transTyp,APPConfig.AgentID));
              //  videoPresenter.getVideoInfo(new PlaceBean("1059","68370AAB0D0934D5A23C6B0E434615B0",pageSize,page,type));
            }
        });
        long transTyp = EncryptionHelper.getDate();
        String transTy="1059"+transTyp+"";
        String transKe = EncryptionHelper.md5(transTy);
        videoPresenter.getVideoInfo(new PlaceBean("1059",transKe,pageSize,page,type,transTyp,APPConfig.AgentID));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), PlayActivity.class).putExtra("url",videoList.get(position-1).getVidelUrl()));
            }
        });
        return view;
    }

    @Override
    public void getVideoBack(VideoBean videoBean) {
        if (!isAdded()){
            return;
        }
        listview.onRefreshComplete();
        if (videoBean!=null&&videoBean.getNResul()==1){
            ArrayList<VideoBean.DataBean.Video> list =  videoBean.getDataBean().getList();
            int nPageCount = videoBean.getDataBean().getPageCount();
            if (page==nPageCount){
                T.showMessage(getActivity(), "当前是最后一页",2000);
                //   Toast.makeText(getActivity(), "当前是最后一页", Toast.LENGTH_SHORT).show();
            }else if (page>nPageCount){
                if (nPageCount==0){
                    nPageCount=1;
                }
                page=nPageCount;
                videoList.clear();
                if (Is.isNoEmptyAll(adapter)){
                   adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            if (list!=null&&list.size()>0){

                videoList.clear();
                videoList.addAll(list);
                if (!Is.isNoEmptyAll(adapter)){
                    adapter = new VideoAdapter(videoList, getActivity());
                    listview.setAdapter(adapter);
                } else{
                    adapter.notifyDataSetChanged();
                }
            }else{
                videoList.clear();
                if (Is.isNoEmptyAll(adapter)){
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "没有查询到相关信息", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(getActivity(), "没有更多了..", Toast.LENGTH_SHORT).show();
        }
    }

    class VideoAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<VideoBean.DataBean.Video> list;
        private LayoutInflater inflater;
        private int [] icon={R.drawable.icon_jp,R.drawable.icon_zs,R.drawable.icon_zz,R.drawable.icon_dl,R.drawable.icon_hhr,R.drawable.icon_gd};

        public VideoAdapter(ArrayList<VideoBean.DataBean.Video> list, Context context) {
            this.list = list;
            this.context = context;
            inflater=LayoutInflater.from(context);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViedHolder holder;
            if (convertView==null){
                convertView=inflater.inflate(R.layout.video_item,parent,false);
                holder=new ViedHolder();
                holder.title= (TextView) convertView.findViewById(R.id.title);
                holder.face= (ImageView) convertView.findViewById(R.id.tip_img);
                holder.time= (TextView) convertView.findViewById(R.id.time);
                holder.content= (TextView) convertView.findViewById(R.id.content);
                convertView.setTag(holder);
            }else{
                holder= (ViedHolder) convertView.getTag();
            }
            VideoBean.DataBean.Video video = list.get(position);
            holder.title.setText(video.getTitle());
            holder.time.setText("发表时间:"+video.getAddTime());
            holder.content.setText(video.getDescribe());
            String imgUrl = video.getImgUrl();
            if (imgUrl!=null&&!imgUrl.equals("")){
                Picasso.with(context).load(imgUrl).into(holder.face);
            }else{
                try {
                    MediaMetadataRetriever media = new MediaMetadataRetriever();
                    String videlUrl = video.getVidelUrl();
                    Log.d("zanZQ", "getView: "+videlUrl);
                    media.setDataSource(videlUrl,new HashMap<String, String>());
                    holder.face.setImageBitmap(media.getFrameAtTime());
                    media.release();
                    if (media!=null){
                        media=null;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

            return convertView;
        }
        class ViedHolder{
            ImageView face;
            TextView title,content,time;
        }
    }
    private void initPull() {
        ILoadingLayout layoutProxy = listview.getLoadingLayoutProxy(true,false);
        layoutProxy.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        layoutProxy.setPullLabel("下拉刷新");
        layoutProxy.setRefreshingLabel("正在加载数据");
        layoutProxy.setReleaseLabel("手指释放刷新数据");
        ILoadingLayout endLayout  = listview.getLoadingLayoutProxy(false,true);
        endLayout.setLastUpdatedLabel("上次刷新时间:" + lastUpdateTime);
        endLayout.setPullLabel("上拉刷新");
        endLayout.setRefreshingLabel("正在加载数据");
        endLayout.setReleaseLabel("手指释放刷新数据");
    }
}
