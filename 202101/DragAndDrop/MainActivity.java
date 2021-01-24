package com.sample.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

/**
 *  MainActivity側の実装
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {
    /** ドラッグされたView */
    private View mDragView;
    /** レイアウト */
    private GridLayout mParent;

    /**
     * アプリ起動時の最初に呼ばれるイベント
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 各Viewに、タッチ・ドラッグのイベントを設定していく
        mParent = findViewById(R.id.grid_layout);
        for (int i = 0; i < mParent.getChildCount(); i++) {
            View v = mParent.getChildAt(i);
            v.setOnTouchListener(this);
            v.setOnDragListener(this);
        }
    }

     /**
      * Viewを長押ししたとき通知されるイベント
      * @param v 対象のView
      * @param motionEvent イベント
      * @return イベント有効有無
      */
     @Override
     public boolean onTouch(View v, MotionEvent motionEvent) {
         if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
             // Viewをドラッグ状態にする。
             View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
             v.startDragAndDrop(null, shadow, v, 0);
             mDragView = v;

             // 図（b, c）より、ドラッグ中のViewは見えなくなる必要があるので、透過値調整して消す。
             // Visibilityで消すこともできるが、ドラッグ終了時のアニメーションが入るので、透過値で非表示を表現
             mDragView.setAlpha(0);
         }
         return true;
     }

     /**
      * ドラッグされたとき通知されるイベント
      * @param v 通知を受信したView
      * @param event イベント（使用するのは、ドラッグ終了、ドラッグしたまま他のViewの上にカーソルが合う)
      * @return イベント有効有無(必ずTrueにする)
      */
     @Override
     public boolean onDrag(View v, DragEvent event) {
         switch (event.getAction()) {
             // 手を放し、ドラッグが終了した時の処理。ドラッグしているViewを表示させる。
             case DragEvent.ACTION_DRAG_ENDED:
                 getMainExecutor().execute(() -> mDragView.setAlpha(1));
                 break;

             // ドラッグ中他のViewの上に乗る時の処理。Viewの位置を入れ替える
             case DragEvent.ACTION_DRAG_LOCATION:
                 getMainExecutor().execute(() -> swap(v, mDragView));
                 break;
         }
         return true;
     }

     /**
      * Viewの位置を入れ替える
      * @param v1 入れ替え対象１
      * @param v2 入れ替え対象2
      */
     private void swap(View v1, View v2) {
         // 同じViewなら入れ替える必要なし
         if (v1 == v2) return;

         // レイアウトパラメータを抜き出して、入れ替えを行う
         GridLayout.LayoutParams p1 = (GridLayout.LayoutParams) v1.getLayoutParams();
         GridLayout.LayoutParams p2 =  (GridLayout.LayoutParams) v2.getLayoutParams();
         mParent.removeView(v1);
         mParent.removeView(v2);
         mParent.addView(v1, p2);
         mParent.addView(v2, p1);
     }
}
