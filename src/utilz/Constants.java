package utilz;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int DOWN = 1;
        public static final int UP = 2;
        public static final int RIGHT = 3;
    }

    public static class PlayerConstants {
        public static final int STANDING = 1;
        public static final int WALKING = 2;
        public static int GetSpriteAmount(int player_action) {
            return 3;
        }
    }

    public static class Maps {
        public static final String SCHOOL_OUTSIDE = "res/maps/school_outside";
    }

    public static class Entities {
        public static final String PLAYER_ATLAS = "res/characters/sample_character_02";
    }

    public static class GameStates {
        public static final int MENU = 1;
        public static final int PLAYING = 2;
    }
}
