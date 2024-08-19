package pl.denys.taskmanager;

import pl.denys.taskmanager.model.user.User;

public interface MockUsers {
  User MOCK_USER_1 = User.builder().id(1L).username("mockuser1").password("mockuser1").build();

  static User getMockUser1WithPassword(String password) {
    return new User(
        MOCK_USER_1.getId(), MOCK_USER_1.getUsername(), password, MOCK_USER_1.getRoleSet());
  }
}
