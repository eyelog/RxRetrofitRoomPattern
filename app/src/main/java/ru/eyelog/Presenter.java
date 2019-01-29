package ru.eyelog;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.eyelog.models.ValuteTO;
import ru.eyelog.utils_db.AppDatabase;
import ru.eyelog.utils_db.AppRepository;
import ru.eyelog.utils_db.ValuteDBDao;
import ru.eyelog.utils_net.ApiService;
import ru.eyelog.utils_net.NetRepository;
import ru.eyelog.utils_net.ValCurs;

@InjectViewState
public class Presenter extends MvpPresenter<ViewState> {

    ApiService apiService;

    AppDatabase appDatabase;
    ValuteDBDao valuteDBDao;

    List<ValuteTO> data;

    public Presenter() {

        apiService = NetRepository.getApiService();

        appDatabase = AppRepository.getInstance().getDatabase();
        valuteDBDao = appDatabase.valuteDBDao();

    }

    @SuppressLint("CheckResult")
    public void getCurrencyList(){

        // Using RxAndroid
        Observable<ValCurs> getValCurs = apiService.getCurrency();
        getValCurs
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(valCurs -> {
                    data = new ArrayList<>();
                    for (int i = 0; i < valCurs.getValuteDTOList().size(); i++) {
                        data.add(valCurs.getValuteDTOList().get(i).getListTO());
                    }
                    getViewState().setCurses(data);
                });

    }
}
