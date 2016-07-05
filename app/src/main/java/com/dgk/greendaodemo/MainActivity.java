package com.dgk.greendaodemo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Date;

import de.greenrobot.dao.Student;
import de.greenrobot.dao.StudentDao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_id;
    private EditText et_phone;
    private Button btn_submit;
    private Button btn_check;
    private ListView lv;


    private StudentDao studentDao;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
        initData();
    }

    private void initView() {

        et_name = (EditText) findViewById(R.id.et_name);
        et_id = (EditText) findViewById(R.id.et_id);
        et_phone = (EditText) findViewById(R.id.et_phone);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_check = (Button) findViewById(R.id.btn_check);
        lv = (ListView) findViewById(R.id.lv);
    }

    private void initListener() {

        btn_submit.setOnClickListener(this);
        btn_check.setOnClickListener(this);
    }


    private void initData() {

        studentDao = DBUtil.getDaoSession().getStudentDao();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_submit:
                submit();
                break;

            case R.id.btn_check:
                queryAll();
                break;
        }
    }

    /** 提交数据 */
    private void submit() {

        String name = et_name.getText().toString().trim();
        String stuId = et_id.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();

        Student student = new Student(null, name, stuId, phone, new Date());

        studentDao.insert(student);

        Log.d("【submit】", "Inserted new student, ID: " + student.getId() + " , Name："+name);
    }

    /** 查询所有数据 */
    private void queryAll() {

        String tablename = studentDao.getTablename();
        String name = StudentDao.Properties.Name.columnName;
        String number = StudentDao.Properties.Number.columnName;
        String phone = StudentDao.Properties.Phone.columnName;

        String orderBy = number + " COLLATE LOCALIZED ASC";

        cursor = DBUtil.getDB().query(tablename, studentDao.getAllColumns(), null, null, null, null, orderBy);

        String[] from = { name , number };
        int[] to = { android.R.id.text1, android.R.id.text2 };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);
        lv.setAdapter(adapter);
    }
}
