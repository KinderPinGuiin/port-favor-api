package fr.univrouen.portfavor.service;

import fr.univrouen.portfavor.entity.Image;
import fr.univrouen.portfavor.exception.FunctionalException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service used to handle the API images.
 */
public interface ImageService {

    /**
     * Get all the images and return them.
     *
     * @return All the stored images that the current user can access (without image content).
     */
    List<Image> getAll();

    /**
     * Retrieves the given image and returns it.
     *
     * @param  name                The name of the image to retrieve.
     * @return                     The image associated to the given name.
     * @throws FunctionalException Exception thrown if the given name is invalid (not found or invalid permissions).
     */
    Image getByName(String name) throws FunctionalException;

    /**
     * Get the image data associated to the given name and returns it.
     *
     * @param  imageName           The name of the image to retrieve.
     * @return                     The image as a resource.
     * @throws FunctionalException Exception thrown if the given image name is invalid (doesn't exist or invalid
     *                             permission)
     */
    Resource getImageAsResource(String imageName) throws FunctionalException;

    /**
     * Creates the given image and save it on the storage.
     *
     * @param  name                The image's name.
     * @param  description         The image's description.
     * @param  isPublic            Indicates if the image is public or not.
     * @param  data                The image's data as a multipart file.
     * @return                     The created image entity.
     * @throws FunctionalException Exception thrown if an error occurs while saving file on the storage.
     */
    Image create(String name, String description, boolean isPublic, Resource data) throws FunctionalException;

    /**
     * Updates the image associated to the given ID with the given information.
     *
     * @param id                   The image's to update ID.
     * @param newName              The image's new name.
     * @param newDescription       The image's description.
     * @param newIsPublic          The new image's privacy.
     * @return                     The updated image.
     * @throws FunctionalException Exception thrown if the given ID is invalid.
     */
    Image update(Long id, String newName, String newDescription, boolean newIsPublic) throws FunctionalException;

    /**
     * Deletes the image associated to the given ID.
     *
     * @param  id                  The ID of the image to delete.
     * @return                     The deleted image.
     * @throws FunctionalException Exception thrown when the given ID is invalid.
     */
    Image delete(Long id) throws FunctionalException;

}
