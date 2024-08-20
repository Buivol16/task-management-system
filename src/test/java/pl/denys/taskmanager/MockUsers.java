package pl.denys.taskmanager;

import pl.denys.taskmanager.model.user.User;

import static pl.denys.taskmanager.enums.Role.USER;

public interface MockUsers {
  User MOCK_USER_1 =
      User.builder().id(1L).username("mockuser1").password("mockuser1").role(USER).build();

  static User getMockUser1WithPassword(String password) {
    return new User(
        MOCK_USER_1.getId(), MOCK_USER_1.getUsername(), password, MOCK_USER_1.getRole());
  }
}
