package com.kanishk.code.bloop.presenter.fragment;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kanishk.code.bloop.data.DBHelper;
import com.kanishk.code.bloop.databinding.FragmentNotesBinding;
import com.kanishk.code.bloop.model.NotesTable;
import com.kanishk.code.bloop.view.adapter.TagRCViewAdapter;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kanishk on 19/7/17.
 */

public class RecentFragmentPresenter extends BaseObservable {

    private FragmentNotesBinding binding;
    private RecyclerView recyclerView;
    private GridLayoutManager grid;
    private Context context;
    private DBHelper dbHelper;
    private TagRCViewAdapter adapter;
    private PresenterListener mListener;
    private List<NotesTable> notesTableList;

    public RecentFragmentPresenter(FragmentNotesBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
        dbHelper = new DBHelper(context.getApplicationContext());
    }

    public void getNotesFromDb() {
        new AsyncFetchNotes().execute();
    }

    public void deleteSession(int indexId) {
        try {
            dbHelper.deleteById(NotesTable.class, indexId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class AsyncFetchNotes extends AsyncTask<Void, Void, Boolean> {

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
            mListener.onGetList(notesTableList);
        }
    }

    public void setPresenterListener(PresenterListener listener) {
        this.mListener = listener;
    }

    public interface PresenterListener {
        void onGetList(List<NotesTable> list);
    }
}
