package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.roots.impl.libraries.LibraryEx;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryProperties;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class FregeSdk {

    private static Pattern LIBRARY_VERSION = Pattern.compile("(?<=:|-)\\d+\\.\\d+\\.\\d+[^:\\s]*");

    private final Library library;

    public FregeSdk(Library library) {
        this.library = library;
    }

    private FregeLibraryProperties properties() {
        return fregeProperties(library).orElseThrow(() -> new IllegalStateException("Library is not Frege SDK: " + library.getName()));
    }

    private Optional<FregeLibraryProperties> fregeProperties(Library library) {
        if (!(library instanceof LibraryEx)) {
            return Optional.empty();
        }
        LibraryEx libraryEx = (LibraryEx) library;
        LibraryProperties properties = libraryEx.getProperties();
        if (properties instanceof FregeLibraryProperties) {
            return Optional.of((FregeLibraryProperties) properties);
        } else {
            return Optional.empty();
        }
    }

    public Optional<String> compilerVersion() {
        return LIBRARY_VERSION.splitAsStream(library.getName()).findFirst();
    }

    public List<File> compilerClasspath() {
        return properties().getCompilerClasspath();
    }

    public FregeLanguageLevel languageLevel() {
        return properties().getLanguageLevel();
    }

    public static Library toLibrary(FregeSdk v) {
        return v.library;
    }

    public static String documentationUrlFor(Optional<Version> version) {
        return "http://www.frege-lang.org/doc/"; // TODO version.map(Version::getNumber).orElse("current")
    }
}
