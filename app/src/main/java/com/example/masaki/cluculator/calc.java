package com.example.masaki.cluculator;

import java.util.ArrayList;
import java.util.Arrays;

public class calc{

    public static void main (String[] args){
//        ArrayList<String> x=new ArrayList<String>();
//        x.add("aa");
//        x.add("bb");
//        x.add("cc");
//        x.add("dd");
//
//        for(int i=0;i<4;i++){
//            for(String a:x){
//                System.out.print(a+" ");
//            }
//            System.out.println("");
//            x.remove(0);
//        }
        Calculator calc=new Calculator();
        ArrayList<String> list = new ArrayList<String>(Arrays.asList("4","+","5","×","6","×","7","÷","3","+","2"));
        System.out.println(calc.calc_all(list));

        list = new ArrayList<String>(Arrays.asList("4",".","7","×","2"));
        ButtonClickListener a = new ButtonClickListener(null);
        list=a.convert_num(list);
        int i=0;

    }
}