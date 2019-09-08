package com.merpam.onenight.constants;

public final class Constants {

    public static int EXPIRATION_INTERVAL = 7 * 24 * 60 * 60;

    private Constants() {
        //empty to avoid instantiating this constant class
    }


    public static final class HystrixGroups {
        public static final String SPOTIFY = "spotify";

        private HystrixGroups() {
            //empty to avoid instantiating this constant class
        }
    }
}
