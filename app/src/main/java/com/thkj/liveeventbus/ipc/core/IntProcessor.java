package com.thkj.liveeventbus.ipc.core;

import android.os.Bundle;

import com.thkj.liveeventbus.ipc.consts.IpcConst;

/**
 * Created by liaohailiang on 2019/5/30.
 */
public class IntProcessor implements Processor {

    @Override
    public boolean writeToBundle(Bundle bundle, Object value) {
        if (!(value instanceof Integer)) {
            return false;
        }
        bundle.putInt(IpcConst.KEY_VALUE, (int) value);
        return true;
    }

    @Override
    public Object createFromBundle(Bundle bundle) {
        return bundle.getInt(IpcConst.KEY_VALUE);
    }
}
