package it.trashband.usefulanvil;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.Sync;

@Modmenu(modId = "usefulanvil")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@Config(name = "usefulanvil-config", wrapperName = "UsefulAnvilConfig")
public class UsefulAnvilConfigModel {
    public boolean displayEfficacy = true;
    public MendingDecayStyle mendingDecayStyle = MendingDecayStyle.LINEAR;
    public int repairsBeforeBreak = 3;

    public enum MendingDecayStyle {
        LINEAR, STOP_AT_END
    }
}
