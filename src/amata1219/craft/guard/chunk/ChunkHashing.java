package amata1219.craft.guard.chunk;

public class ChunkHashing {

    public static long hashing(long x, long z) {
        return ((x >> 4) << 32) ^ (z >> 4);
    }

}
