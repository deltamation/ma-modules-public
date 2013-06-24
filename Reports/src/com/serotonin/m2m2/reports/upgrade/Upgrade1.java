package com.serotonin.m2m2.reports.upgrade;

import com.serotonin.m2m2.db.upgrade.DBUpgrade;

public class Upgrade1 extends DBUpgrade {
    @Override
    protected void upgrade() throws Exception {
        runScript(new String[] { //
        "alter table reportInstancePoints add column individualChart char(1);", //
                "update reportInstancePoints set individualChart='Y';", //
        });
    }

    @Override
    protected String getNewSchemaVersion() {
        return "2";
    }
}
