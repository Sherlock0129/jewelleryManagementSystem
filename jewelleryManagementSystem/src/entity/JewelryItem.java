package entity;

import tool.MyDoubleLinkedList;

import java.io.Serializable;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;


public class JewelryItem implements Serializable {

    //描述
    private String description;

    //类型
    private String type;

    //目标性别
    private String targetGender;

    //图片URL
    private String imageUrl;

    //价格
    private double price;

    private MyDoubleLinkedList<Component> components;// 容器 存成分

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTargetGender() {
        return targetGender;
    }

    public void setTargetGender(String targetGender) {
        this.targetGender = targetGender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MyDoubleLinkedList<Component> getComponents() {
        return components;
    }

    public void setComponents(MyDoubleLinkedList<Component> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return "{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", targetGender='" + targetGender + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", components=" + components.toString() +
                '}';
    }



    public String toStringSearch() {
        return "{" +
                "description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", targetGender='" + targetGender + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                '}';
    }

    //对比相似度
    public static int compare(JewelryItem j1, JewelryItem j2){
        try{
            if (j1 != null && j2 != null){
                int score = 0;
                // 类型得分,权重最高
                if (j1.getType() == j2.getType()){
                    score = 9 * 10000 + score;
                }

                // 性别得分
                if (j1.getTargetGender() == j2.getTargetGender()){
                    score = 9 * 1000 + score;
                }

                //价格得分
                double pricediff = 0;
                if (abs(j1.getPrice()) < abs(j2.getPrice())) {
                    pricediff = abs(j1.getPrice())/abs(j2.getPrice());
                } else {
                    pricediff = abs(j2.getPrice())/abs(j1.getPrice());
                }
                score = (int) (ceil(pricediff * 9) * 100);

                //材质得分
                int sameComponent = 0;
                MyDoubleLinkedList<Component> c1 = j1.getComponents();
                MyDoubleLinkedList<Component> c2 = j2.getComponents();
                if (c1.size() > 0 && c2.size() >0 ){
                    for (int i = 0; i < c1.size(); i++) {
                        for (int j = 0; j < c2.size(); j++) {
                            if (c1.get(i).getName() == c2.get(i).getName()) {
                                sameComponent++;
                                if (sameComponent > 9) { // 9种材料相同，该项得满分
                                    break;
                                }
                            }
                        }
                    }
                }

                score = sameComponent * 10 + score;
                return score;
            }
            return -1;
        } catch (Exception e){
            System.out.print("珠宝对比异常");
            return -1;
        }
    }
}
