package Team4.egg.AfterClass.service;

import Team4.egg.AfterClass.entity.Member;
import Team4.egg.AfterClass.entity.Role;
import Team4.egg.AfterClass.entity.User;
import Team4.egg.AfterClass.entity.dto.RegisterDTO;
import Team4.egg.AfterClass.repository.UserRepository;
import Team4.egg.AfterClass.utility.ErrorService;
import Team4.egg.AfterClass.utility.StringsMethods;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, StringsMethods {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final MemberService memberService;


    @Transactional
    public void createUser(RegisterDTO dto, Member member) throws ErrorService {
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("There is already a user associated with the email entered");

        dto.setUser_name(transformString(dto.getUser_name()));
        User user= new User();
        user.setUser_name(dto.getUser_name());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));//encripta la contraseña
        user.setMember(member);

        if (userRepository.findAll().isEmpty()) user.setRole(Role.ADMIN);
        else user.setRole(Role.USER); //EL PRIMER USUARIO ES ADMIN

        if(validateUser(user)){
            userRepository.save(user);
        }

    }

    @Transactional
    public void updateUser(RegisterDTO dto){ //validaciones
        User us=userRepository.findById(dto.getIdUser()).get();

        us.setUser_name(dto.getUser_name());
        us.setEmail(dto.getEmail());

        userRepository.save(us);
    }

    @Transactional(readOnly = true)
    public Member getMember(int idUser){
       int idMember= userRepository.findIdMember(idUser);

        return memberService.getById(idMember);
    }


    @Transactional(readOnly = true)
    public void updatePassword(String email,String password){

        User user = userRepository.findByEmail(email).get();

        user.setPassword(encoder.encode(password));

    }

    @Transactional(readOnly = true)
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)//validaciones
    public User getById(int id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public void deleteById(int id){//validaciones
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User getUserByIdMember(int id){
        return getById(userRepository.findIdUser(id));
    }


    @Transactional(readOnly = true)
    public Boolean validateUser(User user) throws ErrorService {
        User us = userRepository.existUser(user.getUser_name());
        if(!user.getUser_name().isEmpty()){
            if(us == null){
                return true;
            } else {
                throw new ErrorService("ERROR: There is already an User registered under that username");
            }
        }else{
            throw new ErrorService("ERROR: Invalid name");
        }
    }





    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {//con que se loguea, username o email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("There is no user associated with the email entered")); //expresion lamda
            GrantedAuthority authority = () -> "ROLE_" + user.getRole().name(); //maneja las authorities

           ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
           HttpSession session = attributes.getRequest().getSession(true);

            session.setAttribute("id", user.getId());
            session.setAttribute("role", user.getRole().name());
            session.setAttribute("member",user.getMember().getId());
            if(user.getRole()==Role.USER) {
                session.setAttribute("university", user.getMember().getUniversity().getId());
            }


        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), singletonList(authority));
// ambiguedad de clase
        //agrega a spring security
    }

}
