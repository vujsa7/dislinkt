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

    @GetMapping(value = "/{username}")
    public ResponseEntity findProfile(@PathVariable String username){
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
}