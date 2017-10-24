package cc.isotopestudio.credit;
/*
 * Created by david on 10/23/2017.
 * Copyright ISOTOPE Studio
 */

import java.util.HashMap;
import java.util.Map;

import static cc.isotopestudio.credit.Credit.config;

public class ConfigUpdateTask {

    public static final Map<String, Integer> defaultCredit = new HashMap<>();

    public static void run() {
        defaultCredit.clear();
        for (String item : config.getConfigurationSection("default").getKeys(false)) {
            defaultCredit.put(item.contains(":") ? item : item + ":0", config.getInt("default." + item));
        }
    }

}
