package gin.model;

public enum Result {
    UNKNOWN{
        @Override
        public Result add(Result rhs) {
            if(rhs == null) {
                return UNKNOWN;
            }

            switch(rhs){
                case FAILED: return FAILED;
            }
            return UNKNOWN;
        }
    },
    PASSED {
        @Override
        public Result add(Result rhs) {
            if(rhs == null) {
                return UNKNOWN;
            }

            switch(rhs){
                case PASSED: return PASSED;
                case FAILED: return FAILED;
            }
            return UNKNOWN;
        }
    },
    FAILED {
        @Override
        public Result add(Result rhs) {
            return FAILED;
        }
    },
    IGNORED {
        @Override
        public Result add(Result rhs) {
            if(rhs == null) {
                return UNKNOWN;
            }

            switch(rhs){
                case FAILED: return FAILED;
                case IGNORED: return IGNORED;
            }
            return UNKNOWN;
        }
    };

    public abstract Result add(Result rhs);

        /**
         *   PASSED + FAILED = FAILED
         *   PASSED + IGNORED = ?
         *   PASSED + UNKNONW = ?
         *   FAILED + * = FAILED
         *   IGNORED + FAILED = FAILED
         *   IGNORED + PASSED = IGNORED
         *   IGNORED + UNKNOWN = UNKNONW
         *   UNKNOWN + FAILED = FAILED
         *   UNKNOWN + PASSED = UNKNOWn
         *   UNKNOWN + IGNORED = UNKNOWN
         */
}
