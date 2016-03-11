package org.fregelang.plugin.idea.project;

import com.intellij.ide.util.projectWizard.AbstractModuleBuilder;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.platform.ProjectTemplate;
import org.fregelang.plugin.idea.icon.FregeIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FregeProjectTemplate implements ProjectTemplate {
    @NotNull
    @Override
    public String getName() {
        return "Frege";
    }

    @Nullable
    @Override
    public String getDescription() {
        return "Simple module with attached Frege SDK";
    }

    @Override
    public Icon getIcon() {
        return FregeIcons.DEFAULT;
    }

    @NotNull
    @Override
    public AbstractModuleBuilder createModuleBuilder() {
        return new FregeModuleBuilder();
    }

    @Nullable
    @Override
    public ValidationInfo validateSettings() {
        return null;
    }
}
