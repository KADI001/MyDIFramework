package org.kadirov.core.service;

import org.kadirov.core.constants.Constants;
import org.kadirov.core.exception.PackageDoesntExistException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultPackageScanner implements PackageScanner {

    private final List<Class<?>> allClasses;

    public DefaultPackageScanner() {
        allClasses = new ArrayList<>();
    }

    @Override
    public List<Class<?>> scan(String rootPackage) throws PackageDoesntExistException {
        allClasses.clear();

        File file = new File(rootPackage);

        if (!file.isDirectory()) {
            throw new PackageDoesntExistException("The package " + rootPackage + " does not exist.");
        }

        try {
            File[] listFiles = file.listFiles();
            for (File childFile : Objects.requireNonNull(listFiles)) {
                scanForAllClasses(childFile, "");
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return allClasses;
    }

    private void scanForAllClasses(File childFile, String packageName) {
        try {
            if (childFile.isDirectory()) {
                packageName += childFile.getName() + ".";
                File[] listFiles = childFile.listFiles();
                for (File childFile2 : Objects.requireNonNull(listFiles)) {
                    scanForAllClasses(childFile2, packageName);
                }
            } else {
                String fileName = childFile.getName();

                if (!fileName.endsWith(Constants.JAVA_BINARY_FILE_EXTENSION)) {
                    return;
                }

                String fileNameWithoutExtension = fileName
                        .replaceAll(Constants.JAVA_BINARY_FILE_EXTENSION, "");

                String className = packageName + fileNameWithoutExtension;
                Class<?> clazz = Class.forName(className);
                allClasses.add(clazz);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}