import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase {
    private final Map<String, Integer> mainStore = new HashMap<>();
    private Map<String, Integer> tempStore = null;
    private boolean isActive = false;

    public void begin_transaction() {
        if (isActive) {
            throw new IllegalStateException("A transaction is already active.");
        }
        tempStore = new HashMap<>(mainStore);
        isActive = true;
    }

    public void put(String key, Integer value) {
        if (!isActive) {
            throw new IllegalStateException("put() cannot be called outside of a transaction.");
        }
        tempStore.put(key, value);
    }

    public Integer get(String key) {
        return mainStore.getOrDefault(key, null);
    }

    public void commit() {
        if (!isActive) {
            throw new IllegalStateException("No active transaction to commit.");
        }
        mainStore.clear();
        mainStore.putAll(tempStore);
        tempStore = null;
        isActive = false;
    }

    public void rollback() {
        if (!isActive) {
            throw new IllegalStateException("No active transaction to rollback.");
        }
        tempStore = null;
        isActive = false;
    }
};
