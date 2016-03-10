package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.roots.libraries.LibraryKind;
import com.intellij.openapi.roots.libraries.NewLibraryConfiguration;
import com.intellij.openapi.roots.ui.configuration.libraries.CustomLibraryDescription;
import com.intellij.openapi.roots.ui.configuration.projectRoot.LibrariesContainer;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Objects.toStringHelper;
import static java.util.stream.Collectors.groupingBy;

public class FregeLibraryDescription extends CustomLibraryDescription {

    public static final FregeLibraryDescription INSTANCE = new FregeLibraryDescription();

    @NotNull
    @Override
    public Set<? extends LibraryKind> getSuitableLibraryKinds() {
        return Collections.singleton(FregeLibraryKind.INSTANCE);
    }

    @Nullable
    @Override
    public NewLibraryConfiguration createNewLibrary(@NotNull JComponent parentComponent, @Nullable VirtualFile contextDirectory) {
//        Comparator<FregeSdkDescriptor> comparator = comparing(FregeSdkDescriptor::getVersion);

        List<SdkChoice> sdks =
//                localSkdsIn(virtualToIoFile(contextDirectory)).map(SdkChoice(_, "Project")) ++
//                systemSdks.sortBy(_.version).map(SdkChoice(_, "System")) ++
//                ivySdks.sortBy(_.version).map(SdkChoice(_, "Ivy")) ++
                mavenSdks().stream()
//                        .sorted(comparator) // FIXME
                        .map(d -> new SdkChoice(d, "Maven"))
                        .collect(Collectors.toList());

        SdkSelectionDialog dialog = new SdkSelectionDialog(parentComponent, () -> sdks);

        return Optional.ofNullable(dialog.open()).map(FregeSdkDescriptor::createNewLibraryConfiguration).orElse(null);
    }


    private List<FregeSdkDescriptor> mavenSdks() {
        String homePath = System.getProperty("user.home");
        return sdksIn(Paths.get(homePath, ".m2", "repository", "org", "scala-lang"));
    }

    private List<FregeSdkDescriptor> sdksIn(Path root) {
        List<File> allFiles = Files.allFiles(root.toFile());
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
            this.sdk = sdk;
            this.source = source;
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
