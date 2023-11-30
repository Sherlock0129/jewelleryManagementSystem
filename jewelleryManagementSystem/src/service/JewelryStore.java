package service;


import entity.Component;
import entity.DisplayCase;
import entity.DisplayTray;
import entity.JewelryItem;
import tool.MyDoubleLinkedList;
import tool.MyHashMap;
import tool.MySet;
import java.io.*;


public class JewelryStore implements Serializable {

    private MyHashMap<String, DisplayCase> cases;   // 用于存储展示柜的哈希表 hashmap的key是唯一的

    private MySet<String> usedDisplayCaseIds;     // 用于跟踪已使用的展示柜的ID 集合里面的元素是唯一的

    private MySet<String> usedDisplayTrayIds;     // 用于跟踪已使用的展示盘的ID

    public JewelryStore() {
        cases = new MyHashMap<>();  // 初始化展示柜哈希表
        usedDisplayCaseIds = new MySet<>();   // 初始化已使用的展示柜ID集合
        usedDisplayTrayIds = new MySet<>();   // 初始化已使用的展示盘ID集合
    }

    public MyHashMap<String, DisplayCase> getCases() {
        return cases;
    }

    public void setCases(MyHashMap<String, DisplayCase> cases) {
        this.cases = cases;
    }

    public MySet<String> getUsedDisplayCaseIds() {
        return usedDisplayCaseIds;
    }

    public void setUsedDisplayCaseIds(MySet<String> usedDisplayCaseIds) {
        this.usedDisplayCaseIds = usedDisplayCaseIds;
    }

    public MySet<String> getUsedDisplayTrayIds() {
        return usedDisplayTrayIds;
    }

    public void setUsedDisplayTrayIds(MySet<String> usedDisplayTrayIds) {
        this.usedDisplayTrayIds = usedDisplayTrayIds;
    }

    // 添加展示柜
    public void addDisplayCase(DisplayCase displayCase) {
        if (usedDisplayCaseIds.contains(displayCase.getId())) {
            System.out.println("Error: Display case ID already exists");
            return;
        }
        cases.put(displayCase.getId(), displayCase);    // 将展示柜添加到哈希表
        usedDisplayCaseIds.add(displayCase.getId());    // 更新已使用的展示柜ID集合
    }

    // 显示所有展示柜
    public MyHashMap<String, DisplayCase> showAllDisplayCase() {
        for (String s : cases.keySet()) {
            System.out.println(cases.get(s).toString());
        }
        return cases;
    }

    // 添加展示盘
    public void addDisplayTray(String caseId, DisplayTray tray) {
        DisplayCase displayCase = cases.get(caseId);
        if (displayCase == null) {
            System.out.println("Error: Display case not found");
            return;
        }

        if (usedDisplayTrayIds.contains(tray.getId())) {
            System.out.println("Error: Display tray ID already exists");
            return;
        }

        if (displayCase.getTrays() == null) {   // 该柜中为空
            MyDoubleLinkedList<DisplayTray> displayTrays = new MyDoubleLinkedList<>();
            displayTrays.add(tray);
            displayCase.setTrays(displayTrays);
        } else {
            displayCase.getTrays().add(tray);
        }

        usedDisplayTrayIds.add(tray.getId());
    }

    // 显示所有展示盘
    public void showAllDisplayTray() {
        for (String s : cases.keySet()) {
            DisplayCase displayCase = cases.get(s);
            MyDoubleLinkedList<DisplayTray> trays = displayCase.getTrays();
            for (int i = 0; trays != null && i < trays.size(); i++) {
                System.out.println(trays.get(i).toString());
            }
        }
    }

    // 添加珠宝项
    public void addJewelryItem(String caseId, String trayId, JewelryItem item) {
        DisplayCase displayCase = cases.get(caseId);
        if (displayCase == null) {
            System.out.println("Error: Display case not found");
            return;
        }

        MyDoubleLinkedList<DisplayTray> trays = displayCase.getTrays();
        if (trays == null || trays.size() == 0) {
            System.out.println("Error: Display case not have this tray");
            return;
        }
        boolean flag = false;
        DisplayTray tray = trays.get(0);
        for (int i = 0; trays != null && i < trays.size(); i++) {
            tray = trays.get(i);
            if (tray.getId().equals(trayId)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Error: Display case not have this tray");
            return;
        }

        if (tray.getItems() == null) {  // 若该盘中为空
            MyDoubleLinkedList<JewelryItem> jewelryItems = new MyDoubleLinkedList<>();
            jewelryItems.add(item);
            tray.setItems(jewelryItems);
        } else {
            tray.getItems().add(item);
        }
    }

    // 搜索珠宝项
    public MySet<JewelryItem> searchJewelryItems(String keyword) {
        MySet<JewelryItem> results = new MySet<>();
        MySet<String> keySet = cases.keySet();
        for (String s : keySet) {   // 根据key值遍历所有的珠宝柜
            DisplayCase displayCase = cases.get(s);
            MyDoubleLinkedList<DisplayTray> trays = displayCase.getTrays(); // 获得该柜的所有展示盘
            for (int i = 0; trays != null && i < trays.size(); i++) {    // 遍历展示盘
                MyDoubleLinkedList<JewelryItem> items = trays.get(i).getItems(); // 获得该展示盘中所有珠宝
                for (int j = 0; items != null && j < items.size(); j++) { // 遍历珠宝
                    JewelryItem item = items.get(j);
                    if (item.getDescription().contains(keyword)) {  // 如果该珠宝的描述信息中包含搜索的关键词
                        results.add(item);      // 将该珠宝添加到结果集中
                        continue;
                    }
                    MyDoubleLinkedList<Component> components = item.getComponents();    // 获得该珠宝的组成信息
                    for (int k = 0; components != null && k < components.size(); k++) {   // 遍历组成信息
                        Component component = components.get(k);
                        if (component.getName().contains(keyword) ||
                                component.getDescription().contains(keyword)) {    // 组成成分姓名包含关键词
                            results.add(item);
                            break;
                        }
                    }
                }
            }
        }
        return results;
    }

    // 展示所有的珠宝
    public void showAllJewelryItems() {
        for (String s : cases.keySet()) {   // 遍历所有的展示柜
            DisplayCase displayCase = cases.get(s);
            System.out.println("case:" + s.toString());
            MyDoubleLinkedList<DisplayTray> trays = displayCase.getTrays(); // 获得该柜中的所有展示盘
            for (int i = 0; trays != null && i < trays.size(); i++) {    // 遍历该展示柜中的所有展示盘
                DisplayTray tray = trays.get(i);
                System.out.println("tray:" + tray.toString());
                MyDoubleLinkedList<JewelryItem> items = tray.getItems();    // 获得该展示盘中所有的珠宝
                for (int j = 0; items != null && j < items.size(); j++) {
                    System.out.println("items:" + items.get(j).toString());
                }
            }
        }
    }

    // 删除一个珠宝
    public void deleteJewelryItem(String description, String type, String gender, double price) {
        for (String s : cases.keySet()) {   // 遍历所有的展示柜
            DisplayCase displayCase = cases.get(s);
            MyDoubleLinkedList<DisplayTray> trays = displayCase.getTrays(); // 获得该柜中的所有展示盘
            for (int i = 0; trays != null && i < trays.size(); i++) {    // 遍历该展示柜中的所有展示盘
                DisplayTray tray = trays.get(i);
                MyDoubleLinkedList<JewelryItem> items = tray.getItems();    // 获得该展示盘中所有的珠宝
                for (int j = 0; items != null && j < items.size(); j++) {    // 遍历该展示盘中所有的珠宝
                    JewelryItem item = items.get(j);
                    // 根据相关信息移除一个珠宝
                    if (item.getDescription().equals(description) && item.getType().equals(type) && item.getTargetGender().equals(gender) && item.getPrice() == price) {
                        items.remove(item);
                        tray.setItems(items);
                        displayCase.setTrays(trays);
                        cases.put(s, displayCase);
                        System.out.println("remove success");
                    }
                }
            }
        }
    }

    // Value stock facility
    public void getAllStock() {
        int allCount = 0;
        double allPrice = 0;
        for (String s : cases.keySet()) {   // 遍历所有的展示柜
            int caseCount = 0;
            double casePrice = 0;
            DisplayCase displayCase = cases.get(s);
            MyDoubleLinkedList<DisplayTray> trays = displayCase.getTrays(); // 获得该柜中的所有展示盘
            for (int i = 0; trays != null && i < trays.size(); i++) {    // 遍历该展示柜中的所有展示盘
                int trayCount = 0;
                double trayPrice = 0;
                DisplayTray tray = trays.get(i);
                MyDoubleLinkedList<JewelryItem> items = tray.getItems();    // 获得该展示盘中所有的珠宝
                for (int j = 0; items != null && j < items.size(); j++) {    // 遍历该展示盘中所有的珠宝
                    JewelryItem item = items.get(j);

                    trayCount++;
                    caseCount++;
                    allCount++;
                    trayPrice += item.getPrice();
                    casePrice += item.getPrice();
                    allPrice += item.getPrice();
                }
                System.out.println(tray + " -> count: " + trayCount + " price: " + trayPrice);
            }
            System.out.println(displayCase + " -> count: " + caseCount + " price: " + casePrice);
        }
        System.out.println("all -> count: " + allCount + " price: " + allPrice);
    }

    // 清空
    public void clearAll() {
        cases = new MyHashMap<>();
        System.out.println("clear all items success.");
        File file = new File("store.txt");
        // 检查文件是否存在
        if (file.exists()) {
            // 尝试删除文件
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("file has been deleted.");
            } else {
                System.err.println("Failed to delete file.");
            }
        }
    }

    // 序列化
    public void serialize() {
        try {
            // 打开文件 'store.txt' 以进行序列化
            FileOutputStream fileOut = new FileOutputStream("store.txt");
            // 创建一个对象输出流
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            // 将当前对象写入输出流
            out.writeObject(this);
            // 关闭输出流
            out.close();
            // 关闭文件输出流
            fileOut.close();
            // 打印成功的消息
            System.out.println("数据保存成功");
        } catch (IOException e) {
            System.err.println("数据保存失败");
        }
    }

    // 反序列化
    public void deserialize() {
        try {
            // 打开文件 'store.txt' 以进行反序列化
            FileInputStream fileIn = new FileInputStream("store.txt");
            // 创建一个对象输入流
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // 从输入流中读取对象并进行类型转换
            JewelryStore obj = (JewelryStore) in.readObject();
            // 关闭输入流
            in.close();
            // 关闭文件输入流
            fileIn.close();
            // 将反序列化后的数据设置给当前对象
            this.cases = obj.cases;
            this.usedDisplayCaseIds = obj.getUsedDisplayCaseIds();
            this.usedDisplayTrayIds = obj.getUsedDisplayTrayIds();
            // 打印成功的消息
            System.out.println("数据读取成功");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("数据读取失败");
        }
    }
}
