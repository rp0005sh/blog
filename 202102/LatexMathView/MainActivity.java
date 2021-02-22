package com.sample.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import ru.noties.jlatexmath.JLatexMathView;

public class MainActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JLatexMathView j_latex_math_view = findViewById(R.id.j_latex_math_view);
        j_latex_math_view.setLatex(" x = \\frac{-b \\pm \\sqrt{b^2-4ac}} {2a}");
    }
}
