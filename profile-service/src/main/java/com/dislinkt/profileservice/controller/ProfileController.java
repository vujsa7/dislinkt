package com.dislinkt.profileservice.controller;

import com.dislinkt.profileservice.dto.ProfileBasicInfoDto;
import com.dislinkt.profileservice.dto.SearchedProfileDto;
import com.dislinkt.profileservice.model.Education;
import com.dislinkt.profileservice.model.Position;
import com.dislinkt.profileservice.model.Profile;
import com.dislinkt.profileservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getProfile(@PathVariable String id){
        Optional<Profile> profile = profileService.findById(id);
        if(profile.isPresent()){
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(params = "username")
    public ResponseEntity findProfile(@RequestParam String username){
        Optional<Profile> profile = profileService.findByUsername(username);
        if(profile.isPresent()){
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity findProfiles(@RequestParam String query, @RequestParam(required = false) Integer size, Principal principal){
        List<Profile> profiles;
        if(size != null){
             profiles = profileService.findProfiles(query, size, principal != null).getContent();
        } else{
            profiles = profileService.findProfiles(query, principal != null);
        }

        List<SearchedProfileDto> dtos = profiles.stream()
                .map(p -> new SearchedProfileDto(p.getId(), p.getUsername(), p.getFirstName(), p.getLastName()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createProfileWithBasicInfo(@RequestBody ProfileBasicInfoDto profileDto){
        Profile profile = new Profile(profileDto.getId(), profileDto.getUsername(), profileDto.getFirstName(), profileDto.getLastName(),
                profileDto.getEmail(), profileDto.getPhoneNumber(), profileDto.getGender(), profileDto.getDateOfBirth(), profileDto.getBiography(), profileDto.getProfileType());
        profileService.save(profile);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}/skills")
    public ResponseEntity changeSkills(@PathVariable String id, @RequestBody List<String> skills, Principal principal){
        if(!Objects.equals(principal.getName(), id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Profile> profile = profileService.findById(id);
        if(profile.isPresent()){

            if(!skills.equals(profile.get().getSkills())){
                profile.get().setSkills(skills);
            }

            profileService.saveInfo(profile.get());

            return new ResponseEntity<>(profile, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/{id}/interests")
    public ResponseEntity changeInterests(@PathVariable String id, @RequestBody List<String> interests, Principal principal){
        if(!Objects.equals(principal.getName(), id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Profile> profile = profileService.findById(id);
        if(profile.isPresent()){

            if(!interests.equals(profile.get().getInterests())){
                profile.get().setInterests(interests);
            }

            profileService.saveInfo(profile.get());

            return new ResponseEntity<>(profile, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/{id}/education")
    public ResponseEntity updateEducation(@PathVariable String id, @RequestBody List<Education> education, Principal principal){
        if(!Objects.equals(principal.getName(), id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Profile> profile = profileService.findById(id);
        if(profile.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!education.equals(profile.get().getEducation())){
            profile.get().setEducation(education);
            profileService.saveInfo(profile.get());
        }
        return new ResponseEntity<>(profile, HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{id}/experience")
    public ResponseEntity updateExperience(@PathVariable String id, @RequestBody List<Position> experience, Principal principal){
        if(!Objects.equals(principal.getName(), id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Profile> profile = profileService.findById(id);
        if(profile.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!experience.equals(profile.get().getExperience())){
            profile.get().setExperience(experience);
            profileService.saveInfo(profile.get());
        }
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity updateProfileInfo(@PathVariable String id, @RequestBody ProfileBasicInfoDto profileDto, Principal principal){
        if(!Objects.equals(principal.getName(), id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Profile> profile = profileService.findById(id);
        if(profile.isPresent()){
            boolean isNewUsername = !profile.get().getUsername().equals(profileDto.getUsername());
            Profile updatedProfile = profile.get();
            updatedProfile.setFirstName(profileDto.getFirstName());
            updatedProfile.setLastName(profileDto.getLastName());
            updatedProfile.setEmail(profileDto.getEmail());
            updatedProfile.setPhoneNumber(profileDto.getPhoneNumber());
            updatedProfile.setGender(profileDto.getGender());
            updatedProfile.setDateOfBirth(profileDto.getDateOfBirth());
            updatedProfile.setUsername(profileDto.getUsername());
            updatedProfile.setBiography(profileDto.getBiography());
            updatedProfile.setProfileType(profileDto.getProfileType());

            profileService.update(updatedProfile, isNewUsername);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}