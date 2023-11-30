package tool;


import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class MySet<E> implements Serializable,Iterable<E> {
    // 用数组存储元素
    private E[] elements;
    private int size;

    private static final int DEFAULT_SIZE = 16;

    // 构造函数，初始化数组
    public MySet() {
        elements = (E[]) new Object[DEFAULT_SIZE];
        size = 0;
    }

    // 构造函数，初始化数组
    public MySet(int capacity) {
        elements = (E[]) new Object[capacity];
        size = 0;
    }

    // 添加元素到集合中

    public void add(E element) {
        if (size == elements.length) {
            resizeArray();
        }else if (contains(element)){
            return;
        }
        elements[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public E[] toArray() {
        E[] array = (E[]) new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = (E) elements[i];
        }
        return array;
    }

    // 从集合中删除元素

    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                // 如果找到要删除的元素，缩小数组并返回true
                for (int j = i + 1; j < size; j++) {
                    elements[j - 1] = elements[j];
                }
                size--;
                return true;
            }
        }
        return false; // 如果没找到要删除的元素，返回false
    }

    // 检查集合中是否包含指定的元素

    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return true; // 如果找到匹配的元素，返回true
            }
        }
        return false; // 如果没找到匹配的元素，返回false
    }

    private void resizeArray() {
        int newCapacity = elements.length * 2;
        Object[] newElements = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = (E[]) newElements;
    }


    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return elements[index++];
            }
        };
    }

    // 返回集合的大小

    public int size() {
        return size; // 返回集合大小
    }


}