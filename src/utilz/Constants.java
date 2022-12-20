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
}
