package com.example.masaki.cluculator;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview=findViewById(R.id.textView4);
        textview.setText("");
        ViewGroup parent1,parent2,parent3;
        Button button;

        parent1= findViewById(R.id.constraintLayout);
        for (int i = 0; i < parent1.getChildCount(); i++) {
            if(parent1.getChildAt(i) instanceof ViewGroup) {
                parent2 = (ViewGroup) parent1.getChildAt(i);
                if (parent2.getId() == R.id.tableLayout2) {
                    for (int j = 0; j < parent2.getChildCount(); j++) {
                        if (parent2.getChildAt(j) instanceof TableRow) {
                            parent3 = (ViewGroup) parent2.getChildAt(j);
                            for (int k = 0; k < parent3.getChildCount(); k++) {
                                button = (Button) parent3.getChildAt(k);
                                button.setOnClickListener(new ButtonClickListener(this));
                            }
                        }
                    }
                }
            }
        }




    }
}
