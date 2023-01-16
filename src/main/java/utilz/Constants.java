package utilz;

public class Constants {

    public static class Directions {
        public static final int UP = 0;
        public static final int LEFT = 1;
        public static final int DOWN = 2;
        public static final int RIGHT = 3;
    }

    public static class PlayerConstants {
        public static final int STANDING = 0;
        public static final int WALKING = 1;
        public static final int ATTACKING_01 = 4;
        public static final int ATTACKING_02 = 8;
        //number of animation frames
        public static final int WALKING_FRAMES = 8;
        public static final int ATTACKING_01_FRAMES = 8;
        public static final int ATTACKING_02_FRAMES = 6;
    }

    public static class Maps {
        public static final String TEST_MAP = "maps/testMap";
    }

    public static class NpcCsv {
        public static final int ID = 0;
        public static final int IS_ON_MAP = 1;
        public static final int POSITION_X = 2;
        public static final int POSITION_Y = 3;
        public static final int HITBOX_UP = 4;
        public static final int HITBOX_LEFT = 5;
        public static final int HITBOX_DOWN = 6;
        public static final int HITBOX_RIGHT = 7;
        public static final int DIRECTION = 8;
        public static final int CAN_MOVE = 9;
        public static final int NAME = 10;
        public static final int ANI_FRAME = 11;
        public static final int MAX_HP = 12;
        public static final int SPRITE_ATLAS = 13;
    }

    public static class Entities {
        public static final String PLAYER_ATLAS = "characters/player";
        public static final String NPC_01 = "characters/npc_01_chemist";
    }

    public static class GameStates {
        public static final int MENU = 1;
        public static final int PLAYING = 2;
    }
}
