package com.kanishk.code.bloop.presenter.activity;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.AsyncTask;
import android.util.Log;

import com.kanishk.code.bloop.data.DBHelper;
import com.kanishk.code.bloop.model.NotesTable;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kanishk on 1/8/17.
 */

public class MainActivityPresenter extends BaseObservable {

    private DBHelper dbHelper;
    private Context context;
    private MainActivityPresenterListener mListener;

    public MainActivityPresenter(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context.getApplicationContext());
    }

    public void getNotesFromDb() {
        new AsyncFetchNotes().execute();
    }

    class AsyncFetchNotes extends AsyncTask<Void, Void, Boolean> {

        private List<NotesTable> notesTableList;

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                notesTableList = dbHelper.getAll(NotesTable.class);
                for (int i = 0; i < notesTableList.size(); i++) {
                    Log.e("NotesTable" , String.valueOf(notesTableList.get(i).getIndex()));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.onFetchNotes(notesTableList);
        }
    }

    public void setMainActivityPresenterListener(MainActivityPresenterListener listener) {
        this.mListener = listener;
    }

    public interface MainActivityPresenterListener {
        void onFetchNotes(List<NotesTable> list);
    }
}
