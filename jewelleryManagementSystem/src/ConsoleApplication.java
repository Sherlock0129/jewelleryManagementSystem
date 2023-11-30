import entity.Component;
import entity.DisplayCase;
import entity.DisplayTray;
import entity.JewelryItem;
import service.JewelryStore;
import tool.MyDoubleLinkedList;
import tool.MyHashMap;
import tool.MySet;

import java.util.Scanner;
import java.util.InputMismatchException;


public class ConsoleApplication {

    private static  JewelryStore store = new JewelryStore();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 选项菜单
        while (true) {
            System.out.println("1. Add display case");
            System.out.println("2. Show all display case");
            System.out.println("3. Add display tray");
            System.out.println("4. Show all display tray");
            System.out.println("5. Add jewelry item");
            System.out.println("6. Smart Add jewelry item");
            System.out.println("7. Delete jewelry item");
            System.out.println("8. Search jewelry items");
            System.out.println("9. Show all jewelry items");
            System.out.println("10. show stock");
            System.out.println("11. clear all");
            System.out.println("12. save as file");
            System.out.println("13. load from file");
            System.out.println("14. sort Tray");
            System.out.println("15. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (choice) {
                    case 1:
                        addDisplayCase(scanner);
                        break;
                    case 2:
                        showAllDisplayCase();
                        break;
                    case 3:
                        addDisplayTray(scanner);
                        break;
                    case 4:
                        showAllDisplayTray();
                        break;
                    case 5:
                        addJewelryItem(scanner);
                        break;
                    case 6:
                        smartAddJewelryItem(scanner);
                        break;
                    case 7:
                        deleteJewelryItem(scanner);
                        break;
                    case 8:
                        searchJewelryItems(scanner);
                        break;
                    case 9:
                        showAllJewelryItems();
                        break;
                    case 10:
                        showStock();
                        break;
                    case 11:
                        clearAllJewelryItems();
                        break;
                    case 12:
                        saveData();
                        break;
                    case 13:
                        loadData();
                        break;
                    case 14:
                        sortTray();
                        break;
                    case 15:
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void addDisplayCase(Scanner scanner) {
        System.out.print("Enter case id: ");
        String id = scanner.nextLine();
        System.out.print("Enter case type: 1.wall-mounted  2.freestanding" +"\n");
        int chosenType = scanner.nextInt();
        String type;
        if(chosenType == 1){
            type = "wall-mounted";
        }else if(chosenType == 2){
             type = "freestanding";
        }else {
            System.out.println("Please entry true number.");
            return;
        }
        DisplayCase displayCase = new DisplayCase();
        try {
            System.out.print("Is light on (true/false): ");
            Boolean isLightOn = scanner.nextBoolean();
            displayCase.setLightOn(isLightOn);
        }catch (InputMismatchException e){
            System.out.println("Please entry true or false");
            return;
        }
        displayCase.setId(id);
        displayCase.setType(type);
        store.addDisplayCase(displayCase);

    }

    private static void showAllDisplayCase() {
        store.showAllDisplayCase();
    }

    private static void addDisplayTray(Scanner scanner) {
        System.out.print("Enter case id: ");
        String caseId = scanner.nextLine();
        System.out.print("Enter tray id: ");
        String id = scanner.nextLine();

        //判断ID是否符合规范要求
        try{
            if (id.length() == 0 ){
                throw new RuntimeException();
            }
            char[] idCharArray = id.toCharArray();
            for (int i = 0; i < idCharArray.length; i++) {
                if (i == 0){
                    if (idCharArray[i] < 65 || idCharArray[i] > 90) {   //大写字母对应的是65-90。小写字母对应的是96-122
                        throw new RuntimeException();
                    }
                } else {
                    if (idCharArray[i] < 48 || idCharArray[i] > 57) {   //数字0-9对应48-57
                        throw new RuntimeException();
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("请输入大写英文+数字组合，例如：A12");
            return;
        }

        System.out.print("Enter tray color: ");
        String color = scanner.nextLine();
        System.out.print("Enter tray width: ");
        int width = scanner.nextInt();
        System.out.print("Enter tray height: ");
        int height = scanner.nextInt();
        scanner.nextLine();
        DisplayTray tray = new DisplayTray();
        tray.setId(id);
        tray.setColor(color);
        tray.setWidth(width);
        tray.setHeight(height);
        store.addDisplayTray(caseId, tray);
    }

    private static void showAllDisplayTray() {
        store.showAllDisplayTray();
    }

    private static void addJewelryItem(Scanner scanner) {
        System.out.print("Enter case id: ");
        String caseId = scanner.nextLine();
        System.out.print("Enter tray id: ");
        String trayId = scanner.nextLine();
        System.out.print("Enter item description: ");
        String description = scanner.nextLine();
        System.out.print("Enter item type: ");
        String type = scanner.nextLine();
        System.out.print("Enter item gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter item image url: ");
        String imageUrl = scanner.nextLine();
        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();
        JewelryItem item = new JewelryItem();
        item.setDescription(description);
        item.setType(type);
        item.setTargetGender(gender);
        item.setImageUrl(imageUrl);
        item.setPrice(price);
        MyDoubleLinkedList<Component> components = new MyDoubleLinkedList<>();
        System.out.println("Enter item components ");
        while (true) {  // 循环添加珠宝的成分信息
            Component component = new Component();
            scanner.nextLine();
            System.out.println("Enter component name: ");
            String name = scanner.nextLine();
            System.out.println("Enter component description: ");
            String desc = scanner.nextLine();
            System.out.println("Enter component count: ");
            int count = scanner.nextInt();
            component.setName(name);
            component.setDescription(desc);
            component.setCount(count);
            components.add(component);
            try {
                System.out.print("Is continue (true/false): ");
                boolean isContinue = scanner.nextBoolean(); // 是否继续添加
                if (!isContinue) {
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("Please entry true or false");
                return;
            }

        }
        item.setComponents(components);
        store.addJewelryItem(caseId, trayId, item);
    }

    private static void smartAddJewelryItem(Scanner scanner) {
        System.out.print("Enter item description: ");
        String description = scanner.nextLine();
        System.out.print("Enter item type: ");
        String type = scanner.nextLine();
        System.out.print("Enter item gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter item image url: ");
        String imageUrl = scanner.nextLine();
        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        JewelryItem item = new JewelryItem();
        item.setDescription(description);
        item.setType(type);
        item.setTargetGender(gender);
        item.setImageUrl(imageUrl);
        item.setPrice(price);
        MyDoubleLinkedList<Component> components = new MyDoubleLinkedList<>();
        System.out.println("Enter item components ");
        while (true) {
            Component component = new Component();
            System.out.println("Enter component name: ");
            String name = scanner.nextLine();
            System.out.println("Enter component description: ");
            String desc = scanner.nextLine();
            System.out.println("Enter component count: ");
            int count = scanner.nextInt();
            component.setName(name);
            component.setDescription(desc);
            component.setCount(count);
            components.add(component);
            try {
                System.out.print("Is continue (true/false): ");
                boolean isContinue = scanner.nextBoolean(); // 是否继续添加
                if (!isContinue) {
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("Please entry true or false");
                return;
            }

        }
        item.setComponents(components);
        // *** 以上同addJewelryItem
        String caseId = "";
        String trayId = "";
        MyHashMap<String, DisplayCase> displayCaseMyHashMap = store.showAllDisplayCase();

        //遍历所有的珠宝，找到相似度最高的珠宝
        int tempScore = 0;
        for (String s : displayCaseMyHashMap.keySet()) {    // 遍历所有的展示柜
            MyDoubleLinkedList<DisplayTray> tempTrays = displayCaseMyHashMap.get(s).getTrays();
            if (tempTrays != null && tempTrays.size() != 0) {
                for (int i = 0; i < tempTrays.size(); i++) {
                    MyDoubleLinkedList<JewelryItem> tempItems = tempTrays.get(i).getItems();
                    if (tempItems != null && tempItems.size() != 0) {
                        for (int j = 0; j < tempItems.size(); j++) {
                            //对比得分，高于原得分则保留珠宝及得分
                            int compareScore = JewelryItem.compare(item, tempItems.get(j));
                            if (compareScore > tempScore && compareScore > 120) { //类型、性别不同情况下，至少满足金额差异10倍以内，两种元素相同
                                tempScore = compareScore;
                                caseId = displayCaseMyHashMap.get(s).getId();
                                trayId = tempTrays.get(i).getId();
                            }
                        }
                    }
                }
            }
        }
        //无任何珠宝相似，放最后
        if (tempScore == 0) {
            MySet<String> keys = displayCaseMyHashMap.keySet();
            DisplayCase tempCase =  displayCaseMyHashMap.get((String) keys.toArray()[keys.size() - 1]);
            caseId = tempCase.getId();
            MyDoubleLinkedList<DisplayTray> tempTrays = tempCase.getTrays();
            trayId = tempTrays.get(tempTrays.size() - 1).getId();
        }

        store.addJewelryItem(caseId, trayId, item);
    }

    private static void deleteJewelryItem(Scanner scanner) {
        System.out.print("Enter item description: ");
        String description = scanner.nextLine();
        System.out.print("Enter item type: ");
        String type = scanner.nextLine();
        System.out.print("Enter item gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();
        store.deleteJewelryItem(description, type, gender, price);
    }

    private static void searchJewelryItems(Scanner scanner) {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        MySet<JewelryItem> results = store.searchJewelryItems(keyword);
        MyDoubleLinkedList<JewelryItem> resultsList = new MyDoubleLinkedList<>();
        int index = 0;
        for (JewelryItem item : results) {
            index ++;
            System.out.println(index +" "+ item.toStringSearch());
            resultsList.add(item);
        }
        System.out.println("Which item do you want look more closely?");
        int chosenIndex = scanner.nextInt();
        System.out.println(chosenIndex+ "item " +resultsList.get(chosenIndex-1).getComponents().toString());

    }

    //针对所有展示盘排序（不变更展示柜）
    private static void sortTray() {
        MyHashMap<String, DisplayCase> displayCaseMyHashMap = store.showAllDisplayCase();
        System.out.println("Before sort");
        System.out.println("-------------------------------------------------");
        for (String s : displayCaseMyHashMap.keySet()) {
            MyDoubleLinkedList<DisplayTray> trays = displayCaseMyHashMap.get(s).getTrays();
            for (int i = 0; i < trays.size(); i++) {
                System.out.println(trays.get(i).toString());
            }
            // Bubble Sort for sorting trays by id
            for (int i = 0; trays != null && i < trays.size() - 1; i++) {
                for (int j = 0; j < trays.size() - i - 1; j++) {
                    DisplayTray tray1 = trays.get(j);
                    DisplayTray tray2 = trays.get(j + 1);

                    // Compare tray ids and swap if necessary
                    if (tray1.getId().hashCode() > tray2.getId().hashCode()) {
                        DisplayTray temp = tray1;
                        trays.set(j, tray2);
                        trays.set(j + 1, temp);
                    }
                }
            }
            System.out.println("After sort");
            System.out.println("-------------------------------------------------");
            for (int i = 0; i < trays.size(); i++) {
                System.out.println(trays.get(i).toString());
            }
        }
    }


    private static void showAllJewelryItems() {
        store.showAllJewelryItems();
    }

    private static void showStock() {
        store.getAllStock();
    }

    private static void clearAllJewelryItems() {
        store.clearAll();
    }

    private static void saveData() {
        store.serialize();
    }

    private static void loadData() {
        store.deserialize();
    }
}


