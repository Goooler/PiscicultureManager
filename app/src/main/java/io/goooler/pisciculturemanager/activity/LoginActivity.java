package io.goooler.pisciculturemanager.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.ActivityCollector;
import io.goooler.pisciculturemanager.base.BaseActivity;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.UserInfoStateBean;
import io.goooler.pisciculturemanager.util.DatabaseUtil;
import io.goooler.pisciculturemanager.util.SpUtil;
import io.goooler.pisciculturemanager.util.ToastUtil;

/**
 * 管理登录的页面，记住登录状态后直接跳转 MainActivity
 * TODO: 本页的密码明文存储，校验也是明文校验，只是为了方便演示原理，后期可以加入加密
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView welcomeTxt;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button submitBtn;
    private Button signupBtn;

    private boolean signing;
    private boolean altering;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView titleImg = find(R.id.login_title_img);
        welcomeTxt = find(R.id.welcome);
        usernameTxt = find(R.id.login_username);
        passwordTxt = find(R.id.login_password);
        submitBtn = find(R.id.submit);
        signupBtn = find(R.id.signup);
        submitBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);

        skipLoginOrAlterPassword();
    }

    /**
     * 验证是否跳过登录或修改密码
     * 启动 app 时已登录直接跳转到 MainActivity，要修改密码时可复用登录页
     */
    private void skipLoginOrAlterPassword() {
        altering = getIntent().getBooleanExtra(Constants.ALTER_PASSWORD, false);
        boolean logined = SpUtil.getUserInfoState().isSaved();
        if (logined && !altering) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (logined && altering) {
            welcomeTxt.setText(R.string.alter_password);
            usernameTxt.setText(SpUtil.getUserInfoState().getUsername());
            submitBtn.setText(R.string.enter);
            signupBtn.setText(R.string.cancel);
            setEditable(false);
        }
    }

    /**
     * 设置单个 EditText 的可编辑状态
     *
     * @param editable 可编辑与否
     */
    private void setEditable(boolean editable) {
        //设置输入框中的光标不可见
        usernameTxt.setCursorVisible(editable);
        //无焦点
        usernameTxt.setFocusable(editable);
        //触摸时也得不到焦点
        usernameTxt.setFocusableInTouchMode(editable);
    }

    /**
     * 校验用户名和密码
     */
    private void verify(String username, String password) {
        if (Constants.NULL_STRING.equals(password) || Constants.NULL_STRING.equals(username)) {
            ToastUtil.showToast(R.string.login_missed);
        } else {
            if (!DatabaseUtil.haveTheUser(username)) {
                ToastUtil.showToast(R.string.login_none);
            } else {
                if (DatabaseUtil.verifyUser(username, password)) {
                    SpUtil.setUserInfoState(new UserInfoStateBean(username, true));
                    initOverallDB();
                    startActivity(new Intent(this, MainActivity.class));
                    ToastUtil.showToast(R.string.log_in_succeed);
                    finish();
                } else {
                    ToastUtil.showToast(R.string.login_failed);
                }
            }
        }
    }

    /**
     * 创建新用户，将信息加入数据库
     */
    private void addUser(String username, String password) {
        if (Constants.NULL_STRING.equals(password)) {
            ToastUtil.showToast(R.string.register_nopasswd);
            return;
        }
        if (!DatabaseUtil.haveTheUser(username)) {
            //没有重复则新建用户
            DatabaseUtil.addUser(username, password);
            ToastUtil.showToast(R.string.register_succeed);
        } else {
            ToastUtil.showToast(R.string.register_duplicated);
        }
    }

    /**
     * 更改用户密码
     */
    private void alterPassword() {
        DatabaseUtil.alterUser(usernameTxt.getText().toString(),
                passwordTxt.getText().toString());
        ToastUtil.showToast(R.string.data_updated);
        finish();
    }

    /**
     * 初始化主数据库，仅在程序第一次运行的时候初始化
     */
    private void initOverallDB() {
        if (SpUtil.isFirstRun()) {
            DatabaseUtil.initDatabase();
            SpUtil.setFirstRunState(false);
        }
    }

    /**
     * 界面恢复默认状态
     */
    private void restoreDefault() {
        submitBtn.setText(R.string.submit);
        signupBtn.setText(R.string.signup);
        welcomeTxt.setText(R.string.welcome);
        signing = false;
    }

    @Override
    public void onClick(View v) {
        if (v == submitBtn) {
            //正在修改密码
            if (altering) {
                alterPassword();
            } else {
                //正在注册
                if (signing) {
                    addUser(usernameTxt.getText().toString(), passwordTxt.getText().toString());
                    restoreDefault();
                } else {
                    verify(usernameTxt.getText().toString(), passwordTxt.getText().toString());
                }
            }
        } else if (v == signupBtn) {
            //正在修改密码
            if (altering) {
                finish();
            } else {
                //正在注册
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();
    }
}
