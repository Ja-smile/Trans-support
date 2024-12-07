import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase {
    private final Map<String, Integer> mainStore = new HashMap<>();
    private Map<String, Integer> tempStore = null;
    private boolean isActive = false;

    public void begin_transaction() {
        if (isActive) {
            System.out.println("A transaction is already active.");
            return;
        }
        tempStore = new HashMap<>(mainStore);
        isActive = true;
    }

    public void put(String key, Integer value) {
        if (!isActive) {
            System.out.println("put() cannot be called outside of a transaction.");
            return;
        }
        tempStore.put(key, value);
    }

    public Integer get(String key) {
        return mainStore.getOrDefault(key, null);
    }

    public void commit() {
        if (!isActive) {
            System.out.println("No active transaction to commit.");
            return;
        }
        mainStore.clear();
        mainStore.putAll(tempStore);
        tempStore = null;
        isActive = false;
    }

    public void rollback() {
        if (!isActive) {
            System.out.println("No active transaction to rollback.");
            return;
        }
        tempStore = null;
        isActive = false;
    }
};
