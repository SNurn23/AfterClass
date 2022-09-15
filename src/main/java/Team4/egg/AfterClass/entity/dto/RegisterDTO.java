package Team4.egg.AfterClass.entity.dto;

import Team4.egg.AfterClass.entity.Career;
import Team4.egg.AfterClass.entity.University;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RegisterDTO {

  private int idMember;
  private int idUser;
  private String user_name;
  private String email;
  private String password;
  private String full_name;
  private char gender;
  private String description;
  private String occupation;
  private String profile_img;
  private String cover_img;
  private University university;
  private Career career;
}