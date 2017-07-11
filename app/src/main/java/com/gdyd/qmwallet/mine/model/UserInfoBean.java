package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;

/**
 * Created by zanzq on 2017/3/1.
 */

public class UserInfoBean implements Serializable {

    /**
     * TransType : 1046
     * TransKey :
     * Merchant : {"ID":1,"City":"","DetailAddress":"","Email":"","ManagerName":"","Name":"","PaymentCode":"","Province":"","RoyaltyRate":"","IDCardNo":""}
     * BankInfo : {"BankName":"","BigBankCode":"","BranchBankCode":"","CardNo":"","City":"","Province":"","Type":0,"PeopleName":""}
     */

    private int TransType;
    private String TransKey;
    private MerchantBean Merchant;
    private BankInfoBean BankInfo;
    private long TrasnTimeSpan;
    public UserInfoBean(int transType, String transKey, MerchantBean merchant, BankInfoBean bankInfo,long trasnTimeSpan) {
        TransType = transType;
        TransKey = transKey;
        Merchant = merchant;
        BankInfo = bankInfo;
        TrasnTimeSpan = trasnTimeSpan;
    }


    public long getTrasnTimeSpan() {
        return TrasnTimeSpan;
    }

    public void setTrasnTimeSpan(long trasnTimeSpan) {
        TrasnTimeSpan = trasnTimeSpan;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "TransType=" + TransType +
                ", TransKey='" + TransKey + '\'' +
                ", Merchant=" + Merchant +
                ", BankInfo=" + BankInfo +
                '}';
    }

    public int getTransType() {
        return TransType;
    }

    public void setTransType(int TransType) {
        this.TransType = TransType;
    }

    public String getTransKey() {
        return TransKey;
    }

    public void setTransKey(String TransKey) {
        this.TransKey = TransKey;
    }

    public MerchantBean getMerchant() {
        return Merchant;
    }

    public void setMerchant(MerchantBean Merchant) {
        this.Merchant = Merchant;
    }

    public BankInfoBean getBankInfo() {
        return BankInfo;
    }

    public void setBankInfo(BankInfoBean BankInfo) {
        this.BankInfo = BankInfo;
    }

    public static class MerchantBean implements Serializable {
        /**
         * ID : 1
         * City :
         * DetailAddress :
         * Email :
         * ManagerName :
         * Name :
         * PaymentCode :
         * Province :
         * RoyaltyRate :
         * IDCardNo :
         */

        private int ID;
        private String City;
        private String DetailAddress;
        private String Email;
        private String ManagerName;
        private String Name;
        private String PaymentCode;
        private String Province;
        private String RoyaltyRate;
        private String IDCardNo;
        private String ReserveNumber;
        public MerchantBean() {
        }

        public MerchantBean(int ID, String city, String detailAddress, String email, String managerName, String name, String paymentCode, String province, String royaltyRate, String IDCardNo,String reserveNumber) {
            this.ID = ID;
            City = city;
            DetailAddress = detailAddress;
            Email = email;
            ManagerName = managerName;
            Name = name;
            PaymentCode = paymentCode;
            Province = province;
            RoyaltyRate = royaltyRate;
            this.IDCardNo = IDCardNo;
            ReserveNumber=reserveNumber;
        }

        @Override
        public String toString() {
            return "MerchantBean{" +
                    "ID=" + ID +
                    ", City='" + City + '\'' +
                    ", DetailAddress='" + DetailAddress + '\'' +
                    ", Email='" + Email + '\'' +
                    ", ManagerName='" + ManagerName + '\'' +
                    ", Name='" + Name + '\'' +
                    ", PaymentCode='" + PaymentCode + '\'' +
                    ", Province='" + Province + '\'' +
                    ", RoyaltyRate='" + RoyaltyRate + '\'' +
                    ", IDCardNo='" + IDCardNo + '\'' +
                    '}';
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
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

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getManagerName() {
            return ManagerName;
        }

        public void setManagerName(String ManagerName) {
            this.ManagerName = ManagerName;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPaymentCode() {
            return PaymentCode;
        }

        public void setPaymentCode(String PaymentCode) {
            this.PaymentCode = PaymentCode;
        }

        public String getProvince() {
            return Province;
        }

        public void setProvince(String Province) {
            this.Province = Province;
        }

        public String getRoyaltyRate() {
            return RoyaltyRate;
        }

        public void setRoyaltyRate(String RoyaltyRate) {
            this.RoyaltyRate = RoyaltyRate;
        }

        public String getIDCardNo() {
            return IDCardNo;
        }

        public void setIDCardNo(String IDCardNo) {
            this.IDCardNo = IDCardNo;
        }
    }

    public static class BankInfoBean implements Serializable {
        @Override
        public String toString() {
            return "BankInfoBean{" +
                    "BankName='" + BankName + '\'' +
                    ", BigBankCode='" + BigBankCode + '\'' +
                    ", BranchBankCode='" + BranchBankCode + '\'' +
                    ", CardNo='" + CardNo + '\'' +
                    ", City='" + City + '\'' +
                    ", Province='" + Province + '\'' +
                    ", Type=" + Type +
                    ", PeopleName='" + PeopleName + '\'' +
                    '}';
        }

        /**
         * BankName :
         * BigBankCode :
         * BranchBankCode :
         * CardNo :
         * City :
         * Province :
         * Type : 0
         * PeopleName :
         */

        private String BankName;
        private String BigBankCode;
        private String BranchBankCode;
        private String CardNo;
        private String City;
        private String Province;
        private int Type;
        private String PeopleName;

        public BankInfoBean(String bankName, String bigBankCode, String branchBankCode, String cardNo, String city, String province, int type, String peopleName) {
            BankName = bankName;
            BigBankCode = bigBankCode;
            BranchBankCode = branchBankCode;
            CardNo = cardNo;
            City = city;
            Province = province;
            Type = type;
            PeopleName = peopleName;
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
    }
}
