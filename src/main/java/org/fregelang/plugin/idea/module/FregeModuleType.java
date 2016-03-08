package org.fregelang.plugin.idea.module;

import com.intellij.openapi.module.ModuleType;
import org.fregelang.plugin.idea.filetype.FregeFileType;
import org.fregelang.plugin.idea.icon.FregeIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class FregeModuleType extends ModuleType<FregeModuleBuilder> {

    public static final FregeModuleType INSTANCE = new FregeModuleType();

    public FregeModuleType() {
        super("FREGE_MODULE");
    }

    @NotNull
    @Override
    public FregeModuleBuilder createModuleBuilder() {
        return new FregeModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "Frege Module";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Frege Module";
    }

    @Override
    public Icon getBigIcon() {
        return FregeIcons.DEFAULT;
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean isOpened) {
        return FregeFileType.INSTANCE.getIcon();
    }
}
