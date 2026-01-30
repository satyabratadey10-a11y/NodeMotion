package com.example.nodemotion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.Gravity;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    // Load the C++ library
    static {
        System.loadLibrary("nodemotion");
    }

    private EditText codeInput;
    private TextView statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Dark Theme UI Layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#121212"));
        layout.setPadding(40, 40, 40, 40);

        // Header
        TextView title = new TextView(this);
        title.setText("NodeMotion");
        title.setTextColor(Color.CYAN);
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 20);
        layout.addView(title);

        // Code Input
        codeInput = new EditText(this);
        codeInput.setHint("// Write JS Script...\nreturn 10 + 20;");
        codeInput.setHintTextColor(Color.GRAY);
        codeInput.setTextColor(Color.GREEN);
        codeInput.setBackgroundColor(Color.parseColor("#1E1E1E"));
        codeInput.setMinLines(10);
        layout.addView(codeInput);

        // Run Button
        Button runBtn = new Button(this);
        runBtn.setText("RUN SCRIPT");
        runBtn.setBackgroundColor(Color.parseColor("#03DAC5"));
        runBtn.setTextColor(Color.BLACK);
        runBtn.setOnClickListener(v -> {
            String script = codeInput.getText().toString();
            String res = runScript(script);
            statusView.setText("Result: " + res);
        });
        layout.addView(runBtn);

        // Output
        statusView = new TextView(this);
        statusView.setText("Ready.");
        statusView.setTextColor(Color.WHITE);
        statusView.setTextSize(18);
        statusView.setPadding(0, 30, 0, 0);
        layout.addView(statusView);

        setContentView(layout);
    }

    public native String runScript(String scriptCode);
}
