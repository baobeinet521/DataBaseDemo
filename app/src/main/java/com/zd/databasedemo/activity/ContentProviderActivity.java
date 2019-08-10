package com.zd.databasedemo.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zd.databasedemo.R;
import com.zd.databasedemo.contentprovider.TeacherContentProvider;

public class ContentProviderActivity extends Activity {
    private Button mInsertBtn;
    private Button mQueryDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_layout);
        mInsertBtn = findViewById(R.id.insert_sql_btn);
        mQueryDataBtn = findViewById(R.id.query_sql_btn);

        mInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置URI
                Uri uri_teacher = TeacherContentProvider.TEACHER_URI;
                // 插入表中数据
                ContentValues values = new ContentValues();
                values.put("_id", 4);
                values.put("job", "开发呀");
                // 获取ContentResolver
                ContentResolver resolver = getContentResolver();
                // 通过ContentResolver 根据URI 向ContentProvider中插入数据
                resolver.insert(uri_teacher, values);
            }
        });

        mQueryDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置URI
                Uri uri_teacher = TeacherContentProvider.TEACHER_URI;
                // 获取ContentResolver
                ContentResolver resolver = getContentResolver();
                // 通过ContentResolver 向ContentProvider中查询数据
                String[] querystr = new String[]{"_id", "job"};
                Cursor cursor = resolver.query(uri_teacher, null, "_id == ? ", new String[]{"4"}, null);
                try{
                    while (cursor.moveToNext()) {
                        System.out.println("query book:" + cursor.getInt(cursor.getColumnIndex(querystr[0])) + " " + cursor.getString(cursor.getColumnIndex(querystr[1])));
                        // 将表中数据全部输出
                    }

                    // 关闭游标
                }catch (Exception e){

                }finally {
                    cursor.close();
                }

            }
        });

    }
}
