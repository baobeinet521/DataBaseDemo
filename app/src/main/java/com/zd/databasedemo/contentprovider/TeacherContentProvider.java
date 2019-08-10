package com.zd.databasedemo.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.zd.databasedemo.db.DBHelper;

public class TeacherContentProvider extends ContentProvider {
    private Context mContext;
    DBHelper mDbHelper = null;
    SQLiteDatabase db = null;
    //这里的AUTHORITY就是我们在AndroidManifest.xml中配置的authorities
    public static final String AUTHORITY = "com.zddatabase.teacherProviderLj";

    public static final int Student_Code = 1;
    public static final int Teacher_Code = 2;

    //匹配成功后的匹配码
    private static final int MATCH_CODE = 100;
    // UriMatcher类使用:在ContentProvider 中注册URI
    private static UriMatcher uriMatcher;

    private TeacherDao teacherDao;

    //数据改变后指定通知的Uri
    public static final Uri TEACHER_URI = Uri.parse("content://" + AUTHORITY + "/teacher");

    static {
        //匹配不成功返回NO_MATCH(-1)
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //添加我们需要匹配的uri
        uriMatcher.addURI(AUTHORITY,"student", Student_Code);
        //添加我们需要匹配的uri
        uriMatcher.addURI(AUTHORITY,"teacher", Teacher_Code);
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();

        // 在ContentProvider创建时对数据库进行初始化
        // 运行在主线程，故不能做耗时操作,此处仅作展示
        mDbHelper = new DBHelper(getContext());
        db = mDbHelper.getWritableDatabase();

        // 初始化两个表的数据(先清空两个表,再各加入一个记录)
        db.execSQL("delete from student");
        db.execSQL("insert into student values(1,'Carson');");
        db.execSQL("insert into student values(2,'Kobe');");

        db.execSQL("delete from teacher");
        db.execSQL("insert into teacher values(1,'Android');");
        db.execSQL("insert into teacher values(2,'iOS');");
        return false;
    }


    @Override
    public Cursor query( Uri uri, String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面
        String table = getTableName(uri);

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

        // 查询数据
        return db.query(table,projection,selection,selectionArgs,null,null,sortOrder,null);
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri,ContentValues values) {
        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面
        String table = getTableName(uri);

        // 向该表添加数据
        db.insert(table, null, values);

        // 当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
        mContext.getContentResolver().notifyChange(uri, null);

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

        return uri;
    }

    @Override
    public int delete(Uri uri,String selection,String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri,ContentValues values,String selection, String[] selectionArgs) {
        return 0;
    }


    /**
     * 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
     */
    private String getTableName(Uri uri){
        String tableName = null;
        switch (uriMatcher.match(uri)) {
            case Student_Code:
                tableName = DBHelper.STUDENT_TABLE_NAME;
                break;
            case Teacher_Code:
                tableName = DBHelper.TEACHER_TABLE_NAME;
                break;
        }
        return tableName;
    }
}
