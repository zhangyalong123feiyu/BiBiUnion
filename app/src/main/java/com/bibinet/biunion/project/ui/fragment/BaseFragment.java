package com.bibinet.biunion.project.ui.fragment;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bibinet.biunion.project.application.Constants;
import com.bibinet.biunion.project.utils.LoactionUtils;

import butterknife.ButterKnife;

/**
 * Created by bibinet on 2017-6-20.
 */

public abstract class BaseFragment extends Fragment{

    public boolean isHasPermisson(String permisson){
      return   ContextCompat.checkSelfPermission(getActivity(),permisson)==PackageManager.PERMISSION_GRANTED;
    }
    public void reQuestPermisson(String permisson,int reQustCode){
          requestPermissions(new String[]{permisson},reQustCode);
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    protected abstract int getLayoutId();

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        onBaseViewCreated(view, savedInstanceState);
    }

    protected abstract void onBaseViewCreated(View view, Bundle savedInstanceState);
}
