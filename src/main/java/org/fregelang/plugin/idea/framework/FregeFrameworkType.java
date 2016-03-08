package org.fregelang.plugin.idea.framework;

import com.intellij.framework.FrameworkTypeEx;
import com.intellij.framework.addSupport.FrameworkSupportInModuleProvider;
import org.fregelang.plugin.idea.icon.FregeIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class FregeFrameworkType extends FrameworkTypeEx {

    public static final FregeFrameworkType INSTANCE = FrameworkTypeEx.EP_NAME.findExtension(FregeFrameworkType.class);

    protected FregeFrameworkType() {
        super("Frege");
    }

    @NotNull
    @Override
    public FrameworkSupportInModuleProvider createProvider() {
        return new FregeSupportProvider();
    }

    @NotNull
    @Override
    public String getPresentableName() {
        return "Frege";
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return FregeIcons.DEFAULT;
    }
}
