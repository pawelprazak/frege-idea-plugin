package org.fregelang.plugin.idea.framework;

import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessListener;
import com.intellij.openapi.util.Key;

import java.io.IOException;
import java.util.function.Consumer;

import static java.lang.String.format;

public class Downloader {

    public static final String MAVEN_DEPENDENCY_PLUGIN_VERSION = "2.8";

    public static void downloadFrege(String version, Consumer<String> listener) throws DownloadException {
        StringBuffer buffer = new StringBuffer();

        Process process;
        try {
            process = Runtime.getRuntime().exec(mvnCommandFor("org.frege-lang", "frege", version, "jar", ""));
        } catch (IOException e) {
            throw new DownloadException(e);
        }

        ProcessListener listenerAdapter = new ProcessAdapter() {
            @Override
            public void onTextAvailable(ProcessEvent event, Key outputType) {
                String text = event.getText();
                listener.accept(text);
                buffer.append(text);
            }
        };

        OSProcessHandler handler = new OSProcessHandler(process, null, null);
        handler.addProcessListener(listenerAdapter);
        handler.startNotify();
        handler.waitFor();

        if (process.exitValue() != 0) {
            throw new DownloadException(buffer.toString());
        }
    }

    private static String mvnCommandFor(String groupId, String artifactId, String version, String packaging, String classifier) {
        return format("mvn org.apache.maven.plugins:maven-dependency-plugin:%s:get -Dartifact=%s:%s:%s:%s:%s",
                MAVEN_DEPENDENCY_PLUGIN_VERSION, groupId, artifactId, version, packaging, classifier
        );
    }

}