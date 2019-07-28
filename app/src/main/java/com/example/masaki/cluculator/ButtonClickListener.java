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

    //計算過程
    protected static ArrayList<String> calcprocess=new ArrayList<String>();

    public ButtonClickListener(MainActivity activity){
        this.textview=activity.textview;
        this.activity=activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        String inputValue=String.valueOf(((Button)view).getText());
        int viewId=view.getId();
        if(viewId==R.id.equal) {//計算
            try{
                //整合性チェック
                Valid();

                //数字の羅列を一つの要素にまとめる前処理
                calcprocess=convert_num(calcprocess);

                int result;
                Calculator calculator = new Calculator();
                result=calculator.calc_all(calcprocess);

                //calc_allによって計算結果のみとなっていると思うが、念のため計算結果のみを改めてセット。
                calcprocess.clear();
                calcprocess.add(String.valueOf(result));

                //計算結果を表示
                textview.setText(String.valueOf(result));
            }catch(IllegalValueException e){
                Toast.makeText(activity,"不正です。入力しなおしてください",Toast.LENGTH_SHORT);
            }

        }else if (viewId==R.id.clear) {//画面クリア
            textview.setText("");
            calcprocess.clear();
        }else {//数字や演算記号
            String a="";
            calcprocess.add(inputValue);
            for(String value:calcprocess){
                a+=value;
            }
            textview.setText(a);
        }
    }

    //整合性チェック(不要な入力をしていないか)
    private void Valid() throws IllegalValueException{
        for(String a:calcprocess){
            if(a.equals("+/-")||a.equals("%")||a.equals(".")){
                throw new IllegalValueException();
            }
        }
    }

    //2桁以上の数字について、まとめて1箇所のリスト要素に格納する。
    ArrayList<String> convert_num(ArrayList<String> list) {
        ArrayList<String> replaceList=new ArrayList<String>();
        String addString="";
        int i=0;
        for(String a : list){
            if (Pattern.matches("^[0-9]*$", a)) {
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
