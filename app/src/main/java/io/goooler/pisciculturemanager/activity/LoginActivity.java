package io.goooler.pisciculturemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.ActivityCollector;
import io.goooler.pisciculturemanager.base.BaseActivity;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.UserInfoStateBean;
import io.goooler.pisciculturemanager.util.DatabaseUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView titleImg;
    private TextView welcomeTxt;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button submitBtn;
    private Button signupBtn;

    private boolean signing;

    @Override
    protected void onStart() {
        super.onStart();
        skipLogin();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titleImg = find(R.id.login_title_img);
        welcomeTxt = find(R.id.welcome);
        usernameTxt = find(R.id.login_username);
        passwordTxt = find(R.id.login_password);
        submitBtn = find(R.id.submit);
        signupBtn = find(R.id.signup);

        submitBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    //验证是否已经登录，已登录直接跳转到 MainActivity
    private void skipLogin() {
        if (BaseApplication.getUserInfoState().isSaved()) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    //登录时验证用户名和密码
    private void verify(String username, String password) {
        if (Constants.NULL_STRING.equals(password) || Constants.NULL_STRING.equals(username)) {
            BaseApplication.showToast(R.string.login_missed);
        } else {
            if (!DatabaseUtil.haveTheUser(username)) {
                BaseApplication.showToast(R.string.login_none);
            } else {
                if (DatabaseUtil.verifyUser(username, password)) {
                    BaseApplication.setUserInfoState(new UserInfoStateBean(username, true));
                    initOverallDB();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    BaseApplication.showToast(R.string.login_failed);
                }
            }
        }
    }

    //创建新用户，将信息加入数据库
    private void addUser(String username, String password) {
        if (Constants.NULL_STRING.equals(password)) {
            BaseApplication.showToast(R.string.register_nopasswd);
            return;
        }
        if (!DatabaseUtil.haveTheUser(username)) {
            //没有重复则新建用户
            DatabaseUtil.addUser(username, password);
            BaseApplication.showToast(R.string.register_succeed);
        } else {
            BaseApplication.showToast(R.string.register_duplicated);
        }
    }

    //初始化主数据库，仅在第一次运行的时候初始化
    private void initOverallDB() {
        if (BaseApplication.isFirstRun()) {
            DatabaseUtil.initDatabase();
            BaseApplication.setFirstRunState(false);
        }
    }

    //界面恢复默认状态
    private void restoreDefault() {
        submitBtn.setText(R.string.submit);
        signupBtn.setText(R.string.signup);
        welcomeTxt.setText(R.string.welcome);
        signing = false;
    }

    @Override
    public void onClick(View v) {
        if (v == submitBtn) {
            if (signing) {
                addUser(usernameTxt.getText().toString().trim(), passwordTxt.getText().toString().trim());
                restoreDefault();
            } else {
                verify(usernameTxt.getText().toString().trim(), passwordTxt.getText().toString().trim());
            }
        } else if (v == signupBtn) {
            if (signing) {
                restoreDefault();
            } else {
                welcomeTxt.setText(R.string.signingup);
                submitBtn.setText(R.string.enter);
                signupBtn.setText(R.string.cancel);
                signing = true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();
    }
}
