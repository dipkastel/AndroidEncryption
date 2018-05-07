package bloom.com.encryption;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Enc {
    Key key = new Key();
    public Enc(List<String> _Keys) {
         key.addKeys(_Keys);
    }

    /**
     * Encrypt return value as string
     *
     * @param  INPUT String to Encrypt
     * @since  1.0
     */
    public String Encrypt(String INPUT) {
        int KeyNumber = key.GetRndKeyNumber();
        Integer[] data = new Integer[0];
        try {
            data = Encrypt(KeyMaker(key.getKey(KeyNumber)), ByteArrayToIntArray(INPUT.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(intarrayToByteArray(data), Base64.DEFAULT).replace("\n","")+"#"+KeyNumber;
    }
    /**
     * Encrypt return value as string
     * don't remember append key number after returned String
     * like EncryptedData#keynumber
     *
     * @param  key Enc key.
     * @param  INPUT String to Encrypt
     * @since  1.0
     */
    public String Encrypt(String key, String INPUT) throws Exception {

        Integer[] data = Encrypt(KeyMaker(key), ByteArrayToIntArray(INPUT.getBytes("UTF-8")));
        return Base64.encodeToString(intarrayToByteArray(data), Base64.DEFAULT);
    }

    /**
     * Decript return value as string
     *
     * @param  data Encrypted String
     * @since  1.0
     */
    public String Decrypt(String data) {

        String[] SplitedData = data.split("#");

        byte[] decoded = Base64.decode(SplitedData[0], Base64.DEFAULT);
        Integer[] fixed = new Integer[decoded.length];
        for (int i = 0; i < decoded.length; i++) {
            fixed[i] = byteToInt(decoded[i]);
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return new String(intarrayToByteArray(Decrypt(KeyMaker(key.getKey(Integer.valueOf(SplitedData[1]))), fixed)), StandardCharsets.UTF_8);
            }else{
                return new String(intarrayToByteArray(Decrypt(KeyMaker(key.getKey(Integer.valueOf(SplitedData[1]))), fixed)), StandardCharsets.UTF_8);
            }


        }catch (Exception e){
            return "Decrypt Error";
        }
    }
    /**
     * Decript return value as string
     *
     * @param  key Enc key.
     * @param  data Encrypted String
     * @since  1.0
     */
    public String Decrypt(String key, String data) {


        byte[] decoded = Base64.decode(data, Base64.DEFAULT);
        Integer[] fixed = new Integer[decoded.length];
        for (int i = 0; i < decoded.length; i++) {
            fixed[i] = byteToInt(decoded[i]);
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return new String(intarrayToByteArray(Decrypt(KeyMaker(key), fixed)), StandardCharsets.UTF_8);
            } else {
                return new String(intarrayToByteArray(Decrypt(KeyMaker(key), fixed)), StandardCharsets.UTF_8);
            }

        }catch (Exception e){
            return "Decrypt Error";
        }
    }

    private static Integer[] Encrypt(Integer[] pwd, Integer[] data){
        int a, i, j, k, tmp;
        int[] key, box;
        Integer[] cipher;

        key = new int[256];
        box = new int[256];
        cipher = new Integer[data.length];

        for (i = 0; i < 256; i++)
        {
            key[i] = pwd[i % pwd.length];
            box[i] = i;
        }
        for (j = i = 0; i < 256; i++)
        {
            j = (j + box[i] + key[i]) % 256;
            tmp = box[i];
            box[i] = box[j];
            box[j] = tmp;
        }
        for (a = j = i = 0; i < data.length; i++)
        {
            a++;
            a %= 256;
            j += box[a];
            j %= 256;
            tmp = box[a];
            box[a] = box[j];
            box[j] = tmp;
            k = box[((box[a] + box[j]) % 256)];
            cipher[i] = (Integer)(data[i] ^ k);
        }
        ArrayList array = new ArrayList<Integer>();
        for (int x :cipher){
            if(x>0)
                array.add(x);
        }
        Integer[] bytes = new Integer[array.size()];
        for (int d = 0;d<array.size();d++){
            bytes[d]=(Integer) array.get(d);
        }
        return bytes;
    }

    private static Integer[] Decrypt(Integer[] pwd, Integer[] data)
    {
        return Encrypt(pwd, data);
    }

    private Integer[] KeyMaker(String keyString) {
        Integer[] key = new Integer[0];
        try {
            key = ByteArrayToIntArray(keyString.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (key.length < 1 || key.length > 256)
        {
            return null;
        }

        int keylen = key.length;
        Integer[] S = new Integer[256];
        Integer[] T = new Integer[256];

        for (int i = 0; i < 256; i++)
        {
            S[i] = i;
            T[i] = key[i % keylen];
        }
        int j = 0;
        int tmp;
        Integer[] h = new Integer[256];
        for (int i = 0; i < 256; i++)
        {
            int si,ti;
            si = S[i]>0?S[i]:S[i]+256;
            ti = T[i]>0?T[i]:T[i]+256;

            j = (j + si +ti) & 0xFF;
            tmp = S[j];
            S[j] = S[i];
            S[i] =  tmp;
            h[i]=S[i];
        }
        return S;
    }

    private int byteToInt(byte b){
        return (b>0)?b:(256+(int) b);

    }

    private Integer[] ByteArrayToIntArray(byte[] b){
        Integer[] in = new Integer[b.length];
        for(int i=0;i<b.length;i++){
            in[i] = (int) b[i];
        }
        return in;
    }

    private byte[] intarrayToByteArray(Integer[] b){
        byte[] in = new byte[b.length];
        for(int i=0;i<b.length;i++){
            in[i] = (byte) ((int) b[i]);
        }
        return in;
    }
}