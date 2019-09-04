package com.example.minch.musicplayer_20181020;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
/**
 * Created by minch on 2018-11-11.
 */

public class PlayActivity  extends AppCompatActivity {
    Button btnBack, btnP,btnS;
    ImageView setImage;
    TextView textV;
    MediaPlayer music;
//    myDBHelper myMusic;//그냥 쓸수없음 객체생성하고 사용, 객체선언
//    SQLiteDatabase sqlDB;//쿼리문 수행용 객체선언   .execSQL();사용용
//    Cursor m;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnP = (Button) findViewById(R.id.btnP);
        btnS = (Button) findViewById(R.id.btnS);
        setImage = (ImageView) findViewById(R.id.setImage);
        textV = (TextView) findViewById(R.id.textV);
//        myMusic = new myDBHelper(this);
        music = MediaPlayer.create(this, R.raw.test1);
        music.setLooping(true);

//        String ex1 = null;
//        ex1 = "artist";

//        sqlDB = myMusic.getWritableDatabase();//쓰기전용으로 DB연다
//        sqlDB.execSQL("INSERT INTO musicTBL VALUES (1,'R.raw.test1','R.drawable.sample','title','artist');");
////        sqlDB.execSQL("INSERT INTO musicTBL VALUES (1,'R.raw.test1','R.drawable.sample','title','artist'"");");
//        sqlDB.close();//레코드 추가작업 끝나면 DB닫기


//        Cursor cursor;
//        cursor = sqlDB.rawQuery("SELECT * FROM musicTBL WHERE num = 1;", null); // 개수 2차원 배열 형태로 저장됨
//        String musicName = cursor.getString(1);
//        String imageName = cursor.getString(2);
//        String titleName = cursor.getString(3);
//        String artistName = cursor.getString(4);


        Integer imageFileId[] = { R.drawable.sample, R.drawable.sample2, R.drawable.sample3, R.drawable.sample4};
        final Integer musicFiled[] = { R.raw.test1, R.raw.test2, R.raw.test3, R.raw.test4};
        Integer select = 0;
        Intent inIntent = getIntent();
        final int selectNum = inIntent.getIntExtra("Num", 0);

        if(selectNum==0) {
            select = musicFiled[0];
            setImage.setImageResource(imageFileId[0]);
            textV.setText("예제1 - 가수1");
        }
        if(selectNum==1) {
            select = musicFiled[1];
            setImage.setImageResource(imageFileId[1]);
            textV.setText("예제2 - 가수2");
        }
        if(selectNum==2) {
            select = musicFiled[2];
            setImage.setImageResource(imageFileId[2]);
            textV.setText("예제3 - 가수3");
        }
        if(selectNum==3) {
            select = musicFiled[3];
            setImage.setImageResource(imageFileId[3]);
            textV.setText("예제4 - 가수4");
        }



        final Integer selectMusic = select;



//        textV.setText(titleName.toString()+"-"+artistName.toString());
        music = MediaPlayer.create(PlayActivity.this, selectMusic);

//        cursor.close();//cursor를 사용했으면 종료해줘야함
//        sqlDB.close();






        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MediaPlayer 객체 할당
                music = MediaPlayer.create(PlayActivity.this, selectMusic);
                music.start();
            }
        });

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 정지버튼
                music.stop();
                // 초기화
                music.reset();
            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }//onCreate
    protected void onDestroy() {
        super.onDestroy();
        // MediaPlayer 해지
        if(music != null) {
            music.release();
            music = null;
        }
    }

//    public static class myDBHelper extends SQLiteOpenHelper {
//        //2개의 필수 메소드 onCreate onUpgrave()
//        //myBDhelper클래스 내부에 커서를 놓고 메소드 override해서 씀
//        //myBDhelper는 생성자가 데이터베이스를 만듦 SQLiteopenHelper();
//        public myDBHelper(Context context){//클래스 추가 Context 프로젝트에 DB생성
//            super(context,"musicDB",null,1);//실질적인 DB생성위치 SQLiteOpenHelper(); 생성자
//        }
//        @Override//code override 에서 추가
//        public void onCreate(SQLiteDatabase db) {//데이터베이스생성
//            db.execSQL("CREATE TABLE musicTBL ( num int PRIMARY KEY,mName char(50),mImage char(50),mTitle char(50),mArtist char(50));");//SQL문을 수행하는 메소드
//        }
//        //추가 메소드가 필요함 onUpgrade()필수메소드 추가되면 에러메시지 사라짐
//        @Override//code override 에서 추가,
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//테이블이 있으면 삭제하고 테이블을 다시 생성
//            //데이터베이스의 테이블을 신버전으로 바꿔라
//            db.execSQL("DROP TABLE IF EXISTS musicTBL");//테이블이 있으면 삭제하는 쿼리
//            //테이블 없으면 테이블 생성 위에서 썻던것 호출
//            onCreate(db);
//        }
//    }

}