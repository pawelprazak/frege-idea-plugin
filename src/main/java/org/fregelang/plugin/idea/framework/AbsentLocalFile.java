package org.fregelang.plugin.idea.framework;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AbsentLocalFile extends VirtualFile {

    private static final VirtualFileSystem ABSENT_LOCAL_FILESYSTEM = new VirtualFileSystem() {
        @NotNull
        @Override
        public String getProtocol() {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void renameFile(Object requestor, @NotNull VirtualFile vFile, @NotNull String newName) throws IOException {
            throw new UnsupportedOperationException();
        }

        @NotNull
        @Override
        protected VirtualFile createChildFile(Object requestor, @NotNull VirtualFile vDir, @NotNull String fileName) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addVirtualFileListener(@NotNull VirtualFileListener listener) {
            throw new UnsupportedOperationException();
        }

        @Nullable
        @Override
        public VirtualFile refreshAndFindFileByPath(@NotNull String path) {
            throw new UnsupportedOperationException();
        }

        @NotNull
        @Override
        protected VirtualFile copyFile(Object requestor, @NotNull VirtualFile virtualFile, @NotNull VirtualFile newParent, @NotNull String copyName) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void refresh(boolean asynchronous) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isReadOnly() {
            throw new UnsupportedOperationException();
        }

        @NotNull
        @Override
        protected VirtualFile createChildDirectory(Object requestor, @NotNull VirtualFile vDir, @NotNull String dirName) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void removeVirtualFileListener(@NotNull VirtualFileListener listener) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void moveFile(Object requestor, @NotNull VirtualFile vFile, @NotNull VirtualFile newParent) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Nullable
        @Override
        public VirtualFile findFileByPath(@NotNull @NonNls String path) {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void deleteFile(Object requestor, @NotNull VirtualFile vFile) throws IOException {
            throw new UnsupportedOperationException();
        }

        @NotNull
        @Override
        public String extractPresentableUrl(@NotNull String path) {
            throw new UnsupportedOperationException();
        }
    };

    private final String url;
    private final String path;

    public AbsentLocalFile(String url, String path) {
        this.url = url;
        this.path = path;
    }

    @NotNull
    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLength() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public VirtualFileSystem getFileSystem() {
        return ABSENT_LOCAL_FILESYSTEM;
    }

    @NotNull
    @Override
    public byte[] contentsToByteArray() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public VirtualFile getParent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refresh(boolean asynchronous, boolean recursive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void refresh(boolean asynchronous, boolean recursive, @Nullable Runnable postRunnable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTimeStamp() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public OutputStream getOutputStream(Object requestor, long newModificationStamp, long newTimeStamp) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDirectory() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean isWritable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public VirtualFile[] getChildren() {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public String getUrl() {
        return url;
    }
}