package win.i029.syslib.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import win.i029.platlib.FirstClass;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = findViewById(R.id.id_text_view);
        Button bt = findViewById(R.id.id_button);
		// final variable
		final String tag3 = FirstClass.TAG3;
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstClass firstClass = new FirstClass(getApplicationContext());
                tv.setText(String.format("Result = %d   , TAG3=%s", firstClass.Add(2,20), tag3));
            }
        });
    }
}
