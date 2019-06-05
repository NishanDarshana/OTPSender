package otpsender;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Nishan
 */
public class OTPSender {
    
    public static String genPass(int len) { 
        String cchrs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        String schrs = "abcdefghijklmnopqrstuvwxyz"; 
        String nums = "0123456789"; 
        String vals = cchrs + schrs + nums; 
        char[] pass = new char[len]; 
  
        for (int i = 0; i < len; i++) { 
            pass[i] = vals.charAt(new Random().nextInt(vals.length()));  
        } 
        return new String(pass); 
    }
    
    public static boolean sendMail(String email, String pass) {
        try{
            String host ="smtp.gmail.com" ;
            String user = "otpsendercom@gmail.com";
            String subject = "This is your OTP";
            String body = "OTP is : " + pass;
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(user));
            InternetAddress[] address = {new InternetAddress(email)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setText(body);

            Transport transport=mailSession.getTransport("smtp");
            transport.connect(host, user, "passcomotp");
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
        System.out.println("\n***OTP send successfully***");
        return true;
    }
    
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter your email : ");
        String email=sc.next();
        String otp=genPass(6);
        if(sendMail(email,otp)){
            System.out.print("\nEnter your OTP : ");
            if(sc.next().equals(otp)) System.out.println("\nAccess Granted...");
        }
    }
}