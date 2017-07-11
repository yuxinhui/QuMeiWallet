package com.gdyd.qmwallet.friends;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zanzq on 2017/5/4.
 */

public class FriendDataBean implements Serializable{

    /**
     * Data : {
     "PageCount": 1,
     "GraphicSharing": "[{\"GraphicSharing\":{\"ID\":5,\"MerchantID\":9,\"State\":1,\"Text\":\"这\",\"Title\":\"\"},\"ImageList\":[\"Merchant\\/2017\\/5\\/4\\/9\\/Merchant20175411140_1493866901.jpg\"]},{\"GraphicSharing\":{\"ID\":4,\"MerchantID\":2,\"State\":1,\"Text\":\"\",\"Title\":\"你好\"},\"ImageList\":[]},{\"GraphicSharing\":{\"ID\":3,\"MerchantID\":2,\"State\":1,\"Text\":\"\",\"Title\":\"你好\"},\"ImageList\":[]},{\"GraphicSharing\":{\"ID\":2,\"MerchantID\":2,\"State\":1,\"Text\":\"\",\"Title\":\"你好\"},\"ImageList\":[]},{\"GraphicSharing\":{\"ID\":1,\"MerchantID\":2,\"State\":1,\"Text\":\"\",\"Title\":\"你好\"},\"ImageList\":[]}]"
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

    public static  class DataBean implements Serializable{

        /**
         * PageCount : 1
         * GraphicSharing : [{"GraphicSharing":{"ID":5,"MerchantID":9,"State":1,"Text":"这","Title":""},"ImageList":["Merchant\/2017\/5\/4\/9\/Merchant20175411140_1493866901.jpg"]},{"GraphicSharing":{"ID":4,"MerchantID":2,"State":1,"Text":"","Title":"你好"},"ImageList":[]},{"GraphicSharing":{"ID":3,"MerchantID":2,"State":1,"Text":"","Title":"你好"},"ImageList":[]},{"GraphicSharing":{"ID":2,"MerchantID":2,"State":1,"Text":"","Title":"你好"},"ImageList":[]},{"GraphicSharing":{"ID":1,"MerchantID":2,"State":1,"Text":"","Title":"你好"},"ImageList":[]}]
         */

        private int PageCount;
        private String GraphicSharing;
        private ArrayList<GraphicSharing2> graphicSharing2;


        public ArrayList<GraphicSharing2> getGraphicSharing2() {
            return graphicSharing2;
        }

        public void setGraphicSharing2(ArrayList<GraphicSharing2> graphicSharing2) {
            this.graphicSharing2 = graphicSharing2;
        }

        public static class GraphicSharing2 implements Serializable{

             /**
              * GraphicSharing : {"ID":5,"MerchantID":9,"State":1,"Text":"这","Title":""}
              * ImageList : ["Merchant/2017/5/4/9/Merchant20175411140_1493866901.jpg"]
              */

             private GraphicSharingBean GraphicSharing;
             private List<String> ImageList;

             public GraphicSharingBean getGraphicSharing() {
                 return GraphicSharing;
             }

             public void setGraphicSharing(GraphicSharingBean GraphicSharing) {
                 this.GraphicSharing = GraphicSharing;
             }

             public List<String> getImageList() {
                 return ImageList;
             }

             public void setImageList(List<String> ImageList) {
                 this.ImageList = ImageList;
             }

             public static class GraphicSharingBean implements Serializable {
                 /**
                  * ID : 5
                  * MerchantID : 9
                  * State : 1
                  * Text : 这
                  * Title :
                  */

                 private int ID;
                 private int MerchantID;
                 private int State;
                 private String Text;
                 private String Title;
                 private String SendTime;

                 public String getSendTime() {
                     return SendTime;
                 }

                 public void setSendTime(String sendTime) {
                     SendTime = sendTime;
                 }

                 public int getID() {
                     return ID;
                 }

                 public void setID(int ID) {
                     this.ID = ID;
                 }

                 public int getMerchantID() {
                     return MerchantID;
                 }

                 public void setMerchantID(int MerchantID) {
                     this.MerchantID = MerchantID;
                 }

                 public int getState() {
                     return State;
                 }

                 public void setState(int State) {
                     this.State = State;
                 }

                 public String getText() {
                     return Text;
                 }

                 public void setText(String Text) {
                     this.Text = Text;
                 }

                 public String getTitle() {
                     return Title;
                 }

                 public void setTitle(String Title) {
                     this.Title = Title;
                 }
             }
         }
        public int getPageCount() {
            return PageCount;
        }

        public void setPageCount(int PageCount) {
            this.PageCount = PageCount;
        }

        public String getGraphicSharing() {
            return GraphicSharing;
        }

        public void setGraphicSharing(String GraphicSharing) {
            this.GraphicSharing = GraphicSharing;
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
