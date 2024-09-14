package com.project.postly.repos;

import org.springframework.data.jpa.repository.JpaRepository;


import com.project.postly.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Profile findByProfileName(String userName);

}
