package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.libraries.LibraryProperties;
import com.intellij.openapi.roots.libraries.LibraryType;
import com.intellij.openapi.roots.libraries.NewLibraryConfiguration;
import com.intellij.openapi.roots.libraries.ui.LibraryEditorComponent;
import com.intellij.openapi.roots.libraries.ui.LibraryPropertiesEditor;
import com.intellij.openapi.vfs.VirtualFile;
import org.fregelang.plugin.idea.icon.FregeIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.NoSuchElementException;
import java.util.Optional;

public class FregeLibraryType extends LibraryType<FregeLibraryProperties> {

    public static final FregeLibraryType INSTANCE =
            Optional.ofNullable((FregeLibraryType) LibraryType.findByKind(FregeLibraryKind.INSTANCE))
                    .orElseThrow(() -> new NoSuchElementException("Scala library type not found"));

    FregeLibraryType() {
        super(FregeLibraryKind.INSTANCE);
    }

    @Nullable
    @Override
    public Icon getIcon() {
        // FIXME add an sdk icon
        return FregeIcons.DEFAULT;
    }

    @Nullable
    @Override
    public Icon getIcon(@Nullable LibraryProperties properties) {
        // FIXME add an sdk icon
        return FregeIcons.DEFAULT;
    }

    @Nullable
    @Override
    public String getCreateActionName() {
        return "Frege SDK";
    }

    @Nullable
    @Override
    public NewLibraryConfiguration createNewLibrary(@NotNull JComponent parentComponent,
                                                    @Nullable VirtualFile contextDirectory,
                                                    @NotNull Project project) {
        return new FregeLibraryDescription().createNewLibrary(parentComponent, contextDirectory);
    }

    @Nullable
    @Override
    public LibraryPropertiesEditor createPropertiesEditor(@NotNull LibraryEditorComponent<FregeLibraryProperties> editorComponent) {
        return new FregeLibraryPropertiesEditor(editorComponent);
    }
}