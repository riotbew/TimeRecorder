package com.jim.recorder.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jim.common.BaseActivity;
import com.jim.recorder.R;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {

    TextView rs;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    protected void initView() {
        rs = findViewById(R.id.result);
        findViewById(R.id.btn_rx_java).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                File[] folders =new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/baofeng/download/").listFiles();
                rs.setText("");
                File[] folders = new File("/system/media/Pre-loaded/").listFiles();
                Disposable disposable = Observable.fromArray(folders)
                        .flatMap(new Function<File, ObservableSource<File>>() {
                            @Override
                            public ObservableSource<File> apply(File file) throws Exception {
                                return Observable.fromArray(file.listFiles());
                            }
                        })
                        .filter(new Predicate<File>() {
                            @Override
                            public boolean test(File file) throws Exception {
                                return file.getName().endsWith("jpg");
                            }
                        })
                        .map(new Function<File, String>() {
                            @Override
                            public String apply(File file) throws Exception {
                                return file.getName();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                rs.append(s+"\n");
                            }
                        });
                compositeDisposable.add(disposable);

            }
        });
    }
}
