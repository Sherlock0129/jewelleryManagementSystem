package tool;

import java.io.Serializable;


public  class Node<E> implements Serializable {
    E data;  // 节点数据
    Node<E> prev;   // 前一个节点
    Node<E> next;   // 下一个节点

    Node(E data) {
        this.data = data;
    }

    Node() {}

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }
}

