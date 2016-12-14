package org.launchcode.blogz.controllers;



import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class PostController extends AbstractController {
	
	
	

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		String error = request.getParameter("error");
		
		
		User author = getUserFromSession(request.getSession());

		
		if(title == null || title.equals("") || body == null || body.equals("")){
			
			model.addAttribute("title", title);
			model.addAttribute("body", body);
			
			error = "Must have title and post";
			model.addAttribute("error", error);
			return "newpost";
			
		} else {
			
			Post post = new Post(title, body, author);
			postDao.save(post);
			
			String username = author.getUsername();
			int uid = post.getUid();
			
			model.addAttribute("title", title);
			model.addAttribute("body", body);
			model.addAttribute("username", username);
			model.addAttribute("uid", uid);
			
			String url = username + "/" + uid;
		
			return "redirect:/blog/" + url; // TODO - this redirect should go to the new post's page 
		}
	}
	
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(HttpServletRequest request, @PathVariable String username,
			                         @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		Post post = postDao.findByUid(uid);
		
		User user = userDao.findByUsername(username);
		
		List<Post> userPosts = user.getPosts();
		
		
		if(userPosts.contains(post)){
			
			model.addAttribute("post", post);
			
			if(user.equals(getUserFromSession(request.getSession()))){
				
				return "edit";
				
			}
		
			return "post";
			
		} else {
		
			String error = "There is no post with ID# " + uid + " for username " + username;
			model.addAttribute("error", error);
			
			return "err";
		
		}
	}
	
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.POST)
	public String editPost(HttpServletRequest request, @PathVariable String username, 
			                            @PathVariable int uid, Model model) {
		
		Post post = postDao.findByUid(uid);
		String title = post.getTitle();
		String body = post.getBody();
		User user = userDao.findByUsername(username);
		
		
		if(user.equals(getUserFromSession(request.getSession()))){
			
			model.addAttribute("post", post);
			String newBody = request.getParameter("body").trim();
			String newTitle = request.getParameter("title").trim();
			String button = request.getParameter("button");
			
			if(button.equals("delete")){
				//deleteGui Gui = new deleteGui();
				
				postDao.delete(post);
				return "redirect:/blog";
			}
			
			if(!title.equals(newTitle) || !body.equals(newBody)){
				
				post.setBody(newBody);
				post.setTitle(newTitle);
				
				if(newTitle == null || newTitle.equals("") || newBody == null || newBody.equals("")){
					
					String error = "Must have title and post";
					model.addAttribute("error", error);
					
				} else {
					
				    if(button.equals("save")){
				    	post.setModified(new Date());	
				    	postDao.save(post);
				    }
				}
			}
		}
		
		return "edit";
		
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		User user = userDao.findByUsername(username);
		
		List<User> users = userDao.findAll();
		
		if(!users.contains(user)){
			
			String error = "Username " + username + " does not exist";
			model.addAttribute("error", error);
			
			return "err";
			
		} else {
			
			List<Post> posts = user.getPosts();	
			String author = user.getUsername();
		
			model.addAttribute("posts", posts);
			model.addAttribute("author", author);
		
			return "blog";
		
		}
	
	}
	
}

