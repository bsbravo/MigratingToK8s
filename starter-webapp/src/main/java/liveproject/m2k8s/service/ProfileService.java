package liveproject.m2k8s.service;

import liveproject.m2k8s.domain.Profile;
import liveproject.m2k8s.data.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile getProfile(String username) {
        return profileRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found"));
    }

    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile update(Profile profile) {
        Profile dbProfile = profileRepository.findByUsername(profile.getUsername()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found"));
        boolean dirty = false;
        if (!StringUtils.isEmpty(profile.getEmail())
                && !profile.getEmail().equals(dbProfile.getEmail())) {
            dbProfile.setEmail(profile.getEmail());
            dirty = true;
        }
        if (!StringUtils.isEmpty(profile.getFirstName())
                && !profile.getFirstName().equals(dbProfile.getFirstName())) {
            dbProfile.setFirstName(profile.getFirstName());
            dirty = true;
        }
        if (!StringUtils.isEmpty(profile.getLastName())
                && !profile.getLastName().equals(dbProfile.getLastName())) {
            dbProfile.setLastName(profile.getLastName());
            dirty = true;
        }
        if (dirty) {
            return profileRepository.save(profile);
        }
        return dbProfile;
    }
}
