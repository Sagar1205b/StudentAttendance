package com.sahana.StudentAttendance.Service;


import com.sahana.StudentAttendance.Model.Recgonition.FaceResponse;
import com.sahana.StudentAttendance.Util.MultipartInputStreamFileResource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class FaceResponseService {

    @Value("${face.recognition.url}")
    private String externalApiUrl;

    @Value("${recognition.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

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

    // Face recognition API call using a MultipartFile
    public FaceResponse faceResponse(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("The file is empty!");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        httpHeaders.set("x-api-key", apiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<FaceResponse> response = restTemplate.exchange(externalApiUrl, HttpMethod.POST, requestEntity, FaceResponse.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to process face recognition: " + response.getStatusCode());
        }

        return response.getBody();
    }


    public String extractDpUrlFromInstagram(String profileUrl) throws IOException {
        Document doc = Jsoup.connect(profileUrl).get();
        Element metaOgImage = doc.selectFirst("meta[property=og:image]");
        if (metaOgImage != null) {
            return metaOgImage.attr("content");
        }
        throw new IOException("Could not find profile picture URL from profile");
    }
    // Face recognition API call using an image URL
    public FaceResponse faceResponseFromUrl(String url) throws IOException {
        String imageUrl = url;

        // If it's an Instagram profile URL, extract the DP image URL


        File downloadedImage = downloadImageFromUrl(imageUrl);
        MultipartFile multipartFile = convertFileToMultipartFile(downloadedImage);
        return faceResponse(multipartFile);
    }
}
