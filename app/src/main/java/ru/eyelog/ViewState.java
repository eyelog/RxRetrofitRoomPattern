package ru.eyelog;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.eyelog.models.ValuteTO;

public interface ViewState extends MvpView {
    void setCurses(List<ValuteTO> valuteTOS);
}
