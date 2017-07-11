package com.gdyd.qmwallet.home.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/4/6.
 */

public class VideoBean implements Serializable {

    /**
     * Data : {
     "Video": "[{\"AddTime\":null,\"Class\":1,\"Describe\":\"宣传\",\"ID\":1,\"ImgUrl\":null,\"Title\":\"产品宣传\",\"VidelUrl\":\"http:\\/\\/106.15.45.237\\/Video\\/4fbc061bfc4627efb4f4c9fe7937fc1c.mp4\"}]",
     "PageCount": 1
     }
     * nResul : 1
     * sMessage : null
     */

    private String Data;
    private int nResul;
    private String sMessage;
    private DataBean dataBean;

    public int getnResul() {
        return nResul;
    }

    public void setnResul(int nResul) {
        this.nResul = nResul;
    }

    public String getsMessage() {
        return sMessage;
    }

    public void setsMessage(String sMessage) {
        this.sMessage = sMessage;
    }

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public static class DataBean implements Serializable {

         /**
          * Video : [{"AddTime":null,"Class":1,"Describe":"宣传","ID":1,"ImgUrl":null,"Title":"产品宣传","VidelUrl":"http:\/\/106.15.45.237\/Video\/4fbc061bfc4627efb4f4c9fe7937fc1c.mp4"}]
          * PageCount : 1
          */

         private String Video;
         private int PageCount;
        private ArrayList<Video> list;

        public ArrayList<DataBean.Video> getList() {
            return list;
        }

        public void setList(ArrayList<DataBean.Video> list) {
            this.list = list;
        }

        public static class Video implements Serializable {

             /**
              * AddTime : null
              * Class : 1
              * Describe : 宣传
              * ID : 1
              * ImgUrl : null
              * Title : 产品宣传
              * VidelUrl : http://106.15.45.237/Video/4fbc061bfc4627efb4f4c9fe7937fc1c.mp4
              */

             private String AddTime;
         //    private String Class;
             private String Describe;
             private int ID;
             private String ImgUrl;
             private String Title;
             private String VidelUrl;

             public String getAddTime() {
                 return AddTime;
             }

             public void setAddTime(String AddTime) {
                 this.AddTime = AddTime;
             }

//             public String getClass() {
//                 return Class;
//             }
//
//             public void setClass(int Class) {
//                 this.Class = Class;
//             }

             public String getDescribe() {
                 return Describe;
             }

             public void setDescribe(String Describe) {
                 this.Describe = Describe;
             }

             public int getID() {
                 return ID;
             }

             public void setID(int ID) {
                 this.ID = ID;
             }

             public String getImgUrl() {
                 return ImgUrl;
             }

             public void setImgUrl(String ImgUrl) {
                 this.ImgUrl = ImgUrl;
             }

             public String getTitle() {
                 return Title;
             }

             public void setTitle(String Title) {
                 this.Title = Title;
             }

             public String getVidelUrl() {
                 return VidelUrl;
             }

             public void setVidelUrl(String VidelUrl) {
                 this.VidelUrl = VidelUrl;
             }
         }
         public String getVideo() {
             return Video;
         }

         public void setVideo(String Video) {
             this.Video = Video;
         }

         public int getPageCount() {
             return PageCount;
         }

         public void setPageCount(int PageCount) {
             this.PageCount = PageCount;
         }
     }
    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public int getNResul() {
        return nResul;
    }

    public void setNResul(int nResul) {
        this.nResul = nResul;
    }

    public String getSMessage() {
        return sMessage;
    }

    public void setSMessage(String sMessage) {
        this.sMessage = sMessage;
    }
}
