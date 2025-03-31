package com.huangzizhu.utils;

@Component
public class FileListener extends FileAlterationListenerAdaptor {
    @Override
    public void onFileChange(File file) {
        long fileSize = file.length();
        long lastModified = file.lastModified();
        System.out.println("File " + file.getAbsolutePath() + " has been modified. Size: " + fileSize + " bytes, Last Modified: " + lastModified);
    }
}