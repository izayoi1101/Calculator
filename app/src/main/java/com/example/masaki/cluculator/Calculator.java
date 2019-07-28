package com.example.masaki.cluculator;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Calculator {

    protected static final int EXPECTED_ERROR=-1;
    double result;

    double calc_all(ArrayList<String> calcprocess){

        //最終的な計算値が出る=計算過程(calcprocess)の要素数が1になるまで
        while(calcprocess.size()!=1) {

            int index = 0;
            //優先順位が最高の範囲の始め
            int first_index = 0;
            //優先順位が最高の範囲の終わり
            int last_index = 0;
            //優先順位
            int priority = 0, priority_max = 0;

            for (String value : calcprocess) {
                //演算子
                if(index < calcprocess.size()-1) {
                    if (!Pattern.matches("\\d+(\\.\\d+)?", value)) {
                        priority = getPriority(value);
                        if (priority > priority_max) {
                            priority_max = priority;
                            first_index = index;
                        } else if (priority < priority_max) {//優先順位が下がる または 過程の末端になった
                            last_index = index;
                            break;
                        }
                    }
                }else {
                    //本来は演算子だが、この場合は数値のため+1する。
                    last_index=index+1;
                    break;
                }
                index++;
            }

            //上で切り出した「演算子の優先度がmaxの範囲」(開始の演算子一つ前の数)を計算する
            for (int i = first_index - 1; i + 2 <= last_index - 1; i += 2) {
                if (i == first_index-1) {
                    if ((result =
                            calc(Double.parseDouble(calcprocess.get(i)), calcprocess.get(i + 1), Double.parseDouble(calcprocess.get(i + 2)))) == -1) {
                        //想定外の演算子エラー
                    }
                } else {
                    if ((result =
                            calc(result, calcprocess.get(i + 1), Double.parseDouble(calcprocess.get(i + 2)))) == -1) {
                        //想定外の演算子エラー
                    }
                }
            }

            //上で切り出した「演算子の優先度がmaxの範囲」(開始の演算子一つ前の数)を削除する
            for (int i = first_index - 1; i <= last_index - 2; i++) {
                //削除すると右に自動で詰められるので、先頭(index=first_index-1)を,「要素の個数分-1」(計算結果に置き換え用にとっておく)削除する。
                calcprocess.remove(first_index - 1);
            }

            //計算結果をfirst_index-1の要素に上書き
            calcprocess.set(first_index - 1, String.valueOf(result));
        }

        //最終的な計算結果を返却
        return Double.parseDouble(String.valueOf(result));
    }

    int getPriority(String a){
        switch(a){
            case ".":
                return 3;
            case "÷":
            case "×":
                return 2;
            case "-":
            case "+":
                return 1;
        }
        return EXPECTED_ERROR;
    }


    double calc(double value1,String ope,double value2){
        switch(ope){
            case ".":
                return -1;
            case "÷":
                return value1 / value2;
            case "×":
                return value1 * value2;
            case "-":
                return value1 - value2;
            case "+":
                return value1 + value2;
        }
        return EXPECTED_ERROR;
    }
}
