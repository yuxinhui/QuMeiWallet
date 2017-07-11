package com.gdyd.qmwallet.home.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/3/8.
 */

public class MemberDetailsBean implements Serializable {

    /**
     * Data : {
     "PageCount": 0,
     "TotalCount": 0,
     "Merchant": "[]"
     }
     * nResul : 1
     * sMessage : null
     */

    private String Data;
    private int nResul;
    private Object sMessage;
     private DataBean dataBean;


    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public Object getsMessage() {
        return sMessage;
    }

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setsMessage(Object sMessage) {
        this.sMessage = sMessage;
    }

    public int getnResul() {

        return nResul;
    }

    public void setnResul(int nResul) {
        this.nResul = nResul;
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

    public Object getSMessage() {
        return sMessage;
    }

    public void setSMessage(Object sMessage) {
        this.sMessage = sMessage;
    }
    public static class DataBean implements Serializable{

        /**
         * PageCount : 0
         * TotalCount : 0
         * Merchant : []
         */

        private int PageCount;
        private int TotalCount;
        private String Merchant;
        private ArrayList<MerchantBean> list;

        public ArrayList<MerchantBean> getList() {
            return list;
        }

        public void setList(ArrayList<MerchantBean> list) {
            this.list = list;
        }

        public int getPageCount() {
            return PageCount;
        }

        public void setPageCount(int PageCount) {
            this.PageCount = PageCount;
        }

        public int getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(int TotalCount) {
            this.TotalCount = TotalCount;
        }

        public String getMerchant() {
            return Merchant;
        }

        public void setMerchant(String Merchant) {
            this.Merchant = Merchant;
        }
        public static class MerchantBean implements Serializable{

            /**
             * Direct : 1
             * _m : {"ID":7,"Name":"魏富伦","PhoneNumber":"15018632944","Email":"","CheckState":2,"AgentID":1,"RoyaltyRate":0,"PaymentCode":"http://qm.qiaomeishidai.com:8080/qiaomeiqianbao/order.jsp?mchtNo=QMC0000000005","AddTime":"2017-04-01T15:35:04.117","ManagerName":"魏富伦","Password":"88888888","Province":"5800","City":"5950","DetailAddress":"你好","BarCodeNumber":"QMC0000000005","Type":1,"IDCardNo":"441502199212202338","MerchantNo":"QMC0000000005","OpenID":null,"RecommendNo":"QMD0000000001","RecommendRate":null,"LevelValue":4,"ReserveNumber":null}
             */

            private int Direct;
            private MBean _m;

            public int getDirect() {
                return Direct;
            }

            public void setDirect(int Direct) {
                this.Direct = Direct;
            }

            public MBean get_m() {
                return _m;
            }

            public void set_m(MBean _m) {
                this._m = _m;
            }

            public static class MBean implements Serializable{
                /**
                 * ID : 7
                 * Name : 魏富伦
                 * PhoneNumber : 15018632944
                 * Email :
                 * CheckState : 2
                 * AgentID : 1
                 * RoyaltyRate : 0.0
                 * PaymentCode : http://qm.qiaomeishidai.com:8080/qiaomeiqianbao/order.jsp?mchtNo=QMC0000000005
                 * AddTime : 2017-04-01T15:35:04.117
                 * ManagerName : 魏富伦
                 * Password : 88888888
                 * Province : 5800
                 * City : 5950
                 * DetailAddress : 你好
                 * BarCodeNumber : QMC0000000005
                 * Type : 1
                 * IDCardNo : 441502199212202338
                 * MerchantNo : QMC0000000005
                 * OpenID : null
                 * RecommendNo : QMD0000000001
                 * RecommendRate : null
                 * LevelValue : 4
                 * ReserveNumber : null
                 */

                private int ID;
                private String Name;
                private String PhoneNumber;
                private String Email;
                private int CheckState;
                private int AgentID;
                private double RoyaltyRate;
                private String PaymentCode;
                private String AddTime;
                private String ManagerName;
                private String Password;
                private String Province;
                private String City;
                private String DetailAddress;
                private String BarCodeNumber;
                private int Type;
                private String IDCardNo;
                private String MerchantNo;
                private Object OpenID;
                private String RecommendNo;
                private Object RecommendRate;
                private int LevelValue;
                private Object ReserveNumber;

                public int getID() {
                    return ID;
                }

                public void setID(int ID) {
                    this.ID = ID;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public String getPhoneNumber() {
                    return PhoneNumber;
                }

                public void setPhoneNumber(String PhoneNumber) {
                    this.PhoneNumber = PhoneNumber;
                }

                public String getEmail() {
                    return Email;
                }

                public void setEmail(String Email) {
                    this.Email = Email;
                }

                public int getCheckState() {
                    return CheckState;
                }

                public void setCheckState(int CheckState) {
                    this.CheckState = CheckState;
                }

                public int getAgentID() {
                    return AgentID;
                }

                public void setAgentID(int AgentID) {
                    this.AgentID = AgentID;
                }

                public double getRoyaltyRate() {
                    return RoyaltyRate;
                }

                public void setRoyaltyRate(double RoyaltyRate) {
                    this.RoyaltyRate = RoyaltyRate;
                }

                public String getPaymentCode() {
                    return PaymentCode;
                }

                public void setPaymentCode(String PaymentCode) {
                    this.PaymentCode = PaymentCode;
                }

                public String getAddTime() {
                    return AddTime;
                }

                public void setAddTime(String AddTime) {
                    this.AddTime = AddTime;
                }

                public String getManagerName() {
                    return ManagerName;
                }

                public void setManagerName(String ManagerName) {
                    this.ManagerName = ManagerName;
                }

                public String getPassword() {
                    return Password;
                }

                public void setPassword(String Password) {
                    this.Password = Password;
                }

                public String getProvince() {
                    return Province;
                }

                public void setProvince(String Province) {
                    this.Province = Province;
                }

                public String getCity() {
                    return City;
                }

                public void setCity(String City) {
                    this.City = City;
                }

                public String getDetailAddress() {
                    return DetailAddress;
                }

                public void setDetailAddress(String DetailAddress) {
                    this.DetailAddress = DetailAddress;
                }

                public String getBarCodeNumber() {
                    return BarCodeNumber;
                }

                public void setBarCodeNumber(String BarCodeNumber) {
                    this.BarCodeNumber = BarCodeNumber;
                }

                public int getType() {
                    return Type;
                }

                public void setType(int Type) {
                    this.Type = Type;
                }

                public String getIDCardNo() {
                    return IDCardNo;
                }

                public void setIDCardNo(String IDCardNo) {
                    this.IDCardNo = IDCardNo;
                }

                public String getMerchantNo() {
                    return MerchantNo;
                }

                public void setMerchantNo(String MerchantNo) {
                    this.MerchantNo = MerchantNo;
                }

                public Object getOpenID() {
                    return OpenID;
                }

                public void setOpenID(Object OpenID) {
                    this.OpenID = OpenID;
                }

                public String getRecommendNo() {
                    return RecommendNo;
                }

                public void setRecommendNo(String RecommendNo) {
                    this.RecommendNo = RecommendNo;
                }

                public Object getRecommendRate() {
                    return RecommendRate;
                }

                public void setRecommendRate(Object RecommendRate) {
                    this.RecommendRate = RecommendRate;
                }

                public int getLevelValue() {
                    return LevelValue;
                }

                public void setLevelValue(int LevelValue) {
                    this.LevelValue = LevelValue;
                }

                public Object getReserveNumber() {
                    return ReserveNumber;
                }

                public void setReserveNumber(Object ReserveNumber) {
                    this.ReserveNumber = ReserveNumber;
                }
            }
        }
    }
}
