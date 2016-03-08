package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.roots.libraries.LibraryProperties;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.intellij.openapi.util.io.FileUtil.toCanonicalPath;
import static com.intellij.openapi.vfs.VfsUtilCore.pathToUrl;
import static com.intellij.openapi.vfs.VfsUtilCore.urlToPath;
import static java.util.stream.Collectors.toList;

public class FregeLibraryProperties extends LibraryProperties<FregeLibraryPropertiesState> {

    private FregeLanguageLevel languageLevel;
    private List<File> compilerClasspath;

    public FregeLibraryProperties() {
        loadState(new FregeLibraryPropertiesState());
    }

    @Override
    public void loadState(FregeLibraryPropertiesState state) {
        this.languageLevel = FregeLanguageLevel.from(state.languageLevel);
        compilerClasspath = Arrays.asList(state.compilerClasspath).stream()
                .map(path -> new File(urlToPath(path)))
                .collect(toList());
    }

    @Nullable
    @Override
    public FregeLibraryPropertiesState getState() {
        FregeLibraryPropertiesState state = new FregeLibraryPropertiesState();
        state.languageLevel = languageLevel.proxy();
        state.compilerClasspath = compilerClasspath.stream()
                .map(file -> pathToUrl(toCanonicalPath(file.getAbsolutePath()))).toArray(String[]::new);
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FregeLibraryProperties that = (FregeLibraryProperties) o;
        return Objects.equals(languageLevel, that.languageLevel) &&
                Objects.equals(compilerClasspath, that.compilerClasspath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(languageLevel, compilerClasspath);
    }
}
