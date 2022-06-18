package com.dislinkt.profileservice.service;


import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

public interface StorageService {

    Mono<Path> uploadProfileImage(String id, Mono<FilePart> file);

    String getProfileImage(String id);

    Resource loadAsResource(String filename);
}
