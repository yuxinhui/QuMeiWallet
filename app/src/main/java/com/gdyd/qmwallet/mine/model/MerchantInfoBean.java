package com.gdyd.qmwallet.mine.model;

import java.util.List;

/**
 * Created by zanzq on 2017/3/5.
 */

public class MerchantInfoBean {

    /**
     * UserData : {"BankInfo":{"BankName":"建设银行","BigBankCode":"01050000","BranchBankCode":"105595010207","CardNo":"622700317224284","City":"惠州市       ","Id":2477,"MerchantID":2480,"PeopleName":"你好","Province":"广东省       ","Type":1},"Bank_stlno":{"BANK":"中国建设银行","BANK_CODE":"01050000","BANK_FLAG":"0         ","BANK_NAME":"中国建设银行股份有限公司惠州市分行","CITY":"惠州市","Id":79166,"OPEN_STLNO":"105595010207","PROV":"广东省","ZF_CODE":null},"Bank_toal_code":{"Id":3,"bankcode":"01050000","bankcode2":"9008","bankcode3":"105","bankname":"建设银行"},"DTotalMoney":0,"ImagelistUrl":[],"Merchant":{"AddTime":"","AgentID":1,"BarCodeNumber":"SMC0000136667","CheckState":0,"City":"惠州市","DetailAddress":"1231","Email":"","ID":2480,"IDCardNo":"441625199501016670","ManagerName":"你好","MerchantNo":"SMC0000136667","Name":"你好","OpenID":null,"Password":"88888888","PaymentCode":"","PhoneNumber":"18825066894","Province":"广东省","RecommendNo":null,"RoyaltyRate":null,"Type":1},"PayUrl":"http:\/\/wx.gdydit.cn:8088\/register\/codeServer.jsp?NewBarCode=SMC0000136667&OldBarCode=&Type=1"}
     * nResul : 1
     * sMessage : 获取商户信息成功
     */

    private String Data;
    private int nResul;
    private String sMessage;
    private UserData userData;

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
    public static class UserData {

        /**
         * BankInfo : {"BankName":"建设银行","BigBankCode":"01050000","BranchBankCode":"105595010207","CardNo":"622700317224284","City":"惠州市 ","Id":2477,"MerchantID":2480,"PeopleName":"你好","Province":"广东省 ","Type":1}
         * Bank_stlno : {"BANK":"中国建设银行","BANK_CODE":"01050000","BANK_FLAG":"0 ","BANK_NAME":"中国建设银行股份有限公司惠州市分行","CITY":"惠州市","Id":79166,"OPEN_STLNO":"105595010207","PROV":"广东省","ZF_CODE":null}
         * Bank_toal_code : {"Id":3,"bankcode":"01050000","bankcode2":"9008","bankcode3":"105","bankname":"建设银行"}
         * DTotalMoney : 0
         * ImagelistUrl : []
         * Merchant : {"AddTime":"","AgentID":1,"BarCodeNumber":"SMC0000136667","CheckState":0,"City":"惠州市","DetailAddress":"1231","Email":"","ID":2480,"IDCardNo":"441625199501016670","ManagerName":"你好","MerchantNo":"SMC0000136667","Name":"你好","OpenID":null,"Password":"88888888","PaymentCode":"","PhoneNumber":"18825066894","Province":"广东省","RecommendNo":null,"RoyaltyRate":null,"Type":1}
         * PayUrl : http://wx.gdydit.cn:8088/register/codeServer.jsp?NewBarCode=SMC0000136667&OldBarCode=&Type=1
         */

        private BankInfoBean BankInfo;
        private BankStlnoBean Bank_stlno;
        private BankToalCodeBean Bank_toal_code;
        private int DTotalMoney;
        private MerchantBean Merchant;
        private String PayUrl;
        private List<String> ImagelistUrl;

        public BankInfoBean getBankInfo() {
            return BankInfo;
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

        public int getDTotalMoney() {
            return DTotalMoney;
        }

        public void setDTotalMoney(int DTotalMoney) {
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

        public List<String> getImagelistUrl() {
            return ImagelistUrl;
        }

        public void setImagelistUrl(List<String> ImagelistUrl) {
            this.ImagelistUrl = ImagelistUrl;
        }

        public static class BankInfoBean {
            /**
             * BankName : 建设银行
             * BigBankCode : 01050000
             * BranchBankCode : 105595010207
             * CardNo : 622700317224284
             * City : 惠州市
             * Id : 2477
             * MerchantID : 2480
             * PeopleName : 你好
             * Province : 广东省
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

        public static class BankStlnoBean {
            /**
             * BANK : 中国建设银行
             * BANK_CODE : 01050000
             * BANK_FLAG : 0
             * BANK_NAME : 中国建设银行股份有限公司惠州市分行
             * CITY : 惠州市
             * Id : 79166
             * OPEN_STLNO : 105595010207
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

        public static class BankToalCodeBean {
            /**
             * Id : 3
             * bankcode : 01050000
             * bankcode2 : 9008
             * bankcode3 : 105
             * bankname : 建设银行
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

        public static class MerchantBean {
            /**
             * AddTime :
             * AgentID : 1
             * BarCodeNumber : SMC0000136667
             * CheckState : 0
             * City : 惠州市
             * DetailAddress : 1231
             * Email :
             * ID : 2480
             * IDCardNo : 441625199501016670
             * ManagerName : 你好
             * MerchantNo : SMC0000136667
             * Name : 你好
             * OpenID : null
             * Password : 88888888
             * PaymentCode :
             * PhoneNumber : 18825066894
             * Province : 广东省
             * RecommendNo : null
             * RoyaltyRate : null
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
            private Object OpenID;
            private String Password;
            private String PaymentCode;
            private String PhoneNumber;
            private String Province;
            private Object RecommendNo;
            private Object RoyaltyRate;
            private int Type;

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

            public Object getOpenID() {
                return OpenID;
            }

            public void setOpenID(Object OpenID) {
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

            public Object getRecommendNo() {
                return RecommendNo;
            }

            public void setRecommendNo(Object RecommendNo) {
                this.RecommendNo = RecommendNo;
            }

            public Object getRoyaltyRate() {
                return RoyaltyRate;
            }

            public void setRoyaltyRate(Object RoyaltyRate) {
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
