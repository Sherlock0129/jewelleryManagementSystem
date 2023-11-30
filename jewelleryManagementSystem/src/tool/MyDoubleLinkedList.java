package tool;


import java.io.Serializable;

public class MyDoubleLinkedList<E> implements Serializable {
    private Node<E> head;   // 链表头部节点
    private Node<E> tail;   // 链表尾部节点
    private int size;   // 链表大小

    public Node<E> getHead() {
        return head;
    }

    public void setHead(Node<E> head) {
        this.head = head;
    }


    // 向链表尾部添加元素
    public void add(E element) {
        Node<E> newNode = new Node<>(element);  // 创建新节点
        if (head == null) { // 如果链表为空
            head = newNode; // 新节点成为头部节点
            tail = newNode; // 新节点也成为尾部节点
        } else {
            tail.next = newNode;    // 当前尾部节点的下一个节点指向新节点
            newNode.prev = tail;    // 新节点的前一个节点指向当前尾部节点
            tail = newNode; // 新节点成为新的尾部节点
        }
        size++; // 链表大小加一
    }

    // 获取指定索引位置的元素
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> current = head; // 从头部节点开始
        for (int i = 0; i < index; i++) {
            current = current.next; // 移动到指定索引位置的节点
        }
        return current.data;    // 返回该节点的数据
    }


    public void set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> current = head; // 从头部节点开始
        for (int i = 0; i < index; i++) {
            current = current.next; // 移动到指定索引位置的节点
        }

        current.data = element; // 设置该节点的数据为新值
    }

    // 从链表中删除指定元素
    public void remove(E element) {
        Node<E> current = head; // 从头部节点开始
        while (current != null) {
            if (current.data.equals(element)) { // 找到匹配的元素
                if (current == head) {  // 如果匹配的是头部节点
                    head = current.next;    // 更新头部节点
                    if (head != null) {
                        head.prev = null;
                    }
                } else if (current == tail) {   // 如果匹配的是尾部节点
                    tail = current.prev;    // 更新尾部节点
                    if (tail != null) {
                        tail.next = null;
                    }
                } else {
                    current.prev.next = current.next;   // 更新前一个节点的下一个节点
                    current.next.prev = current.prev;   // 更新下一个节点的前一个节点
                }
                size--; // 链表大小减一
                break;
            }
            current = current.next; // 继续查找下一个节点
        }
    }

    // 获取链表的大小
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("{");
        Node<E> current = head; // 从头部节点开始
        for (int i = 0; current != null && i < size(); i++) {
            buffer.append(((E) current.getData()).toString());
            if (i < size() - 1) {
                buffer.append(", ");
            }
            current = current.next; // 移动到指定索引位置的节点
        }
        buffer.append("}");
        return buffer.toString();
    }

    // 冒泡排序函数
    public static void bubbleSort(Node head) {
        if (head == null || head.next == null) {
            // 如果链表为空或只有一个节点，直接返回
            return;
        }

        Node end = null; // 用于记录已排序部分的末尾节点
        Node current = head; // 当前正在遍历的节点
        boolean swapped = true; // 标记是否发生了交换，用于优化算法

        while (swapped) {
            swapped = false; // 假设本次遍历没有发生交换
            current = head; // 从头节点开始遍历

            while (current.next != null) {
                if (current.getData().hashCode() > current.next.getData().hashCode()) {
                    // 如果当前节点值大于下一个节点值，交换它们的位置
                    Object temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true; // 发生了交换，标记为true
                } else {
                    // 当前节点值小于等于下一个节点值，继续向后遍历下一个节点
                    current = current.next;
                }
            }
            end = current; // 将当前节点作为已排序部分的末尾节点，继续下一轮遍历
        }
    }

    // 在双向链表中查找指定元素的位置
    public int binarySearch(E key) {
        int low = 0; // 定义查找范围的下界
        int high = size - 1; // 定义查找范围的上界
        while (low <= high) { // 当查找范围不为空时
            int mid = (low + high) >>> 1; // 计算中间位置
            Node midNode = node(mid); // 获取中间位置的结点
            int midVal = midNode.getData().hashCode(); // 获取中间位置的元素值
            if (midVal < key.hashCode()) { // 如果中间位置的元素值小于目标值
                low = mid + 1; // 将查找范围缩小到右半部分
            } else if (midVal > key.hashCode()) { // 如果中间位置的元素值大于目标值
                high = mid - 1; // 将查找范围缩小到左半部分
            } else { // 如果中间位置的元素值等于目标值
                return mid; // 返回中间位置
            }
        }
        return -1; // 如果未找到目标值，返回-1
    }

    // 获取指定位置的结点
    private Node node(int index) {
        if (index < (size >> 1)) { // 如果目标位置在链表前半部分
            Node node = head; // 从首节点开始向后查找
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else { // 如果目标位置在链表后半部分
            Node node = tail; // 从尾节点开始向前查找
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }
}
