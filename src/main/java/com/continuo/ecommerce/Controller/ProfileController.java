package com.continuo.ecommerce.Controller;


import com.continuo.ecommerce.Services.ProfileService;
import com.continuo.ecommerce.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getProfile(@PathVariable String email) {

        return ResponseEntity.ok(profileService.getUserProfile(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateProfile(@PathVariable String email,
                                              @RequestParam String  username,
                                              @RequestParam String firstName,
                                              @RequestParam String lastName) {
        return ResponseEntity.ok(profileService.updateUserProfile(email, username, firstName, lastName));
    }

    @PutMapping("/{email}/changer-password")
    public ResponseEntity<String>changePassword(
            @PathVariable String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ){
        profileService.changePassword(email, oldPassword, newPassword);
        return ResponseEntity.ok("Password changed Successfully!");
    }

    @PostMapping("/{email}/upload-picture")
    public ResponseEntity<String> uploadPicture(
            @PathVariable String email,
            @RequestParam("file") MultipartFile file)
    {
        return ResponseEntity.ok(profileService.uploadProfileImage(email, file));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteProfile(@PathVariable String email) {
        profileService.deleteUserProfile(email);
        return ResponseEntity.ok("Profile Deleted Successfully!");
    }
}


