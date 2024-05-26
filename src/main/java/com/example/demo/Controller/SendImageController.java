package com.example.demo.Controller;

import com.example.demo.Entities.MRI;
import com.example.demo.Entities.Paciente;
import com.example.demo.Entities.User;
import com.example.demo.Service.CustomUserDetailsService;
import com.example.demo.Service.MRIService;
import com.example.demo.Service.PacienteService;
import com.example.demo.Service.UserService;
import com.example.demo.repository.PacienteRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

@Controller
public class SendImageController {

   private final MRIService mriService;
   private final PacienteService pacienteService;
   private final UserService userService;

   private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


   @Autowired
    public SendImageController(MRIService mriService, PacienteService pacienteService, UserService userService) {
        this.mriService = mriService;
        this.pacienteService = pacienteService;
       this.userService = userService;
   }

   @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;

    @GetMapping("/upload")
    public String upload(Model model){
        model.addAttribute("formularioEnvio", new Paciente());
        return "upload";
    }

    @PostMapping("/uploadImage")
    public RedirectView uploadFile(@RequestParam("uploadImage")MultipartFile file, Model model, @ModelAttribute Paciente formularioEnvio){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();

        logger.info("Sessao: {}", currentUsername);

        User user = userService.findByUsername(currentUsername);


        ArrayList<String> list = new ArrayList<>();


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

        mri.setUser(user);


        logger.info("Informações de mri: idMri{}, idUser{}, name user{}", mri.idMri, mri.user.getIdUser(), mri.user.getName());



//        mri.setPacienteId(paciente.getIdPaciente());

        mriService.addImage(mri);
        pacienteService.addPaciente(paciente);

        if(file.isEmpty()){
            return null;
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file and upload it!!");
        }

        try{
            byte[] bytes = file.getBytes();

            //Agora mandar para flask api
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            HttpEntity<byte[]> requestEntity = new HttpEntity<>(bytes, headers);




            String apiUrl = "http://127.0.0.1:8000/segmentation/";
            ResponseEntity<byte[]> response = restTemplate.postForEntity(apiUrl, requestEntity, byte[].class);



            String filePathToSave = "src/main/resources/static/images/";

            if(response.getStatusCode() == HttpStatus.OK){
                byte[] imageBytes = response.getBody();
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                String fileName = "uploaded_image.jpg"; // You can use any name you want
                File imageFile = new File(filePathToSave + fileName);

                if(Files.exists(imageFile.toPath())){
                    Files.delete(imageFile.toPath());
                }

                    ImageIO.write(image, "jpg", imageFile);

                String url = "/images/" + fileName;
                model.addAttribute("imageUrl", url);

//                return ResponseEntity.status(HttpStatus.OK).body(url);
                return new RedirectView("/retornoUser");
            }


            else{
                return null;

//                return ResponseEntity.badRequest().body("Upload failed, please check communication with api {status 2}");
            }

//            return ResponseEntity.status(HttpStatus.OK).body("ok");

        }catch (IOException io){
            io.printStackTrace();
            return null;

//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal error. Something went terribly wrong... Status {1}");

            }
        }

}
