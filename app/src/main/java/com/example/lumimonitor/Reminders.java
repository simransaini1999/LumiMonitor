package com.example.lumimonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import android.text.format.DateFormat;
import java.util.Calendar;

import static com.example.lumimonitor.NotificationsApp.CHANNEL_1_ID;

public class Reminders extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    TextView reminderName;
    TextView reminderInfo;
    EditText enterReminderName;
    EditText enterReminderInfo;
    Button Submit;
    Button pick;
    TextView result;
    String remName;
    String remInfo;

    private NotificationManagerCompat notificationManager;

    int day ,month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        reminderName = findViewById(R.id.reminderName);
        reminderInfo = findViewById(R.id.reminderInfo);
        enterReminderName = findViewById(R.id.enterRemName);
        enterReminderInfo = findViewById(R.id.enterRemInfo);
        Submit = findViewById(R.id.submit);
        pick = findViewById(R.id.pick);
        result = findViewById(R.id.textViewResult);

        notificationManager = NotificationManagerCompat.from(this);


        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Reminders.this,Reminders.this,
                        year, month, day);
                datePickerDialog.show();

            }
        });
        ConfigureBackButton();
    }


    private void ConfigureBackButton() {
        Button back = findViewById(R.id.TempBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;


        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(Reminders.this, Reminders.this,
                hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

        remName = enterReminderName.getText().toString();
        remInfo = enterReminderInfo.getText().toString();


        result.setText("year:" +yearFinal + "\n" +
                "month:" +monthFinal + "\n" +
                "day:" +dayFinal + "\n" +
                "hour:" +hourFinal + "\n" +
                "minute:" +minuteFinal + "\n" +
                "name:" +remName + "\n" +
                "info:" +remInfo + "\n");
    }

    public void sendOnChannel1 (View v){
        remName = enterReminderName.getText().toString();
        remInfo = enterReminderInfo.getText().toString();

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_child)
                .setContentTitle(remName)
                .setContentText(remInfo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();

        notificationManager.notify(1,notification);
    }


}
