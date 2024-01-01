package fr.univrouen.portfavor.controller;

import fr.univrouen.portfavor.constant.role.RoleID;
import fr.univrouen.portfavor.dto.request.image.CreateImageRequestDTO;
import fr.univrouen.portfavor.dto.response.image.ImageResponseDTO;
import fr.univrouen.portfavor.dto.response.user.UserResponseDTO;
import fr.univrouen.portfavor.exception.FunctionalException;
import fr.univrouen.portfavor.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
public class ImageController {

    /*
     * Routes
     */

    public static final String IMAGE_ROOT = "/image";
    public static final String GET_IMAGES_SKELETON = IMAGE_ROOT + "/get-skeleton";
    public static final String GET_IMAGE_SKELETON = IMAGE_ROOT + "/get-skeleton/{name}";
    public static final String GET_IMAGE = IMAGE_ROOT + "/get/{name}";
    public static final String CREATE_IMAGE = IMAGE_ROOT + "/create";
    public static final String UPDATE_IMAGE = IMAGE_ROOT + "/update";
    public static final String DELETE_IMAGE = IMAGE_ROOT + "/delete";

    @Autowired
    private ImageService imageService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @return All the stored images that the current user can access (without image content).
     */
    @GetMapping(GET_IMAGES_SKELETON)
    @ResponseBody
    public List<ImageResponseDTO> getAllImages() {
        return this.imageService
            .getAll()
            .stream()
            .map(image -> this.modelMapper.map(image, ImageResponseDTO.class))
            .toList();
    }

    /**
     * Retrieves the given image and return it as base64.
     *
     * @param imageName The name of the image to retrieve.
     * @return          The base64 image.
     */
    @GetMapping(GET_IMAGE)
    @ResponseBody
    public HttpEntity<?> getImage(@PathVariable("name") String imageName) throws FunctionalException {
        // Load the image and convert it to stream
        var image = this.imageService.getImageAsResource(imageName);
        try {
            var imageFile = image.getFile();
            var imageStream = new FileInputStream(imageFile);

            // Send it as base64
            return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(Files.probeContentType(imageFile.toPath())))
                .header(HttpHeaders.CONTENT_ENCODING, "base64")
                .header(HttpHeaders.TRANSFER_ENCODING, "base64")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getFilename() + "\"")
                .body(new InputStreamResource(imageStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the given image and store it.
     *
     * @param  request The image to create.
     * @return         The created image.
     */
    @PostMapping(path = CREATE_IMAGE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasAuthority('" + RoleID.ADMIN + "')")
    @ResponseBody
    public ImageResponseDTO createImage(@ModelAttribute CreateImageRequestDTO request) throws FunctionalException {
        return this.modelMapper.map(
            this.imageService.create(
                request.getName(),
                request.getDescription(),
                request.getIsPublic(),
                request.getImageData().getResource()
            ),
            ImageResponseDTO.class
        );
    }

}