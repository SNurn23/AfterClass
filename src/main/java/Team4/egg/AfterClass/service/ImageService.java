package Team4.egg.AfterClass.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {

    private static final String DIRECTORYGROUP = "src/main/resources/static/profileGroup";
    private static final String DIRECTORYMEMBER = "src/main/resources/static/profileMember";
    private static final String DIRECTORYPOST = "src/main/resources/static/filePosts";

    public String copyImageGroup(MultipartFile image) {
        try {
            String photoName = image.getOriginalFilename();
            Path photoPath = Paths.get(DIRECTORYGROUP, photoName).toAbsolutePath();
            Files.copy(image.getInputStream(), photoPath, StandardCopyOption.REPLACE_EXISTING);
            return photoName;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error saving image");
        }
    }


    public String copyProfileMember(MultipartFile profile_img) {
        try {
            String photoName = profile_img.getOriginalFilename();
            Path photoPath = Paths.get(DIRECTORYMEMBER, photoName).toAbsolutePath();
            Files.copy(profile_img.getInputStream(), photoPath, StandardCopyOption.REPLACE_EXISTING);
            return photoName;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error saving image");
        }
    }

    public String copyFilePost(MultipartFile file) {
        try {
            String photoName = file.getOriginalFilename();
            Path photoPath = Paths.get(DIRECTORYPOST, photoName).toAbsolutePath();
            Files.copy(file.getInputStream(), photoPath, StandardCopyOption.REPLACE_EXISTING);
            return photoName;
        } catch (IOException e) {
            throw new IllegalArgumentException("Error saving image");
        }
    }





}
