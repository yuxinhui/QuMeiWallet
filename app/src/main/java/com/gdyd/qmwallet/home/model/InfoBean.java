package com.gdyd.qmwallet.home.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zanzq on 2017/3/15.
 * 消息实体类
 */

public class InfoBean implements Serializable{

    /**
     * Data : {"nPageCount":1,"smsPushDetail":[{"ID":"9dd19013-3d30-4909-8c13-9b7842ed8030","IsNotifyAll":0,"MerchantNo":"SMD0000004122","MessContent":"尊敬的用户，您好！因配合银行上游调整结算业务，微信实时清算时间暂时调整为早上9:30-晚上22:00，其余时间段交易为次工作日清算到账，日切时间为23点，如23点后交易，将于第二个工作日清算到账，给您带来不便之处敬请谅解。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ","MessTitle":"2017.3.15清算调整","NotifyTime":"2017-03-15 09:23:36","PhoneNo":"13542761814"}]}
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

    public static class DataBean implements Serializable{

        /**
         * nPageCount : 1
         * smsPushDetail : [{"ID":"9dd19013-3d30-4909-8c13-9b7842ed8030","IsNotifyAll":0,"MerchantNo":"SMD0000004122","MessContent":"尊敬的用户，您好！因配合银行上游调整结算业务，微信实时清算时间暂时调整为早上9:30-晚上22:00，其余时间段交易为次工作日清算到账，日切时间为23点，如23点后交易，将于第二个工作日清算到账，给您带来不便之处敬请谅解。 ","MessTitle":"2017.3.15清算调整","NotifyTime":"2017-03-15 09:23:36","PhoneNo":"13542761814"}]
         */

        private int nPageCount;
        private List<SmsPushDetailBean> smsPushDetail;

        public int getNPageCount() {
            return nPageCount;
        }

        public void setNPageCount(int nPageCount) {
            this.nPageCount = nPageCount;
        }

        public List<SmsPushDetailBean> getSmsPushDetail() {
            return smsPushDetail;
        }

        public void setSmsPushDetail(List<SmsPushDetailBean> smsPushDetail) {
            this.smsPushDetail = smsPushDetail;
        }

        public static class SmsPushDetailBean implements Serializable {
            /**
             * ID : 9dd19013-3d30-4909-8c13-9b7842ed8030
             * IsNotifyAll : 0
             * MerchantNo : SMD0000004122
             * MessContent : 尊敬的用户，您好！因配合银行上游调整结算业务，微信实时清算时间暂时调整为早上9:30-晚上22:00，其余时间段交易为次工作日清算到账，日切时间为23点，如23点后交易，将于第二个工作日清算到账，给您带来不便之处敬请谅解。
             * MessTitle : 2017.3.15清算调整
             * NotifyTime : 2017-03-15 09:23:36
             * PhoneNo : 13542761814
             */

            private String ID;
            private int IsNotifyAll;
            private String MerchantNo;
            private String MessContent;
            private String MessTitle;
            private String NotifyTime;
            private String PhoneNo;
            private int SMSPushID;

            public int getSMSPushID() {
                return SMSPushID;
            }

            public void setSMSPushID(int SMSPushID) {
                this.SMSPushID = SMSPushID;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public int getIsNotifyAll() {
                return IsNotifyAll;
            }

            public void setIsNotifyAll(int IsNotifyAll) {
                this.IsNotifyAll = IsNotifyAll;
            }

            public String getMerchantNo() {
                return MerchantNo;
            }

            public void setMerchantNo(String MerchantNo) {
                this.MerchantNo = MerchantNo;
            }

            public String getMessContent() {
                return MessContent;
            }

            public void setMessContent(String MessContent) {
                this.MessContent = MessContent;
            }

            public String getMessTitle() {
                return MessTitle;
            }

            public void setMessTitle(String MessTitle) {
                this.MessTitle = MessTitle;
            }

            public String getNotifyTime() {
                return NotifyTime;
            }

            public void setNotifyTime(String NotifyTime) {
                this.NotifyTime = NotifyTime;
            }

            public String getPhoneNo() {
                return PhoneNo;
            }

            public void setPhoneNo(String PhoneNo) {
                this.PhoneNo = PhoneNo;
            }
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
