package ru.spruceteam.jtfs.objects;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import ru.spruceteam.jtfs.levels.world.Time;

public enum Plants {
    TURNIP,
    ROSE,
    CUCUMBER,
    TULIP,
    TOMATO,
    MELON,
    EGGPLANT,
    LEMON,
    PINEAPPLE,
    RICE,
    WHEAT,
    GRAPES,
    STRAWBERRY,
    CASSAVA,
    POTATO,
    COFFEE,
    ORANGE,
    AVOCADO,
    CORN,
    SUNFLOWER;

    public static final String PLANT_ATLAS = "FarmingCrops16x16/plant.atlas";

    static{
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("properties/plant.properties"));
            Plants[] values = Plants.values();
            for (int i = 0; i < values.length; i++) {
                values[i].dayToGrow = Integer
                        .parseInt(properties.getProperty(values[i].name() + ".dayToGrow"));
                values[i].canBePicked = Integer
                        .parseInt(properties.getProperty(values[i].name() + ".canBePicked"));
                values[i].season =
                        Time.Season.valueOf(properties.getProperty(values[i].name() + ".season"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int dayToGrow;
    private int canBePicked;
    private Time.Season season;

    public int getCanBePicked() {
        return canBePicked;
    }

    public Time.Season getSeason() {
        return season;
    }

    public int getDayToGrow() {
        return dayToGrow;
    }
}
