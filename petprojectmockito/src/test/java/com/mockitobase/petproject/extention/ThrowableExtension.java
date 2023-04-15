package com.mockitobase.petproject.extention;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.io.IOCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.io.IOException;

public class ThrowableExtension implements TestExecutionExceptionHandler {


    @Override
    public void handleTestExecutionException(ExtensionContext context,
                                             Throwable throwable) throws Throwable {
        if (throwable instanceof IOException) {
            throw throwable; // test failed on "IOException"
        }

    }
}
