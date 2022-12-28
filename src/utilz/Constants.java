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
        public static final String SCHOOL_OUTSIDE = "res/maps/school_outside.png";
        public static final String SCHOOL_OUTSIDE_INFO = "res/maps/school_outside.csv";
        public static final int SCHOOL_OUTSIDE_HEIGHT = 1584;
        public static final int SCHOOL_OUTSIDE_WIDTH = 1104;
    }

    public static class Entities {
        public static final String PLAYER_ATLAS = "res/characters/sample_character_02.png";
    }
}
