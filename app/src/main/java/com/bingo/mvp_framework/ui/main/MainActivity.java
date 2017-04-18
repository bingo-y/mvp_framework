package com.bingo.mvp_framework.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bingo.mvp_framework.io.db.DBManager;
import com.bingo.mvp_framework.io.db.entity.Note;
import com.bingo.mvp_framework.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.b_add)
    Button bAdd;
    @BindView(R.id.b_delete)
    Button bDelete;
    @BindView(R.id.tv_data)
    TextView tvData;

    List<Note> notes;
    List<Long> ids = new ArrayList<>();
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        notes = DBManager.getInstance(getApplicationContext()).getDaoSession().getNoteDao().loadAll();
        tvData.setText(getNoteText());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.b_add, R.id.b_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_add:
                Note note = new Note(String.format("note%d", new Random(100).nextInt()), new Date(System.currentTimeMillis()));
                DBManager.getInstance(getApplicationContext()).getDaoSession().getNoteDao().insert(note);
                notes.add(note);

                tvData.setText(getNoteText());
                break;
            case R.id.b_delete:
                if (notes.size() > 0) {
                    DBManager.getInstance(getApplicationContext()).getDaoSession().getNoteDao().delete(notes.get(notes.size() - 1));
                    tvData.setText(getNoteText());
                }
                break;
        }
    }

    public String getNoteText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Note note1 : notes) {
            stringBuilder.append(note1.getId()).append(" : ")
                    .append(note1.getText()).append(" : ")
                    .append(note1.getDate()).append(" : ")
//                    .append(note1.getAuth())
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
