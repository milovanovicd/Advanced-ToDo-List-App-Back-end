package com.todo.application.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.todo.application.domain.User;
import com.todo.application.registration.ConfirmationToken;
import com.todo.application.registration.EmailSenderService;
import com.todo.application.repository.ConfirmationTokenRepository;
import com.todo.application.repository.UserJpaRepository;

@Controller
@CrossOrigin(origins="*")
public class UserAccountController {
	
	@Autowired
    private UserJpaRepository  userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;
    
//    @GetMapping("/register")
//    public ModelAndView displayRegistration(ModelAndView modelAndView, User user)
//    {
//        modelAndView.addObject("user", user);
//        modelAndView.setViewName("register");
//        return modelAndView;
//    }
    
    @PostMapping("/api/users/register")
    public ResponseEntity<Boolean> registerUser(@RequestBody User user)
    {
    	User existingUserUsername = userRepository.findByUsername(user.getUsername());
        User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
        
        if(existingUser != null || existingUserUsername != null)
        {
        	return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
//            modelAndView.addObject("message","This email already exists!");
//            modelAndView.setViewName("error");
        }
        else
        {	
        	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    		String encodedPassword = encoder.encode(user.getPassword());

    		user.setPassword(encodedPassword);
        	
            userRepository.save(user);

            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("todoappinfo@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

//            modelAndView.addObject("emailId", user.getEmail());

//            modelAndView.setViewName("successfulRegisteration");
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        
    }

//    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    @GetMapping("/confirm-account")
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        System.out.println(token);
        
        if(token != null)
        {
        	Calendar cal = Calendar.getInstance();
        	if ((token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
        		
        		confirmationTokenRepository.delete(token);
        		User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
                userRepository.delete(user);
                
        		return new ResponseEntity<>("<h3>Error!!</h3><p>Token has expired!</p>",HttpStatus.BAD_REQUEST);
            } 
        	
        	System.out.println(token.getUser());
        	
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());

            System.out.println(user.getUsername());
            user.setEnabled(true);
            userRepository.save(user);
            return new ResponseEntity<>("<h3>Account sucessfully activated!</h3><p>You can log into your account <a href='http://localhost:4200/login'>here</a></p>", HttpStatus.OK);
//            modelAndView.setViewName("accountVerified");
        }
        else
        {
//            modelAndView.addObject("message","The link is invalid or broken!");
//            modelAndView.setViewName("error");
        	return new ResponseEntity<>(
        			"<h3>Confirmation Error!!</h3><p>Contact your admin!</p>"
        	          , 
        	          HttpStatus.BAD_REQUEST);
        }


    }
    // getters and setters
}
