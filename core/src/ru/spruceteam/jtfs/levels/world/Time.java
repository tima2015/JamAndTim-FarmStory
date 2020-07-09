package ru.spruceteam.jtfs.levels.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import ru.spruceteam.jtfs.Updatable;

public class Time implements Updatable {

    private static final String TAG = "Time";

    private static final float MAX_TIME = 60*24;

    public enum State{
        MORNING(0.8f),
        DAY(1),
        EVENING(0.8f),
        NIGHT(0.4f);

        private State(float alpha){
            this.alpha = alpha;
        }

        private float alpha;

        public float getAlpha() {
            return alpha;
        }
    }

    public enum Season{
        SPRING(15),
        SUMMER(30),
        AUTUMN(45),
        WINTER(60);

        private final int finalDayInYear;

        Season(int finalDayInYear){
            this.finalDayInYear = finalDayInYear;
        }
    }

    private State state = State.MORNING;
    private Season season = Season.SPRING;
    private int day = 1;
    private float time = 400;

    public Time() {
    }

    public Time(int day) {
        this.day = day;
        nextDay();
    }

    @Override
    public void update(float delta) {
        if (time < MAX_TIME){
            time += 10*delta;
            float h = getHour();
            this.state = h < 8 ? State.MORNING : (h < 16 ? State.DAY :
                            (h < 20 ? State.EVENING : State.NIGHT));
        }
    }

    public State getState() {
        return state;
    }

    public float getTime() {
        return time;
    }

    public float getHour(){
        return time / 60;
    }

    public void nextDay(){
        day++;
        if (day == Season.SPRING.finalDayInYear) season = Season.SUMMER;
        else if (day == Season.SUMMER.finalDayInYear) season = Season.AUTUMN;
        else if (day == Season.AUTUMN.finalDayInYear) season = Season.WINTER;
        else if (day == Season.WINTER.finalDayInYear) {
            season = Season.SPRING;
            day = 0;
            time = 0;
        }
    }

    public Season getSeason() {
        return season;
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "Time{" +
                "state=" + state +
                ", season=" + season +
                ", day=" + day +
                ", time=" + time +
                '}';
    }

    public void load(Preferences pref){
        day = pref.getInteger("day", day);
    }

    public void save(Preferences pref){
        Gdx.app.debug(TAG, "save() called");
        pref.putInteger("day", day);
        Gdx.app.debug(TAG, "save: completed!");
    }
}
