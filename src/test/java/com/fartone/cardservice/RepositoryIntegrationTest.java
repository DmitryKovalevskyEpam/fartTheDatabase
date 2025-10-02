// package com.example.usercardapp.repository;
package com.fartone.cardservice;

// import com.fartone.cardservice.entity.CardInfo;
// import com.fartone.cardservice.entity.User;
// import com.fartone.cardservice.dao.UserRepository;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
// import org.springframework.test.context.DynamicPropertyRegistry;
// import org.springframework.test.context.DynamicPropertySource;
// import org.testcontainers.containers.PostgreSQLContainer;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;

// import java.time.LocalDate;
// import java.util.List;
// import java.util.Optional;

// import static org.assertj.core.api.Assertions.assertThat;

// @Testcontainers
// @DataJpaTest
// class UserRepositoryIntegrationTest {

//     @Container
//     public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.5");

//     @DynamicPropertySource
//     static void postgresqlProperties(DynamicPropertyRegistry registry) {
//         registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//         registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//         registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//     }

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private TestEntityManager entityManager;

//     @Test
//     void whenFindByEmail_thenReturnUser() {
//         // given
//         User user = new User();
//         user.setName("Test");
//         user.setSurname("User");
//         user.setEmail("test@example.com");
//         user.setBirthDate(LocalDate.of(2000, 1, 1));
//         entityManager.persist(user);
//         entityManager.flush();

//         // when
//         Optional<User> found = userRepository.findByEmail("test@example.com");

//         // then
//         assertThat(found).isPresent();
//         assertThat(found.get().getEmail()).isEqualTo(user.getEmail());
//     }

//     @Test
//     void whenCascadeSave_thenCardsAreSaved() {
//         User user = new User();
//         user.setName("Cascade");
//         user.setSurname("Test");
//         user.setEmail("cascade@example.com");
//         user.setBirthDate(LocalDate.of(1995, 10, 20));

//         CardInfo card1 = new CardInfo();
//         card1.setNumber("1111-2222-3333-4444");
//         card1.setHolder("Cascade Test");
//         card1.setExpirationDate(LocalDate.now().plusYears(2));

//         user.addCardInfo(card1);

//         userRepository.save(user);
//         entityManager.flush();
//         entityManager.clear();

//         User foundUser = userRepository.findById(user.getId()).orElseThrow();
//         assertThat(foundUser.getCardInfos()).hasSize(1);
//         assertThat(foundUser.getCardInfos().get(0).getNumber()).isEqualTo("1111-2222-3333-4444");
//     }

//     @Test
//     void testFindUsersBornAfter() {
//         // given
//         User user1 = new User();
//         user1.setBirthDate(LocalDate.of(1990, 1, 1));
//         user1.setName("Old");
//         user1.setSurname("Man");
//         user1.setEmail("old@example.com");
//         entityManager.persist(user1);

//         User user2 = new User();
//         user2.setBirthDate(LocalDate.of(2005, 1, 1));
//         user2.setName("Young");
//         user2.setSurname("Blood");
//         user2.setEmail("young@example.com");
//         entityManager.persist(user2);
//         entityManager.flush();

//         // when
//         List<User> users = userRepository.findUsersBornAfter(LocalDate.of(2000, 1, 1));

//         // then
//         assertThat(users).hasSize(1).extracting(User::getName).contains("Young");
//     }
// }
