package com.scramble.pic.krishan.core;

/**
 * Created by krishan on 28/05/2017.
 */

public interface BaseScreen<T extends BasePresenter> {
    void setPresenter(T presenter);
}
