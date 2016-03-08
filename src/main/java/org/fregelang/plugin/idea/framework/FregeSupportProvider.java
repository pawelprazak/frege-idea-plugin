package org.fregelang.plugin.idea.framework;

import com.intellij.framework.FrameworkTypeEx;
import com.intellij.framework.addSupport.FrameworkSupportInModuleConfigurable;
import com.intellij.framework.addSupport.FrameworkSupportInModuleProvider;
import com.intellij.ide.util.frameworkSupport.FrameworkSupportModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeId;
import com.intellij.openapi.roots.ui.configuration.FacetsProvider;
import org.fregelang.plugin.idea.icon.FregeIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Objects;

public class FregeSupportProvider extends FrameworkSupportInModuleProvider {

    @Override
    public Icon getIcon() {
        return FregeIcons.DEFAULT;
    }

    @NotNull
    @Override
    public FrameworkTypeEx getFrameworkType() {
        return FregeFrameworkType.INSTANCE;
    }

    @Override
    public boolean isEnabledForModuleType(@NotNull ModuleType moduleType) {
        String id = moduleType.getId();
        return Objects.equals(ModuleTypeId.JAVA_MODULE, id) || Objects.equals("PLUGIN_MODULE", id); // PluginModuleType.getInstance.getId
    }

    @Override
    public boolean isSupportAlreadyAdded(@NotNull Module module, @NotNull FacetsProvider facetsProvider) {
        // TODO
        return super.isSupportAlreadyAdded(module, facetsProvider);
    }

    @NotNull
    @Override
    public FrameworkSupportInModuleConfigurable createConfigurable(@NotNull FrameworkSupportModel model) {
        return new FregeSupportConfigurable();
    }
}
