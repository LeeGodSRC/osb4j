package org.osb4j.storyboarding.beatmap.section;

public enum PropertyType {

    STRING {
        @Override
        public Object convert(String string) {
            return string;
        }
    },
    INT {
        @Override
        public Object convert(String string) {
            return Integer.parseInt(string);
        }
    },
    DOUBLE {
        @Override
        public Object convert(String string) {
            return Double.parseDouble(string);
        }
    },
    SAMPLE_SET {
        @Override
        public Object convert(String string) {
            return null;
        }
    },
    MODE {
        @Override
        public Object convert(String string) {
            return null;
        }
    };

    public abstract Object convert(String string);

}
