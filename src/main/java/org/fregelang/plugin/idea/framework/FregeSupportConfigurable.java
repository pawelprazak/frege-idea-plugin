package org.fregelang.plugin.idea.framework;

import com.intellij.framework.addSupport.FrameworkSupportInModuleConfigurable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ModifiableModelsProvider;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.libraries.CustomLibraryDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FregeSupportConfigurable extends FrameworkSupportInModuleConfigurable {

    @Nullable
    @Override
    public JComponent createComponent() {
        return null;
    }

    @Nullable
    @Override
    public CustomLibraryDescription createLibraryDescription() {
        return FregeLibraryDescription.INSTANCE;
    }

    @Override
    public boolean isOnlyLibraryAdded() {
        return true;
    }

    @Override
    public void addSupport(@NotNull Module module, @NotNull ModifiableRootModel rootModel, @NotNull ModifiableModelsProvider modifiableModelsProvider) {
        // Do nothing
    }
}
