package test;


import org.junit.*;
import tool.MyHashMap;

public class TestMyHashMap {

    @Test
    public void testMyHashMapPut() {
        MyHashMap<String,Object> map = new MyHashMap<>();
        map.put("6666","666");
        System.out.println(map.size());
    }


    @Test
    public void testMyHashMapGet() {
        MyHashMap<String,Object> map = new MyHashMap<>();
        map.put("6666","666");
        System.out.println(map.get("6666"));
    }


    @Test
    public void testMyHashMapRemove() {
        MyHashMap<String,Object> map = new MyHashMap<>();
        map.put("6666","666");
        map.remove("6666");
        System.out.println(map.size());
    }



    @Test
    public void testMyHashMapResize() {
        MyHashMap<String,Object> map = new MyHashMap<>();
        map.resize(5);
    }
}
