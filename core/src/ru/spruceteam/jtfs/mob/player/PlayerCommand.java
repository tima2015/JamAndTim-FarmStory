package ru.spruceteam.jtfs.mob.player;

public interface PlayerCommand {

    void execute(Object... params) throws WrongParamsException;

    class WrongParamsException extends Exception {
        public WrongParamsException() {
        }

        public WrongParamsException(String s) {
            super(s);
        }
    }
}
