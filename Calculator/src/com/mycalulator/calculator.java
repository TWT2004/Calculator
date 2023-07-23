package com.mycalulator;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.*;
public class calculator extends MYButton {
//表达式的运算
    public calculator(){}
    //方法中缀转后缀
    public static ArrayList<String> TurnTo(Stack<String> stack) {
       //声名Arraylist的字符串集合用来存储后缀表达式
        ArrayList<String> list=new ArrayList<>();
        //存储这正常表达式的栈
        Stack<String> TempStack = new Stack<>();
        //工具栈用于改变操作符的位置
        Stack<String> StackTemp = new Stack<>();
        //中转栈，用于foreach方法是从栈顶开始遍历的，如果不用中转栈存储的表达式的顺序会发生颠倒
        Stack<String> stringStack=new Stack<>();
        //将表达式存入到栈中
        String str = "";
        for (String s:stack
             ) {stringStack.push(s);
        }
       while(!stringStack.empty()) {
           TempStack.push(stringStack.pop());
       }
       //中缀转后缀的代码块
        while (!TempStack.empty()) {
            int PointCount=0;
            //去出存储栈的栈顶元素
            String temp = TempStack.pop();
            //存储数字
            while ((!temp.equals("x")) &(!temp.equals("+"))& (!temp.equals("-")) &
                    (!temp.equals("/")) &(!temp.equals("("))& (!temp.equals(")"))) {
                //循环判断是否为操作符，能够实现数字的拼接，例如出栈元素若为 1,2,3,.,4,则可将其拼接为123.4，改步骤能实现小数点的加入以及非个位数的输入
                str = str + temp;
                if(!TempStack.empty()) {
                    temp = TempStack.pop();
                }
                //如果最后一个元素为数字的话，会出现死循环，所以加入改代码可以及时跳出循环
                else {
                    temp="exit";
                    break;
                }
            }
            //判断是否有数字输入，如有则添加进集合中
            if (!str.equals("")) {
                list.add(str);
                System.out.println(Change(str));
                if(!Change(str))MYButton.Point=false;
                str="";
                if(temp.equals("exit"))break;
            }
            //判断是否为操作符
            if(!temp.equals("(")&&!temp.equals(")")) {
                //如果工具栈为空，则直接存入数字
                if (StackTemp.empty()) {
                    StackTemp.push(temp);
                }
                else {
                    if(!StackTemp.empty()) {
                        //比较工具栈栈顶操作符和即将输入操作符的优先级
                        String str2 = StackTemp.pop();
                        //判断工具栈顶是否为（
                        if (!str2.equals("(")) {
                            if (bos(str2, temp)) {
                                //如果工具栈顶元素优先级大于输入操作符，则将改元素添加到集合中
                                list.add(str2);
                                //将输入的操作符从新添加到存储栈中用于下一次比较
                                TempStack.push(temp);
                            }
                            else {
                                StackTemp.push(str2);
                                StackTemp.push(temp);
                            }
                        }
                        //如果没有达到添加条件，则将输入元素压入工具栈中
                        else {
                            StackTemp.push(str2);
                            StackTemp.push(temp);
                        }
                    }
                }
            }
            //如果输入元素为”(“则直接压入工具栈中
            if(temp.equals("(")) StackTemp.push(temp);
            //如果输入元素为大括号则将工具栈中栈顶的操作符持续添加到集合中，知道读取元素为"("时停止
            else if(temp.equals(")"))
            {
                String str3=StackTemp.pop();
                while(!str3.equals("(")){
                    list.add(str3);
                    str3=StackTemp.pop();
                }
            }
        }
        //当存储栈为空时，则将工具栈中的元素全部添加到集合中
        while(!StackTemp.empty()) {
            list.add(StackTemp.pop());
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
        }
        //返回改变后地集合
        return list;
    }
    //判断两个操作符地优先级
    private static boolean bos(String str1,String str2){
         if((str2.equals("/")||str2.equals("x"))&&(str1.equals("+")||str1.equals("-"))){
            return false;
        }
        else return true;
    }
    //实现后缀表达式的运算
    public static float  calculate(ArrayList<String> list) {
       //表达式地运算栈
        Stack<Float> FloatStack=new Stack<>();
        for (int i = 0; i < list.size(); i++) {
            //入果此时集合中输出的元素为数字时则压入运算栈中
            if(!list.get(i).equals("+")&&!list.get(i).equals("-")&&!list.get(i).equals("x")&&!list.get(i).equals("/")) {
                //利用方法Float.parseFloat()可将字符串转化会Float类型的数字
                FloatStack.push(Float.parseFloat(list.get(i)));
            }
            //如果为运算符时，则出栈栈顶的两个元素并运算
            else{
                switch (list.get(i))
                {
                    //由于计算机无法计算如0.3，0.03等数字所以需要先将数字先翻倍再运算最后再将所得数字缩小相应倍数
                    case "+":{
                        float num1=FloatStack.pop();
                        float num2 = (FloatStack.pop()*1000000)+(num1*1000000);
                        FloatStack.push(num2/1000000);
                        break;
                    }
                    case "-":{
                        float num1=FloatStack.pop();
                        float num2=(FloatStack.pop()*1000000)-(num1*1000000);
                        FloatStack.push(num2/1000000);
                        break;
                    }
                    case "x":{
                        float num1=FloatStack.pop();
                        float num2=(num1*1000)*(FloatStack.pop()*1000);
                        FloatStack.push(num2/1000000);
                        break;
                    }
                    case "/":{
                        //由于java自身会将除数为零的表达实运算的结果转化为Infinity所以不用进行除数为零的判断
                        float num1=FloatStack.pop();
                        float num2=(FloatStack.pop()*1000000)/(num1*1000000);
                        FloatStack.push(num2);
                        break;
                    }
                }
            }
        }
        //返回最终的结果
        return FloatStack.pop();
    }
   private static boolean Change(String str){
        int count=0;
       for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i)=='.')count++;
       }
       if(count<=1)return true;
       else return false;
   }
}
