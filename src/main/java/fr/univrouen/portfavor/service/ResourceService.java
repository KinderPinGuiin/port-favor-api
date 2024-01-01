package fr.univrouen.portfavor.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Service handling the API resources (images, videos...).
 */
public interface ResourceService {

    /**
     * @return             The resources folder path.
     * @throws IOException Exception thrown when the resources path can't be loaded.
     */
    Path getResourcesPath() throws IOException;

    /**
     * Retrieve the data associated to the given file name or path.
     * @param  filename    The file's name or path to retrieve.
     * @return             The given file's data.
     * @throws IOException Exception if the given file can't be loaded.
     */
    Resource getResource(String filename) throws IOException;

    /**
     * Saves the given multipart file.
     *
     * @param  file        The file's to save data.
     * @param  folder      The file's parent folder (can be null).
     * @param  filename    The new file's name (can be null).
     * @throws IOException Exception thrown if the given file can't be saved.
     */
    void save(Resource file, String folder, String filename) throws IOException;

    File toFile(Resource resource) throws IOException;

}
