package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.roots.libraries.PersistentLibraryKind;
import org.jetbrains.annotations.NotNull;

public class FregeLibraryKind extends PersistentLibraryKind<FregeLibraryProperties> {

    public static final FregeLibraryKind INSTANCE = new FregeLibraryKind();

    public FregeLibraryKind() {
        super("Frege");
    }

    @NotNull
    @Override
    public FregeLibraryProperties createDefaultProperties() {
        return new FregeLibraryProperties();
    }
}
