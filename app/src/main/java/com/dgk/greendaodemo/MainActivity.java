package com.dgk.greendaodemo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.Student;
import de.greenrobot.dao.StudentDao;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_id;
    private EditText et_phone;
    private EditText et_stuid;
    private TextView tv_check_one;
    private Button btn_submit;
    private Button btn_check;
    private Button btn_clear;
    private Button btn_check_one;
    private Button btn_delete_one;
    private Button btn_delete_all;

    private ListView lv;

    private List<Student> list;
    private MyLVAdapter adapter;

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
        et_stuid = (EditText) findViewById(R.id.et_stuid);
        tv_check_one = (TextView) findViewById(R.id.tv_check_one);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_check_one = (Button) findViewById(R.id.btn_check_one);
        btn_delete_one = (Button) findViewById(R.id.btn_delete_one);
        btn_delete_all = (Button) findViewById(R.id.btn_delete_all);

        lv = (ListView) findViewById(R.id.lv);
    }

    private void initListener() {

        btn_submit.setOnClickListener(this);
        btn_check.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_check_one.setOnClickListener(this);
        btn_delete_one.setOnClickListener(this);
        btn_delete_all.setOnClickListener(this);
    }


    private void initData() {}

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_submit:// 提交
                submit();
                break;

            case R.id.btn_clear://  清除数据
                clear();
                break;

            case R.id.btn_check:// 查询所有的数据
                queryAll();
                break;

            case R.id.btn_check_one:// 查询一条数据
                queryOne();
                break;

            case R.id.btn_delete_one:// 删除一条数据
                deleteOne();
                break;

            case R.id.btn_delete_all:// 删除所有数据
                deleteAll();
                break;

        }
    }

    /** 清除数据 */
    private void clear() {
        et_name.setText("");
        et_id.setText("");
        et_phone.setText("");
        et_name.requestFocus();
    }

    /** 插入数据 */
    private void submit() {

        String name = et_name.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {

        }

        String stuId = et_id.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();

        Student student = new Student(null, name, stuId, phone, new Date());

        //数据库里的一条记录对应着一个Java对象，所以插入操作，仅仅需要插入一个对象即可。
        DBUtil.getStudentDao().insert(student);
        Log.d("【submit】", "Inserted new student, ID: " + student.getId() + " , Name："+name);
    }

    /** 查询所有数据 */
    private void queryAll() {

        /*
            获取属性名称，这一步主要是为了查询的时候作为查询条件使用
            String number = StudentDao.Properties.Number.columnName;
            String orderBy = number + " COLLATE LOCALIZED ASC"; // 按照学号的升序排列

            该方法是调用数据库SQLiteDatabase的query()方法来进行查询的，比较繁琐，但是说明也是执行SQL语句的
            cursor = DBUtil.getDB().query(tablename, DBUtil.getStudentDao().getAllColumns(), null, null, null, null, orderBy);

            String[] from = { name , number };
            int[] to = { android.R.id.text1, android.R.id.text2 };
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);
            lv.setAdapter(adapter);
         */

        // 以下是通过GreenDAO的方式查询数据库，所有的操作都是面向对象的方式
        Query<Student> query = DBUtil.getStudentDao().queryBuilder()
                                .where(StudentDao.Properties.Number.isNotNull())
                                .orderAsc(StudentDao.Properties.Number)     // 按照学号的升序排列
                                .build();

        list = query.list();

        if ((list != null) && (list.size()>0)) {
            if (adapter == null) {
                adapter = new MyLVAdapter();
                lv.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(MainActivity.this,"数据库里没有数据了!",Toast.LENGTH_SHORT).show();
        }
    }

    /** 查询一条数据 */
    private void queryOne() {

        String stuId = et_stuid.getText().toString().trim();
        Log.i("【查询的学生的学号】",stuId);

        StudentDao studentDao = DBUtil.getStudentDao();

        // 一个可以被反复执行的查询的实体类
        Query<Student> query = studentDao.queryBuilder()
                                .where(StudentDao.Properties.Number.eq(stuId))  // 学号属性等于被查询的数值
                                .build();

        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        // 查询结果以list返回
        List<Student> list = query.list();

        String result = "";
        if ((list != null) && (list.size()>0)) {
            Student student = list.get(0);
            result = student.getId()+","+student.getName()+","+student.getNumber()+","+student.getPhone()+","+new SimpleDateFormat().format(student.getDate());
        } else {
            Toast.makeText(MainActivity.this,"查无此人!",Toast.LENGTH_SHORT).show();
        }
        tv_check_one.setText(result);

    }

    /** 删除一条数据 */
    private void deleteOne() {

        String stuId = et_stuid.getText().toString().trim();
        Log.i("【删除的学生的学号】",stuId);

        StudentDao studentDao = DBUtil.getStudentDao();

        // 一个可以被反复执行的查询的实体类
        Query<Student> query = studentDao.queryBuilder()
                .where(StudentDao.Properties.Number.eq(stuId))  // 学号属性等于被查询的数值
                .build();

        List<Student> list = query.list();

        for (int i = 0; i < list.size(); i++) {
            studentDao.deleteByKey(list.get(i).getId());    //通过id删除掉记录
        }
    }

    /** 删除数据库所有数据 */
    private void deleteAll() {

        Log.i("【删除数据库所有数据】"," ======== 清空了~~~ ========= ");
        DBUtil.getStudentDao().deleteAll();
        if ((list != null) && (adapter != null)) {
            Log.i("【删除数据库所有数据】"," ======== 刷新界面 ========= ");
            list.clear();
            adapter.notifyDataSetChanged();
        }
    }



    private class MyLVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Student getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {

                convertView = View.inflate(MainActivity.this,R.layout.lv_item,null);
                holder = new ViewHolder();
                holder.id = (TextView) convertView.findViewById(R.id.tv_id);
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.stuId = (TextView) convertView.findViewById(R.id.tv_stuid);
                holder.phone = (TextView) convertView.findViewById(R.id.tv_phone);
                holder.date = (TextView) convertView.findViewById(R.id.tv_date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Student stu = list.get(position);
            holder.id.setText("ID："+stu.getId().toString());
            holder.name.setText("姓名："+stu.getName());
            holder.stuId.setText("学号："+stu.getNumber());
            holder.phone.setText("手机号："+stu.getPhone());
            holder.date.setText("入学时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(stu.getDate()));

            return convertView;
        }
    }

    public static class ViewHolder {
        public TextView id;
        public TextView name;
        public TextView stuId;
        public TextView phone;
        public TextView date;
    }
}
