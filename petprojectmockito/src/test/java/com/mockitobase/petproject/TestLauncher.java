package com.mockitobase.petproject;


import com.mockitobase.petproject.service.UserServiceTest;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

public class TestLauncher {
    public static void main(String[] args) {
        var launcher = LauncherFactory.create();
        var summaryGeneratingListener = new SummaryGeneratingListener();

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
//                .selectors(DiscoverySelectors.selectClass(UserServiceTest.class))
                .selectors(DiscoverySelectors.selectPackage("com.mockitobase.petproject"))
                .filters(
                        TagFilter.includeTags("login")//запускаем тесты с тегом "login"
                )
                .build();
        launcher.execute(request, summaryGeneratingListener);
        try (var writer = new PrintWriter(System.out)) {
            summaryGeneratingListener.getSummary().printTo(writer);
        }
    }
}
