package com.kanishk.code.bloop.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.ActivityParentBinding;
import com.kanishk.code.bloop.model.NotesTable;
import com.kanishk.code.bloop.presenter.activity.MainActivityPresenter;
import com.kanishk.code.bloop.view.fragment.NotesFragment;
import com.kanishk.code.bloop.view.fragment.SearchFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

public class ParentActivity extends AppCompatActivity {

    private ActivityParentBinding binding;
    private MainActivityPresenter presenter;
    private ArrayList<NotesTable> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parent);
        initToolbarActions();

        //startInitialFragment();

        BottomBar navigation = binding.navigation;
        navigation.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.tab_all_notes:
                        final NotesFragment notesFragment = NotesFragment.newInstance(1);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, notesFragment)
                                .commit();
                        break;
                    case R.id.tab_photo_notes:
                        final NotesFragment notesFragmentPhoto = NotesFragment.newInstance(2);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, notesFragmentPhoto)
                                .commit();
                        break;
                    case R.id.tab_audio_notes:
                        final NotesFragment notesFragmentAudio = NotesFragment.newInstance(3);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, notesFragmentAudio)
                                .commit();
                        break;
                    case R.id.tab_checklist_notes:
                        final NotesFragment notesFragmentChecklist = NotesFragment.newInstance(4);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, notesFragmentChecklist)
                                .commit();
                        break;
                    case R.id.tab_quote_notes:
                        final NotesFragment notesFragmentQuotes = NotesFragment.newInstance(5);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_container, notesFragmentQuotes)
                                .commit();
                        break;
                }
            }
        });

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }*/
    }

    private void initToolbarActions() {
        binding.actionSearch.setOnClickListener(v -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            SearchFragment newFragment = SearchFragment.newInstance();
            newFragment.show(ft, "");
        });
    }

    private void startInitialFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, NotesFragment.newInstance(1))
                .commit();
    }

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.menu_all_notes:
                final NotesFragment notesFragment = NotesFragment.newInstance(notesList, 1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, notesFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_photo_notes:
                final NotesFragment notesFragmentPhoto = NotesFragment.newInstance(notesList, 2);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, notesFragmentPhoto)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_audio_notes:
                final NotesFragment notesFragmentAudio = NotesFragment.newInstance(notesList, 3);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, notesFragmentAudio)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_checklist_notes:
                final NotesFragment notesFragmentChecklist = NotesFragment.newInstance(notesList, 4);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, notesFragmentChecklist)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_quote_notes:
                final NotesFragment notesFragmentQuotes = NotesFragment.newInstance(notesList, 5);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, notesFragmentQuotes)
                        .addToBackStack(null)
                        .commit();
                break;
        }
        return false;
    };*/

}
