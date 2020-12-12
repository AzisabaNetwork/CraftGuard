package amata1219.craft.guard.chunk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ChunkMap<V> {

    private final HashMap<Long, ArrayList<V>> state = new HashMap<>();

    public void put(long hash, V value) {
        ArrayList<V> list = state.computeIfAbsent(hash, k -> new ArrayList<>());
        list.add(value);
    }

    public void remove(long hash, V value) {
        if (!state.containsKey(hash)) return;

        ArrayList<V> list = state.get(hash);
        list.remove(value);

        if (list.isEmpty()) state.remove(hash);
    }

    public List<V> get(long hash) {
        return state.containsKey(hash) ? state.get(hash) : Collections.emptyList();
    }

}
