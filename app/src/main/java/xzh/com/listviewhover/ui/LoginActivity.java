package xzh.com.listviewhover.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import xzh.com.listviewhover.R;
import xzh.com.listviewhover.base.Constants;

/**
 * Created by xiangzhihong on 2016/3/14 on 12:07.
 * 测试服务端的登录json
 */
public class LoginActivity extends AppCompatActivity {
    @InjectView(R.id.account)
    EditText account;
    @InjectView(R.id.pwd)
    EditText pwd;
    @InjectView(R.id.login)
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       initProxy();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        initView();
    }

    private void initProxy() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        //设置虚拟机的策略
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    private void initView() {
    }

    @OnClick(R.id.login)
    void loginClick(View v){
        String userName=account.getText().toString();
        String password=pwd.getText().toString();
        doLogin(userName,password);
    }

    private void doLogin(String userName, String password) {
        final String[] result = {null};
        String reqUrl=null;
        reqUrl= Constants.LOGIN_URL+"userName="+userName+"&password="+password;
        try {
            doHttp(result, reqUrl);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage(result[0])
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                    }).setNegativeButton("取消",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        }  catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void doHttp(final String[] result, String reqUrl) {

        //            HttpClient httpclient = new DefaultHttpClient();
//            HttpGet request=new HttpGet(reqUrl);
//            request.addHeader("Accept","text/json");
//            HttpResponse response =httpclient.execute(request);
//            HttpEntity entity=response.getEntity();
//            String json = EntityUtils.toString(entity,"UTF-8");
//            if(json!=null&&json.contains("message")){
////                JSONObject jsonObject=new JSONObject(json);
////                result=jsonObject.get("message").toString();
//                result="登录成功";
//            }else {
//              result="登录失败请重新登录";
//            }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(this,reqUrl,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                if (content!=null&&content.contains("message")){
                    result[0] ="登录成功";
                }else {
                    result[0] ="登录失败";
                }
            }
        });
    }
}
