package org.fregelang.plugin.idea.framework;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.NewLibraryConfiguration;
import com.intellij.openapi.roots.ui.configuration.libraryEditor.LibraryEditor;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FregeSdkDescriptor {

    private final Optional<Version> version;
    private final List<File> compilerFiles;
    private final List<File> libraryFiles;
    private final List<File> sourceFiles;
    private final List<File> docFiles;

    public FregeSdkDescriptor(Optional<Version> version,
                              List<File> compilerFiles,
                              List<File> libraryFiles,
                              List<File> sourceFiles,
                              List<File> docFiles) {

        this.version = version;
        this.compilerFiles = compilerFiles;
        this.libraryFiles = libraryFiles;
        this.sourceFiles = sourceFiles;
        this.docFiles = docFiles;
    }

    public Optional<Version> getVersion() {
        return version;
    }

    public List<File> getCompilerFiles() {
        return compilerFiles;
    }

    public List<File> getDocFiles() {
        return docFiles;
    }

    public List<File> getLibraryFiles() {
        return libraryFiles;
    }

    public List<File> getSourceFiles() {
        return sourceFiles;
    }

    def createNewLibraryConfiguration() = {
        val properties = new ScalaLibraryProperties()

        properties.languageLevel = version.flatMap(ScalaLanguageLevel.from).getOrElse(ScalaLanguageLevel.Default)
        properties.compilerClasspath = compilerFiles

        val name = "scala-sdk-" + version.map(_.number).getOrElse("Unknown")

        return new NewLibraryConfiguration(name, FregeLibraryType.instance, properties) {
            override def addRoots(editor:LibraryEditor): Unit = {
                libraryFiles.map(_.toLibraryRootURL).foreach(editor.addRoot(_, OrderRootType.CLASSES))
                sourceFiles.map(_.toLibraryRootURL).foreach(editor.addRoot(_, OrderRootType.SOURCES))
                docFiles.map(_.toLibraryRootURL).foreach(editor.addRoot(_, JavadocOrderRootType.getInstance))

                if (sourceFiles.isEmpty && docFiles.isEmpty) {
                    editor.addRoot(ScalaSdk.documentationUrlFor(version), JavadocOrderRootType.getInstance)
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FregeSdkDescriptor that = (FregeSdkDescriptor) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(compilerFiles, that.compilerFiles) &&
                Objects.equals(libraryFiles, that.libraryFiles) &&
                Objects.equals(sourceFiles, that.sourceFiles) &&
                Objects.equals(docFiles, that.docFiles);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("compilerFiles", compilerFiles)
                .add("version", version)
                .add("libraryFiles", libraryFiles)
                .add("sourceFiles", sourceFiles)
                .add("docFiles", docFiles)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, compilerFiles, libraryFiles, sourceFiles, docFiles);
    }

    public static FregeSdkDescriptor from(List<Component> components) {
        Map<Component.Kind, List<Component>> componentsByKind = components.stream()
                .collect(Collectors.groupingBy(Component::getKind));

        List<Component> binaryComponents = componentsByKind.getOrDefault(Component.Kind.Binaries.INSTANCE, new ArrayList<>());
        List<Component> sourceComponents = componentsByKind.getOrDefault(Component.Kind.Sources.INSTANCE, new ArrayList<>());
        List<Component> docComponents = componentsByKind.getOrDefault(Component.Kind.Docs.INSTANCE, new ArrayList<>());

        Set<Component.Artifact> requiredBinaryArtifacts = ImmutableSet.of(
                Component.Artifact.FregeLibrary.INSTANCE, Component.Artifact.FregeCompiler.INSTANCE);

        Set<Component.Artifact> existingBinaryArtifacts = binaryComponents.stream()
                .map(Component::getArtifact)
                .collect(Collectors.toSet());

        Sets.SetView<Component.Artifact> missingBinaryArtifacts =
                Sets.difference(requiredBinaryArtifacts, existingBinaryArtifacts); // all the required without the existing

        if (missingBinaryArtifacts.isEmpty()) {
            Stream<Component> compilerBinaries = binaryComponents.stream()
                    .filter(it -> requiredBinaryArtifacts.contains(it.getArtifact()));

            List<Component.Artifact> libraryArtifacts = Component.Artifact.VALUES.stream()
                    .filter(v -> v == Component.Artifact.FregeCompiler.INSTANCE)
                    .collect(toList());

            Stream<Component> libraryBinaries = binaryComponents.stream().filter(it -> libraryArtifacts.contains(it.getArtifact()));
            Stream<Component> librarySources = sourceComponents.stream().filter(it -> libraryArtifacts.contains(it.getArtifact()));
            Stream<Component> libraryDocs = docComponents.stream()
                    .filter(it -> libraryArtifacts.contains(it.getArtifact()));

            Optional<Version> libraryVersion = binaryComponents.stream()
                    .filter(c -> c.getArtifact() == Component.Artifact.FregeLibrary.INSTANCE)
                    .map(Component::getVersion)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findFirst();

            return new FregeSdkDescriptor(
                    libraryVersion,
                    compilerBinaries.map(Component::getFile).collect(Collectors.toList()),
                    libraryBinaries.map(Component::getFile).collect(Collectors.toList()),
                    librarySources.map(Component::getFile).collect(Collectors.toList()),
                    libraryDocs.map(Component::getFile).collect(Collectors.toList())
            );
        } else {
            throw new RuntimeException("Not found: " + missingBinaryArtifacts.stream()
                    .map(Component.Artifact::getTitle)
                    .reduce((a,b) -> a+", "+b));
        }
    }
}
