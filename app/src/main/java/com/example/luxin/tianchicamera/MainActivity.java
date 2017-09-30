package com.example.luxin.tianchicamera;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luxin.tianchicamera.sqlite.SQLiteHandle;

//
//luxinxin
//
public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CODE_IMAGE = 1;
    String picturePath;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        init();
    }

    private void init() {

        //实例化控件
        Button btnCamera = (Button) findViewById(R.id.btn_camera);
        final EditText etRoad = (EditText) findViewById(R.id.et_road);
        final EditText etNumber = (EditText) findViewById(R.id.et_number);
        final EditText etGuanDuan = (EditText) findViewById(R.id.et_guanduan);
        final EditText etGuanJing = (EditText) findViewById(R.id.et_guanjing);
        final EditText etCaiZhi = (EditText) findViewById(R.id.et_caizhi);
        final EditText etType = (EditText) findViewById(R.id.et_mixtype);
        final EditText etLocation = (EditText) findViewById(R.id.et_location);
        final EditText etSource = (EditText) findViewById(R.id.et_wushuilaiyuan);
        final EditText etLiuLiang = (EditText) findViewById(R.id.et_liuliang);
        final EditText etBeiZhu = (EditText) findViewById(R.id.et_beizhu);
        Button btnSubmit = (Button) findViewById(R.id.btn_submit);
        Button btnSubmitAndAdd = (Button) findViewById(R.id.btn_submitandadd);
        etRoad.setSelected(false);

        etGuanDuan.setOnClickListener(new View.OnClickListener() {
            int count = 0;

            @Override
            public void onClick(View view) {
                if (count == 0) {
                    count++;
                    etGuanDuan.setText("Y");

                    etGuanDuan.setSelected(true);
                }

            }
        });

        //拍照按钮点击事件
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CODE_IMAGE);
            }
        });

        //处理提交按钮
        btnSubmit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //异步将信息插入数据库
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //将信息插入数据库
                        SQLiteHandle handle = new SQLiteHandle(MainActivity.this);
                        handle.insertItem(etRoad.getText().toString(),
                                etNumber.getText().toString(),
                                etGuanDuan.getText().toString(),
                                etGuanJing.getText().toString(),
                                etCaiZhi.getText().toString(),
                                etType.getText().toString(),
                                etLocation.getText().toString(),
                                etSource.getText().toString(),
                                etLiuLiang.getText().toString(),
                                etBeiZhu.getText().toString());
                    }
                }).start();

                Toast.makeText(MainActivity.this, "信息提交成功", Toast.LENGTH_SHORT).show();
            }
        });

        //处理提交并再次添加按钮
        btnSubmitAndAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //异步将信息插入数据库
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //将信息插入数据库
                        SQLiteHandle handle = new SQLiteHandle(MainActivity.this);
                        handle.insertItem(etRoad.getText().toString(),
                                etNumber.getText().toString(),
                                etGuanDuan.getText().toString(),
                                etGuanJing.getText().toString(),
                                etCaiZhi.getText().toString(),
                                etType.getText().toString(),
                                etLocation.getText().toString(),
                                etSource.getText().toString(),
                                etLiuLiang.getText().toString(),
                                etBeiZhu.getText().toString());
                        
                        //截取路径字符串，取得图片名称
                        int location = picturePath.lastIndexOf("/");
                        String name = picturePath.substring(0, location);

                        //创建修改提交对象
                        ContentValues values = new ContentValues();
                        values.put("MediaStore.Images.Media.DATA", name + etNumber.getText().toString() + ".jpg");

                        getContentResolver().update(uri, values, "MediaStore.Images.Media.DATA = " + "'" + picturePath + "'", null);
                    }
                }).start();

                Toast.makeText(MainActivity.this, "信息提交成功", Toast.LENGTH_SHORT).show();
                // 注意activity的模式必须为默认，即为standard模式
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //处理拍照返回的图片数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE) {

            //取消拍照
            if (resultCode == RESULT_CANCELED) {
                Log.e("拍照", "return error!!!");
                Toast.makeText(this, "取消了拍照", Toast.LENGTH_SHORT).show();

            } else {
                //拍照成功
                Log.i("拍照", "return success!!!");

                //获取拍照bitmap对象，可作为缩略图使用
                Bitmap picture = data.getParcelableExtra("data");

                //获取拍照路径
                uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);

                if (cursor != null && cursor.moveToNext()) {
                    //此为照片路径
                    picturePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    Log.i("照片路径", picturePath);
                }
                Toast.makeText(this, "拍照成功！！请输入管线信息！！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //双击back健退出
    //  逻辑为通过判断两次触摸时间来执行操作
    private int sum = 0;
    long startTime = 0;
    long endTime = 0;

    @Override
    public void onBackPressed() {

        sum++;
        Log.i("onBackPressed", "sum = " + sum);
        switch (sum) {
            case 1:
                Toast.makeText(MainActivity.this, "再按一次退出Reer", Toast.LENGTH_SHORT).show();
                startTime = System.currentTimeMillis();// 第一次时间对应的毫秒数
                break;
            case 2:
                endTime = System.currentTimeMillis();// 第二次时间对应的毫秒数
                if (endTime - startTime < 1500) {
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "再按一次退出Reer", Toast.LENGTH_SHORT).show();
                    startTime = System.currentTimeMillis();// 当前时间对应的毫秒数
                    sum = 1;
                }
                break;

        }

    }
}

