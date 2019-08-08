package com.zd.databasedemo.activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zd.databasedemo.DataBaseApplication;
import com.zd.databasedemo.R;
import com.zd.databasedemo.greendao.DaoSession;
import com.zd.databasedemo.greendao.Student;

import java.util.List;

public class GreenDaoActivity extends Activity {
    private static String TAG = "GreenDaoActivity";
    private Button mQueryBtn;
    private Button mInsertBtn;
    private Button mDeleteOneData;
    private Button mDeleteAllData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        mQueryBtn = findViewById(R.id.query_all_data_greendao);
        mInsertBtn = findViewById(R.id.insert_data);
        mDeleteOneData =findViewById(R.id.delete_one_data);
        mDeleteAllData = findViewById(R.id.delete_all_data);

        mQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DaoSession daoSession = ((DataBaseApplication)getApplication()).getDaoSession();
                List<Student> students = daoSession.loadAll(Student.class);
                if(students.size() > 0){
                    Toast.makeText(GreenDaoActivity.this,"一共有"+students.size()+"个学生",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(GreenDaoActivity.this,"还没有学生",Toast.LENGTH_LONG).show();
                }
            }

        });

        mInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserData();
            }
        });

        mInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserData();
            }
        });
        mDeleteOneData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setStudentNo(1);
                int age =  1;
                student.setAge(age);
                student.setTelPhone("131```````");
                String chineseName = "不知道";
                student.setName(chineseName);
                student.setSex("男");
                student.setAddress("陕西省。。。。。。。");
                student.setGrade(String.valueOf(age % 10) + "年纪");
                student.setSchoolName("郧阳中学"+1);
                deleteOneData(student);
            }
        });
        mDeleteAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllData();
            }
        });
    }

    /**
     * 插入数据
     */
    public  void inserData(){
        DaoSession daoSession = ((DataBaseApplication) getApplication()).getDaoSession();
        for (int i = 0; i < 1000; i++) {
            Student student = new Student();
            student.setStudentNo(i);
            int age =  i++;
            student.setAge(age);
            student.setTelPhone("131```````");
            String chineseName = "不知道";
            student.setName(chineseName);
            if (i % 2 == 0) {
                student.setSex("男");
            } else {
                student.setSex("女");
            }
            student.setAddress("陕西省。。。。。。。");
            student.setGrade(String.valueOf(age % 10) + "年纪");
            student.setSchoolName("郧阳中学"+i);
            daoSession.insert(student);
//            daoSession.insertOrReplace(student);//插入或替换
        }
    }

    /**
     * 删除一条数据
     */
    public void deleteOneData(Student s) {
//        DaoSession daoSession = ((DataBaseApplication) getApplication()).getDaoSession();
//        daoSession.delete(s);
    }

    /**
     * 删除所有数据
     */
    public void deleteAllData(){
        DaoSession daoSession = ((DataBaseApplication) getApplication()).getDaoSession();
        daoSession.deleteAll(Student.class);
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Log.e(TAG, "getDataColumn: "+ uri);
        Cursor cursor = null;
        final String column = MediaStore.Audio.Media.DATA;
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

}
