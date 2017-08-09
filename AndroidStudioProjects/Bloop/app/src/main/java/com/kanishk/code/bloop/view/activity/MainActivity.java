package com.kanishk.code.bloop.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.ActivityMainBinding;
import com.kanishk.code.bloop.model.NotesTable;
import com.kanishk.code.bloop.presenter.activity.MainActivityPresenter;
import com.kanishk.code.bloop.view.fragment.SelectTagFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ActivityMainBinding binding;
    private MainActivityPresenter presenter;
    private ArrayList<NotesTable>
            notesList = new ArrayList<>(),
            photoList = new ArrayList<>(),
            audioList = new ArrayList<>(),
            checkList = new ArrayList<>(),
            quoteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        initPresenter();
        initViews();
    }

    private void initPresenter() {
        presenter = new MainActivityPresenter(this);
        presenter.getNotesFromDb();
        presenter.setMainActivityPresenterListener(new MainActivityPresenter.MainActivityPresenterListener() {
            @Override
            public void onFetchNotes(List<NotesTable> list) {
                sortList(list);
                setViewPager();
            }

            private void sortList(List<NotesTable> list) {
                notesList.addAll(list);
                /*for (NotesTable notesTable : list) {
                    if (notesTable.getAudioNote() != null) {
                        audioList.add(notesTable);
                    }
                    if (notesTable.getPhotoNote() != null) {
                        photoList.add(notesTable);
                    }
                    if (notesTable.getCheckListnote() != null) {
                        checkList.add(notesTable);
                    }
                    if (notesTable.getQuoteNote() != null) {
                        quoteList.add(notesTable);
                    }
                }*/
            }
        });
    }

    private void initViews() {
        binding.rippleTagTitle.setOnClickListener(v -> {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            SelectTagFragment newFragment = SelectTagFragment.newInstance();
            newFragment.show(ft, "");
        });
    }

    private void setViewPager() {
        tabLayout = binding.tabs;
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = binding.container;
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabIconState(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabIconState(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                changeTabIconState(tab, true);
            }

            private void changeTabIconState(TabLayout.Tab tab, boolean isSelected) {
                View view = tab.getCustomView();
                assert view != null;
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                if (isSelected) {
                    switch (tab.getPosition()) {
                        case 0:
                            imageView.setBackgroundResource(R.drawable.tab_icon_mix_note_selected);
                            break;
                        case 1:
                            imageView.setBackgroundResource(R.drawable.tab_icon_photo_note_selected);
                            break;
                        case 2:
                            imageView.setBackgroundResource(R.drawable.tab_icon_audio_note_selected);
                            break;
                        case 3:
                            imageView.setBackgroundResource(R.drawable.tab_icon_checklist_note_selected);
                            break;
                        case 4:
                            imageView.setBackgroundResource(R.drawable.tab_icon_quotes_note_selected);
                            break;
                    }
                } else {
                    switch (tab.getPosition()) {
                        case 0:
                            imageView.setBackgroundResource(R.drawable.tab_icon_mix_note_normal);
                            break;
                        case 1:
                            imageView.setBackgroundResource(R.drawable.tab_icon_photo_note_normal);
                            break;
                        case 2:
                            imageView.setBackgroundResource(R.drawable.tab_icon_audio_note_normal);
                            break;
                        case 3:
                            imageView.setBackgroundResource(R.drawable.tab_icon_checklist_note_normal);
                            break;
                        case 4:
                            imageView.setBackgroundResource(R.drawable.tab_icon_quotes_note_normal);
                            break;
                    }
                }

            }
        });
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(mSectionsPagerAdapter.getTabView(i));
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        int tabIcons[] = new int[]{
                R.drawable.tab_icon_mix_note_selected,
                R.drawable.tab_icon_photo_note_normal,
                R.drawable.tab_icon_audio_note_normal,
                R.drawable.tab_icon_checklist_note_normal,
                R.drawable.tab_icon_quotes_note_normal
        };

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        View getTabView(int position) {
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            ImageView imageView = (ImageView) tab.findViewById(R.id.image);
            imageView.setBackgroundResource(tabIcons[position]);
            return tab;
        }
    }
}
