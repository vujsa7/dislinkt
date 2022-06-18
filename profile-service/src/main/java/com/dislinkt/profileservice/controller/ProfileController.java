package com.dislinkt.profileservice.controller;

import com.dislinkt.profileservice.dto.ImageDto;
import com.dislinkt.profileservice.dto.ProfileBasicInfoDto;
import com.dislinkt.profileservice.dto.SearchedProfileDto;
import com.dislinkt.profileservice.exception.ApiRequestException;
import com.dislinkt.profileservice.model.Education;
import com.dislinkt.profileservice.model.Position;
import com.dislinkt.profileservice.model.Profile;
import com.dislinkt.profileservice.service.ProfileService;
import com.dislinkt.profileservice.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/profiles")
@Slf4j
public class ProfileController {
    private final ProfileService profileService;
    private final StorageService storageService;

    @Autowired
    public ProfileController(ProfileService profileService, StorageService storageService) {
        this.profileService = profileService;
        this.storageService = storageService;
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

    @PatchMapping(value = "/skills")
    public ResponseEntity changeSkills(@RequestBody List<String> skills, Principal principal, ServerHttpRequest request){

        Optional<Profile> profile = profileService.findById(principal.getName());
        if(profile.isPresent()){

            if(!skills.equals(profile.get().getSkills())){
                profile.get().setSkills(skills);
            }

            profileService.saveInfo(profile.get());

            log.info("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "200 Ok for HTTP PATCH \"/profiles/skills\"");
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/interests")
    public ResponseEntity changeInterests(@RequestBody List<String> interests, Principal principal, ServerHttpRequest request){

        Optional<Profile> profile = profileService.findById(principal.getName());
        if(profile.isPresent()){

            if(!interests.equals(profile.get().getInterests())){
                profile.get().setInterests(interests);
            }

            profileService.saveInfo(profile.get());

            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
        log.info("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "200 Ok for HTTP PATCH \"/profiles/interests\"");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/education")
    public ResponseEntity updateEducation(@RequestBody List<Education> education, Principal principal, ServerHttpRequest request){

        Optional<Profile> profile = profileService.findById(principal.getName());
        if(profile.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!education.equals(profile.get().getEducation())){
            profile.get().setEducation(education);
            profileService.saveInfo(profile.get());
        }

        log.info("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "200 Ok for HTTP PATCH \"/profiles/education\"");
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PatchMapping(value = "/experiences")
    public ResponseEntity updateExperience(@RequestBody List<Position> experience, Principal principal, ServerHttpRequest request){

        Optional<Profile> profile = profileService.findById(principal.getName());
        if(profile.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!experience.equals(profile.get().getExperience())){
            profile.get().setExperience(experience);
            profileService.saveInfo(profile.get());
        }

        log.info("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "200 Ok for HTTP PATCH \"/profiles/experiences\"");
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity updateProfileInfo(@PathVariable String id, @RequestBody ProfileBasicInfoDto profileDto, Principal principal, ServerHttpRequest request){
        if(!Objects.equals(principal.getName(), id)){
            log.warn("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "401 Unauthorized for HTTP PATCH \"/profiles/" + id + "\"");
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

            log.info("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "200 Ok for HTTP PATCH \"/profiles/" + id + "\"");
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Path> uploadProfileImage(Principal principal, @RequestPart("file") Mono<FilePart> file){
        return storageService.uploadProfileImage(principal.getName(), file);
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity getProfileImage(@PathVariable String id){
        String url = storageService.getProfileImage(id);
        if(url == null)
            return new ResponseEntity(new ImageDto(""), HttpStatus.OK);
        return new ResponseEntity(new ImageDto("https://localhost:9090/profile-service/storage/" + storageService.getProfileImage(id)), HttpStatus.OK);
    }

}