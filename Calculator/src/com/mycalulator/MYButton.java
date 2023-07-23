package com.mycalulator;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.security.Key;
import java.util.*;


public class MYButton extends JFrame {
    //创建stack栈用于输入内容
    public static Stack<String> stack = new Stack<>();
    //创建窗口对象
    public static JFrame jfm = new JFrame();
    //设置窗口的高度和宽度，用final关键字防止数据发生改变
    private static final int FRAME_HIGH = 560;
    private static final int FRAME_LENtH = 400;
    //创建字符串数组，用于添加到按钮中方便用户辨别按钮作用
    private static final String[] s = {"(", ")", "CE", "Del", "7", "8", "9", "/", "4", "5", "6", "x"
            , "1", "2", "3", "-", ".", "0", "=", "+"};
    //声名按钮数组
    public static JButton Jbt[] = new JButton[20];
    //创建文本输入框用于输入输出
    public static JTextField Input = new JTextField();
    public static JTextField Output = new JTextField();
    public static boolean Point=true;
    //构造空参方法
    public MYButton() {
        InitButton();
        InitFrame();
        InitField();
        Input();
        print();
        jfm.setVisible(true);
    }
    //方法：初始化按钮
    private static void InitButton() {
        //设置字体
        Font f = new Font("宋体", Font.BOLD, 20);
        //将相应字符串加载到相应按钮中
        for (int i = 0; i < s.length; i++) {
            Jbt[i] = new JButton(s[i]);
            Jbt[i].setSize(90, 55);
            Jbt[i].setFont(f);
        }
        //改变按钮背景颜色，美化按钮
        for (int i = 0; i < 20; i++) {
            int x = 5 + (i % 4) * 95;
            int y = 225 + (i / 4) * 60;
            Jbt[i].setLocation(x, y);
            if (i / 4 < 1 || i % 4 == 3) {
                Jbt[i].setBackground(new Color(230, 246, 255));
            } else if (i == 18) {
                Jbt[i].setBackground(new Color(202, 139, 72));
            } else Jbt[i].setBackground(Color.WHITE);
            Jbt[i].setFocusPainted(false);
            //添加按钮到窗口中
            MYButton.jfm.getContentPane().add(Jbt[i]);
        }
    }
    //方法：初始化文本输入框
    private void InitField() {
        //设置字体
        Font f = new Font("微软雅黑", Font.CENTER_BASELINE, 30);
        //设置文本框的相应属性
        Input.setSize(375, 100);
        Input.setLocation(5, 5);
        Input.setFont(f);
        Input.setBackground(new Color(255, 239, 141));
        Input.setEnabled(true);
        Input.setHorizontalAlignment(4);
        //禁止文本框自行输入
        Input.setEnabled(false);
        Output.setFont(f);
        Output.setSize(375, 100);
        Output.setLocation(5, 110);
        Output.setBackground(new Color(202, 255, 215));
        Output.setHorizontalAlignment(4);
        Output.setEnabled(false);
        //将文本框添加到窗口中
        jfm.getContentPane().add(Input);
        jfm.getContentPane().add(Output);
    }
    //初始化窗口
    private void InitFrame() {
        //设置窗口的相应属性
        jfm.setSize(FRAME_LENtH, FRAME_HIGH);
        jfm.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jfm.setLayout(null);
        jfm.setLocationRelativeTo(null);
        jfm.getContentPane().setBackground(new Color(246, 240, 249));
        jfm.setTitle("MyCalculator");
        jfm.setResizable(false);
    }
    //方法，监听按钮并将表达式呈现在文本框中
    private void Input() {
        for (int i = 0; i < MYButton.Jbt.length; i++) {
            int finalI = i;
            //使用匿名内部类
            Jbt[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("djl");
                    //当点击的按钮为“CE”时，清空栈
                    if (Jbt[finalI].getText().equals("CE")) {
                        MYButton.Point=true;
                        while (!MYButton.stack.empty()) {
                            MYButton.stack.pop();
                        }
                        MYButton.Output.setText("");
                    }//为数字或运算符号时入栈
                    else if (!Jbt[finalI].getText().equals("Del")&!Jbt[finalI].getText().equals("=")&&!Output.getText().equals("Error")) {
                        String s1 = Jbt[finalI].getText();
                        if(!stack.empty()) {
                            String sss = MYButton.stack.peek();
                            if (!((sss.equals("+") || sss.equals("-") || sss.equals("/") || sss.equals("x")||sss.equals("(")) &&
                                    (s1.equals("+") || s1.equals("/") || s1.equals("x") || s1.equals("-")||s1.equals(")")))) {
                                if((!((sss.equals(")"))&& !(s1.equals("+") || s1.equals("/") || s1.equals("x") || s1.equals("-")||s1.equals(")"))))&&
                                        (!((!(sss.equals("+") || sss.equals("/") || sss.equals("x") || sss.equals("-")||sss.equals(")")||sss.equals("(")))&&(s1.equals("("))))) {
                                    MYButton.stack.push(Jbt[finalI].getText());
                                    System.out.println(Jbt[finalI].getText());
                                }
                            } else {
                                System.out.println("你干嘛");
                            }
                        }
                        else{
                            if(!(s1.equals("+") || s1.equals("/") || s1.equals("x") || s1.equals("-")||s1.equals(")"))) {
                                MYButton.stack.push(Jbt[finalI].getText());
                                System.out.println(Jbt[finalI].getText());
                            }
                            else System.out.println("\\a");
                        }
                    }
                    //为等号时，运算表达式并将答案呈现在输出框中
                    if(Jbt[finalI].getText().equals("=")&&!Output.getText().equals("Error")) {
                        if(!stack.empty()) {

                            ArrayList<String> List = new ArrayList<>();
                            AddList(List);
                            System.out.println(MYButton.Point);
                            if (if_$(List)) {
                                calculator.TurnTo(stack);
                                if(MYButton.Point){
                                System.out.println("111  "+MYButton.Point);
                                float f = calculator.calculate(calculator.TurnTo(MYButton.stack));
                                Output.setText("=" + String.valueOf(f));}
                                else MYButton.Output.setText("Error");
                            } else MYButton.Output.setText("Error");
                        }
                    }
                    //为删除符号时移除栈顶元素
                    else if (!MYButton.stack.empty()&Jbt[finalI].getText().equals("Del")&&!Output.getText().equals("Error"))
                        System.out.println(MYButton.stack.pop());
                    //展示表的时的方法要在监听事件的匿名内部类中调用，否则输入框中将无展示的信息
                        print();
                }
            });
        }
    }
    //将栈中的字符串拼接并呈现在输入框中
    private void print(){

            String str = "";
            if (!MYButton.stack.empty()) {
                //用foreach进行遍历栈并不会销毁栈，若用pop方法则会将栈销毁
                for (String c : MYButton.stack) {
                    str = str + c;
                }
            }//设置文本框的Text为拼接后的字符串
            MYButton.Input.setText(str);
            System.out.println(MYButton.Input.getText());
    }
    private void AddList(ArrayList<String> list){
        for (String str:MYButton.stack
             ) {
            if(str.equals("(")||str.equals(")"))list.add(str);
        }

    }
    //判断表达式的括号匹配问题
    private  boolean if_$(ArrayList<String> list) {
        Stack<String> s=new Stack<>();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("qqq"+list.get(0));
            if(list.get(i).equals("(")){
                s.push(list.get(i));
            }
            else if(list.get(i).equals(")")){
                if(!s.empty()) {
                    if (!$_if(s.pop(), list.get(i))) return false;
                }
                else return false;
            }
        }
        if(s.empty())return true;
        else return false;
    }
    private boolean $_if(String str1,String str2) {
        if(str1.equals("(")&&str2.equals(")"))return true;
        else return false;
    }


}