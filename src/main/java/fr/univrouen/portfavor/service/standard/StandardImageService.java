package fr.univrouen.portfavor.service.standard;

import fr.univrouen.portfavor.constant.error.ErrorMessage;
import fr.univrouen.portfavor.constant.role.RoleID;
import fr.univrouen.portfavor.entity.Image;
import fr.univrouen.portfavor.entity.User;
import fr.univrouen.portfavor.exception.FunctionalException;
import fr.univrouen.portfavor.repository.ImageRepository;
import fr.univrouen.portfavor.service.AuthenticationService;
import fr.univrouen.portfavor.service.ImageService;
import fr.univrouen.portfavor.service.ResourceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * The standard image service using the database.
 */
@Service("standard-image-service")
public class StandardImageService implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${portfavor.images.folder}")
    private String imagesFolder;

    @Override
    public List<Image> getAll() {
        // If the user is not authenticated, return only the public images.
        var user = this.authenticationService.getCurrentUser();
        if (!this.canAccessPrivate(user)) {
            return this.imageRepository.findAllPublic();
        }

        return this.imageRepository.findAll();
    }

    @Override
    public Image getByName(String name) throws FunctionalException {
        var image = this.imageRepository.findByPath(name).orElse(null);
        if (image == null) {
            throw new FunctionalException(ErrorMessage.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        // Check that the image is not accessible
        var currentUser = this.authenticationService.getCurrentUser();
        if (!image.isPublic() && !this.canAccessPrivate(currentUser)) {
            throw new FunctionalException(ErrorMessage.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return image;
    }

    @Override
    public Resource getImageAsResource(String imageName) throws FunctionalException {
        // Check if the image exists
        var image = this.getByName(imageName);

        // Return the image as a resource
        try {
            return this.resourceService.getResource(Paths.get(imagesFolder, imageName).toString());
        } catch (IOException e) {
            throw new FunctionalException(ErrorMessage.UNKNOWN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Image create(String name, String description, boolean isPublic, Resource data) throws FunctionalException {
        // Check that the given file is an image
        String mime;
        File tempFile = null;
        try {
            tempFile = this.resourceService.toFile(data);
            mime = MediaType.valueOf(Files.probeContentType(tempFile.toPath())).toString();
            if (!mime.startsWith("image/")) {
                throw new FunctionalException(ErrorMessage.INVALID_CONTENT_TYPE, HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FunctionalException(ErrorMessage.CANT_UPLOAD_IMAGES, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }

        // Crop the image
        Resource croppedResource;
        try {
            var croppedImage = this.cropAsSquare(data.getContentAsByteArray(), mime);
            croppedResource = new ByteArrayResource(croppedImage);
        } catch (IOException e) {
            throw new FunctionalException(ErrorMessage.CANT_UPLOAD_IMAGES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Save the image data
        var fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(data.getFilename());
        Image image;
        try {
            image = new Image(0L, name, description, isPublic, mime, fileName);
            this.resourceService.save(croppedResource, imagesFolder, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FunctionalException(ErrorMessage.CANT_UPLOAD_IMAGES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Save the image entity and returns it
        return this.imageRepository.save(image);
    }

    /**
     * Crop the given image to make it a square on it's center.
     *
     * @param originalImageBytes The original image as a byte array.
     * @param mime               The mime type of the image (necessary to recreate it).
     * @return                   The cropped image.
     * @throws IOException       Exception thrown if the image can't be saved or loaded.
     */
    private byte[] cropAsSquare(byte[] originalImageBytes, String mime) throws IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImageBytes)) {
            BufferedImage originalImage = ImageIO.read(inputStream);

            int size = Math.min(originalImage.getWidth(), originalImage.getHeight());
            int x = (originalImage.getWidth() - size) / 2;
            int y = (originalImage.getHeight() - size) / 2;

            BufferedImage squareImage = originalImage.getSubimage(x, y, size, size);

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ImageIO.write(squareImage, mime.split("/")[1], outputStream);
                return outputStream.toByteArray();
            }
        }
    }

    /**
     * @param  u The user to check.
     * @return   True if the user can access private portfolio, false otherwise.
     */
    private boolean canAccessPrivate(User u) {
        return !(u == null || u.getRoles().stream().noneMatch(role -> role.getName().equals(RoleID.PRIVATE_USER)));
    }

}
