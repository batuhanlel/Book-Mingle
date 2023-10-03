package com.lel.bookmingle.utility.context;

import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Setter
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ContextProvider {

    private Context context;

    public Context get() {
        return context;
    }

    public void clearContext() {
        this.context = null;
    }
}
