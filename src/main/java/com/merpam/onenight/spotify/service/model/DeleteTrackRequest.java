package com.merpam.onenight.spotify.service.model;

    public class DeleteTrackRequest {
        private int[] positions;

        private String uri;

        public int[] getPositions() {
            return positions;
        }

        public void setPositions(int[] positions) {
            this.positions = positions;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
}
