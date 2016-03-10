package org.fregelang.plugin.idea.framework;

import com.intellij.util.xmlb.annotations.AbstractCollection;
import com.intellij.util.xmlb.annotations.Tag;

import java.util.Arrays;

public class FregeLibraryPropertiesState {

    // We have to rely on the Java's enumeration for serialization
    public FregeLanguageLevelProxy languageLevel = FregeLanguageLevel.DEFAULT.proxy();

    @Tag("compiler-classpath")
    @AbstractCollection(surroundWithTag = false, elementTag = "root", elementValueAttribute = "url")
    public String[] compilerClasspath = new String[]{};

    @Override
    public boolean equals(Object obj) {
        FregeLibraryPropertiesState that = (FregeLibraryPropertiesState) obj;
        return languageLevel == that.languageLevel && Arrays.equals(compilerClasspath, that.compilerClasspath);
    }
}
