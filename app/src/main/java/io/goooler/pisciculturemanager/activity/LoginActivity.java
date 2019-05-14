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
import io.goooler.pisciculturemanager.base.BaseActivity;
import io.goooler.pisciculturemanager.base.BaseApplication;
import io.goooler.pisciculturemanager.model.UserBean;
import io.goooler.pisciculturemanager.model.UserBeanDao;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView titleImg;
    private TextView welcomeTxt;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private Button submitBtn;
    private Button signupBtn;

    private UserBeanDao dao;

    private final String defaultName = "admin";
    private final String defaultPasswd = "12345";
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

        submitBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    private void verify(String userName, String password) {
        if (NULL_STRING.equals(password) || NULL_STRING.equals(userName)) {
            showToast(getString(R.string.login_missed));
        } else {
            List userList = dao.queryBuilder().where(UserBeanDao.Properties.Username.eq(userName)).build().list();
            if (userList.size() == 0) {
                showToast(getString(R.string.login_none));
            } else {
                UserBean bean = (UserBean) userList.get(0);
                if (bean.getUsername().equals(userName) && bean.getPassword().equals(password)) {
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    showToast(getString(R.string.login_failed));
                }
            }
        }
    }

    private void addUser(String userName, String password) {
        if (NULL_STRING.equals(password)) {
            showToast(getString(R.string.register_nopasswd));
            return;
        }
        int querySize = dao.queryBuilder().where(UserBeanDao.Properties.Username.eq(userName)).build().list().size();
        if (querySize == 0) {
            //没有重复则新建用户
            dao.insert(new UserBean(userName, password));
            showToast(getString(R.string.register_succeed));
        } else {
            showToast(getString(R.string.register_duplicated));
        }
    }

    private void setDefault() {
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
                setDefault();
            } else {
                verify(usernameTxt.getText().toString().trim(), passwordTxt.getText().toString().trim());
            }
        } else if (v == signupBtn) {
            if (signing) {
                setDefault();
            } else {
                welcomeTxt.setText(R.string.signingup);
                submitBtn.setText(R.string.enter);
                signupBtn.setText(R.string.cancel);
                signing = true;
            }
        }
    }
}
