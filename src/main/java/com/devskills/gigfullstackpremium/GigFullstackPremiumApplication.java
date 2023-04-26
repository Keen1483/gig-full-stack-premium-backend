package com.devskills.gigfullstackpremium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.devskills.gigfullstackpremium.service.UserService;
import com.devskills.gigfullstackpremium.service.CommentService;
import com.devskills.gigfullstackpremium.service.PostService;
import com.devskills.gigfullstackpremium.service.RoleService;
import com.devskills.gigfullstackpremium.domain.User;
import com.devskills.gigfullstackpremium.domain.Comment;
import com.devskills.gigfullstackpremium.domain.Post;
import com.devskills.gigfullstackpremium.domain.Role;
import com.github.javafaker.Faker;
import java.util.HashSet;
import java.util.List; //*/

@SpringBootApplication
public class GigFullstackPremiumApplication {

	public static void main(String[] args) {
		SpringApplication.run(GigFullstackPremiumApplication.class, args);
	}
	/*
	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService, PostService postService, CommentService commentService) {
		return args -> {
			
			roleService.saveRole(new Role(null, "USER", null, null, null));
			roleService.saveRole(new Role(null, "ADMIN", null,  null, null));
			roleService.saveRole(new Role(null, "MANAGER", null, null, null));
			roleService.saveRole(new Role(null, "CUSTOMER", null, null, null));
			roleService.saveRole(new Role(null, "MARKETER", null, null, null));
			roleService.saveRole(new Role(null, "SELLER", null, null, null));
			
			List<Role> roles = roleService.getRoles();
			
			Faker faker = new Faker();
			
			// SAVE USERS
			for (int i = 0; i < 25; i++) {
				HashSet<Role> singleRole = new HashSet<>();
				singleRole.add(roles.get((int) Math.floor(Math.random() * roles.size())));
				userService.saveUser(new User(
						null,
						faker.internet().emailAddress(),
						faker.name().fullName(),
						faker.name().username(),
						"1234",
						singleRole,
						null,
						null,
						null,
						null
					)
				);
			}
			
			// SAVE POSTS
			List<User> users = userService.getUsers();
			users.forEach(user -> {
				for (int i = 0; i < faker.number().numberBetween(1, 6); i++) {
					Post post = new Post(null,
						faker.lorem().sentence(),
						faker.lorem().paragraph(faker.number().numberBetween(10, 22)),
						"https://dummyimage.com/800x4:2/09f/fff.png",
						user,
						null,
						null,
						null
					);
					postService.savePost(post);
				}
			});
			
			// SAVE COMMENTS
			List<Post> posts = postService.getPosts();
			posts.forEach(post -> {
				for (int i = 0; i < faker.number().numberBetween(1, 10); i++) {
					Comment comment = new Comment(null,
						faker.lorem().paragraph(faker.number().numberBetween(1, 4)),
						post,
						users.get((int) Math.floor(Math.random() * users.size())),
						null,
						null
					);
					commentService.saveComment(comment);
				}
			});
			
		};
	} //*/

}
