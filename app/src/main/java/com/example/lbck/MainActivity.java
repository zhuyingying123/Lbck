package com.example.lbck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.lbck.fragment.QuickAreaFragment;

public class MainActivity extends EBaseActivity {

    private FrameLayout frag_area;
    private SwitchButton switch_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        switch_btn = findViewById(R.id.switch_btn);
        frag_area = findViewById(R.id.frag_area);
        //快捷区


    }

    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            if (!to.isAdded()) {
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.add(R.id.frag_area, to).commitAllowingStateLoss();
                }

            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.show(to).commitAllowingStateLoss();
                }

            }
        }
    }
}
