package pl.edu.agh.rndvz.controllers;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rndvz.model.Photo;
import pl.edu.agh.rndvz.model.User;
import pl.edu.agh.rndvz.model.UserWithPhotos;
import pl.edu.agh.rndvz.persistence.UserRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PhotoController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/usersWithPhotos/{id}", method = RequestMethod.GET)

    public @ResponseBody ResponseEntity<UserWithPhotos>  getFile(@PathVariable Long id) {

        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user.getPhotos().add(new Photo("/home/robert/IdeaProjects/rndvz/dupa.jpg"));

        List<byte[]> photos = user.getPhotos().
                stream()
                .map(Photo::getPhotoPath)
                .map(this::photoToByteArray)
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());
        try{
            String encodedfile =new String(Base64.encodeBase64(photos.get(0)), "UTF-8");
            UserWithPhotos userWithPhotos = new UserWithPhotos(user, Arrays.asList(encodedfile,encodedfile));
            return ResponseEntity.ok(userWithPhotos);
        }catch (Exception ex){
            System.out.println(ex);
        }
        return ResponseEntity.ok(new UserWithPhotos());


//        return new ResponseEntity<>(new UserWithPhotos(user,photos), HttpStatus.OK);

    }



    private Optional<byte[]> photoToByteArray(String path) {
        try {
            InputStream is = new FileInputStream(path);

            // Prepare buffered image.
            BufferedImage img = ImageIO.read(is);

            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream(); // fileupload zamiast zwyklego?

            // Write to output stream
            ImageIO.write(img, "jpg", bao);

            return Optional.of(bao.toByteArray());
        } catch (IOException ex) {
            System.out.println("could not upload for " + path);
            return Optional.empty();
        }
    }

}
