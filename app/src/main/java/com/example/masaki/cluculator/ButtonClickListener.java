package com.example.masaki.cluculator;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.masaki.cluculator.exception.IllegalValueException;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ButtonClickListener implements View.OnClickListener {

    TextView textview;
    MainActivity activity;
    private static int opeCount=0,numCount=0,callCount=0,decCount=0;

    //計算過程
    protected static ArrayList<String> calcprocess=new ArrayList<String>();

    //最大桁数
    private static final int MAX_NUMBER_OF_DIGITS=8;

    public ButtonClickListener(MainActivity activity){
        this.textview=activity.textview;
        this.activity=activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        callCount++;
        String inputValue=String.valueOf(((Button)view).getText());
        int viewId=view.getId();
        if(viewId==R.id.equal) {//計算
            try {
                //計算過程の末尾が演算子であればエラー
                if(!Pattern.matches("^[0-9]*$", calcprocess.get(calcprocess.size()-1))){
                    throw new IllegalValueException();
                }

                //数字の羅列を一つの要素にまとめる前処理
                calcprocess = convert_num(calcprocess);

                double result;
                Calculator calculator = new Calculator();
                result = calculator.calc_all(calcprocess);

                //calc_allによって計算結果のみとなっていると思うが、念のため計算結果のみを改めてセット。
                calcprocess.clear();
                calcprocess.add(String.valueOf(result));

                opeCount = 0;
                numCount=0;
                decCount=0;

                //計算結果を表示
                textview.setText(String.valueOf(result));
            }catch(IllegalValueException e){

            }
        }else if (viewId==R.id.clear) {//画面クリア
            callCount=0;
            opeCount=0;
            numCount=0;
            decCount=0;
            textview.setText("");
            calcprocess.clear();
        }else {//数字や演算記号
            //計算過程に追記する
            String a="";

            try {
                //整合性チェック
                Valid(inputValue);
                calcprocess.add(inputValue);
                for(String value:calcprocess){
                    a+=value;
                }
                textview.setText(a);
            } catch(IllegalValueException e){
                Toast.makeText(activity,"不正です。入力しなおしてください",Toast.LENGTH_SHORT);
            }
        }
    }

    //整合性チェック(不要な入力をしていないか)
    private void Valid(String a) throws IllegalValueException{
        if(!Pattern.matches("\\d+(\\.\\d+)?", a) && !a.equals(".")){
            opeCount++;
        }else if(!a.equals(".")){
            opeCount=0;
        }

        //今入力している整数、小数の桁数または、小数点の個数を算出する
        if(Pattern.matches("\\d+(\\.\\d+)?", a)){
            numCount++;
        }else if(a.equals(".")){
            decCount++;
        }else{//演算子(「.」を除く)ならリセットする
            numCount=0;
            decCount=0;
        }

        //演算子が2つ以上続く　または　最初に演算子を入力するとエラー
        //整数、小数の羅列が最大桁数を超えるとエラー
        //小数の小数点の個数が1より大きいとエラー
        if(opeCount>1 || (callCount==1 && !Pattern.matches("^[0-9]*$", a)) || numCount>MAX_NUMBER_OF_DIGITS){
            throw new IllegalValueException();
        }

        if(decCount>1){
            decCount=1;
            throw new IllegalValueException();
        }
    }

    //2桁以上の数字について、まとめて1箇所のリスト要素に格納する。
    ArrayList<String> convert_num(ArrayList<String> list) {
        ArrayList<String> replaceList=new ArrayList<String>();
        String addString="";
        int i=0;
        for(String a : list){
            if (Pattern.matches("\\d+(\\.\\d+)?", a) || a.equals(".")) {
                //数字の羅列を作る
                addString+=a;
            }else{
                //演算子の場合、数字の羅列が途切れるため、ここでリストに格納する
                replaceList.add(addString);
                replaceList.add(a);
                addString="";
            }

            //末端まで来たら、その時点での数字の羅列を保存
            if(i==list.size()-1){
                replaceList.add(addString);
            }

            i++;
        }
        //変換したリストで、計算過程を上書きする
        return replaceList;
    }

}
