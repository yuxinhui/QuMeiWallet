package com.gdyd.qmwallet.Other.model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/3/1.
 */

public class LoginInfoBean implements Serializable {

    /**
     * UserData : {"BankInfo":null,"ImagelistUrl":[],"Merchant":{"AddTime":"","AgentID":1,"BarCodeNumber":"SMC0000136664","CheckState":0,"City":null,"DetailAddress":null,"Email":null,"ID":2477,"IDCardNo":null,"ManagerName":null,"MerchantNo":"SMC0000136664","Name":null,"OpenID":null,"Password":"123123","PaymentCode":null,"PhoneNumber":"18825066894","Province":null,"RecommendNo":"","RoyaltyRate":null,"Type":1},"Url":null}
     * nResul : 1
     * sMessage : 登录成功
     */

    private String Data;
    private int nResul;
    private String sMessage;
    private UserData userData;

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

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public static class UserData implements Serializable {

        /**
         * BankInfo : {"BankName":"中国农业银行股份有限公司惠州惠阳白云支行","BigBankCode":"01030000","BranchBankCode":"103595023361","CardNo":"6228481136088467676","City":"5950 ","Id":106,"MerchantID":108,"PeopleName":"刘奕俊","Province":"5800 ","Type":1}
         * Bank_stlno : {"BANK":"中国农业银行","BANK_CODE":"01030000","BANK_FLAG":"0 ","BANK_NAME":"中国农业银行股份有限公司惠州惠阳白云支行","CITY":"惠州市","Id":78641,"OPEN_STLNO":"103595023361","PROV":"广东省","ZF_CODE":null}
         * Bank_toal_code : {"Id":2,"bankcode":"01030000","bankcode2":"9009","bankcode3":"103","bankname":"农业银行"}
         * DTotalMoney : 928018.6
         * ImagelistUrl : ["Enclosure/2016/10/21/Enclosure20161021155335_1477036416_01.jpg","Enclosure/2016/10/21/Enclosure20161021155338_1477036419_02.jpg","Enclosure/2016/10/21/Enclosure20161021155341_1477036422_03.jpg","Enclosure/2016/10/21/Enclosure20161021155345_1477036426_04.jpg"]
         * Merchant : {"AddTime":"2017/2/23 9:42:55","AgentID":63,"BarCodeNumber":"SMC0000039915","CheckState":2,"City":"5950","DetailAddress":"。","Email":"","ID":108,"IDCardNo":"441323199912152010","ManagerName":"刘奕俊","MerchantNo":"SMC0000039915","Name":"刘奕俊","OpenID":"oIcnyw7qw1MNfvDMfH-qs4matSG4","Password":"11111111","PaymentCode":"http://wx.gdydit.cn/onlinepay/order.jsp?mchtNo=SMC0000039915","PhoneNumber":"13680888888","Province":"5800","RecommendNo":null,"RoyaltyRate":0,"Type":1}
         * PayUrl : http://wx.gdydit.cn:8088/register/codeServer.jsp?NewBarCode=SMC0000039915&OldBarCode=&Type=1
         */

        private BankInfoBean BankInfo;
        private BankStlnoBean Bank_stlno;
        private BankToalCodeBean Bank_toal_code;
        private double DTotalMoney;
        private MerchantBean Merchant;
        private ShareHolderBean ShareholderInfo;
        private String PayUrl;
        private ArrayList<String> ImagelistUrl;

        public BankInfoBean getBankInfo() {
            return BankInfo;
        }

        public ShareHolderBean getShareholderInfo() {
            return ShareholderInfo;
        }

        public void setShareholderInfo(ShareHolderBean shareholderInfo) {
            ShareholderInfo = shareholderInfo;
        }

        public void setBankInfo(BankInfoBean BankInfo) {
            this.BankInfo = BankInfo;
        }

        public BankStlnoBean getBank_stlno() {
            return Bank_stlno;
        }

        public void setBank_stlno(BankStlnoBean Bank_stlno) {
            this.Bank_stlno = Bank_stlno;
        }

        public BankToalCodeBean getBank_toal_code() {
            return Bank_toal_code;
        }

        public void setBank_toal_code(BankToalCodeBean Bank_toal_code) {
            this.Bank_toal_code = Bank_toal_code;
        }

        public double getDTotalMoney() {
            return DTotalMoney;
        }

        public void setDTotalMoney(double DTotalMoney) {
            this.DTotalMoney = DTotalMoney;
        }

        public MerchantBean getMerchant() {
            return Merchant;
        }

        public void setMerchant(MerchantBean Merchant) {
            this.Merchant = Merchant;
        }

        public String getPayUrl() {
            return PayUrl;
        }

        public void setPayUrl(String PayUrl) {
            this.PayUrl = PayUrl;
        }

        public ArrayList<String> getImagelistUrl() {
            return ImagelistUrl;
        }

        public void setImagelistUrl(ArrayList<String> ImagelistUrl) {
            this.ImagelistUrl = ImagelistUrl;
        }

        public static class BankInfoBean implements Serializable {
            /**
             * BankName : 中国农业银行股份有限公司惠州惠阳白云支行
             * BigBankCode : 01030000
             * BranchBankCode : 103595023361
             * CardNo : 6228481136088467676
             * City : 5950
             * Id : 106
             * MerchantID : 108
             * PeopleName : 刘奕俊
             * Province : 5800
             * Type : 1
             */

            private String BankName;
            private String BigBankCode;
            private String BranchBankCode;
            private String CardNo;
            private String City;
            private int Id;
            private int MerchantID;
            private String PeopleName;
            private String Province;
            private int Type;

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

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getMerchantID() {
                return MerchantID;
            }

            public void setMerchantID(int MerchantID) {
                this.MerchantID = MerchantID;
            }

            public String getPeopleName() {
                return PeopleName;
            }

            public void setPeopleName(String PeopleName) {
                this.PeopleName = PeopleName;
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
        }

        public static class BankStlnoBean implements Serializable {
            /**
             * BANK : 中国农业银行
             * BANK_CODE : 01030000
             * BANK_FLAG : 0
             * BANK_NAME : 中国农业银行股份有限公司惠州惠阳白云支行
             * CITY : 惠州市
             * Id : 78641
             * OPEN_STLNO : 103595023361
             * PROV : 广东省
             * ZF_CODE : null
             */

            private String BANK;
            private String BANK_CODE;
            private String BANK_FLAG;
            private String BANK_NAME;
            private String CITY;
            private int Id;
            private String OPEN_STLNO;
            private String PROV;
            private Object ZF_CODE;

            public String getBANK() {
                return BANK;
            }

            public void setBANK(String BANK) {
                this.BANK = BANK;
            }

            public String getBANK_CODE() {
                return BANK_CODE;
            }

            public void setBANK_CODE(String BANK_CODE) {
                this.BANK_CODE = BANK_CODE;
            }

            public String getBANK_FLAG() {
                return BANK_FLAG;
            }

            public void setBANK_FLAG(String BANK_FLAG) {
                this.BANK_FLAG = BANK_FLAG;
            }

            public String getBANK_NAME() {
                return BANK_NAME;
            }

            public void setBANK_NAME(String BANK_NAME) {
                this.BANK_NAME = BANK_NAME;
            }

            public String getCITY() {
                return CITY;
            }

            public void setCITY(String CITY) {
                this.CITY = CITY;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getOPEN_STLNO() {
                return OPEN_STLNO;
            }

            public void setOPEN_STLNO(String OPEN_STLNO) {
                this.OPEN_STLNO = OPEN_STLNO;
            }

            public String getPROV() {
                return PROV;
            }

            public void setPROV(String PROV) {
                this.PROV = PROV;
            }

            public Object getZF_CODE() {
                return ZF_CODE;
            }

            public void setZF_CODE(Object ZF_CODE) {
                this.ZF_CODE = ZF_CODE;
            }
        }

        public static class BankToalCodeBean implements Serializable {
            /**
             * Id : 2
             * bankcode : 01030000
             * bankcode2 : 9009
             * bankcode3 : 103
             * bankname : 农业银行
             */

            private int Id;
            private String bankcode;
            private String bankcode2;
            private String bankcode3;
            private String bankname;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getBankcode() {
                return bankcode;
            }

            public void setBankcode(String bankcode) {
                this.bankcode = bankcode;
            }

            public String getBankcode2() {
                return bankcode2;
            }

            public void setBankcode2(String bankcode2) {
                this.bankcode2 = bankcode2;
            }

            public String getBankcode3() {
                return bankcode3;
            }

            public void setBankcode3(String bankcode3) {
                this.bankcode3 = bankcode3;
            }

            public String getBankname() {
                return bankname;
            }

            public void setBankname(String bankname) {
                this.bankname = bankname;
            }
        }


        public static class ShareHolderBean implements Serializable {
            private String MerchantNo;
            private String Name;
            private String PhoneNumber;

            public String getMerchantNo() {
                return MerchantNo;
            }

            public void setMerchantNo(String merchantNo) {
                MerchantNo = merchantNo;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getPhoneNumber() {
                return PhoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                PhoneNumber = phoneNumber;
            }
        }

        public static class MerchantBean implements Serializable {
            /**
             * AddTime : 2017/2/23 9:42:55
             * AgentID : 63
             * BarCodeNumber : SMC0000039915
             * CheckState : 2
             * City : 5950
             * DetailAddress : 。
             * Email :
             * ID : 108
             * IDCardNo : 441323199912152010
             * ManagerName : 刘奕俊
             * MerchantNo : SMC0000039915
             * Name : 刘奕俊
             * OpenID : oIcnyw7qw1MNfvDMfH-qs4matSG4
             * Password : 11111111
             * PaymentCode : http://wx.gdydit.cn/onlinepay/order.jsp?mchtNo=SMC0000039915
             * PhoneNumber : 13680888888
             * Province : 5800
             * RecommendNo : null
             * RoyaltyRate : 0
             * Type : 1
             */

            private String AddTime;
            private int AgentID;
            private String BarCodeNumber;
            private int CheckState;
            private String City;
            private String DetailAddress;
            private String Email;
            private int ID;
            private String IDCardNo;
            private String ManagerName;
            private String MerchantNo;
            private String Name;
            private String OpenID;
            private String Password;
            private String PaymentCode;
            private String PhoneNumber;
            private String Province;
            private String Token;
            private String RecommendNo;
            private int RoyaltyRate;
            private int Type;
            private int LevelValue;
            private String ReserveNumber;
            private String HeadImage;
            private int Permissions;

            public String getToken() {
                return Token;
            }

            public void setToken(String token) {
                Token = token;
            }

            public int getPermissions() {
                return Permissions;
            }

            public void setPermissions(int permissions) {
                Permissions = permissions;
            }

            public String getHeadImage() {
                return HeadImage;
            }

            public void setHeadImage(String headImage) {
                HeadImage = headImage;
            }

            public String getReserveNumber() {
                return ReserveNumber;
            }

            public void setReserveNumber(String reserveNumber) {
                ReserveNumber = reserveNumber;
            }

            public int getLevelValue() {
                return LevelValue;
            }

            public void setLevelValue(int levelValue) {
                LevelValue = levelValue;
            }

            public String getAddTime() {
                return AddTime;
            }

            public void setAddTime(String AddTime) {
                this.AddTime = AddTime;
            }

            public int getAgentID() {
                return AgentID;
            }

            public void setAgentID(int AgentID) {
                this.AgentID = AgentID;
            }

            public String getBarCodeNumber() {
                return BarCodeNumber;
            }

            public void setBarCodeNumber(String BarCodeNumber) {
                this.BarCodeNumber = BarCodeNumber;
            }

            public int getCheckState() {
                return CheckState;
            }

            public void setCheckState(int CheckState) {
                this.CheckState = CheckState;
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

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getIDCardNo() {
                return IDCardNo;
            }

            public void setIDCardNo(String IDCardNo) {
                this.IDCardNo = IDCardNo;
            }

            public String getManagerName() {
                return ManagerName;
            }

            public void setManagerName(String ManagerName) {
                this.ManagerName = ManagerName;
            }

            public String getMerchantNo() {
                return MerchantNo;
            }

            public void setMerchantNo(String MerchantNo) {
                this.MerchantNo = MerchantNo;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getOpenID() {
                return OpenID;
            }

            public void setOpenID(String OpenID) {
                this.OpenID = OpenID;
            }

            public String getPassword() {
                return Password;
            }

            public void setPassword(String Password) {
                this.Password = Password;
            }

            public String getPaymentCode() {
                return PaymentCode;
            }

            public void setPaymentCode(String PaymentCode) {
                this.PaymentCode = PaymentCode;
            }

            public String getPhoneNumber() {
                return PhoneNumber;
            }

            public void setPhoneNumber(String PhoneNumber) {
                this.PhoneNumber = PhoneNumber;
            }

            public String getProvince() {
                return Province;
            }

            public void setProvince(String Province) {
                this.Province = Province;
            }

            public String getRecommendNo() {
                return RecommendNo;
            }

            public void setRecommendNo(String RecommendNo) {
                this.RecommendNo = RecommendNo;
            }

            public int getRoyaltyRate() {
                return RoyaltyRate;
            }

            public void setRoyaltyRate(int RoyaltyRate) {
                this.RoyaltyRate = RoyaltyRate;
            }

            public int getType() {
                return Type;
            }

            public void setType(int Type) {
                this.Type = Type;
            }


        }
    }
}
