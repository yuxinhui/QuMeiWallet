package com.gdyd.qmwallet.myview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gdyd.qmwallet.R;
import com.gdyd.qmwallet.mine.model.AreaBean;
import com.gdyd.qmwallet.mine.model.CityBean;
import com.gdyd.qmwallet.mine.model.ProvinceBean;
import com.gdyd.qmwallet.mine.presenter.PwPresenter;
import com.gdyd.qmwallet.mine.view.IUserInfoView;
import com.gdyd.qmwallet.myview.kankan.wheel.widget.OnWheelChangedListener;
import com.gdyd.qmwallet.myview.kankan.wheel.widget.OnWheelScrollListener;
import com.gdyd.qmwallet.myview.kankan.wheel.widget.WheelView;
import com.gdyd.qmwallet.myview.kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.framed.Variant;

/**
 * Created by zanzq on 2017/2/27.
 */

public class ChangeAddressPopwindow extends PopupWindow implements View.OnClickListener,IUserInfoView {

    private WheelView wvProvince;
    private WheelView wvCitys;
    private WheelView wvArea;
    private View lyChangeAddress;
    private View lyChangeAddressChild;
    private TextView btnSure;
    private TextView btnCancel;

    private Context context;
    private JSONArray mJsonObj;
    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市s
     */
//    private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
//    /**
//     * key - 市 values - 区s
//     */
//    private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();

   private PwPresenter presenter=new PwPresenter(this);
    private ArrayList<String> arrProvinces = new ArrayList<String>();
    private ArrayList<String> arrCitys = new ArrayList<String>();
    private ArrayList<String> arrAreas = new ArrayList<String>();
    private AddressTextAdapter provinceAdapter;
    private AddressTextAdapter cityAdapter;
    private AddressTextAdapter areaAdapter;
    private int provinceId;
    private int cityId;
    private String strProvince = "北京市";
    private String strCity = "北京市";
    private String strArea = "密云县";
    private OnAddressCListener onAddressCListener;

    private int maxsize = 14;
    private int minsize = 12;
    private final View view;
    private List<ProvinceBean.DataBean> provinceData;
    private List<AreaBean.DataBean> areaData;
    private List<CityBean.DataBean> cityData;

    public ChangeAddressPopwindow(final Context context) {
        super(context);
        this.context = context;
        view = View.inflate(context, R.layout.edit_changeaddress_pop_layout,null);
        wvProvince = (WheelView) view.findViewById(R.id.wv_address_province);
        wvCitys = (WheelView) view.findViewById(R.id.wv_address_city);
        wvArea = (WheelView) view. findViewById(R.id.wv_address_area);
        lyChangeAddress = view.findViewById(R.id.ly_myinfo_changeaddress);
        lyChangeAddressChild = view.findViewById(R.id.ly_myinfo_changeaddress_child);
        btnSure = (TextView) view.findViewById(R.id.btn_myinfo_sure);
        btnCancel = (TextView) view. findViewById(R.id.btn_myinfo_cancel);
        wvProvince.setWheelBackground(R.color.colorWhite);
        wvCitys.setWheelBackground(R.color.colorWhite);
        wvArea.setWheelBackground(R.color.colorWhite);
        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//      this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.colorGray));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        lyChangeAddressChild.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
         presenter.SearchProvince();
     //  presenter.SearchCity(1200+"");
 //     presenter.SearchArea(1100+"");
    //    initJsonData();
//        initDatas();


       // initProvinces();
//        provinceAdapter = new AddressTextAdapter(context, arrProvinces, getProvinceItem(strProvince), maxsize, minsize);
//        wvProvince.setVisibleItems(5);
//        wvProvince.setViewAdapter(provinceAdapter);
//        wvProvince.setCurrentItem(getProvinceItem(strProvince));
//
//        initCitys(mCitisDatasMap.get(strProvince));
//        cityAdapter = new AddressTextAdapter(context, arrCitys, getCityItem(strCity), maxsize, minsize);
//        wvCitys.setVisibleItems(5);
//        wvCitys.setViewAdapter(cityAdapter);
//        wvCitys.setCurrentItem(getCityItem(strCity));

//        initAreas(mAreaDatasMap.get(strCity));
//        areaAdapter = new AddressTextAdapter(context, arrAreas, getAreaItem(strArea), maxsize, minsize);
//        wvArea.setVisibleItems(5);
//        wvArea.setViewAdapter(areaAdapter);
//        wvArea.setCurrentItem(getAreaItem(strArea));

        wvProvince.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                strProvince = currentText;
                provinceId=provinceData.get(wheel.getCurrentItem()).getID();
                setTextviewSize(currentText, provinceAdapter);

//                String[] citys = mCitisDatasMap.get(currentText);
//            //    initCitys(citys);
//                cityAdapter = new AddressTextAdapter(context, arrCitys, 0, maxsize, minsize);
//                wvCitys.setVisibleItems(5);
//                wvCitys.setViewAdapter(cityAdapter);
//                wvCitys.setCurrentItem(0);
//                setTextviewSize("0", cityAdapter);
//
//                //根据市，地区联动
//                String[] areas = mAreaDatasMap.get(citys[0]);
//              //  initAreas(areas);
//                areaAdapter = new AddressTextAdapter(context, arrAreas, 0, maxsize, minsize);
//                wvArea.setVisibleItems(5);
//                wvArea.setViewAdapter(areaAdapter);
//                wvArea.setCurrentItem(0);
//                setTextviewSize("0", areaAdapter);
            }
        });

        wvProvince.addScrollingListener(new OnWheelScrollListener() {



            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, provinceAdapter);
                ProvinceBean.DataBean dataBean = provinceData.get(wvProvince.getCurrentItem());
                strProvince=dataBean.getVALUE();
                provinceId = dataBean.getID();
                if (IsExtra(dataBean.getVALUE())){
                    cityData = new ArrayList<>();
                    CityBean.DataBean e = new CityBean.DataBean();
                    e.setID(dataBean.getID());
                    e.setVALUE(dataBean.getVALUE());
                    cityData.add(e);
                    initCitys(cityData);
                    cityAdapter = new AddressTextAdapter(context, arrCitys, getCityItem(strCity), maxsize, minsize);
                    wvCitys.setVisibleItems(5);
                    wvCitys.setViewAdapter(cityAdapter);
                    wvCitys.setCurrentItem(getCityItem(strCity));
                    presenter.SearchArea(provinceData.get(wvProvince.getCurrentItem()).getID()+"");
                }else{
                    presenter.SearchCity(provinceData.get(wvProvince.getCurrentItem()).getID()+"");
                }

            }
        });

        wvCitys.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                strCity = currentText;
                cityId=cityData.get(wheel.getCurrentItem()).getID();
//                setTextviewSize(currentText, cityAdapter);
//
//                //根据市，地区联动
//                String[] areas = mAreaDatasMap.get(currentText);
//            //    initAreas(areas);
//                areaAdapter = new AddressTextAdapter(context, arrAreas, 0, maxsize, minsize);
//                wvArea.setVisibleItems(5);
//                wvArea.setViewAdapter(areaAdapter);
//                wvArea.setCurrentItem(0);
//                setTextviewSize("0", areaAdapter);


            }
        });

        wvCitys.addScrollingListener(new OnWheelScrollListener() {



            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, cityAdapter);
                CityBean.DataBean dataBean = cityData.get(wvCitys.getCurrentItem());
                strCity=dataBean.getVALUE();
                cityId = dataBean.getID();
                presenter.SearchArea(dataBean.getID()+"");
            }
        });

        wvArea.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) areaAdapter.getItemText(wheel.getCurrentItem());
                strArea = currentText;
                setTextviewSize(currentText, cityAdapter);
            }
        });

        wvArea.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub

                strArea=  areaData.get(wvArea.getCurrentItem()).getVALUE();
              //  setTextviewSize(currentText, areaAdapter);
            }
        });


    }

    @Override
    public void IProvincePlaceView(ProvinceBean provinceBean) {
        if (provinceBean!=null&&provinceBean.getNResul()==1){
            provinceData = provinceBean.getData();
            initProvinces(provinceData);
            provinceAdapter = new AddressTextAdapter(context, arrProvinces, getProvinceItem(strProvince), maxsize, minsize);
            wvProvince.setVisibleItems(5);
            wvProvince.setViewAdapter(provinceAdapter);
            wvProvince.setCurrentItem(getProvinceItem(strProvince));
            ProvinceBean.DataBean dataBean = provinceData.get(wvProvince.getCurrentItem());
            if (IsExtra(dataBean.getVALUE())){
                cityData = new ArrayList<>();
                CityBean.DataBean e = new CityBean.DataBean();
                e.setID(dataBean.getID());
                e.setVALUE(dataBean.getVALUE());
                cityData.add(e);
                initCitys(cityData);
                cityAdapter = new AddressTextAdapter(context, arrCitys, getCityItem(strCity), maxsize, minsize);
                wvCitys.setVisibleItems(5);
                wvCitys.setViewAdapter(cityAdapter);
                wvCitys.setCurrentItem(getCityItem(strCity));
                presenter.SearchArea(provinceData.get(wvProvince.getCurrentItem()).getID()+"");
            }else{
                presenter.SearchCity(provinceData.get(wvProvince.getCurrentItem()).getID()+"");
            }
        }else{
            Toast.makeText(context, "网络不好，请稍后再试", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void IAreaPlaceView(AreaBean areaBean) {
      if (areaBean!=null&&areaBean.getNResul()==1){
          areaData = areaBean.getData();
          initAreas(areaData);
          areaAdapter = new AddressTextAdapter(context, arrAreas, getAreaItem(strArea), maxsize, minsize);
          wvArea.setVisibleItems(5);
          wvArea.setViewAdapter(areaAdapter);
          wvArea.setCurrentItem(getAreaItem(strArea));
      }

    }

    @Override
    public void ICityPlaceView(CityBean cityBean) {
       if (cityBean!=null&&cityBean.getNResul()==1){
           cityData = cityBean.getData();
           initCitys(cityData);
           cityAdapter = new AddressTextAdapter(context, arrCitys, getCityItem(strCity), maxsize, minsize);
           wvCitys.setVisibleItems(5);
           wvCitys.setViewAdapter(cityAdapter);
           wvCitys.setCurrentItem(getCityItem(strCity));
           presenter.SearchArea(cityData.get(wvCitys.getCurrentItem()).getID()+"");
       }
    }


    private class AddressTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;
        private  int currentItem;
        private int maxsize;
        private int minsize;
    //    ArrayList<TextView> listview=new ArrayList<>();
        protected AddressTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE);
            this.list = list;
            this.currentItem=currentItem;
            this.maxsize=maxsize;
            this.minsize=minsize;
            setItemTextResource(R.id.country_name);
        }


        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view =  super.getItem(index, cachedView, parent);

            return view;
        }



        @Override
        public TextView getTextView(View view, int textResource) {
            TextView textView = super.getTextView(view, textResource);
            return textView;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }
        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
//        ArrayList<TextView> listview = adapter.getListview();
//        for (int i = 0; i < listview.size(); i++) {
//                        TextView textvew =listview.get(i);
//            String currentText = textvew.getText().toString();
//            if (curriteItemText.equals(currentText)) {
//               // textvew.setTextSize(14);
//                textvew.setTextColor(context.getResources().getColor(R.color.colorBlack));
//            } else {
//                textvew.setTextColor(context.getResources().getColor(R.color.blue));
//               // textvew.setTextSize(12);
//            }
//        }

        //   ArrayList<View> arrayList = adapter.getTextSize();
//        int size = adapter.getItemsCount();
//        String currentText;
//        for (int i = 0; i < size; i++) {
//            ViewGroup p= new ViewGroup(context) {
//                @Override
//                protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//                }
//            };
//
//            TextView textvew = (TextView) adapter.getItem(i,null,null);
//            currentText = textvew.getText().toString();
//            if (curriteItemText.equals(currentText)) {
//                textvew.setTextSize(14);
//            } else {
//                textvew.setTextSize(12);
//            }
//        }
    }

    public void setAddresskListener(OnAddressCListener onAddressCListener) {
        this.onAddressCListener = onAddressCListener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnSure) {
            if (onAddressCListener != null) {
                onAddressCListener.onClick(strProvince, strCity,strArea,provinceId,cityId);
            }
        } else if (v == btnCancel) {

        } else if (v == lyChangeAddressChild) {
            return;
        } else {
//          dismiss();
        }
        dismiss();
    }

    /**
     * 回调接口
     *
     * @author Administrator
     *
     */
    public interface OnAddressCListener {
        public void onClick(String province, String city, String area,int p,int c);
    }

    /**
     * 从文件中读取地址数据
     */
    private void initJsonData() {
        try {
            String line="";
            StringBuffer sb = new StringBuffer();
            InputStreamReader is =  new InputStreamReader( context.getResources().getAssets().open("city.json") );
            BufferedReader bufReader = new BufferedReader(is);
            byte[] buf = new byte[1024];
            while ((line = bufReader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            mJsonObj = new JSONArray(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
//    private void initDatas()
//    {
//        try
//        {
//
//            mProvinceDatas = new String[mJsonObj.length()];
//            for (int i = 0; i < mJsonObj.length(); i++)
//            {
//                JSONObject jsonP = mJsonObj.getJSONObject(i);// 每个省的json对象
//                String province = jsonP.getString("name");// 省名字
//
//                mProvinceDatas[i] = province;
//
//                JSONArray jsonCs = null;
//                try
//                {
//                    /**
//                     * Throws JSONException if the mapping doesn't exist or is
//                     * not a JSONArray.
//                     */
//                    jsonCs = jsonP.getJSONArray("city");
//                } catch (Exception e1)
//                {
//                    continue;
//                }
//                String[] mCitiesDatas = new String[jsonCs.length()];
//                for (int j = 0; j < jsonCs.length(); j++)
//                {
//                    JSONObject jsonCity = jsonCs.getJSONObject(j);
//                    String city = jsonCity.getString("name");// 市名字
//                    mCitiesDatas[j] = city;
//                    JSONArray jsonAreas = null;
//                    try
//                    {
//                        /**
//                         * Throws JSONException if the mapping doesn't exist or
//                         * is not a JSONArray.
//                         */
//                        jsonAreas = jsonCity.getJSONArray("area");
//                    } catch (Exception e)
//                    {
//                        continue;
//                    }
//
//                    String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
//                    for (int k = 0; k < jsonAreas.length(); k++)
//                    {
//                        String area = jsonAreas.getString(k);// 区域的名称
//                        mAreasDatas[k] = area;
//                    }
//                    mAreaDatasMap.put(city, mAreasDatas);
//                }
//
//                mCitisDatasMap.put(province, mCitiesDatas);
//            }
//
//        } catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
//        mJsonObj = null;
//    }

    /**
     * 初始化省会
     * @param data
     */
    public void initProvinces(List<ProvinceBean.DataBean> data) {
        int length = data.size();
        for (int i = 0; i < length; i++) {
            arrProvinces.add(data.get(i).getVALUE().trim());
        }
    }

    /**
     * 根据省会，生成该省会的所有城市
     *
     * @param citys
     *
     */
    public void initCitys(List<CityBean.DataBean> citys) {
        if (citys != null) {
            arrCitys.clear();
            int length = citys.size();
            for (int i = 0; i < length; i++) {
                arrCitys.add(citys.get(i).getVALUE().trim());
            }
//        } else {
//            String[] city = mCitisDatasMap.get("广东");
//            arrCitys.clear();
//            int length = city.length;
//            for (int i = 0; i < length; i++) {
//                arrCitys.add(city[i]);
//            }
      }
        if (arrCitys != null && arrCitys.size() > 0
                && !arrCitys.contains(strCity)) {
            strCity = arrCitys.get(0);
            cityId=citys.get(0).getID();
        }
    }

    /**
     * 根据城市，生成该城市的所有地区
     *
     * @param areas
     * @param areas
     */
    public void initAreas(List<AreaBean.DataBean> areas) {
        if (areas != null) {
            arrAreas.clear();
            int length = areas.size();
            for (int i = 0; i < length; i++) {
                arrAreas.add(areas.get(i).getVALUE().trim());
            }
//        } else {
//            String[] area = mAreaDatasMap.get("深圳");
//            arrAreas.clear();
//            int length = area.length;
//            for (int i = 0; i < length; i++) {
//                arrAreas.add(area[i]);
//            }
        }
        if (arrAreas != null && arrAreas.size() > 0
                && !arrAreas.contains(strArea)) {
            strArea = arrAreas.get(0);
        }
    }

    /**
     * 初始化地点
     *
     * @param province
     * @param city
     */
    public void setAddress(String province, String city, String area) {
        if (province != null && province.length() > 0) {
            this.strProvince = province;
        }
        if (city != null && city.length() > 0) {
            this.strCity = city;
        }

        if (area != null && area.length() > 0) {
            this.strArea = area;
        }
    }

    /**
     * 返回省会索引，没有就返回默认“广东”
     *
     * @param province
     * @return
     */
    public int getProvinceItem(String province) {
        int size = arrProvinces.size();
        int provinceIndex = 0;
        boolean noprovince = true;
        for (int i = 0; i < size; i++) {
            if (province.equals(arrProvinces.get(i))) {
                noprovince = false;
                provinceId=provinceData.get(i).getID();
                return provinceIndex;
            } else {
                provinceIndex++;
            }
        }
        if (noprovince) {
            strProvince = "北京市 ";
            return 0;
        }
        return provinceIndex;
    }

    /**
     * 得到城市索引，没有返回默认“深圳”
     *
     * @param city
     * @return
     */
    public int getCityItem(String city) {
        int size = arrCitys.size();
        int cityIndex = 0;
        boolean nocity = true;
        for (int i = 0; i < size; i++) {
            System.out.println(arrCitys.get(i));
            if (city.equals(arrCitys.get(i))) {
                nocity = false;
                cityId=cityData.get(i).getID();
                return cityIndex;
            } else {
                cityIndex++;
            }
        }
        if (nocity) {
            strCity = "深圳";
            return 2;
        }
        return cityIndex;
    }

    /**
     * 得到地区索引，没有返回默认“福田区”
     *
     * @param area
     * @return
     */
    public int getAreaItem(String area) {
        int size = arrAreas.size();
        int areaIndex = 0;
        boolean noarea = true;
        for (int i = 0; i < size; i++) {
            System.out.println(arrAreas.get(i));
            if (area.equals(arrAreas.get(i))) {
                noarea = false;
                return areaIndex;
            } else {
                areaIndex++;
            }
        }
        if (noarea) {
            strArea = "";
            return 1;
        }
        return areaIndex;
    }
   private boolean IsExtra(String name){
       name=name.trim();
       if (name.equals("北京市")||name.equals("天津市")||name.equals("上海市")||name.equals("重庆市")){
           return true;
       }
       return false;
   }

}