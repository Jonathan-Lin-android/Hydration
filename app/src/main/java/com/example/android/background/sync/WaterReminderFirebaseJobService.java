package com.example.android.background.sync;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.content.Context;
import android.os.AsyncTask;

public class WaterReminderFirebaseJobService extends JobService {
    private AsyncTask mBackgroundTask;
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(final Object[] objects) {
                Context context = WaterReminderFirebaseJobService.this;
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(final Object o) {
                jobFinished(jobParameters, false);
            }
        };
        mBackgroundTask.execute();
        // job still doing work in background thread
        return true;
    }

    @Override
    public boolean onStopJob(final JobParameters params) {
        if (mBackgroundTask != null)
            mBackgroundTask.cancel(true);
        //as soon as conditions are re-met the job will be retried again
        return true;
    }
}
