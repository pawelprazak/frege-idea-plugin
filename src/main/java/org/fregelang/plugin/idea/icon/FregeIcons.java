package org.fregelang.plugin.idea.icon;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * All tool windows should have icon size of 13x13 pixels and all actions should have icon size of 16x16 pixels
 * (tree nodes, file types and almost all icons have size 16x16 pixels).
 *
 * - iconName.png W x H pixels (Will be used on non-Retina devices with default look and feel)
 * - iconName@2x.png 2*W x 2*H pixels (Will be used on Retina devices with default look and feel)
 * - iconName_dark.png W x H pixels (Will be used on non-Retina devices with Darcula look and feel)
 * - iconName@2x_dark.png 2*W x 2*H pixels (Will be used on Retina devices with Darcula look and feel)
 */
public class FregeIcons {
    public static final Icon FREGE_SMALL = IconLoader.getIcon("/icons/frege16.png");
    public static final Icon FREGE_24 = IconLoader.getIcon("/icons/frege24.png");
    public static final Icon FREGE_BIG = IconLoader.getIcon("/icons/frege32.png");
    public static final Icon DEFAULT = FREGE_SMALL;

    // TODO
    public static final Icon FILE_TYPE = DEFAULT;
    public static final Icon SCRIPT_FILE = DEFAULT;
    public static final Icon WORKSHEET = DEFAULT;
    public static final Icon FREGE_SDK = DEFAULT;
    public static final Icon FREGE_CONSOLE = DEFAULT;
}
