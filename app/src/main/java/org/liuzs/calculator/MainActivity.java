package org.liuzs.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import org.liuzs.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;//组件存储类
    private MyViewModel myViewModel;//数据模型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);//自动化封装，获取所有组件的id
        //获取数据模型
        myViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);


        //事件监听getMainNum()
        myViewModel.getMainNum().observe(this, new Observer<String>() {
            @Override
            //监听mainnum 数据发生改变时
            public void onChanged(String s) {

                binding.myTextView.setText(myViewModel.getMainNum().getValue());

                //让textview小窗口显示表达式子
                if (myViewModel.sign2.equals("")){//如果不存在第二个计算符号，有两种情况
                    if (myViewModel.sign1.equals("")){//且第一个计算符号也不存在
                        //就在计算过程框中展示：比如输入1就展示1，输入12就展示12
                        binding.textView.setText(myViewModel.getMainNum().getValue());
                    }else {
                        //且如果是像a+b这样的式子时，就展示num的第一个数字 + 第一个计算符号 + 输入的第二数字
                        binding.textView.setText(myViewModel.num[0] + myViewModel.sign1 + myViewModel.getMainNum().getValue());
                    }
                }else {//否则就是像a+b*c这样的式子时，就展示num的第一个数字 + 第一个计算符号 + 输入的第二数字 + 第二个计算符号 + 第二次输入的数字
                    binding.textView.setText(myViewModel.num[0] + myViewModel.sign1 + myViewModel.num[1] + myViewModel.sign2 + myViewModel.getMainNum().getValue());
                }
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setMainNum("1");
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setMainNum("2");
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setMainNum("3");
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setMainNum("4");
            }
        });

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setMainNum("5");
            }
        });

        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setMainNum("6");
            }
        });

        binding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setMainNum("7");
            }
        });

        binding.button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setMainNum("8");
            }
        });

        binding.button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setMainNum("9");
            }
        });

        binding.button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.setMainNum("0");
            }
        });

        binding.buttonPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myViewModel.havePoint){//true执行
                    myViewModel.getMainNum().setValue(myViewModel.getMainNum().getValue()+".");
                    myViewModel.havePoint = true;//当没有小数点的时候加上小数点，然后再按小数点的时候不执行
                }
            }
        });

        //加法和减法的计算方法一致
        binding.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewModel.sign1.equals("")){
                    myViewModel.sign1="+";
                    myViewModel.num[0] = myViewModel.getMainNum().getValue();
                    myViewModel.getMainNum().setValue("0");
                    //防止第二个小数点出现
                    myViewModel.havePoint = false;

                }else if (myViewModel.sign2.equals("")){//第二次按+号、当没有第二个加号的时候需要计算前面的数字之和
                    myViewModel.num[0] = myViewModel.mainNumWithNum_0_Tocal();
                    myViewModel.sign1="+";
                    myViewModel.getMainNum().setValue("0");
                    myViewModel.havePoint =false;

                }else {//否则就是两个运算符号都不为空，那么就得考虑 a+b*c 的情况
                    System.out.println("加法：运算符都存在的情况，a+b*c");
                    //第三个运算符为+号，计算出总结果；
                    myViewModel.getMainNum().setValue(myViewModel.mainNumWithNum_1_Tocal());
                    //初始化第二个运算符和num1
                    myViewModel.sign2="";
                    myViewModel.num[1] = "";
                    //把剩下的num0计算出来，初始化第一个+号
                    myViewModel.num[0] = myViewModel.mainNumWithNum_0_Tocal();
                    myViewModel.sign1 = "+";
                    //初始化并展示第二个待输入的数字为0
                    myViewModel.getMainNum().setValue("0");
                    myViewModel.havePoint =false;
                }
            }
        });

        binding.buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewModel.sign1.equals("")){
                    myViewModel.sign1="-";
                    myViewModel.num[0] = myViewModel.getMainNum().getValue();
                    myViewModel.getMainNum().setValue("0");
                    myViewModel.havePoint = false;
                }else if (myViewModel.sign2.equals("")){
                    myViewModel.num[0] = myViewModel.mainNumWithNum_0_Tocal();
                    myViewModel.sign1="-";
                    myViewModel.getMainNum().setValue("0");
                    myViewModel.havePoint =false;
                }else {//a+b*c
                    myViewModel.getMainNum().setValue(myViewModel.mainNumWithNum_1_Tocal());
                    myViewModel.sign2="";
                    myViewModel.num[1] = "";
                    myViewModel.num[0] = myViewModel.mainNumWithNum_0_Tocal();
                    myViewModel.sign1 = "-";
                    myViewModel.getMainNum().setValue("0");
                    myViewModel.havePoint =false;
                }
            }
        });

        //乘法和除法的计算方法一致
        binding.buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewModel.sign1.equals("")){//true执行
                   // System.out.println("乘法：第一个运算符是为空的情况，显示第一个 x 号");
                    myViewModel.sign1="x";
                    myViewModel.num[0] = myViewModel.getMainNum().getValue();
                    myViewModel.getMainNum().setValue("0");
                    myViewModel.havePoint = false;
                }else if(myViewModel.sign2.equals("")){
                    if (myViewModel.sign1.equals("x") || myViewModel.sign1.equals("÷")){
                        //System.out.println("乘法：第二个运算符为空但是第一个运算符号含有 x 或者 ÷ 号，需要按照前后顺序乘除计算");
                        //如果包含乘号也包含除号则按照顺序进行计算
                        myViewModel.num[0] = myViewModel.mainNumWithNum_0_Tocal();
                        myViewModel.sign1="x";
                        myViewModel.getMainNum().setValue("0");
                        myViewModel.havePoint =false;
                    }else {
                        //System.out.println("乘法：第二个运算符为空但是第一个运算符号含有 + 或者 - 号");
                        //则需要显示第二个 x 号出来，根据下一步的按钮情况进行四则混合计算
                        myViewModel.num[1] = myViewModel.getMainNum().getValue();
                        myViewModel.sign2 = "x";
                        myViewModel.getMainNum().setValue("0");
                        myViewModel.havePoint =false;
                    }
                }else {
                    //如果是像a+b*c ,需要先算乘号，但不必算出所有结果
                    //System.out.println("乘法：运算符都存在的情况，a+b*c");
                    //则需要把后面两个乘或者除的式子算出来，放到num1
                    myViewModel.num[1] = myViewModel.mainNumWithNum_1_Tocal();
                    myViewModel.sign2 = "x";
                    myViewModel.getMainNum().setValue("0");
                    myViewModel.havePoint = false;
                }
            }
        });

        binding.buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewModel.sign1.equals("")){//true执行
                    myViewModel.sign1="÷";
                    myViewModel.num[0] = myViewModel.getMainNum().getValue();
                    myViewModel.getMainNum().setValue("0");
                    myViewModel.havePoint = false;
                }else if(myViewModel.sign2.equals("")){
                    if (myViewModel.sign1.equals("x") || myViewModel.sign1.equals("÷")){
                        //如果包含乘号也包含除号则按照顺序进行计算
                        myViewModel.num[0] = myViewModel.mainNumWithNum_0_Tocal();
                        myViewModel.sign1="÷";
                        myViewModel.getMainNum().setValue("0");
                        myViewModel.havePoint =false;
                    }else {
                        myViewModel.num[1] = myViewModel.getMainNum().getValue();
                        myViewModel.sign2 = "÷";
                        myViewModel.getMainNum().setValue("0");
                        myViewModel.havePoint =false;
                    }
                }else {
                    //如果是像a+b*c ,需要先算乘号
                    myViewModel.num[1] = myViewModel.mainNumWithNum_1_Tocal();
                    myViewModel.sign2 = "÷";
                    myViewModel.getMainNum().setValue("0");
                    myViewModel.havePoint = false;
                }
            }
        });

        binding.buttonClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.sign2="";
                myViewModel.num[1]="";
                myViewModel.sign1="";
                myViewModel.num[0]="";
                myViewModel.getMainNum().setValue("0");
                myViewModel.havePoint=false;
            }
        });

        binding.buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewModel.sign2.equals("")){
                    if (!myViewModel.sign1.equals("")){
                        myViewModel.getMainNum().setValue(myViewModel.mainNumWithNum_0_Tocal());
                        if (myViewModel.getMainNum().getValue().contains(".")){
                            myViewModel.havePoint=true;
                        }else {
                            myViewModel.havePoint=false;
                        }
                        myViewModel.num[0]="";
                        myViewModel.sign1="";
                    }
                }else {
                    //如果是像a+b*c
                    myViewModel.getMainNum().setValue(myViewModel.mainNumWithNum_1_Tocal());
                    myViewModel.num[1]="";
                    myViewModel.sign2="";
                    myViewModel.getMainNum().setValue(myViewModel.mainNumWithNum_0_Tocal());
                    if (myViewModel.getMainNum().getValue().contains(".")){
                        myViewModel.havePoint=true;
                    }else {
                        myViewModel.havePoint=false;
                    }
                    myViewModel.num[0]="";
                    myViewModel.sign1="";
                }
                binding.textView.setText(myViewModel.getMainNum().getValue());
            }
        });

        binding.ibTuige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myViewModel.getMainNum().getValue().equals("0")){
                    myViewModel.getMainNum().setValue(myViewModel.getMainNum().getValue().substring(0,myViewModel.getMainNum().getValue().length()-1));
                    if (myViewModel.getMainNum().getValue().equals("")){
                        myViewModel.getMainNum().setValue("0");
                    }
                }
            }
        });
    }

}