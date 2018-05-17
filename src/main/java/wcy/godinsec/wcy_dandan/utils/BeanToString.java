package wcy.godinsec.wcy_dandan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BeanToString {

    private static BeanToString instance;

    private static final String DEFAULT_NAME = "channel.xml";
    private String name = DEFAULT_NAME;

    public static BeanToString getInstance(){
        if(instance == null){
            synchronized (BeanToString.class){
                if(instance == null){
                    instance = new BeanToString();
                }
            }
        }
        return instance;
    }

    public  void putBean(Context context, String key, Object obj) {
        SharedPreferences sp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();


        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(),
                        0));
                editor.putString(key, string64).commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public  Object getBean(Context context, String key,String defValue) {
        SharedPreferences sp = context.getSharedPreferences(name,Context.MODE_PRIVATE);

        Object obj = null;
        try {
            String base64 = sp.getString(key, defValue);
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {

        }
        return obj;
}


    public void remove(Context mContext,String key){
        SharedPreferences sp = mContext.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (key!=null){
            editor.remove(key);
            editor.commit();
        }
    }

}
