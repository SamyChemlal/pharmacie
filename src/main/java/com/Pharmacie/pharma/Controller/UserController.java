package com.Pharmacie.pharma.Controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Pharmacie.pharma.Model.User;
import com.Pharmacie.pharma.Repository.UserRepository;
import com.Pharmacie.pharma.Autre.ConnexionRequest;
import com.Pharmacie.pharma.Autre.CustomUserDetails;
import com.Pharmacie.pharma.Autre.InscriptionRequest;
import com.Pharmacie.pharma.Service.CustomUserDetailsService;
import com.Pharmacie.pharma.Service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            UserService userService,
            BCryptPasswordEncoder bcryptEncoder,
            CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.bcryptEncoder = bcryptEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/inscription")
    public ResponseEntity<Map<String, String>> sInscrire(@RequestBody InscriptionRequest request,
            HttpServletRequest httpRequest) {
        Optional<User> existingClient = userService.existeEmail(request.getEmail());
        if (existingClient.isPresent()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "L'adresse e-mail existe déjà");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setPsw(bcryptEncoder.encode(request.getPsw()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        String userId = userService.inscrire(user);
        Loginn(user.getEmail(), request.getPsw(), httpRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Inscription réussie");
        response.put("userId", userId);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping(value = "/Role/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getRoleById(@PathVariable String userId) {
      

        Optional<User> userOp = userRepository.findById(userId);

        if (userOp.isPresent()) {
            
            String role = userOp.get().getRole();

            Map<String, String> response = new HashMap<>();
            response.put("role", role);

            return ResponseEntity.ok(response);
        } else {
           
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User introuvable");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    private void Loginn(String username, String password, HttpServletRequest request) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            
        }
    }

    @PostMapping("/connexion")
    public ResponseEntity<Map<String, String>> seConnecter(@RequestBody ConnexionRequest request) {
        String userId = getuserIdByEmail(request.getEmail());

        if (verifierIdentifiants(request.getEmail(), request.getpsw()) && userId != null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Connexion réussie");
            response.put("userId", userId);
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Identifiants incorrects. Veuillez réessayer.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    private String getuserIdByEmail(String email) {
        Optional<User> userOp = userService.existeEmail(email);
        return userOp.map(User::getId).orElse(null);
    }



    private boolean verifierIdentifiants(String email, String mdp) {
        Optional<User> userOp = userRepository.findByEmail(email);
        return userOp.isPresent() && bcryptEncoder.matches(mdp, userOp.get().getPsw());
    }

    @PostMapping("/connexion/deconnexion")
    public ResponseEntity<String> seDeconnecter(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        JSONObject json = new JSONObject();
        json.put("message", "Déconnexion réussie");
        return ResponseEntity.ok(json.toString());
    }

   @GetMapping("/connexion/user/details/{email}")
    public ResponseEntity<CustomUserDetails> getuserDetails(@PathVariable String email) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        return ResponseEntity.ok(customUserDetails);
    }
}
