package com.statckbackjd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.statckbackjd.fragment.Fragment1;
import com.statckbackjd.fragment.Fragment2;
import com.statckbackjd.fragment.Fragment3;
import com.statckbackjd.fragment.Fragment4;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup radioGroup;
    private RadioButton rb_cart;
    private RadioButton rb_category;
    private RadioButton rb_home;
    private RadioButton rb_personal;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // 获取Fragment管理者
        fragmentManager = getSupportFragmentManager();
        // 先默认添加fragment1
        addFragment(new Fragment1(), "fragment1");
    }

    /**
     * 初始化控件
     */
    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb_cart = (RadioButton) findViewById(R.id.rb_cart);
        rb_category = (RadioButton) findViewById(R.id.rb_category);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_personal = (RadioButton) findViewById(R.id.rb_personal);

        rb_cart.setOnClickListener(this);
        rb_category.setOnClickListener(this);
        rb_home.setOnClickListener(this);
        rb_personal.setOnClickListener(this);
    }

    public void addFragment(Fragment fragment, String tag) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(R.id.framelayout, fragment, tag);
        // 添加到回退栈,并定义标记
        beginTransaction.addToBackStack(tag);
        beginTransaction.commit();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_home:
                addFragment(new Fragment1(), "fragment1");
                break;
            case R.id.rb_cart:
                addFragment(new Fragment2(), "fragment2");
                break;
            case R.id.rb_category:
                addFragment(new Fragment3(), "fragment3");
                break;
            case R.id.rb_personal:
                addFragment(new Fragment4(), "fragment4");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判断当前按键是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 获取当前回退栈中的Fragment个数
            int backStackEntryCount = fragmentManager.getBackStackEntryCount();
            if (backStackEntryCount > 1) {
                // 立即回退一步
                fragmentManager.popBackStackImmediate();
                // 获取当前退到了哪一个Fragment上,重新获取当前的Fragment回退栈中的个数
                FragmentManager.BackStackEntry backStack = fragmentManager
                        .getBackStackEntryAt(fragmentManager
                                .getBackStackEntryCount() - 1);
                // 获取当前栈顶的Fragment的标记值
                String tag = backStack.getName();
                if ("fragment1".equals(tag)) {
                    // 设置首页选中
                    rb_home.setChecked(true);
                } else if ("fragment2".equals(tag)) {
                    rb_cart.setChecked(true);
                } else if ("fragment3".equals(tag)) {
                    rb_category.setChecked(true);
                } else if ("fragment4".equals(tag)) {
                    rb_personal.setChecked(true);
                }
            } else {
                finish();
            }
        }
        return true;
    }
}
