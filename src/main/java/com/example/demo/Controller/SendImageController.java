package com.example.demo.Controller;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller("/upload")
public class SendImageController {

    @GetMapping("/upload")
    public String upload(){
        return "upload";
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadFile(@RequestParam("File")MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file and upload it!!");
        }

        try{
            byte[] bytes = file.getBytes();

            //Agora mandar para flask api
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            HttpEntity<byte[]> requestEntity = new HttpEntity<>(bytes, headers);

            String apiUrl = "http://localhost:9090";

            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            if(responseEntity.getStatusCode() == HttpStatus.OK){
                return ResponseEntity.status(HttpStatus.OK).body("Good");
            }
            else{
                return ResponseEntity.badRequest().body("Upload failed, please check communication with api {status 2}");
            }


        }catch (IOException io){
            io.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal error. Something went terribly wrong... Status {1}");

        }
    }

}
