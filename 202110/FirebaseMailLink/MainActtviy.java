package com.sample.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Optional;

public class MainActivity extends AppCompatActivity {
    /**
     * 起動時にする処理
     * ・認証ボタンの活性化可否
     * ・一度認証したメアドの再表示
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 認証ボタンはデフォルト非活性。メールからアプリ起動したときだけ活性化
        Optional.ofNullable(getIntent()).map(Intent::getData).map(Object::toString)
                .ifPresent(link -> findViewById(R.id.SignIn).setEnabled(true));

        // 招待要求したメールは不揮発領域に持っておき、この画面が表示される度に表示させる
        ((TextView) findViewById(R.id.editText)).setText(
        getSharedPreferences("mail",MODE_PRIVATE).getString("address", ""));

    }

    /** 招待メールボタン押下イベント
     * Firebaseに招待メールを送信するように要求する */
   public void onClickSendMail(View v) {
       TextView tv = findViewById(R.id.editText);
       requestEmail(tv.getText().toString());
    }

    /** ログインボタン押下イベント
     * Firebaseにログイン認証を要求する */
    public void onClickSignIn(View v) {
       // メールのリンクから起動したときはIntentにemailLink情報が入っている
        Intent intent = getIntent();
        String emailLink = intent.getData().toString();

        // 入力エリアからメールアドレスを取得
        TextView tv = findViewById(R.id.editText);
        String email = tv.getText().toString();

        // 認証要求
        requestSignIn(email, emailLink);
    }

    /**
     * firebaseに招待メールを要求
     * @param email 受信したいメールアドレス
     */
    private void requestEmail(String email) {
       ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
               .setUrl("https://mailloginsample.page.link")
               .setHandleCodeInApp(true)
               .build();
       FirebaseAuth auth = FirebaseAuth.getInstance();
       auth.sendSignInLinkToEmail(email, actionCodeSettings)
               .addOnCompleteListener(task -> {
                   // 招待メールの要求に成功した。
                   if (task.isSuccessful()) {
                       Toast.makeText(this, "このメールアドレスにメールを送信しました。", Toast.LENGTH_SHORT)
                               .show();
                       // メールアドレスは不揮発領域に保存しておく
                       getSharedPreferences("mail", MODE_PRIVATE).edit().putString("address", email).apply();
                   }
                   // 招待メールの要求に失敗した。サーバのドメインが違う、メアドがおかしい等。エラーログをトースト表示さえて詳細確認
                   else {
                       Toast.makeText(this, String.valueOf(task.getException()), Toast.LENGTH_LONG)
                               .show();
                   }
               });
    }

    /**
     * fairebaseに認証要求
     * @param email 認証するメールアドレス
     * @param emailLink メールリンク情報
     */
    private void requestSignIn(String email, String emailLink) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.isSignInWithEmailLink(emailLink)) {
            auth.signInWithEmailLink(email, emailLink)
                    .addOnCompleteListener(task -> {
                        // 認証成功。認証後に画面遷移させたければここに書く
                        if (task.isSuccessful()) {
                            AuthResult result = task.getResult();
                            Toast.makeText(this, "認証成功：IDは" + result.getUser().getUid(), Toast.LENGTH_LONG).show();
                        }
                        
                        // 認証失敗。エラー内容をトーストに出すので確認する
                        else {
                            Toast.makeText(this, String.valueOf(task.getException()), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(this, "リンクが正しくありません。emailLink=" + emailLink, Toast.LENGTH_LONG).show();
        }
    }
}
