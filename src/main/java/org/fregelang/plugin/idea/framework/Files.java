package org.fregelang.plugin.idea.framework;

import com.google.common.collect.ImmutableList;
import com.intellij.openapi.vfs.VfsUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Files {

    public static Stream<File> allFiles(File file) {
        return allFiles(ImmutableList.of(file));
    }

    public static Stream<File> allFiles(List<File> files) {
        if (files == null) {
            return Stream.empty();
        }
        return Stream.concat(
                files.stream().filter(File::isFile),
                files.stream().filter(File::isDirectory).flatMap(file -> allFiles(innerAllFiles(file)))
        );
    }

//    def parent: Option[File] = Option(delegate.getParentFile)

//    def children: Seq[File] = Option(delegate.listFiles).map(_.toSeq).getOrElse(Seq.empty)

//    def directories: Seq[File] = children.filter(_.isDirectory)

//    def files: Seq[File] = children.filter(_.isFile)

    private static List<File> innerAllFiles(File file) {
        File[] files = file.listFiles();
        return files == null ? new ArrayList<>() : Arrays.asList(files);
    }

    public static Function<File, String> toLibraryRootURL = VfsUtil::getUrlForLibraryRoot;
}