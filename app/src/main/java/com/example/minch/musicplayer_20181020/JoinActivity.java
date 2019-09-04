package com.example.minch.musicplayer_20181020;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by minch on 2018-10-20.
 */

public class JoinActivity extends AppCompatActivity {
    EditText edtJoinName, edtJoinId, edtJoinEmail, edtJoinPhone, edtJoinPw, edtReJoinPw;//위젯을 사용하게 위젯 선언
    Button btnOk, btnNo, btnIdtest;
    myDBHelper myHelper;//그냥 쓸수없음 객체생성하고 사용, 객체선언
    SQLiteDatabase sqlDB;//쿼리문 수행용 객체선언   .execSQL();사용용
    String pass1=null;
    String pass2=null;
    Cursor s;
    int IDflag=0,PWflag=0, Reflag=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinactivity);

        edtJoinName=(EditText) findViewById(R.id.edtJoinName);
        edtJoinId=(EditText) findViewById(R.id.edtJoinId);
        edtJoinEmail=(EditText) findViewById(R.id.edtJoinEmail);
        edtJoinPhone=(EditText) findViewById(R.id.edtJoinPhone);
        edtJoinPw=(EditText) findViewById(R.id.edtJoinPw);
        edtReJoinPw=(EditText) findViewById(R.id.edtReJoinPw);
        btnOk=(Button) findViewById(R.id.btnOk);
        btnNo=(Button) findViewById(R.id.btnNo);
        btnIdtest=(Button) findViewById(R.id.btnIdtest);
        myHelper = new myDBHelper(this);



        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(Reflag==1) {
                pass1 = edtJoinPw.getText().toString();
                pass2 = edtReJoinPw.getText().toString();


                if (pass1.equals(pass2)) {
                    sqlDB = myHelper.getWritableDatabase();//쓰기전용으로 DB연다
                    sqlDB.execSQL("INSERT INTO userTBL VALUES ('" + edtJoinId.getText().toString() + "','" + edtJoinName.getText().toString() + "','" + edtJoinPw.getText().toString() + "','" + edtJoinEmail.getText().toString() + "','" + edtJoinPhone.getText().toString() + "');");
                    sqlDB.close();//레코드 추가작업 끝나면 DB닫기
                    //사용자는 레코드가 추가되면 잘 입역되었다는 안내맨트 친절하게 전달
                    Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 동일하지 않습니다.\n다시 입력해주세요.", Toast.LENGTH_LONG).show();
                }
            }
            else
                Toast.makeText(getApplicationContext(), "ID 중복확인을 해주세요.", Toast.LENGTH_LONG).show();


            }
        });

        btnIdtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM userTBL;", null); // 개수 2차원 배열 형태로 저장됨
                String edt1 = null;
                String uId1 = null;

//                    Toast.makeText(getApplicationContext(), "중복확인 되었습니다.", Toast.LENGTH_SHORT).show();
//                    Reflag = 1;


                    while (cursor.moveToNext()) {
                        edt1 = cursor.getString(0);
                        uId1 = edtJoinId.getText().toString();
                        if (edt1.equals(uId1)) {
                            Toast.makeText(getApplicationContext(), "동일한 ID가 존재합니다.", Toast.LENGTH_SHORT).show();
                            Reflag = 0;
                            break;
                        } else {
                            Toast.makeText(getApplicationContext(), "중복확인 되었습니다.", Toast.LENGTH_SHORT).show();
                            Reflag = 1;
                            break;
                        }
                    }

                    cursor.close();//cursor를 사용했으면 종료해줘야함
                    sqlDB.close();//데이터베이스를 열었으면 닫아야 한다

            }
        });
    }






    public static class myDBHelper extends SQLiteOpenHelper {
        //2개의 필수 메소드 onCreate onUpgrave()
        //myBDhelper클래스 내부에 커서를 놓고 메소드 override해서 씀
        //myBDhelper는 생성자가 데이터베이스를 만듦 SQLiteopenHelper();
        public myDBHelper(Context context){//클래스 추가 Context 프로젝트에 DB생성
            super(context,"userDB",null,1);//실질적인 DB생성위치 SQLiteOpenHelper(); 생성자
        }
        @Override//code override 에서 추가
        public void onCreate(SQLiteDatabase db) {//데이터베이스생성
            db.execSQL("CREATE TABLE userTBL ( uId CHAR(30) PRIMARY KEY,uName CHAR(10),uPw CHAR(32),uEmail CHAR(40),uPhone CHAR(20));");//SQL문을 수행하는 메소드
        }
        //추가 메소드가 필요함 onUpgrade()필수메소드 추가되면 에러메시지 사라짐
        @Override//code override 에서 추가,
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//테이블이 있으면 삭제하고 테이블을 다시 생성
            //데이터베이스의 테이블을 신버전으로 바꿔라
            db.execSQL("DROP TABLE IF EXISTS userTBL");//테이블이 있으면 삭제하는 쿼리
            //테이블 없으면 테이블 생성 위에서 썻던것 호출
            onCreate(db);
        }
    }

}//MainActivity


