package appewtc.masterung.ungrestaurant;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TesterActivity extends AppCompatActivity {

    //Explicit
    private TextView studentTextView, dateTextView, questionTextView;
    private RadioGroup choiceRadioGroup;
    private RadioButton choice1RadioButton, choice2RadioButton,
            choice3RadioButton, choice4RadioButton;
    private String studentString, dateString;
    private String[] questionStrings, choice1Strings,
            choice2Strings, choice3Strings, choice4Strings, answerStrings;
    private int intTime = 0, intStudentChoose = 0, intScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        //Bind Widget
        bindWidget();

        //Show View
        showView();

        //Read All SQLite
        readAllData();

        //Show Times First
        changeView(0);

        //Radio Controller
        radioController();


    }   // Main Method


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }

    private void radioController() {

        choiceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.radioButton:
                        intStudentChoose = 1;
                        break;
                    case R.id.radioButton2:
                        intStudentChoose = 2;
                        break;
                    case R.id.radioButton3:
                        intStudentChoose = 3;
                        break;
                    case R.id.radioButton4:
                        intStudentChoose = 4;
                        break;
                }


            }   // onChecked
        });

    }

    public void clickAnswer(View view) {

        if (intTime < questionStrings.length) {

            try {
                if (intStudentChoose == Integer.parseInt(answerStrings[intTime])) {
                    intScore += 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } //Try

            intTime += 1;

            if (intTime < questionStrings.length) {
                changeView(intTime);
            } else {
                //Show Score
                Intent intent = new Intent(TesterActivity.this, ShowScoreActivity.class);
                intent.putExtra("Student", studentString);
                intent.putExtra("Date", dateString);
                intent.putExtra("Score", intScore);
                startActivity(intent);
            }


            Log.d("Score", "Score = " + intScore);


        }


    }   // clickAnswer

    private void changeView(int intIndex) {

        questionTextView.setText(Integer.toString(intTime + 1) + ". " +
                questionStrings[intIndex]);

        choice1RadioButton.setText(choice1Strings[intIndex]);
        choice2RadioButton.setText(choice2Strings[intIndex]);
        choice3RadioButton.setText(choice3Strings[intIndex]);
        choice4RadioButton.setText(choice4Strings[intIndex]);

    }


    private void readAllData() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyManage.question_table, null);
        cursor.moveToFirst();
        int intCount = cursor.getCount();

        questionStrings = new String[intCount];
        choice1Strings = new String[intCount];
        choice2Strings = new String[intCount];
        choice3Strings = new String[intCount];
        choice4Strings = new String[intCount];
        answerStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            questionStrings[i] = cursor.getString(1);
            choice1Strings[i] = cursor.getString(2);
            choice2Strings[i] = cursor.getString(3);
            choice3Strings[i] = cursor.getString(4);
            choice4Strings[i] = cursor.getString(5);
            answerStrings[i] = cursor.getString(6);

            cursor.moveToNext();
        }   // for


    }   // readAllData

    private void showView() {
        studentString = getIntent().getStringExtra("Student");
        studentTextView.setText(studentString);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateString = dateFormat.format(date);
        dateTextView.setText(dateString);


    }

    private void bindWidget() {
        studentTextView = (TextView) findViewById(R.id.textView2);
        dateTextView = (TextView) findViewById(R.id.textView3);
        questionTextView = (TextView) findViewById(R.id.textView4);
        choiceRadioGroup = (RadioGroup) findViewById(R.id.ragChoice);
        choice1RadioButton = (RadioButton) findViewById(R.id.radioButton);
        choice2RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        choice3RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        choice4RadioButton = (RadioButton) findViewById(R.id.radioButton4);
    }

}   // Main Class
