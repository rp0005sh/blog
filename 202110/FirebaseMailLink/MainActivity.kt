package com.sample.test.myapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    /**
     * 起動時にする処理
     * ・認証ボタンの活性化可否
     * ・一度認証したメアドの再表示
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 認証ボタンはデフォルト非活性。メールからアプリ起動したときだけ活性化
        findViewById<View>(R.id.SignIn)?.isEnabled = (intent.data == null)
        

        // 招待要求したメールは不揮発領域に持っておき、この画面が表示される度に表示させる
        findViewById<TextView>(R.id.editText).text =
            getSharedPreferences("mail", MODE_PRIVATE).getString("address", "")
    }

    /** 招待メールボタン押下イベント
     * Firebaseに招待メールを送信するように要求する  */
    fun onClickSendMail(v: View?) {
        val tv = findViewById<TextView>(R.id.editText)
        requestEmail(tv.text.toString())
    }

    /** ログインボタン押下イベント
     * Firebaseにログイン認証を要求する  */
    fun onClickSignIn(v: View?) {
        // メールのリンクから起動したときはIntentにemailLink情報が入っている
        val emailLink = intent.data?.toString() ?: ""

        // 入力エリアからメールアドレスを取得
        val tv = findViewById<TextView>(R.id.editText)
        val email = tv.text.toString()

        // 認証要求
        requestSignIn(email, emailLink)
    }

    /**
     * firebaseに招待メールを要求
     * @param email 受信したいメールアドレス
     */
    private fun requestEmail(email: String) {
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setUrl("https://mailloginsample.page.link")
            .setHandleCodeInApp(true)
            .build()
        val auth = FirebaseAuth.getInstance()
        auth.sendSignInLinkToEmail(email, actionCodeSettings)
            .addOnCompleteListener { task: Task<Void?> ->
                // 招待メールの要求に成功した。
                if (task.isSuccessful) {
                    Toast.makeText(this, "このメールアドレスにメールを送信しました。", Toast.LENGTH_SHORT)
                        .show()
                    // メールアドレスは不揮発領域に保存しておく
                    getSharedPreferences("mail", MODE_PRIVATE).edit()
                        .putString("address", email).apply()
                }
                // 招待メールの要求に失敗した。サーバのドメインが違う、メアドがおかしい等。エラーログをトースト表示さえて詳細確認
                else {
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
    }

    /**
     * fairebaseに認証要求
     * @param email 認証するメールアドレス
     * @param emailLink メールリンク情報
     */
    private fun requestSignIn(email: String, emailLink: String) {
        val auth = FirebaseAuth.getInstance()
        if (auth.isSignInWithEmailLink(emailLink)) {
            auth.signInWithEmailLink(email, emailLink)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    // 認証成功。認証後に画面遷移させたければここに書く
                    if (task.isSuccessful) {
                        val result = task.result
                        Toast.makeText(
                            this,
                            "認証成功：IDは" + result.user!!.uid,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    // 認証失敗。エラーメッセージを確認
                    else {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "リンクが正しくありません。emailLink=$emailLink", Toast.LENGTH_LONG).show()
        }
    }
}
