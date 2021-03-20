package org.liuzs.calculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//用于储存数据模型
public class MyViewModel extends ViewModel {

    private MutableLiveData<String> mainNum;//主数值(用户正在操作的数值)

    public String num[] = {"",""};//存储参与计算的数值
    public String sign1 = "" ,sign2 = "";//用于存储运算符号,展示在显示框

    public boolean havePoint = false;//主数值中是否含有小数点

    //获取主数值
    public MutableLiveData<String> getMainNum() {
        if (mainNum == null) {
            mainNum = new MutableLiveData<>();
            mainNum.setValue("0");
        }
        return mainNum;
    }

    //设置主数值
    public void setMainNum(String num) {
        if (mainNum.getValue().equals("0")) {//当主数值是0时，直接替换传进来的值
            mainNum.setValue(num);
        } else {//否则直接在末尾加上新数值即可
            mainNum.setValue(mainNum.getValue() + num);
        }
    }

    //第一种计算方法，用于一个运算符的情况
    public String mainNumWithNum_0_Tocal() {
        String value = "0";
        if (mainNum.getValue().contains(".") || num[0].contains(".")) {//如果两个数字的其中一个有小数点的话
            switch (sign1) {
                case "+":
                    value = String.valueOf(Double.valueOf(num[0]) + Double.valueOf(mainNum.getValue()));
                    break;
                case "-":
                    value = String.valueOf(Double.valueOf(num[0]) - Double.valueOf(mainNum.getValue()));
                    break;
                case "x":
                    value = String.valueOf(Double.valueOf(num[0]) * Double.valueOf(mainNum.getValue()));
                    break;
                case "÷":
                    if (mainNum.getValue().equals("0")) {
                        mainNum.setValue("1");//分母不能为0，强制给个1
                    }
                    value = String.valueOf(Double.valueOf(num[0]) / Double.valueOf(mainNum.getValue()));
            }
        } else {
            //没有小数点则是int
            switch (sign1) {
                case "+":
                    value = String.valueOf(Integer.valueOf(num[0]) + Integer.valueOf(mainNum.getValue()));
                    break;
                case "-":
                    value = String.valueOf(Integer.valueOf(num[0]) - Integer.valueOf(mainNum.getValue()));
                    break;
                case "x":
                    value = String.valueOf(Integer.valueOf(num[0]) * Integer.valueOf(mainNum.getValue()));
                    break;
                case "÷":
                    if (mainNum.getValue().equals("0")) {
                        mainNum.setValue("1");//分母不能为0，强制给个1
                    }
                    value = String.valueOf(Double.valueOf(num[0]) / Double.valueOf(mainNum.getValue()));
            }
        }
        return value;
    }

    public String mainNumWithNum_1_Tocal(){
        String value = "0";
        if (mainNum.getValue().contains(".") || num[1].contains(".")) {//如果两个数字的其中一个有小数点的话
            switch (sign2) {
                case "x":
                    value = String.valueOf(Double.valueOf(num[1]) * Double.valueOf(mainNum.getValue()));
                    break;
                case "÷":
                    if (mainNum.getValue().equals("0")) {
                        mainNum.setValue("1");//分母不能为0，强制给个1
                    }
                    value = String.valueOf(Double.valueOf(num[1]) / Double.valueOf(mainNum.getValue()));
            }
        } else {
            //没有小数点则是int
            switch (sign2) {
                case "x":
                    value = String.valueOf(Integer.valueOf(num[1]) * Integer.valueOf(mainNum.getValue()));
                    break;
                case "÷":
                    if (mainNum.getValue().equals("0")) {
                        mainNum.setValue("1");//分母不能为0，强制给个1
                    }
                    value = String.valueOf(Double.valueOf(num[1]) / Double.valueOf(mainNum.getValue()));
            }
        }
        return value;
    }
}
