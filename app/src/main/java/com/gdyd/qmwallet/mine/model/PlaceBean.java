package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/2/28.
 */

public class PlaceBean implements Serializable {
    private String TransType;
    private String TransKey;
    private String ID;
    private int PageSize;
    private int CurPage;
    private String CityName;
    private String BankCode;
    private String AeeaName;
    private String Type;
    private String OperaType;
    private String PhoneNumber;
    private String Code;
    private String PhoneNo;
    private String NewPwd;
   private String OldPwd;
    private String UserName;
    private String Pwd;
    private String AppType;
   private int Level;
    private int CheckState;
    private String MerchantNo;
    private String StartDate;
    private String EndDate;
    private double PostMoney;
    private String MerchantName;
    private String RechargeMerNo;
    private int Source;
    private String BankName;
    private int Class;
    private String PayType;
    private int PayClass;
    private ArrayList<String> ImageBuf;
    private GraphicSharingbean GraphicSharing;
    private int Direct;
    private int AgentID;
    private long TrasnTimeSpan;
    private String IDCardNo;
    private String CardNo;
    private int CardType;

    public PlaceBean(String transType, String transKey, long trasnTimeSpan, String cardNo, String IDCardNo, String phoneNo, String merchantName,int cardType) {
        TransType = transType;
        TransKey = transKey;
        TrasnTimeSpan = trasnTimeSpan;
        CardNo = cardNo;
        this.IDCardNo = IDCardNo;
        PhoneNo = phoneNo;
        MerchantName = merchantName;
        CardType=cardType;
    }

    public PlaceBean(String transType, String transKey, long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        TrasnTimeSpan = trasnTimeSpan;

    }
    public PlaceBean(String transType, String transKey, long trasnTimeSpan,int agentID) {
        TransType = transType;
        TransKey = transKey;
        TrasnTimeSpan = trasnTimeSpan;
        AgentID=agentID;
    }

    public PlaceBean(String transType, String transKey, ArrayList<String> imageBuf, GraphicSharingbean graphicSharing,long trasnTimeSpan,int agentID) {
        TransType = transType;
        TransKey = transKey;
        ImageBuf = imageBuf;
        GraphicSharing = graphicSharing;
        TrasnTimeSpan = trasnTimeSpan;
        AgentID =agentID;
    }

    public ArrayList<String> getImageBuf() {
        return ImageBuf;
    }

    public void setImageBuf(ArrayList<String> imageBuf) {
        ImageBuf = imageBuf;
    }

    public GraphicSharingbean getGraphicSharing() {
        return GraphicSharing;
    }

    public void setGraphicSharing(GraphicSharingbean graphicSharing) {
        GraphicSharing = graphicSharing;
    }

    public int getPayClass() {
        return PayClass;
    }

    public void setPayClass(int payClass) {
        PayClass = payClass;
    }

    public static class GraphicSharingbean implements Serializable{

        /**
         * MerchantID : 1
         * Title :
         * Text :
         */

        private int MerchantID;
        private String Title;
        private String Text;

        public GraphicSharingbean(int merchantID, String title, String text) {
            MerchantID = merchantID;
            Title = title;
            Text = text;
        }

        public int getMerchantID() {
            return MerchantID;
        }

        public void setMerchantID(int MerchantID) {
            this.MerchantID = MerchantID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getText() {
            return Text;
        }

        public void setText(String Text) {
            this.Text = Text;
        }
    }

    public PlaceBean(String transType, String transKey,  int payClass,String payType,long trasnTimeSpan,int agentID) {
        TransType = transType;
        TransKey = transKey;
        PayType = payType;
        PayClass = payClass;
        TrasnTimeSpan = trasnTimeSpan;
        AgentID=agentID;
    }

    public PlaceBean(String transType, String transKey, int pageSize, int curPage, int aClass,long trasnTimeSpan,int agentID) {
        TransType = transType;
        TransKey = transKey;
        PageSize = pageSize;
        CurPage = curPage;
        Class = aClass;
        TrasnTimeSpan = trasnTimeSpan;
        AgentID=agentID;

    }

    public PlaceBean(String transType, String transKey,String merchantNo, String type, int source, String startDate, String endDate, int curPage, int pageSize,long trasnTimeSpan) {
        MerchantNo=merchantNo;
        TransType = transType;
        TransKey = transKey;
        Type = type;
        Source = source;
        StartDate = startDate;
        EndDate = endDate;
        CurPage = curPage;
        PageSize = pageSize;
        TrasnTimeSpan = trasnTimeSpan;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getNewPwd() {
        return NewPwd;
    }

    public void setNewPwd(String newPwd) {
        NewPwd = newPwd;
    }

    public String getOldPwd() {
        return OldPwd;
    }

    public void setOldPwd(String oldPwd) {
        OldPwd = oldPwd;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public String getAppType() {
        return AppType;
    }

    public void setAppType(String appType) {
        AppType = appType;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getCheckState() {
        return CheckState;
    }

    public void setCheckState(int checkState) {
        CheckState = checkState;
    }

    public String getMerchantNo() {
        return MerchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        MerchantNo = merchantNo;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public double getPostMoney() {
        return PostMoney;
    }

    public void setPostMoney(double postMoney) {
        PostMoney = postMoney;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public void setMerchantName(String merchantName) {
        MerchantName = merchantName;
    }

    public String getRechargeMerNo() {
        return RechargeMerNo;
    }

    public void setRechargeMerNo(String rechargeMerNo) {
        RechargeMerNo = rechargeMerNo;
    }

    public int getSource() {
        return Source;
    }

    public void setSource(int source) {
        Source = source;
    }

    public PlaceBean(String transType, String transKey,String merchantNo, String merchantName, String phoneNo, int pageSize, int curPage, String startDate, String endDate,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        MerchantNo = merchantNo;
        MerchantName = merchantName;
        PhoneNo = phoneNo;
        PageSize = pageSize;
        CurPage = curPage;
        StartDate = startDate;
        EndDate = endDate;
        TrasnTimeSpan = trasnTimeSpan;
    }



    public PlaceBean(String transType, String transKey, String merchantNo, double postMoney,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        MerchantNo = merchantNo;
        PostMoney = postMoney;
        TrasnTimeSpan = trasnTimeSpan;
    }
    public PlaceBean(String transType, String transKey, String merchantNo, int curPage, int pageSize,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        MerchantNo = merchantNo;
        CurPage = curPage;
        PageSize = pageSize;
        TrasnTimeSpan = trasnTimeSpan;

    }
    public PlaceBean(String transType, String transKey, String merchantNo, int curPage, int pageSize,long trasnTimeSpan,int agentID) {
        TransType = transType;
        TransKey = transKey;
        MerchantNo = merchantNo;
        CurPage = curPage;
        PageSize = pageSize;
        TrasnTimeSpan = trasnTimeSpan;
        AgentID=agentID;
    }
    //  private int Type;

    public PlaceBean( String transType, String transKey,String type, int curPage, int pageSize, String endDate, String startDate, String merchantNo,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        Type = type;
        CurPage = curPage;
        PageSize = pageSize;
        EndDate = endDate;
        StartDate = startDate;
        MerchantNo = merchantNo;
        TrasnTimeSpan = trasnTimeSpan;
    }

    public PlaceBean(String transType, String transKey, String merchantNo, int level, int checkState, int curPage, int pageSize ,int direct,long trasnTimeSpan,String merchantName,String phoneNumber) {
        TransType = transType;
        TransKey = transKey;
        MerchantNo = merchantNo;
        Level = level;
        CheckState = checkState;
        CurPage = curPage;
        PageSize = pageSize;
        Direct=direct;
        TrasnTimeSpan = trasnTimeSpan;
        MerchantName = merchantName;
        PhoneNumber = phoneNumber;

    }

    public PlaceBean(String transType, String transKey, String type, String code, String userName, String pwd, String appType,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        Type = type;
        Code = code;
        UserName = userName;
        Pwd = pwd;
        AppType = appType;
        TrasnTimeSpan = trasnTimeSpan;
    }

    public PlaceBean(String transType, String transKey, String oldPwd, String newPwd, String phoneNo, String operaType, String type, String code,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        OldPwd = oldPwd;
        NewPwd = newPwd;
        PhoneNo = phoneNo;
        OperaType = operaType;
        Type = type;
        Code = code;
        TrasnTimeSpan = trasnTimeSpan;
    }

    public PlaceBean(String transType, String transKey, String code, String phoneNumber,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        Code = code;
        PhoneNumber = phoneNumber;
        TrasnTimeSpan = trasnTimeSpan;
    }

    public String getTransType() {
        return TransType;
    }

    public void setTransType(String transType) {
        TransType = transType;
    }

    public String getTransKey() {
        return TransKey;
    }

    public void setTransKey(String transKey) {
        TransKey = transKey;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getCurPage() {
        return CurPage;
    }

    public void setCurPage(int curPage) {
        CurPage = curPage;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getAeeaName() {
        return AeeaName;
    }

    public void setAeeaName(String aeeaName) {
        AeeaName = aeeaName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getOperaType() {
        return OperaType;
    }

    public void setOperaType(String operaType) {
        OperaType = operaType;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public PlaceBean(String transType, String transKey, String operaType, String phoneNumber, String type,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        OperaType = operaType;
        PhoneNumber = phoneNumber;
        Type = type;
        TrasnTimeSpan = trasnTimeSpan;
    }

    /**
     * TransType : 1045
     * Merchant : {"Password":"","PhoneNumber":"","RecommendNo":"","Type":null}
     */
    private MerchantBean Merchant;

    public PlaceBean(String transType, String transKey,String code,MerchantBean merchant,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        Code=code;
        Merchant = merchant;
        TrasnTimeSpan = trasnTimeSpan;
    }

    public PlaceBean(String transType, String transKey, int pageSize, int curPage, String cityName, String bankCode,String aeeaName,String bankName,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        PageSize = pageSize;
        CurPage = curPage;
        CityName = cityName;
        BankCode = bankCode;
        AeeaName=aeeaName;
        BankName =bankName;
        TrasnTimeSpan = trasnTimeSpan;
    }

    public PlaceBean(String transType, String transKey) {
        TransType = transType;
        TransKey = transKey;
    }

    public PlaceBean(String transType, String transKey, int pageSize, int curPage,long trasnTimeSpan,int agentID) {
        TransType = transType;
        TransKey = transKey;
        PageSize = pageSize;
        CurPage = curPage;
        TrasnTimeSpan = trasnTimeSpan;
        AgentID = agentID;
    }

    public PlaceBean(String transType, String transKey, String ID,long trasnTimeSpan) {

        TransType = transType;
        TransKey = transKey;
        this.ID = ID;
        TrasnTimeSpan = trasnTimeSpan;
    }

    private BankInfoBean BankInfo;

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public BankInfoBean getBankInfo() {
        return BankInfo;
    }

    public void setBankInfo(BankInfoBean bankInfo) {
        BankInfo = bankInfo;
    }

    public PlaceBean(String transType, String transKey, long trasnTimeSpan, BankInfoBean bankInfo) {
        TransType = transType;
        TransKey = transKey;
        TrasnTimeSpan = trasnTimeSpan;
        BankInfo = bankInfo;
    }

    public static class BankInfoBean implements Serializable {

        /**
         * MerchantID : 1
         * BankName : null
         * BigBankCode : null
         * BranchBankCode : null
         * CardNo : null
         * City : null
         * Province : null
         * Type : 0
         * PeopleName :
         * CardType : 1
         * Isdefault : 1
         */

        private int MerchantID;
        private String BankName;
        private String BigBankCode;
        private String BranchBankCode;
        private String CardNo;
        private String City;
        private String Province;
        private int Type;
        private String PeopleName;
        private int CardType;
        private int IsDefault;

        public BankInfoBean(int merchantID, String bankName, String bigBankCode, String branchBankCode, String cardNo, String city, String province, int type, String peopleName, int cardType, int isDefault) {
            MerchantID = merchantID;
            BankName = bankName;
            BigBankCode = bigBankCode;
            BranchBankCode = branchBankCode;
            CardNo = cardNo;
            City = city;
            Province = province;
            Type = type;
            PeopleName = peopleName;
            CardType = cardType;
            IsDefault = isDefault;
        }

        public int getMerchantID() {
            return MerchantID;
        }

        public void setMerchantID(int MerchantID) {
            this.MerchantID = MerchantID;
        }

        public String getBankName() {
            return BankName;
        }

        public void setBankName(String BankName) {
            this.BankName = BankName;
        }

        public String getBigBankCode() {
            return BigBankCode;
        }

        public void setBigBankCode(String BigBankCode) {
            this.BigBankCode = BigBankCode;
        }

        public String getBranchBankCode() {
            return BranchBankCode;
        }

        public void setBranchBankCode(String BranchBankCode) {
            this.BranchBankCode = BranchBankCode;
        }

        public String getCardNo() {
            return CardNo;
        }

        public void setCardNo(String CardNo) {
            this.CardNo = CardNo;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getProvince() {
            return Province;
        }

        public void setProvince(String Province) {
            this.Province = Province;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public String getPeopleName() {
            return PeopleName;
        }

        public void setPeopleName(String PeopleName) {
            this.PeopleName = PeopleName;
        }

        public int getCardType() {
            return CardType;
        }

        public void setCardType(int CardType) {
            this.CardType = CardType;
        }

        public int getIsDefault() {
            return IsDefault;
        }

        public void setIsDefault(int IsDefault) {
            this.IsDefault = IsDefault;
        }
    }
    public MerchantBean getMerchant() {
        return Merchant;
    }

    public void setMerchant(MerchantBean Merchant) {
        this.Merchant = Merchant;
    }

    public static class MerchantBean implements Serializable{
        /**
         * Password :
         * PhoneNumber :
         * RecommendNo :
         * Type : null
         */

        private String Password;
        private String PhoneNumber;
        private String RecommendNo;
        private String Type;
        private String AgentID;
       private String ReserveNumber;

        @Override
        public String toString() {
            return "MerchantBean{" +
                    "Password='" + Password + '\'' +
                    ", PhoneNumber='" + PhoneNumber + '\'' +
                    ", RecommendNo='" + RecommendNo + '\'' +
                    ", Type='" + Type + '\'' +
                    ", AgentID='" + AgentID + '\'' +
                    ", ReserveNumber='" + ReserveNumber + '\'' +
                    '}';
        }

        public String getReserveNumber() {
            return ReserveNumber;
        }

        public void setReserveNumber(String reserveNumber) {
            ReserveNumber = reserveNumber;
        }

        public String getAgentID() {
            return AgentID;
        }

        public void setAgentID(String agentID) {
            AgentID = agentID;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        public String getRecommendNo() {
            return RecommendNo;
        }

        public void setRecommendNo(String RecommendNo) {
            this.RecommendNo = RecommendNo;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

    }


    @Override
    public String toString() {
        return "PlaceBean{" +
                "EndDate='" + EndDate + '\'' +
                ", TransType='" + TransType + '\'' +
                ", TransKey='" + TransKey + '\'' +
                ", ID='" + ID + '\'' +
                ", PageSize=" + PageSize +
                ", CurPage=" + CurPage +
                ", CityName='" + CityName + '\'' +
                ", BankCode='" + BankCode + '\'' +
                ", AeeaName='" + AeeaName + '\'' +
                ", Type='" + Type + '\'' +
                ", OperaType='" + OperaType + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Code='" + Code + '\'' +
                ", PhoneNo='" + PhoneNo + '\'' +
                ", NewPwd='" + NewPwd + '\'' +
                ", OldPwd='" + OldPwd + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Pwd='" + Pwd + '\'' +
                ", AppType='" + AppType + '\'' +
                ", Level=" + Level +
                ", CheckState=" + CheckState +
                ", MerchantNo='" + MerchantNo + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", PostMoney=" + PostMoney +
                ", MerchantName='" + MerchantName + '\'' +
                ", RechargeMerNo='" + RechargeMerNo + '\'' +
                ", Source=" + Source +
                ", BankName='" + BankName + '\'' +
                ", Class=" + Class +
                ", PayType='" + PayType + '\'' +
                ", PayClass=" + PayClass +
                ", Merchant=" + Merchant +
                '}';
    }
}
