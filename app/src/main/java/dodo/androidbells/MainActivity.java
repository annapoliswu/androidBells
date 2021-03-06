package dodo.androidbells;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)  {
        Intent intent = new Intent(this, GameActivity.class);
        switch(view.getId())    {
            case R.id.button:
                startActivity(intent);
        }
    }
}
