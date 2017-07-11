package com.gdyd.qmwallet.mine.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zanzq on 2017/2/28.
 */

public class BranchBankBean implements Serializable {


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

    /**
     * Data : {
     "bank_stlno": "[{\"BANK\":\"中国建设银行\",\"BANK_CODE\":\"01050000\",\"BANK_FLAG\":\"0         \",\"BANK_NAME\":\"中国建设银行股份有限公司惠州市分行\",\"CITY\":\"惠州市\",\"Id\":79166,\"OPEN_STLNO\":\"105595010207\",\"PROV\":\"广东省\",\"ZF_CODE\":null},{\"BANK\":\"中国建设银行\",\"BANK_CODE\":\"01050000\",\"BANK_FLAG\":\"0         \",\"BANK_NAME\":\"中国建设银行股份有限公司惠州市分行营业室\",\"CITY\":\"惠州市\",\"Id\":79133,\"OPEN_STLNO\":\"105595010741\",\"PROV\":\"广东省\",\"ZF_CODE\":null}]",
     "PageCount": 1
     }
     * nResul : 1
     * sMessage : null
     */

    private String Data;
    private int nResul;
    private String sMessage;
    private DataBean dataBean;
    public static class DataBean implements Serializable{

        /**
         * bank_stlno : [{"BANK":"中国建设银行","BANK_CODE":"01050000","BANK_FLAG":"0 ","BANK_NAME":"中国建设银行股份有限公司惠州市分行","CITY":"惠州市","Id":79166,"OPEN_STLNO":"105595010207","PROV":"广东省","ZF_CODE":null},{"BANK":"中国建设银行","BANK_CODE":"01050000","BANK_FLAG":"0 ","BANK_NAME":"中国建设银行股份有限公司惠州市分行营业室","CITY":"惠州市","Id":79133,"OPEN_STLNO":"105595010741","PROV":"广东省","ZF_CODE":null}]
         * PageCount : 1
         */

        private String bank_stlno;
        private int PageCount;
        private ArrayList<Bank_stlnoBean> list;

        public ArrayList<Bank_stlnoBean> getList() {
            return list;
        }

        public void setList(ArrayList<Bank_stlnoBean> list) {
            this.list = list;
        }

        public  static class Bank_stlnoBean implements Serializable{

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
            private String ZF_CODE;

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

            public String getZF_CODE() {
                return ZF_CODE;
            }

            public void setZF_CODE(String ZF_CODE) {
                this.ZF_CODE = ZF_CODE;
            }
        }
        public String getBank_stlno() {
            return bank_stlno;
        }

        public void setBank_stlno(String bank_stlno) {
            this.bank_stlno = bank_stlno;
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
