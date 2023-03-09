package it.trashband.usefulanvil;

import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class UsefulAnvilConfig extends ConfigWrapper<it.trashband.usefulanvil.UsefulAnvilConfigModel> {

    private final Option<java.lang.Boolean> displayEfficacy = this.optionForKey(new Option.Key("displayEfficacy"));
    private final Option<it.trashband.usefulanvil.UsefulAnvilConfigModel.MendingDecayStyle> mendingDecayStyle = this.optionForKey(new Option.Key("mendingDecayStyle"));
    private final Option<java.lang.Integer> repairsBeforeBreak = this.optionForKey(new Option.Key("repairsBeforeBreak"));

    private UsefulAnvilConfig() {
        super(it.trashband.usefulanvil.UsefulAnvilConfigModel.class);
    }

    public static UsefulAnvilConfig createAndLoad() {
        var wrapper = new UsefulAnvilConfig();
        wrapper.load();
        return wrapper;
    }

    public boolean displayEfficacy() {
        return displayEfficacy.value();
    }

    public void displayEfficacy(boolean value) {
        displayEfficacy.set(value);
    }

    public it.trashband.usefulanvil.UsefulAnvilConfigModel.MendingDecayStyle mendingDecayStyle() {
        return mendingDecayStyle.value();
    }

    public void mendingDecayStyle(it.trashband.usefulanvil.UsefulAnvilConfigModel.MendingDecayStyle value) {
        mendingDecayStyle.set(value);
    }

    public int repairsBeforeBreak() {
        return repairsBeforeBreak.value();
    }

    public void repairsBeforeBreak(int value) {
        repairsBeforeBreak.set(value);
    }




}

