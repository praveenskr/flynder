package org.springframework.samples.mvc.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("event/")
public class RemoveDataController {
	@Autowired
	FBUsersDao fbUsersDao;
	
	public void deleteapp(){
		
		fbUsersDao.deleteApp();
		

	}
	
}