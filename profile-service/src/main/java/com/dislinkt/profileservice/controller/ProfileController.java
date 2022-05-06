package com.dislinkt.profileservice.controller;

import com.dislinkt.profileservice.dto.ProfileBasicInfoDto;
import com.dislinkt.profileservice.dto.SearchedProfileDto;
import com.dislinkt.profileservice.model.Profile;
import com.dislinkt.profileservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
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

//brisi
//    @PutMapping(value = "/{id}")
//    public ResponseEntity changeSkillsAndInterests(@PathVariable String id, @RequestBody Profile newProfileInfo){
//        Optional<Profile> profile = profileService.findById(id);
//        if(profile.isPresent()){
//
//            if(!newProfileInfo.getSkills().equals(profile.get().getSkills())){
//                profile.get().setSkills(newProfileInfo.getSkills());
//            }
//
//            if(!newProfileInfo.getInterests().equals(profile.get().getInterests())){
//                profile.get().setInterests(newProfileInfo.getInterests());
//            }
//
//            profileService.saveInfo(profile.get());
//
//            return new ResponseEntity<>(profile, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PatchMapping(value = "/{id}/skills")
    public ResponseEntity changeSkills(@PathVariable String id, @RequestBody List<String> skills){
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
    public ResponseEntity changeInterests(@PathVariable String id, @RequestBody List<String> interests){
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

    //test
    @GetMapping(value = "/all")
    public List<Profile> getAllProfiles(){

        return profileService.getAllProfiles();
    }
}