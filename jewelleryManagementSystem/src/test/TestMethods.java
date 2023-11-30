package test;

import tool.MyDoubleLinkedList;
import org.junit.Test;


public class TestMethods {



    @Test
    public void testSort() {
        MyDoubleLinkedList<Object> list = new MyDoubleLinkedList<>();
        list.add("A1");
        list.add("B1");
        list.add("B02");
        list.add("C01");
        list.add("C04");
        list.add("A01");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString() + "    ,   hasecode :"  + list.get(i).toString().hashCode());
        }
        System.out.println("--------------------------------------排序后----------------------------------");
        MyDoubleLinkedList.bubbleSort(list.getHead());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString() + "    ,   hasecode :"  + list.get(i).toString().hashCode());
        }
    }

    @Test
    public void testSearch() {
        MyDoubleLinkedList<Object> list = new MyDoubleLinkedList<>();
        list.add("A01");
        list.add("B01");
        list.add("B02");
        list.add("C01");
        list.add("C04");
        list.add("A01");

        MyDoubleLinkedList.bubbleSort(list.getHead());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString() + "    ,   hasecode :"  + list.get(i).toString().hashCode());
        }
        int i =  list.binarySearch("B02");
        System.out.println("--------------------------------------查找结果:"+ i +"----------------------------------" );



    }

}
