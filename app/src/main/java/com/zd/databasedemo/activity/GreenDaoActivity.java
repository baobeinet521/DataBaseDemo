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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        mQueryBtn = findViewById(R.id.query_all_data_greendao);
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
