package cn.com.zhsz.eco.util;
/**
 * AES 128bit 加密解密工具类
 *
 * @author dufy
 */


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @explain : 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
 * @Author : chao
 * @CreationDate : 2019/3/1
 */
public class AESUtil {

    private static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"NoPadding PkcsPadding
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.getEncoder().encodeToString(encrypted);
//            return  Base64.encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = Base64.getDecoder().decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) {
        logger.debug("加密==============" + data);
        if (StringUtils.isBlank(data)) {
            logger.error("加密参数为空==============");
            return "{}";
        }
        try {
            return encrypt(data.trim(), Constant.KEY, Constant.IV);
        } catch (Exception e) {
            logger.error("加密参数不合法==============" + data, e);
        }
        return "{}";
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) {
        logger.debug("解密==============" + data);
        if (StringUtils.isBlank(data)) {
            logger.error("解密参数为空==============");
            return "{}";
        }
        try {
            String str = desEncrypt(data.trim(), Constant.KEY, Constant.IV).trim();
            logger.debug("结果==============" + str);
            return str;
        } catch (Exception e) {
            logger.error("解密参数不合法==============" + data, e);
        }
        return "{}";
    }

}