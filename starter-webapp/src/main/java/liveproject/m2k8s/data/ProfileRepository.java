package liveproject.m2k8s.data;

import liveproject.m2k8s.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
  Optional<Profile> findByUsername(String username);
}
