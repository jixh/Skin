/*
 * Copyright (c) 2017 jktaihe
 */

package com.jktaihe.skinlibrary.bean;


/**
 * Created by jktaihe on 17-1-12.
 */
public interface ISkinLoader {

    void attach(ISkinUpdate observer);

    void detach(ISkinUpdate observer);

    void notifySkinUpdate();
}
