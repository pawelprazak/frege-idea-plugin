package org.fregelang.plugin.idea.framework;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Files {

    public static Stream<File> allFiles(List<File> files) {
        if (files == null) {
            return Stream.empty();
        }
        return Stream.concat(
                files.stream().filter(File::isFile),
                files.stream().filter(File::isDirectory).flatMap(file -> allFiles(allFiles(file)))
        );
    }

    public static List<File> allFiles(File file) {
        File[] files = file.listFiles();
        return files == null ? new ArrayList<>() : Arrays.asList(files);
    }
}
