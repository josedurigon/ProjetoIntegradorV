package com.example.demo.Controller;

import com.example.demo.Entities.MRI;
import com.example.demo.Service.MRIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class SendImageController {

   private final MRIService mriService;

   @Autowired
    public SendImageController(MRIService mriService) {
        this.mriService = mriService;
    }

    @GetMapping("/upload")
    public String upload(Model model){
        model.addAttribute("formularioEnvio", new MRI());
        return "upload";
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadFile(@RequestParam("image")MultipartFile file, Model model, @ModelAttribute MRI formularioEnvio){

        model.addAttribute("formularioEnvio",new MRI());

        String nomePaciente = formularioEnvio.getNomePaciente();
        String contatoPaciente = formularioEnvio.getContatoPaciente();
        String descricaoPaciente = formularioEnvio.getDescricaoDiagnostico();

        MRI mriData = new MRI();
        mriData.setNomePaciente(nomePaciente);
        mriData.setContatoPaciente(contatoPaciente);
        mriData.setDescricaoDiagnostico(descricaoPaciente);

        mriService.addImage(mriData);
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file and upload it!!");
        }

        try{
            byte[] bytes = file.getBytes();

            //Agora mandar para flask api
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            MRI mri = new MRI();
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
