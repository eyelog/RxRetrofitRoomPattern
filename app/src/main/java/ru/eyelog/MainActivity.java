package ru.eyelog;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.eyelog.models.ValuteTO;

public class MainActivity extends MvpAppCompatActivity implements ViewState {

    ListView listView;
    Button button;

    @InjectPresenter
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);

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
}