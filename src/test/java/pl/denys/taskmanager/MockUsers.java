package pl.denys.taskmanager;

import org.springframework.security.crypto.password.PasswordEncoder;
import pl.denys.taskmanager.model.user.User;

public interface MockUsers {
    User MOCK_USER_1 = User.builder().id(1L).username("mockuser1").password("mockuser1").build();

    static User getMockUser1WithEncodedPassword(PasswordEncoder passwordEncoder){
        var user = MOCK_USER_1;
        user.setPassword(passwordEncoder.encode(MOCK_USER_1.getPassword()));
        return user;
    }
}
