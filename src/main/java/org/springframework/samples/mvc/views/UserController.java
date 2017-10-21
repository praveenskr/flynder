package org.springframework.samples.mvc.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user/")
public class UserController {

	@Autowired
	private FBUsersService fbUsersService;

	@RequestMapping(value="add", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String add(@RequestBody User user) {
		fbUsersService.save(user);
		return "success";
	}

	@RequestMapping(value="{userId}/get", method=RequestMethod.GET)
	@ResponseBody
	public  User get(@PathVariable("userId") String userId) {

		return fbUsersService.get(userId);
	}





}
