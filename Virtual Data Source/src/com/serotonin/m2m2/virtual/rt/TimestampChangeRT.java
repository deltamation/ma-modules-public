/*
    Copyright (C) 2013 Deltamation Software All rights reserved.
    @author Terry Packer
 */
package com.serotonin.m2m2.virtual.rt;

import java.util.Date;

import com.serotonin.m2m2.rt.dataImage.types.DataValue;
import com.serotonin.m2m2.rt.dataImage.types.NumericValue;

/**
 * Copyright (C) 2013 Deltamation Software. All Rights Reserved.
 * @author Terry Packer
 *
 */
public class TimestampChangeRT extends ChangeTypeRT{

    @Override
    public DataValue change(DataValue currentValue){
        //Will convert a long to a double... (Multistate only works for Integers)
        return new NumericValue(new Date().getTime()/1000);
    }
}
