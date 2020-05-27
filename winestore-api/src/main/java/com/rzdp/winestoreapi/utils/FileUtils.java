package com.rzdp.winestoreapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class FileUtils {

    private static final DateFormat fileDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    public static void transferFile(String filePath, String targetPath) {
        File file = new File(filePath);
        Path target = Paths.get(targetPath);
        try {
            String fileName = file.getName();
            Path targetPathFile = target.resolve(fileName);
            if (targetPathFile.toFile().exists()) {
                LOG.info("Selected file [{}] already exists in the target directory." +
                        " Appending timestamp to file.", fileName);
                fileName = fileDateFormat.format(new Date() + "_" + file.getName());
                targetPathFile = target.resolve(fileName);
            }
            Files.move(Paths.get(file.getAbsolutePath()), targetPathFile);
            LOG.info("Selected file [{}] transferred successfully", fileName);
        } catch (FileAlreadyExistsException e) {
            LOG.error("Selected file already exists in the target directory.");
            try {
                Files.delete(Paths.get(file.toURI()));
                LOG.info("Selected file was deleted");
            } catch (IOException e1) {
                LOG.error("An error occurred while trying to delete selected file", e1);
            }
        } catch (IOException e1) {
            LOG.error("An error occurred while trying to move selected file", e1);
        }
    }
}
