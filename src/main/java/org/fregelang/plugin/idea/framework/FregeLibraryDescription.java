package org.fregelang.plugin.idea.framework;

import com.google.common.annotations.VisibleForTesting;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.roots.libraries.LibraryKind;
import com.intellij.openapi.roots.libraries.NewLibraryConfiguration;
import com.intellij.openapi.roots.ui.configuration.libraries.CustomLibraryDescription;
import com.intellij.openapi.roots.ui.configuration.projectRoot.LibrariesContainer;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.google.common.base.Objects.toStringHelper;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.groupingBy;

public class FregeLibraryDescription extends CustomLibraryDescription {

    private static final Logger log = Logger.getInstance(FregeLibraryDescription.class);

    public static final FregeLibraryDescription INSTANCE = new FregeLibraryDescription();

    @NotNull
    @Override
    public Set<? extends LibraryKind> getSuitableLibraryKinds() {
        return Collections.singleton(FregeLibraryKind.INSTANCE);
    }

    @Nullable
    @Override
    public NewLibraryConfiguration createNewLibrary(@NotNull JComponent parentComponent, @Nullable VirtualFile contextDirectory) {
        Supplier<List<SdkChoice>> sdkProvider = () -> mavenSdks().stream()
                .sorted(FregeSdkDescriptor.comparator().reversed())
                .map(sdk -> new SdkChoice(sdk, "Maven"))
                .collect(Collectors.toList());

        SdkSelectionDialog dialog = new SdkSelectionDialog(parentComponent, sdkProvider);
        try {
            return Optional.ofNullable(dialog.open())
                    .map(FregeSdkDescriptor::createNewLibraryConfiguration)
                    .orElseGet(() -> null);
        } catch (Exception e) {
            log.error("Cannot create new library", e);
            Messages.showErrorDialog(parentComponent,
                    format("Cannot create new library, %s SDKs available", dialog.count()), "Error Creating Library");
            throw e;
        }
    }

    @VisibleForTesting
    List<FregeSdkDescriptor> mavenSdks() {
        String homePath = System.getProperty("user.home");
        return sdksIn(Paths.get(homePath, ".m2", "repository", "org", "frege-lang"));
    }

    @VisibleForTesting
    List<FregeSdkDescriptor> sdksIn(Path root) {
        List<File> allFiles = Files.allFiles(root.toFile()).collect(Collectors.toList());
        List<Component> components = Component.discoverIn(allFiles);
        return components.stream()
                .collect(groupingBy(Component::getVersion)).entrySet().stream()
                .map(e -> FregeSdkDescriptor.from(e.getValue()))
                .collect(Collectors.toList());
    }

    @NotNull
    @Override
    public LibrariesContainer.LibraryLevel getDefaultLevel() {
        return LibrariesContainer.LibraryLevel.GLOBAL;
    }

    static class SdkChoice {
        private final FregeSdkDescriptor sdk;
        private final String source;

        public SdkChoice(FregeSdkDescriptor sdk, String source) {
            this.sdk = requireNonNull(sdk);
            this.source = requireNonNull(source);
        }

        public FregeSdkDescriptor getSdk() {
            return sdk;
        }

        public String getSource() {
            return source;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SdkChoice sdkChoice = (SdkChoice) o;
            return Objects.equals(sdk, sdkChoice.sdk) &&
                    Objects.equals(source, sdkChoice.source);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sdk, source);
        }

        @Override
        public String toString() {
            return toStringHelper(this)
                    .add("sdk", sdk)
                    .add("source", source)
                    .toString();
        }
    }
}
