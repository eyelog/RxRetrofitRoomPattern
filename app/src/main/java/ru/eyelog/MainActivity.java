package ru.eyelog;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.eyelog.models.ValuteTO;

public class MainActivity extends MvpAppCompatActivity implements ViewState {

    ListView listView;
    Button button;

    ProgressBar progressBar;

    @InjectPresenter
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        button.setOnClickListener(v -> {
            presenter.getCurrencyList();
        });
    }

    @Override
    public void setCurses(List<ValuteTO> valuteTOS) {
        String valute[] = new String[valuteTOS.size()];
        for (int i = 0; i < valuteTOS.size(); i++) {
            valute[i] = (valuteTOS.get(i).toString());
        }
        ArrayAdapter<String> adapter  =  new  ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, valute);
        listView.setAdapter(adapter);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void sadNetMessage() {
        Toast.makeText(this, "Сервер не доступен", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sadAllMessage() {
        Toast.makeText(this, "И в кеше тоже пустота. Печалька =(", Toast.LENGTH_SHORT).show();
    }
}