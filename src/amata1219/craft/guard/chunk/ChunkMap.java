package amata1219.craft.guard.chunk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ChunkMap<V> {

    private final HashMap<Long, ArrayList<V>> map = new HashMap<>();

    public void put(long hash, V value) {
        ArrayList<V> list = map.computeIfAbsent(hash, k -> new ArrayList<>());
        list.add(value);
    }

    public List<V> get(long hash) {
        return map.containsKey(hash) ? map.get(hash) : Collections.emptyList();
    }

    public void remove(long hash, V value) {
        if (!map.containsKey(hash)) return;

        ArrayList<V> list = map.get(hash);
        list.remove(value);

        if (list.isEmpty()) map.remove(hash);
    }

}
