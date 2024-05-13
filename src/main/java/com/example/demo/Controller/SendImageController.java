package com.example.demo.Controller;

import com.example.demo.Entities.MRI;
import com.example.demo.Entities.Paciente;
import com.example.demo.Service.MRIService;
import com.example.demo.Service.PacienteService;
import com.example.demo.repository.PacienteRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class SendImageController {

   private final MRIService mriService;
   private final PacienteService pacienteService;


   @Autowired
    public SendImageController(MRIService mriService, PacienteService pacienteService) {
        this.mriService = mriService;
        this.pacienteService = pacienteService;
    }

    @GetMapping("/upload")
    public String upload(Model model){
        model.addAttribute("formularioEnvio", new Paciente());
        return "upload";
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadFile(@RequestParam("image")MultipartFile file, Model model, @ModelAttribute Paciente formularioEnvio){

        MRI mri = new MRI();

        model.addAttribute("formularioEnvio",new Paciente());

        String nomePaciente = formularioEnvio.getNomePaciente();
        String contatoPaciente = formularioEnvio.getContatoPaciente();
        String descricaoPaciente = formularioEnvio.getDescricaoDiagnostico();

        Paciente paciente = new Paciente();
        paciente.setNomePaciente(nomePaciente);
        paciente.setContatoPaciente(contatoPaciente);
        paciente.setDescricaoDiagnostico(descricaoPaciente);
        paciente.setMri(mri);

//        mri.setPacienteId(paciente.getIdPaciente());

        mriService.addImage(mri);
        pacienteService.addPaciente(paciente);

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


            //MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
//            requestEntity.add("image_bytes", bytes);

            String apiUrl = "http://127.0.0.1:8000/segmentation/";
//            String apiUrl = "http://localhost:8080/segmentation/";
            ResponseEntity<byte[]> response = restTemplate.postForEntity(apiUrl, requestEntity, byte[].class);



            String filePathToSave = "src/main/resources/static/images/";

            //TODO converter o bytearray do response para Jpeg (isso que sera exibido pro usuário)

            if(response.getStatusCode() == HttpStatus.OK){
                byte[] imageBytes = response.getBody();
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                String fileName = "uploaded_image.jpg"; // You can use any name you want
                File imageFile = new File(filePathToSave + fileName);
                ImageIO.write(image, "jpg", imageFile);

                return ResponseEntity.status(HttpStatus.OK).body("Good");
            }

            else{
                return ResponseEntity.badRequest().body("Upload failed, please check communication with api {status 2}");
            }

//            return ResponseEntity.status(HttpStatus.OK).body("ok");

        }catch (IOException io){
            io.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal error. Something went terribly wrong... Status {1}");

            }
        }

}
