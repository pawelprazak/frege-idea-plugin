package org.fregelang.plugin.idea.framework;

import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ListTableModel;
import org.fregelang.plugin.idea.framework.FregeLibraryDescription.SdkChoice;
import org.jetbrains.annotations.Nullable;

public class SdkTableModel extends ListTableModel<SdkChoice> {

    public SdkTableModel() {
        super(
                new ColumnInfo<SdkChoice, String>("Location") {
                    @Nullable
                    @Override
                    public String valueOf(SdkChoice item) {
                        return item.getSource();
                    }

                    @Nullable
                    @Override
                    public String getPreferredStringValue() {
                        return "Maven";
                    }
                },
                new ColumnInfo<SdkChoice, String>("Version") {
                    @Nullable
                    @Override
                    public String valueOf(SdkChoice item) {
                        return item.getSdk().getVersion().map(Version::getNumber).orElse("Unknown");
                    }

                    @Nullable
                    @Override
                    public String getPreferredStringValue() {
                        return FregeLanguageLevelProxy.Frege_3_23.getInstance().getVersion();
                    }
                },
                new ColumnInfo<SdkChoice, Boolean>("Sources") {
                    @Override
                    public Class<?> getColumnClass() {
                        return Boolean.class;
                    }

                    @Nullable
                    @Override
                    public String getPreferredStringValue() {
                        return "0";
                    }

                    @Nullable
                    @Override
                    public Boolean valueOf(SdkChoice item) {
                        return !item.getSdk().getSourceFiles().isEmpty();
                    }
                },
                new ColumnInfo<SdkChoice, Boolean>("Docs") {
                    @Override
                    public Class<?> getColumnClass() {
                        return Boolean.class;
                    }

                    @Nullable
                    @Override
                    public String getPreferredStringValue() {
                        return "0";
                    }

                    @Nullable
                    @Override
                    public Boolean valueOf(SdkChoice item) {
                        return !item.getSdk().getDocFiles().isEmpty();
                    }
                }
        );
    }
}
