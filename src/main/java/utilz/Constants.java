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
        public static final String SCHOOL_OUTSIDE = "maps/school_outside";
        public static final String TEST_MAP = "maps/school_outside";
    }

    public static class NpcCsv {
        public static final int ID = 0;
        public static final int IS_ON_MAP = 1;
        public static final int POSITION_X = 2;
        public static final int POSITION_Y = 3;
        public static final int HITBOX_LEFT = 4;
        public static final int HITBOX_DOWN = 5;
        public static final int HITBOX_UP = 6;
        public static final int HITBOX_RIGHT = 7;
        public static final int DIRECTION = 8;
        public static final int CAN_MOVE = 9;
        public static final int NAME = 10;
        public static final int SPRITE_ATLAS = 11;
    }

    public static class Entities {
        public static final String PLAYER_ATLAS = "characters/player";
        public static final String NPC_01 = "characters/npc_tony";
        public static final String NPC_02 = "characters/npc_klay";
        public static final String NPC_03 = "characters/npc_john";
        public static final String NPC_04 = "characters/npc_samantha";
        public static final String NPC_05 = "characters/npc_jay";
    }

    public static class GameStates {
        public static final int MENU = 1;
        public static final int PLAYING = 2;
    }
}
