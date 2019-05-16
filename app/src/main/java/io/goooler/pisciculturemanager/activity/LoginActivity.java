package io.goooler.pisciculturemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.goooler.pisciculturemanager.R;
import io.goooler.pisciculturemanager.base.ActivityCollector;
import io.goooler.pisciculturemanager.base.BaseActivity;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.model.Constants;
import io.goooler.pisciculturemanager.model.UserBean;
import io.goooler.pisciculturemanager.model.UserBeanDao;
import io.goooler.pisciculturemanager.model.UserInfoStateBean;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView titleImg;
    private TextView welcomeTxt;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button submitBtn;
    private Button signupBtn;

    private UserBeanDao dao;

    private boolean signing;

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

        dao = BaseApplication.getDaoSession().getUserBeanDao();
        if (BaseApplication.getUserInfoState().isSaved()) {
            startActivity(new Intent(this, MainActivity.class));
        }

        submitBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    //登录时验证用户名和密码
    private void verify(String username, String password) {
        if (Constants.NULL_STRING.equals(password) || Constants.NULL_STRING.equals(username)) {
            BaseApplication.showToast(getString(R.string.login_missed));
        } else {
            List userList = dao.queryBuilder().where(UserBeanDao.Properties.Username.eq(username)).build().list();
            if (userList.size() == 0) {
                BaseApplication.showToast(getString(R.string.login_none));
            } else {
                UserBean bean = (UserBean) userList.get(0);
                if (bean.getUsername().equals(username) && bean.getPassword().equals(password)) {
                    BaseApplication.setUserInfoState(new UserInfoStateBean(username, true));
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    BaseApplication.showToast(getString(R.string.login_failed));
                }
            }
        }
    }

    //创建新用户，将信息加入数据库
    private void addUser(String username, String password) {
        if (Constants.NULL_STRING.equals(password)) {
            BaseApplication.showToast(getString(R.string.register_nopasswd));
            return;
        }
        int querySize = dao.queryBuilder().where(UserBeanDao.Properties.Username.eq(username)).build().list().size();
        if (querySize == 0) {
            //没有重复则新建用户
            dao.insert(new UserBean(username, password));
            BaseApplication.showToast(getString(R.string.register_succeed));
        } else {
            BaseApplication.showToast(getString(R.string.register_duplicated));
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
