package net.jfuentestgn.tutorials.pagedresponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public PagedResponse<UserDTO,User> listUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<User> users = this.userRepository.findAll(pageRequest);

        return new PagedResponse<>(users, this::mapUserToDto);
    }


    private UserDTO mapUserToDto(User u) {
        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setName(u.getFirstName() + " " + u.getLastName());
        dto.setEmail(u.getEmail());
        dto.setGender(String.valueOf(u.getGender().charAt(0)));
        return dto;
    }
}
