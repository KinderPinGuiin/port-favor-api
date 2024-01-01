package fr.univrouen.portfavor.service.standard;

import fr.univrouen.portfavor.service.ResourceService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * The standard resource service using the system storage.
 */
@Service("standard-resource-service")
public class StandardResourceService implements ResourceService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${portfavor.resources.folder}")
    private String resourcesFolderRoot;

    @Override
    public Path getResourcesPath() throws IOException {
        // Create the directories if they don't exist and returns the path
        var path = Path.of(this.resourcesFolderRoot);
        Files.createDirectories(path);

        return path;
    }

    @Override
    public Resource getResource(String filename) throws IOException {
        // Load the resource and check that it's a valid one
        var resource = new UrlResource(this.getResourcesPath().resolve(filename).toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }

        throw new IOException("Can't load file : " + filename);
    }

    @Override
    public void save(Resource file, String folder, String filename) throws IOException {
        // Build the file to save destination
        var root = folder == null ? "" : folder;
        var name = filename == null ?  file.getFilename() : filename;
        var destinationParentFolder = this.getResourcesPath().resolve(Paths.get(root));
        var destinationFile = destinationParentFolder
            .resolve(Objects.requireNonNull(name))
            .normalize()
            .toAbsolutePath();

        // Checks that the obtained path is valid (security check)
        if (!destinationFile.getParent().equals(destinationParentFolder)) {
            throw new IOException("Cannot store file outside given parent directory : " + destinationParentFolder);
        }

        // Saves the file
        Files.createDirectories(destinationFile.getParent());
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Override
    public File toFile(Resource resource) throws IOException {
        var tempFile = new File(Objects.requireNonNull(resource.getFilename()));
        try (InputStream inputStream = resource.getInputStream(); OutputStream outputStream = new FileOutputStream(tempFile)) {
            IOUtils.copy(inputStream, outputStream);
        }

        return tempFile;
    }

}
