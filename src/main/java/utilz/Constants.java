package utilz;

public class Constants {

    public static class Directions {
        public static final int UP = 0;
        public static final int LEFT = 1;
        public static final int DOWN = 2;
        public static final int RIGHT = 3;
    }

    //for status variables
    public static class EntityStatusConstants {
        public static final int NORMAL = 0;
        public static final int DAMAGED = 1;
        public static final int PERISHING = 2;
        public static final int DEAD = 3;
        public static final int GAME_OVER = 4;

    }

    //for actions and animations variables
    public static class PlayerConstants {
        //actions
        public static final int STANDING = 0;
        public static final int WALKING = 1;
        public static final int ATTACKING_01 = 4;
        public static final int ATTACKING_02 = 8;
        public static final int DYING = 12;
        //number of animation frames
        public static final int WALKING_FRAMES = 8;
        public static final int ATTACKING_01_FRAMES = 8;
        public static final int ATTACKING_02_FRAMES = 6;
        public static final int DYING_FRAMES = 6;
    }

    //for actions and animations variables
    public static class EnemyConstants {
        //actions
        public static final int STANDING = 0;
        public static final int WALKING = 1;
        public static final int ATTACKING_01 = 4;
        public static final int DYING = 8;
        //number of animation frames
        public static final int WALKING_FRAMES = 8;
        public static final int ATTACKING_01_FRAMES = 6;
        public static final int DYING_FRAMES = 6;
    }

    public static class Maps {
        public static final String LEVEL_ONE = "maps/stone_cave";
    }

    public static class NpcAndEnemiesCsv {
        public static final int ID = 0;
        public static final int IS_ON_MAP = 1;
        public static final int POSITION_X = 2;
        public static final int POSITION_Y = 3;
        public static final int DIRECTION = 4;
        public static final int CAN_MOVE = 5;
        public static final int NAME = 6;
        public static final int ANI_FRAME = 7;
        public static final int MAX_HP = 8;
        public static final int SPRITE_ATLAS = 9;
    }

    public static class Entities {
        public static final String PLAYER_ATLAS_01 = "characters/player";
    }

    public static class GameStates {
        public static final int MENU = 1;
        public static final int PLAYING = 2;
        public static final int GAME_OVER = 3;
    }
}
