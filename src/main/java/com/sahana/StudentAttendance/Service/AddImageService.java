package com.sahana.StudentAttendance.Service;



import com.sahana.StudentAttendance.Model.AddImage;
import com.sahana.StudentAttendance.Util.MultipartInputStreamFileResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AddImageService {



    @Value("${add.api.url}")
    private String externalApiUrl;

    @Value("${recognition.api.key}")
    private String apiKey;

    private  final RestTemplate restTemplate=new RestTemplate();

    public AddImage addImage(String subject, MultipartFile file)throws IOException{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("x-api-key", apiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

        String url = externalApiUrl + "?subject=" + subject;

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<AddImage> response =
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, AddImage.class);

        return response.getBody();
    }
}
