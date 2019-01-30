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
import ru.eyelog.utils_db.ValuteDB;
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

        Observable<ValCurs> getValCurs = apiService.getCurrency();
        getValCurs
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showProgressBar())
                .doAfterTerminate(() -> getViewState().hideProgressBar())
                .onErrorResumeNext(throwable -> {
                    getViewState().sadNetMessage();
                    valuteDBDao.getAll()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(valuteDBS -> parseDBData(valuteDBS));
                })
                .subscribe(valCurs -> parseNetData(valCurs));

    }

    // Net success function
    private void parseNetData(ValCurs valCurs){
        data = new ArrayList<>();
        for (int i = 0; i < valCurs.getValuteDTOList().size(); i++) {
            data.add(valCurs.getValuteDTOList().get(i).getListTO());
        }

        // Update DB.
        valuteDBDao.deleteAll();

        List<ValuteDB> valuteDBS = new ArrayList<>();
        ValuteDB valuteDB;
        for (int i = 0; i < data.size(); i++) {
            valuteDB = new ValuteDB(
                    data.get(i).getNumCode(),
                    data.get(i).getCharCode(),
                    data.get(i).getNominal(),
                    data.get(i).getName(),
                    data.get(i).getValue());
            valuteDBS.add(valuteDB);
        }
        valuteDBDao.insertAll(valuteDBS);

        getViewState().setCurses(data);
    }

    // If there is no internet take the data from DB
    private void parseDBData(List<ValuteDB> valuteDBS){
        data = new ArrayList<>();
        for (int i = 0; i < valuteDBS.size(); i++) {
            data.add(valuteDBS.get(i).getListTO());
        }

        if(data.size()==0){
            getViewState().sadAllMessage();
        }else {
            getViewState().setCurses(data);
        }
    }
}
