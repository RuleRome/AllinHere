package com.example.toggleButton;

import com.example.toggleButton.view.ToggleButton;
import com.example.toggleButton.view.ToggleButton.OnToggleButtonChangeListener;
import com.example.toggleButton.view.ToggleButton.ToggleState;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ToggleButton tbutton;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tbutton = (ToggleButton) findViewById(R.id.view_tb);
        tbutton.setSlideBackgroundResource(R.drawable.checkbox_bg);
        tbutton.setSwitchBackgroundResource(R.drawable.checkbox_swich);
//        tbutton.setToggleState(ToggleState.Open);
        
        tbutton.setOnToggleButtonChangeListener(new OnToggleButtonChangeListener() {
			
			@Override
			public void onToggleButtonStateChange(ToggleState state) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, state==ToggleState.Open?"¿ªÆô":"¹Ø±Õ", 0).show();
			}
		});
    }


    
    
}
