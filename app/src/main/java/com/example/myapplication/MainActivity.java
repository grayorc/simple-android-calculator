package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.regexp.SubString;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView result,preview;
    androidx.appcompat.widget.AppCompatButton backspace,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17,btn18,btn19,btn20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        preview = findViewById(R.id.preview);

        setId(btn1,R.id.btn1);
        setId(btn2,R.id.btn2);
        setId(btn3,R.id.btn3);
        setId(btn4,R.id.btn4);
        setId(btn5,R.id.btn5);
        setId(btn6,R.id.btn6);
        setId(btn7,R.id.btn7);
        setId(btn8,R.id.btn8);
        setId(btn9,R.id.btn9);
        setId(btn10,R.id.btn10);
        setId(btn11,R.id.btn11);
        setId(btn12,R.id.btn12);
        setId(btn13,R.id.btn13);
        setId(btn14,R.id.btn14);
        setId(btn15,R.id.btn15);
        setId(btn16,R.id.btn16);
        setId(btn17,R.id.btn17);
        setId(btn18,R.id.btn18);
        setId(btn19,R.id.btn19);
        setId(btn20,R.id.btn20);
        setId(backspace,R.id.backspace);
    }

    void setId(androidx.appcompat.widget.AppCompatButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK,50);
        AppCompatButton btn = (AppCompatButton) view;
        String btnText = btn.getText().toString();
        String dataToCalculate = preview.getText().toString();

        if(btnText.equals("AC")){
            preview.setText("");
            result.setText("0");
            return;
        }
        if(btnText.equals("=")){
            preview.setText(result.getText());
            return;
        }

        if(btnText.equals("+/-") && !dataToCalculate.equals("(-")){
            preview.setText("(-" + dataToCalculate);
            return;
        }else if(btnText.equals("+/-") && dataToCalculate.equals("(-")){
            preview.setText("");
            return;
        }

//        if(btn.getText().equals("")){
//            int length = preview.getText().length();
//            String str = new String(preview.getText().toString());
//            preview.setText(str.substring(0,length-1));
//            return;
//        }

        if(btnText.equals("") && preview.getText().length() != 0){
            dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
        }
        dataToCalculate = dataToCalculate+btnText;
        preview.setText(dataToCalculate);

        dataToCalculate = dataToCalculate.replace('÷','/');
        dataToCalculate = dataToCalculate.replace('×','*');
        String finalResult = getResult(dataToCalculate);

        if(!finalResult.equals("Err")){
            if(dataToCalculate.length() == 0){
                result.setText("0");
            }else {
                result.setText(finalResult);
            }
        }

    }

    String getResult(String data){
        try{
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }
}