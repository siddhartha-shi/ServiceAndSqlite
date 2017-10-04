package com.mm.serviceandsqlite;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public Runnable mRunnable = null;
    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final MyDBHelper myDBHelper = new MyDBHelper(getApplicationContext());
        MyDBHelper.insertIntoDatabase(myDBHelper.getReadableDatabase(), "1", "Hi sid");
        final Handler mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                boolean isInfoAvailable = MyDBHelper.isAnyInfoAvailable(getApplicationContext());
                if (isInfoAvailable) {
                    String availableInfo = MyDBHelper.show_available_data(myDBHelper.getReadableDatabase());
                    Toast.makeText(getApplicationContext(), String.valueOf(availableInfo), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "empty Table", Toast.LENGTH_LONG).show();
                }
                mHandler.postDelayed(mRunnable, 10 * 500);
            }
        };
        mHandler.postDelayed(mRunnable, 10 * 500);

        return super.onStartCommand(intent, flags, startId);
    }
}
