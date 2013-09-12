/*
    Copyright (C) 2013 Deltamation Software All rights reserved.
    @author Terry Packer
 */
package com.serotonin.m2m2.virtual.rt;

import com.serotonin.m2m2.rt.dataImage.types.DataValue;
import com.serotonin.m2m2.rt.dataImage.types.MultistateValue;

/**
 * Copyright (C) 2013 Deltamation Software. All Rights Reserved.
 * @author Terry Packer
 *
 */
public class TimestampMultistateChangeRT extends ChangeTypeRT {
    @Override
    public DataValue change(DataValue currentValue) {
        return new MultistateValue((int) (System.currentTimeMillis() / 1000));
    }
}
