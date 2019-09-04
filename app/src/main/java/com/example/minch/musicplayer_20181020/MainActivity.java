package com.example.minch.musicplayer_20181020;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    JoinActivity.myDBHelper myHelper;//그냥 쓸수없음 객체생성하고 사용, 객체선언
    EditText edtUserId, edtUserPw, tesT, tesTt;//위젯을 사용하게 위젯 선언
    Button btnLog, btnJoin;
    SQLiteDatabase sqlDB;//쿼리문 수행용 객체선언   .execSQL();사용용
    Cursor s;
    int IDflag=0,PWflag=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setTitle("뮤직플레이어");//액티비티 타이틀 생성
        edtUserId = (EditText) findViewById(R.id.edtUserId);
        edtUserPw = (EditText) findViewById(R.id.edtUserPw);
        btnLog = (Button) findViewById(R.id.btnLog);
        btnJoin = (Button) findViewById(R.id.btnJoin);
//        tesT = (EditText) findViewById(R.id.tesT);
//        tesTt = (EditText) findViewById(R.id.tesTt);
        myHelper = new JoinActivity.myDBHelper(this);//데이터베이스 생성


        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //전체 레코드 출력 걸그룹 이름만 모아서 edtNameResult+인원 모아서 edtNumberResult
                //조회해서 보여주는것 읽기 전용 DB읽기
                sqlDB=myHelper.getReadableDatabase();//읽기 전용으로 DB열기
                //SELECT문으로 레코드 불러오면 2차원 표형태 저장할 Cursor객체
//                Cursor s;//2차원 테이블 형태로 저장할 객체
//                s=sqlDB.rawQuery("SELECT uPw FROM userTBL WHERE uID='"+edtUserId+"';", null);//조회해서 2차원 cursor에 저장 SELECT문은 execSQL()로 수행할 수 없다. rawQuery()Select
//                //걸그룹 이름을 모을 변수 strNames 인원수 모을것 strNumber
//                String passW=s.toString();
//                String result = edtUserPw.getText().toString();
//                if(!result.equals(passW))
//                {
//                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
//                    s.close();//cursor를 사용했으면 종료해줘야함
//                    sqlDB.close();//데이터베이스를 열었으면 닫아야 한다
//                }
//
//                else {
//                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
//                    s.close();//cursor를 사용했으면 종료해줘야함
//                    sqlDB.close();//데이터베이스를 열었으면 닫아야 한다.
//                }
//
//                tesT.setText(result);
//                tesTt.setText(passW);

                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM userTBL;", null); // 개수 2차원 배열 형태로 저장됨
                String edt1 = null;//로그인화면에서 로그인ID입력한것
                String pass1 = null;//로그인화면에서 로그인비밀번호 입력한것
                String edt2 = null;//데이터베이스에 등록된 회원ID 저장할 변수
                String pass2 = null;//데이터베이스에 등록된 회원 비밀번호 저장할 변수
                while (cursor.moveToNext()) {
                    edt2 = cursor.getString(0);
                    pass2 = cursor.getString(2);
                    edt1 = edtUserId.getText().toString();//내가 휴대폰 로그인 입력한 ID
                    pass1 = edtUserPw.getText().toString(); //내가 휴대폰 로그인 입력한 PASSWORD
                    try {
                        if (edt2.equals(edt1)) {
                            IDflag=1;
                            if (pass2.equals(pass1)) {
                                Toast.makeText(getApplicationContext(), "회원 인증 확인", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                PWflag=1;
                                Intent One = new Intent(getApplicationContext(), MusicActivity.class);
                                startActivity(One);
                                break;
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "회원이지만 비밀번호가 틀립니다", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        else {
                            /* Toast.makeText(getApplicationContext(), "아이디가 틀립니다", Toast.LENGTH_SHORT).show(); */
                        }
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }//while문
                if(IDflag==0 && PWflag==0){
                    Toast.makeText(getApplicationContext(),"아이디가 틀립니다.", Toast.LENGTH_LONG).show();
                }
                cursor.close();//cursor를 사용했으면 종료해줘야함
                sqlDB.close();//데이터베이스를 열었으면 닫아야 한다

            }

        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(i);
            }
        });
    }


}