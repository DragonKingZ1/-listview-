package com.example.administrator.dazuoye;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class UserDataHelper {
    private SQLiteDatabase sqLiteDatabase ;
    private Context context;


    public UserDataHelper(String dbname, Context context ) {
        this.context = context;

        this.sqLiteDatabase = this
                .context.openOrCreateDatabase(dbname,Context.MODE_PRIVATE,null);
    }

    public void createTable(){
        if(!isTableExist("user")){
            //这个方法要进行判断，要注意，表只能创建一次
            String sql = "create table user (_id integer primary key autoincrement," +
                    "username varchar(20) not null," +
                    "password varchar(50) not null)";
            //执行SQL语句
            //表示执行SQL语句，在库中创建一个表
            sqLiteDatabase.execSQL(sql);
        }
    }

    //判断库中是否存在指定的表
    private boolean isTableExist(String tableName){

        //存在一个隐藏表sqlite_master,只要创建表，那么这个表的信息都会存储到
        //sqlite_master表中
        //count(*)是查询结果集的数量

        String sql = "select count(*) from sqlite_master where " +
                "type='table' and name = '"+tableName.trim()+"'";
        //如果结果集中数量不等于0，表示存在表，如果等于0,表示没有表
        //String sql, 查询语句
        //sql="SELECT * from Students where name = ?";
        // String[] selectionArgs 查询语句中使用的 参数
        //this.sqLiteDatabase.rawQuery(sql,new String[]{"猪刚鬣"});
        //cursor表示游标对象
        Cursor cursor = this.sqLiteDatabase.rawQuery(sql,null);

        //这方法有返回值，返回布尔类型的值，如果没有数据了，就会返回false
        if(cursor.moveToNext()){
            //拿到数据,count就是表存在的个数
            int count = cursor.getInt(0);
            if (count != 0){
                return true;
            }
        }
        return  false;
    }
    public Cursor queryAllUsers(){
        String sql = "select * from user";
        Cursor cursor = this.sqLiteDatabase.rawQuery(sql,null);
        return cursor;
    }

    //添加数据

    public void addUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user.getUsername());
        contentValues.put("password",user.getPassword());
        this.sqLiteDatabase.insert("user",null,contentValues);
    }

    public  User queryUserByNmae(String name){
                String findname=name;
            //String sql="select * from user where username='"+name+"'";
            //Cursor cursor = this.sqLiteDatabase.query("user",new String[]{"username"},"username=?",new String[]{findname},null,null,null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from user where username=?",new String []{findname});
        User finduser=null;
            // User finduser1=null;
        //List<User> user = new ArrayList<>();
            while (cursor.moveToNext()) {
            //执行封装的操作
            //通过cursor和对应的列的索引去找到具体的值，
            int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex("username");
            int passwordIndex = cursor.getColumnIndex("password");

           int id = cursor.getInt(idIndex);
             String username= cursor.getString(nameIndex);
            String password = cursor.getString(passwordIndex);
            User user=new User(username,password,id);
            finduser=user;
        }


        return finduser;
    }
    //判断用户名是否存在
    public boolean UserName(String checkname) {
        String sql = "select * from user ";
        Cursor cursor = this.sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex("username");
            String username = cursor.getString(nameIndex);
            if (username.equals(checkname)) {  //用户名存在
                return true;
            } else {
                return false;     //用户名不存在
            }
        }
        return true; //表中无数据返回true
    }
}
