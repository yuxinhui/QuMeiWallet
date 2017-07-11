package com.gdyd.qmwallet.utils;

import android.util.Base64;
import android.util.Log;

import com.gdyd.qmwallet.App;
import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.HandleConfig;
import com.gdyd.qmwallet.config.UrlConfig;
import com.gdyd.qmwallet.mine.model.PlaceBean;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 王松 on 2016/10/27.
 */

public class EncryptionHelper {
    public static final int ENCODE = 0;
    public static final int DECODE = 1;
    public static   String key= APPConfig.a+ HandleConfig.b+ UrlConfig.c+HandleConfig.numer;
  //  public static   String key=App.key;
  //  public static   String key2=App.key2;
    //Meikej
    public static  String  key2=APPConfig.a+HandleConfig.b+UrlConfig.nu2;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    //两个大素数的乘积
    private static final String MODULUS = "100631058000714094813874361191853577129731636346684218206605779824931626830750623070803100189781211343851763275329364056640619755337779928985272486091431384128027213365372009648233171894708338213168824861061809490615593530405056055952622249066180336803996949444124622212096805545953751253607916170340397933039";
    //公钥
    private static final String PUB_KEY = "65537";
    //私钥
    private static final String PRI_KEY = "26900155715313643087786516528374548998821559381075740707715132776187148793016466508650068087107695523642202737697714709374658856733792614490943874205956727606674634563665154616758939576547663715234643273055658829482813503959459653708062875625210008961239643775661357655599312857249418610810177817213648575161";
    public  static long getDate( ){
       return new Date().getTime();
    }
 public  static String md5(String transType){
     try {
         transType=transType+ key2;
         Log.d("zanZQ", "md5: "+transType);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
//         byte[] bytes = digest.digest(transType.getBytes());
//         byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
//         String transKey = new String(encode, 0, encode.length);
         md5.update(transType.getBytes());
         byte[] b = md5.digest();//加密
         StringBuffer buf = new StringBuffer();
         for(int i = 0; i < b.length; i ++){
             int a = b[i];
             if(a<0)
                 a+=256;
             if(a<16)
                 buf.append("0");
             buf.append(Integer.toHexString(a));

         }
         return buf.toString();
     } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
     }
     return null;
 }
    /**
     * @param content 要加密的文本
     * @param key     密钥
     * @param mode    加密还是解密
     * @return
     */
    public static String des(String content, String key, int mode) {
        String charset = "UTF-8";
        try {
            //获取密钥的byte数组
            byte[] keyBytes = key.getBytes(charset);
            byte[] temp = new byte[8];
            //数组拷贝，调用native方法，效率高于for循环拷贝
            System.arraycopy(keyBytes, 0, temp, 0, Math.min(keyBytes.length, temp.length));
            //获取一个密钥对象
            //1.密钥字符串所对应的byte数组
            //2.获取密钥对象的算法名称
            Key keySpec = new SecretKeySpec(temp, "des");
            //获取一个密文生成器
            Cipher cipher = Cipher.getInstance("des");
            if (mode == ENCODE) {
                //初始化密文生成器
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                //加密
                byte[] bytes = cipher.doFinal(content.getBytes(charset));
                byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
                return new String(encode, 0, encode.length);
            } else if (mode == DECODE) {
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                //解密
                byte[] bytes = cipher.doFinal(Base64.decode(content, Base64.DEFAULT));
                return new String(bytes, 0, bytes.length);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param content 要加密的文本
     * @param key     密钥
     * @param mode    加密还是解密
     * @return
     */
    public static String des3(String content, String key, int mode) {
        String charset = "UTF-8";
        try {
            //获取密钥的byte数组
            byte[] keyBytes = key.getBytes(charset);
            byte[] temp = new byte[24];
            //数组拷贝，调用native方法，效率高于for循环拷贝
            System.arraycopy(keyBytes, 0, temp, 0, Math.min(keyBytes.length, temp.length));
            //获取一个密钥对象
            //1.密钥字符串所对应的byte数组
            //2.获取密钥对象的算法名称
            Key keySpec = new SecretKeySpec(temp, "desede");
            //获取一个密文生成器
            Cipher cipher = Cipher.getInstance("desede");
            if (mode == ENCODE) {
                //初始化密文生成器
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                //加密
                byte[] bytes = cipher.doFinal(content.getBytes(charset));
                byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
                return new String(encode, 0, encode.length);
            } else if (mode == DECODE) {
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                //解密
                byte[] bytes = cipher.doFinal(Base64.decode(content, Base64.DEFAULT));
                return new String(bytes, 0, bytes.length);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String aesEncrypt(String str, String key) throws Exception {
        if (str == null || key == null){
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        return new String(encode, 0, encode.length);
    }
    public static String aesDecrypt(String str, String key) throws Exception {
        if (str == null || key == null){
            return null;}
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
     //   byte[] bytes = new BASE64Decoder().decodeBuffer(str);
        byte[] decode = Base64.decode(str, Base64.DEFAULT);
        decode = cipher.doFinal(decode);
        return new String(decode, 0, decode.length);
    }
    /**
     * @param content 要加密的文本
     * @param key     密钥
     * @param mode    加密还是解密
     * @return
     */
    public static String aes(String content, String key, int mode) {
        String charset = "UTF-8";
        try {
            //获取密钥的byte数组
            byte[] keyBytes = key.getBytes(charset);
            byte[] temp = new byte[32];
            //数组拷贝，调用native方法，效率高于for循环拷贝
            System.arraycopy(keyBytes, 0, temp, 0, Math.min(keyBytes.length, temp.length));
            //获取一个密钥对象
            //1.密钥字符串所对应的byte数组
            //2.获取密钥对象的算法名称
            Key keySpec = new SecretKeySpec(temp, "aes");
            //获取一个密文生成器
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            if (mode == ENCODE) {
                //初始化密文生成器
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                //加密
                byte[] bytes = cipher.doFinal(content.getBytes(charset));
                byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
                return new String(encode, 0, encode.length);
            } else if (mode == DECODE) {
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                //解密
                byte[] bytes = cipher.doFinal(Base64.decode(content, Base64.DEFAULT));
                return new String(bytes, 0, bytes.length);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String rsaEncode(String content) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("rsa");
            KeySpec keySpec = new RSAPublicKeySpec(new BigInteger(MODULUS), new BigInteger(PUB_KEY));
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance("rsa");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(content.getBytes("UTF-8"));
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
            return new String(encode, 0, encode.length);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String rsaDecode(String content) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("rsa");
            KeySpec keySpec = new RSAPrivateKeySpec(new BigInteger(MODULUS), new BigInteger(PRI_KEY));
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance("rsa");
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            byte[] bytes = cipher.doFinal(Base64.decode(content, Base64.DEFAULT));
            return new String(bytes, 0, bytes.length);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

}
