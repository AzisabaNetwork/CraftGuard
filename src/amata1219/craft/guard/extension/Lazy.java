package amata1219.craft.guard.extension;

import java.util.function.Supplier;

public class Lazy<T> {

    private Supplier<T> supplier;
    private T value;

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    protected Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T value() {
        if (value == null) {
            value = supplier.get();
            supplier = null;
        }
        return value;
    }

}
