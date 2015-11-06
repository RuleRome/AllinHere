package com.example.lockscreen;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private DevicePolicyManager mPMD;
	private ComponentName mCN;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
     // 获取设备策略服务
		mPMD = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mCN = new ComponentName(this, AdminReceiver.class);
        active();
    }
//	激活设备管理器
	public void active(){
//		激活设备管理器，其实就是跳转到激活设备的activity
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mCN);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "超级设备管理器");
		startActivity(intent);
	}
//  一键锁屏
	public void click2(View v){
		if(mPMD.isAdminActive(mCN)){
//			判断设备管理器是否激活
			mPMD.lockNow();
//			设置锁屏密码
			mPMD.resetPassword("", 0);
			finish();
		}
		else{
			Toast.makeText(this, "锁屏必须激活设备管理器", 0).show();
		}
	}
//	卸载
	public void click3(View v){
		mPMD.removeActiveAdmin(mCN);
//		卸载程序
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setData(Uri.parse("package:"+getPackageName()));
		startActivity(intent);
	}
//  清除数据
	public void click4(View v){
		if(mPMD.isAdminActive(mCN)){
//			判断设备管理器是否激活
//			mPMD.wipeData(0);
			finish();
		}
		else{
			Toast.makeText(this, "锁屏必须激活设备管理器", 0).show();
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
