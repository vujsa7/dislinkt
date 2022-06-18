package com.dislinkt.profileservice.service.impl;

import com.dislinkt.profileservice.dao.ProfileRepository;
import com.dislinkt.profileservice.exception.ApiRequestException;
import com.dislinkt.profileservice.exception.StorageException;
import com.dislinkt.profileservice.exception.StorageFileNotFoundException;
import com.dislinkt.profileservice.model.Profile;
import com.dislinkt.profileservice.model.StorageProperties;
import com.dislinkt.profileservice.service.StorageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {

    private final ProfileRepository profileRepository;
    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl(ProfileRepository profileRepository, StorageProperties properties) {
        this.profileRepository = profileRepository;
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public Mono<Path> uploadProfileImage(String id, Mono<FilePart> file) {
        String imageName = generateUniqueImageName(file);
        Mono<Path> path = uploadImageToFileSystem(file, imageName);
        Optional<Profile> profileOptional = profileRepository.findById(id);
        if(profileOptional.isEmpty())
            throw new ApiRequestException("User not found!");
        Profile profile = profileOptional.get();
        profile.setImage(imageName);
        profileRepository.save(profile);
        return path;
    }

    @Override
    public String getProfileImage(String id) {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        if(profileOptional.isEmpty())
            throw new ApiRequestException("User not found!");
        Profile profile = profileOptional.get();
        return profile.getImage();
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    private Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    private String generateUniqueImageName(Mono<FilePart> file){
        return RandomStringUtils.random(15, true, true) + ".jpg";
    }

    private Mono<Path> uploadImageToFileSystem(Mono<FilePart> file, String imageName) throws StorageException {
        try {
            if (file == null) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationPath = this.rootLocation.resolve(Paths.get(imageName)).normalize().toAbsolutePath();
            if (!destinationPath.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            return file
            .doOnNext(fp -> System.out.println("Received File : " + fp.filename()))
            .flatMap(fp -> fp.transferTo(destinationPath)).thenReturn(destinationPath);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new StorageException("Failed to store file.", e);
        }
    }
}
