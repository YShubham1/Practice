package com.example.prac3;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText n,p;
    RadioGroup rg;
    RadioButton rb;
    Spinner sp;
    ImageView dt,ti;
    Button b1,b2,b3,b4;
    String sd,st;
    String[] docter={"General Physician","Neurologist","Opthalmologist","Dentist","Cardiologist","Ortho"};
    String seld;
    StoreD db;
    String fee="1000";
    String alternate="Data not inserted";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        n=findViewById(R.id.na);
        p=findViewById(R.id.ph);
        rg=findViewById(R.id.rg);
        sp=findViewById(R.id.sp);
        dt=findViewById(R.id.date);
        ti=findViewById(R.id.time);
        b1=findViewById(R.id.bt1);
        b2=findViewById(R.id.bt2);
        b3=findViewById(R.id.bt3);
        b4=findViewById(R.id.bt4);
        db=new StoreD(this,"PDATA",null,1);

        ArrayAdapter ad=new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,docter);
        sp.setAdapter(ad);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seld=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar dto = Calendar.getInstance();
                int year = dto.get(Calendar.YEAR);
                int month = dto.get(Calendar.MONTH);
                int day = dto.get(Calendar.DATE);
                DatePickerDialog d = new DatePickerDialog(MainActivity.this, android.R.style.Theme_DeviceDefault_DialogWhenLarge, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        sd = dayOfMonth + "/" + (month + 1) + "/" + year;
                    }
                }, year, month, day);
                d.show();
            }
        });

        ti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar tm = Calendar.getInstance();
                int hour = tm.get(Calendar.HOUR_OF_DAY);
                int minute = tm.get(Calendar.MINUTE);
                TimePickerDialog dt = new TimePickerDialog(MainActivity.this, android.R.style.Theme_DeviceDefault_DialogWhenLarge, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        st = hourOfDay + ":" + minute;
                    }
                }, hour, minute, true);
                dt.show();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = n.getText().toString();
                String b = p.getText().toString();
                if (a.isEmpty() || b.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                    return;
                }
                rb = findViewById(rg.getCheckedRadioButtonId());
                if (rb == null) {
                    Toast.makeText(MainActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                String c = rb.getText().toString();
                db.insertD(a, b, c, seld, sd, st, fee);
                Toast.makeText(MainActivity.this, "Appointment Booked...", Toast.LENGTH_SHORT).show();
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String da=db.dateDisplay(sd);
                Intent it=new Intent(MainActivity.this, result.class);
                if(da!=null)
                {
                    it.putExtra("Details",da);
                }
                else {
                    it.putExtra("Details",alternate);
                }
                startActivity(it);

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String da=db.docterDisplay(seld);
                Intent it=new Intent(MainActivity.this, result.class);
                it.putExtra("Details",da);
                startActivity(it);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a= n.getText().toString();
                String da=db.fee(a);
                Intent it=new Intent(MainActivity.this, result.class);
                it.putExtra("Details",da);
                startActivity(it);
            }
        });
    }
}