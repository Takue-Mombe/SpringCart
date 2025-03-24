package com.continuo.ecommerce.Services;


import com.continuo.ecommerce.DTO.UpdateProfileRequest;
import com.continuo.ecommerce.Repository.UserRepository;
import com.continuo.ecommerce.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserProfile(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    public User updateUserProfile(String email, UpdateProfileRequest updateProfileRequest) {

        User user = userRepository.findByEmail(email)
                        .orElseThrow(()-> new RuntimeException("User not found"));


        user.setUsername(updateProfileRequest.getUsername());
        user.setFirstName(updateProfileRequest.getFirstName());
        user.setLastName(updateProfileRequest.getLastName());

        return userRepository.save(user);
    }

    public void changePassword(String email, String oldPassword ,String newPassword) {

        User user = getUserProfile(email);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Wrong password, please try again");
        }else {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    private static final String UPLOAD_DIRECTORY = "uploads/";

    public String uploadProfileImage(String email,MultipartFile file) {

        User user = getUserProfile(email);
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIRECTORY + file.getOriginalFilename() + "/" + user.getUsername());
            Files.write(path,bytes);
            user.setProfilePicture(path.toString());
            userRepository.save(user);
            return "Profile image uploaded";

        }catch (IOException e){
            throw new RuntimeException("Failed To Upload file", e);
        }
    }

    public void deleteUserProfile(String email) {
        User user = getUserProfile(email);
        userRepository.delete(user);
    }


}
