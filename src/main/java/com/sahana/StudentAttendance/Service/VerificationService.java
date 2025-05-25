package com.sahana.StudentAttendance.Service;


import com.sahana.StudentAttendance.Model.Recgonition.FaceResponse;
import com.sahana.StudentAttendance.Util.MultipartInputStreamFileResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.UUID;

@Service

public class VerificationService {

    @Value("${verification.api.url}")
    private String externalApiUrl;

    @Value("${verification.api.key}")
    private String apiKey;

    private  final RestTemplate restTemplate=new RestTemplate();


    // Download the image from the given URL
    public File downloadImageFromUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        String fileName = UUID.randomUUID().toString() + ".jpg"; // Ensure you have a proper file extension
        File tempFile = File.createTempFile(fileName, ".jpg");

        try (InputStream in = url.openStream(); OutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }

    // Convert the file to MultipartFile
    private MultipartFile convertFileToMultipartFile(File file) throws IOException {
        return new MultipartInputStreamFileResource(new FileInputStream(file), file.getName());
    }

    public FaceResponse faceResponse(MultipartFile file1, MultipartFile file2)throws IOException {
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.set("x-api-key",apiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("source_image", new MultipartInputStreamFileResource(file1.getInputStream(), file1.getOriginalFilename()));
        body.add("target_image", new MultipartInputStreamFileResource(file2.getInputStream(), file2.getOriginalFilename()));
        String url = externalApiUrl;
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<FaceResponse> response =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, FaceResponse.class);

        return response.getBody();

    }
    public FaceResponse faceResponseFromUrl(String url) throws IOException {
        String imageUrl = url;

        // If it's an Instagram profile URL, extract the DP image URL


        File downloadedImage = downloadImageFromUrl(imageUrl);
        MultipartFile multipartFile = convertFileToMultipartFile(downloadedImage);
        return faceResponse(multipartFile,multipartFile);
    }
}
