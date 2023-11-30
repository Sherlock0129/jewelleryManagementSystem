package tool;

import java.io.Serializable;


public class MyHashMap<K, V> implements Serializable {
    private static final int DEFAULT_CAPACITY = 16; // 默认容量
    private static final float DEFAULT_LOAD_FACTOR = 0.75f; // 默认负载因子 装载因子

    private Entry<K, V>[] table;    // 存储数据的数组，键值对

    private int size;   // 元素数量

    private int threshold;  // 容量上限 6

    private float loadFactor;   // 负载因子0.6


    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);    // 默认构造函数，使用默认容量和负载因子
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR); // 指定容量的构造函数，使用默认负载因子
    }

    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            // 检查初始容量是否合法
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            // 检查负载因子是否合法
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.table = new Entry[initialCapacity];    // 初始化数组
        this.threshold = (int) (initialCapacity * loadFactor);  // 设置容量上限
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Null key is not allowed!"); // 检查是否为null的键
        }
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {    // 如果已存在相同的键，更新值
                entry.value = value;
                return;
            }
        }
        addEntry(hash, key, value, index);  // 添加新的键值对
    }

    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("Null key is not allowed!"); // 检查是否为null的键
        }
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        // 遍历链表
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (entry.key.equals(key)) {    // 查找键对应的值
                return entry.value;
            }
        }
        return null;    // 未找到对应的键
    }

    public void remove(K key) {
        if (key == null) {
            throw new NullPointerException("Null key is not allowed!"); // 检查是否为null的键
        }
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        Entry<K, V> prev = null;
        Entry<K, V> entry = table[index];
        while (entry != null) {
            if (entry.key.equals(key)) {    // 查找并移除指定键的键值对
                if (prev == null) { // 要删除的二元组是链表中第一个节点
                    table[index] = entry.next;  // 更新链表头部
                } else {
                    prev.next = entry.next;
                }
                size--; // 大小减一
                return;
            }
            prev = entry;
            entry = entry.next;
        }
    }


    public MySet<K> keySet() {
        MySet<K> set = new MySet<>();   // 创建存放键的集合
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                set.add(entry.key); // 将键添加到集合
                entry = entry.next;
            }
        }
        return set;
    }

    public int size() {
        return size;    // 返回大小
    }

    private void addEntry(int hash, K key, V value, int index) {
        Entry<K, V> entry = table[index];
        table[index] = new Entry<>(hash, key, value, entry);    // 在指定索引位置添加新的键值对
        if (++size > threshold) {
            resize(2 * table.length);   // 如果超出容量上限，调整容量
        }
    }

    public void resize(int newCapacity) {
        Entry<K, V>[] newTable = new Entry[newCapacity];    // 创建新的数组
        transfer(newTable); // 将数据从旧数组迁移到新数组
        table = newTable;   // 更新数组引用
        threshold = (int) (newCapacity * loadFactor);   // 更新容量上限
    }

    private void transfer(Entry<K, V>[] newTable) {
        int newCapacity = newTable.length;
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                Entry<K, V> next = entry.next;
                int index = indexFor(entry.hash, newCapacity);
                entry.next = newTable[index];
                newTable[index] = entry;    // 将节点迁移到新数组
                entry = next;
            }
        }
    }

    private int hash(K key) {
        return key.hashCode();  // 计算键的哈希值
    }

    /**
     * 确定哈希表（或哈希表数组）中存储位置的计算方法
     * 这行代码使用位运算来确定哈希表中的存储位置。
     * 具体来说，它执行了一个位与运算（&），将哈希值 hash 和 length - 1 进行位与操作。
     * 这将导致哈希值 hash 仅保留在 0 到 length - 1 之间的位，因为 (length - 1) 的二进制表示中所有位都为1。
     * 这个操作的结果是，无论哈希值 hash 有多大，
     * 它都会被映射到哈希表数组中的一个有效索引，范围在 0 到 length - 1 之间。
     * 用于均匀分布键值对到哈希表的不同存储位置，以提高查找效率。
     *
     * @param hash
     *
     * @param length
     * @return
     */
    private int indexFor(int hash, int length) {
        return hash & (length - 1);
        // return hash % length; 0 - length
    }

    private static class Entry<K, V> implements Serializable {
        int hash; // 该条目所关联的键的哈希值
        K key;    // 键对象
        V value;        // 值对象
        Entry<K, V> next;   // 指向下一个哈希条目，用于解决哈希冲突

        Entry() {}

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

    }
}
