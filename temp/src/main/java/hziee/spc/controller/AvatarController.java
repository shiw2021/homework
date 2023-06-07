package hziee.spc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class AvatarController {

    private static final String UPLOAD_DIR = "temp/src/main/resources/static";

    @PostMapping("body")
    public void body( @RequestParam("avatar") Object avatar){
        avatar.toString();
        return;
    }
    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(
            @RequestParam("avatar") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("上传文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileType;
        Path path = Paths.get(UPLOAD_DIR, newFileName);

        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传头像失败");
        }

        String avatarUrl = String.format("/%s/%s", UPLOAD_DIR, newFileName);
        return ResponseEntity.ok(newFileName);
    }
    @PostMapping("/signature")
    public ResponseEntity<?> uploadSignature(
            @RequestParam("signature") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("上传文件不能为空");
        }

        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileType;
        Path path = Paths.get(UPLOAD_DIR, newFileName);

        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上签名失败");
        }

        String sigUrl = String.format("/%s/%s", UPLOAD_DIR, newFileName);
        return ResponseEntity.ok(newFileName);
    }

}

