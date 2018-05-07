package bloom.com.encryption;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Key {
    public List<String> keys = new ArrayList<>();

    public String getKey(int keyNumber) {
        return keys.get(keyNumber);
    }

    public void addKey(String key) {
        keys.add(key);
    }

    public void addKeys(List<String> _keys) {
        keys.addAll(_keys);
    }

    public int GetRndKeyNumber() {
       return new Random().nextInt(keys.size()-1);
    }
}
