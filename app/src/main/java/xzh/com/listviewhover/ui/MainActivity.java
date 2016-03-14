package xzh.com.listviewhover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import xzh.com.listviewhover.R;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.list_button)
    Button listButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.list_button)
    void click(View view) {
        startActivity(new Intent(this, SelectedCityActivity.class));
    }
}


