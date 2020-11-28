package liveproject.m2k8s.web;

import liveproject.m2k8s.domain.Profile;
import liveproject.m2k8s.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Slf4j
@RequestMapping("/profile")
public class ProfileController {

    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public Profile saveProfile(
            @Valid @RequestBody Profile profile) {

        return profileService.save(profile);
    }

    @GetMapping(value = "/{username}")
    public Profile profile(@PathVariable String username) {
        log.debug("Reading model for: "+username);
        return profileService.getProfile(username);
    }

    @PutMapping(value = "/{username}")
    @Transactional
    public Profile updateProfile(@PathVariable String username, @Valid @RequestBody Profile profile) {
        if (!username.equals(profile.getUsername())) {
            throw new RuntimeException("Cannot change username for Profile");
        }
        log.debug("Updating model for: "+username);
        return profileService.update(profile);
    }

}
