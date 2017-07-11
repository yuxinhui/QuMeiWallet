package com.gdyd.qmwallet.talk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdyd.qmwallet.App;
import com.gdyd.qmwallet.BaseFragment;
import com.gdyd.qmwallet.Other.model.LoginInfoBean;
import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.config.APPConfig;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;


/**
 * Created by fx-168 on 17/7/6.
 */

public class TalkFragment extends BaseFragment{
    private View view;

    TextView image_search;

    private ConversationListFragment fragment;

    private String merchantNO;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_talk, container, false);
        }


        fragment = (ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.conversationlist);


       // EventBus.getDefault().register(this);

        image_search = (TextView) view.findViewById(R.id.image_search);


        bean = ((LoginInfoBean) getActivity().getIntent().getSerializableExtra(APPConfig.LOGIN_INFO));

        if(bean!=null){
            String token = bean.getUserData().getMerchant().getToken();
            merchantNO = bean.getUserData().getMerchant().getMerchantNo();
            Log.e("onSuccess","token="+token);
            //reconnect(token);

            enterFragment();
        }


        image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ContactActivity.class);
                intent.putExtra("merchantNO",merchantNO);
                getActivity().startActivity(intent);
            }
        });


        return  view;
    }



//    @Subscribe(threadMode = ThreadMode.MainThread)
//    public void getConnentEvent(ConnentEvent event) {
//
//        Log.e("TAG","flag=="+event.getConnFlag());
//        if("1".equals(event.getConnFlag())){
//            enterFragment();
//        }else{
//
//        }
//    }



    /**
     * 加载 会话列表 ConversationListFragment
     */
    private void enterFragment() {
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);
    }



    private void connect(String token) {



            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("onSuccess", "--onSuccess" + userid);

                }

                /**
                 * 连接融云失败
                 * @param  ，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("onSuccess", "--onSuccess" + errorCode);
                }
            });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (bean==null){
            checkBean();
        }
    }

    /**
     * 重连
     *
     * @param token
     */
    private void reconnect(String token) {
        if (getActivity().getApplicationInfo().packageName.equals(App.getCurProcessName(getActivity()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {
                    Log.e("onSuccess","userId="+s);
                    enterFragment();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e("onSuccess","errorCode="+errorCode);
                }
            });
        }
    }








}
