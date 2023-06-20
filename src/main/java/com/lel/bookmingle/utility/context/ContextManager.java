package com.lel.bookmingle.utility.context;


public class ContextManager {

    private static final ThreadLocal<Context> CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * @param ctx context
     */
    public static void set(Context ctx) {
        CONTEXT_THREAD_LOCAL.set(ctx);
    }

    /**
     * @return context object
     */
    public static Context get() {
        return CONTEXT_THREAD_LOCAL.get();
    }

    /**
     * no return
     */
    public static void clear() {
        CONTEXT_THREAD_LOCAL.remove();
    }

}
