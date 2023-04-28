package de.hs_mannheim.informatik.lambda.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FSService {

    private final Path SOURCE_DIRECTORY;
    private final Path TARGET_DIRECTORY;

    public FSService() throws IOException {
        SOURCE_DIRECTORY = Files.createTempDirectory("SOURCE");
        SOURCE_DIRECTORY.toFile().deleteOnExit();

        TARGET_DIRECTORY = Files.createTempDirectory("TARGET");
        TARGET_DIRECTORY.toFile().deleteOnExit();
    }

    public Path getSourceDirectory() {
        return SOURCE_DIRECTORY;
    }

    public Path getTargetDirectory() {
        return TARGET_DIRECTORY;
    }
}
