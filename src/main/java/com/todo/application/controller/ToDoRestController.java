package com.todo.application.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo.application.domain.Priority;
import com.todo.application.domain.Process;
import com.todo.application.domain.Status;
import com.todo.application.domain.ToDo;
import com.todo.application.domain.Type;
import com.todo.application.domain.User;
import com.todo.application.repository.ProcessJpaRepository;
import com.todo.application.repository.ToDoJpaRepository;
import com.todo.application.repository.UserJpaRepository;


@RestController
@CrossOrigin(origins="*")
public class ToDoRestController {

	@Autowired
	private ToDoJpaRepository toDoJpaRepository;
	
	@Autowired
	private ProcessJpaRepository processJpaRepository;
	
	@Autowired
	private UserJpaRepository userJpaRepository;
	
	
	@GetMapping("/api/processes")
	public List<Process> getAllProcesses() {
		return processJpaRepository.findAll();
	}
	
	@GetMapping("/api/{username}/processes")
	public List<Process> getAllProcessesByUsername(@PathVariable String username) {
		return processJpaRepository.findByUserUsername(username);
	}
	
	@GetMapping("/api/processes/{id}")
	public Process getProcess(@PathVariable Long id) {
		return processJpaRepository.findById(id).get();
	}
	
	@PostMapping("/api/processes/create")
//	public ResponseEntity<Process> createProcess(@RequestParam String username, @RequestParam String name, @RequestParam int priority ) {
	public ResponseEntity<Process> createProcess(@RequestBody Map<String,Object> body) {
		
		User user = new User();
		
		user.setUsername(body.get("username").toString());
		
		Process newProcess = new Process();
		
		newProcess.setUser(user);
		newProcess.setName(body.get("name").toString());
		newProcess.setToDoList(new ArrayList<ToDo>());
		newProcess.setPriority(Priority.values()[Integer.parseInt(body.get("priority").toString())]);

		Process createdProcess = processJpaRepository.save(newProcess);	


		return new ResponseEntity<Process>(createdProcess, HttpStatus.OK);
	}
	
	 
	@PostMapping("/api/processes/update")
	public ResponseEntity<Process> updateProcess(@RequestBody Process process) {

		Process updatedProcess = processJpaRepository.save(process);

		return new ResponseEntity<Process>(updatedProcess, HttpStatus.OK);
	}
	 
	@DeleteMapping("/api/processes/delete/{id}")
	public ResponseEntity<Void> deleteProcess(@PathVariable long id) {
		
		List<ToDo> todosDelete = this.getAllTodosByProcess(id);
		for (ToDo toDo : todosDelete) {
			toDo.setProcess(null);
			toDo.setStatus(Status.DELETED);
			toDoJpaRepository.save(toDo);
		}
		
		processJpaRepository.deleteById(id);

		return ResponseEntity.noContent().build();

	}
	
	@GetMapping("/api/todos")
	public List<ToDo> getAllTodos() {
		return toDoJpaRepository.findAll();
	}
	
	@GetMapping("/api/processes/{processId}/todos")
	public List<ToDo> getAllTodosByProcess(@PathVariable long processId) {
		return toDoJpaRepository.findByProcessProcessId(processId);
	}
	 

	@GetMapping("/api/processes/todos/{id}")
	public ToDo getTodo(@PathVariable long id) {
		return toDoJpaRepository.findById(id).get();
	}
	 

	@DeleteMapping("/api/processes/todos/delete/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable long id) {
		toDoJpaRepository.deleteById(id);

		return ResponseEntity.noContent().build();

//		return ResponseEntity.notFound().build();
	}
	
	

	@PostMapping("/api/processes/todos")
//	public ResponseEntity<ToDo> createTodo(@RequestBody String username, @RequestBody ToDo todo) {
	public ResponseEntity<ToDo> createTodo(@RequestBody Map<String,Object> body) {
		
		User user = new User();
		user.setUsername(body.get("username").toString());
		System.out.println("User: "+ user.getUsername());
		
		ToDo todo = new ToDo();
		
		todo.setUsername(user);
		
		todo.setDescription(body.get("description").toString());
		System.out.println("Description: "+ todo.getDescription());
		
		String dateString = body.get("targetDate").toString();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(dateString);
			todo.setTargetDate(date);
			System.out.println("Date: "+ date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		todo.setPriority(Priority.values()[Integer.parseInt(body.get("priority").toString())]);
		System.out.println("Priority: "+ todo.getPriority());
		
		todo.setType(Type.values()[Integer.parseInt(body.get("type").toString())]);
		System.out.println("Type: "+ todo.getType());
		
		Process p = new  Process();
		p.setProcessId(Long.parseLong(body.get("processId").toString()));
		System.out.println("Process: "+ p.getProcessId());
		
		todo.setProcess(p);
		
		ToDo createdTodo = toDoJpaRepository.save(todo);

		return new ResponseEntity<ToDo>(createdTodo, HttpStatus.OK);
	}
	 
	@PostMapping("/api/processes/todos/update")
	public ResponseEntity<ToDo> updateTodo(@RequestBody ToDo todo) {

		ToDo updatedTodo = toDoJpaRepository.save(todo);

		return new ResponseEntity<ToDo>(updatedTodo, HttpStatus.OK);
	}
	
//	@PostMapping("/api/users/signup")
//	public boolean userSignup(@RequestBody Map<String,Object> body) {
//
//		String username = body.get("username").toString();
//		String password = body.get("password").toString();
//		
//		User user = userJpaRepository.findByUsername(username);
//
//		if(user!=null) {
//			return false;
//		}
//		
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String encodedPassword = encoder.encode(password);
//
//		user = new User();
//		user.setUsername(username);
//		user.setPassword(encodedPassword);
//		user.setEmail("");
//		user.setAdmin(false);
//		
//		userJpaRepository.save(user);
//		
//		return true;
//	}
	
	@PostMapping("/api/users/signup")
	public boolean userSignup(@RequestBody User user) {
		
		System.out.println(user);
		
		User existingUser = userJpaRepository.findByUsername(user.getUsername());
		User existingEmail = userJpaRepository.findByEmail(user.getEmail());

		if(existingUser!=null || existingEmail!=null) {
			return false;
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());

		user.setPassword(encodedPassword);
		
		userJpaRepository.save(user);
		
		return true;
	}
	
	@PostMapping("/api/users/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User updatedUser = userJpaRepository.save(user);
		
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}
	
	@GetMapping("/api/users")
	public List<User> getAllUsers() {
		return userJpaRepository.findAll();
	}
	
	@GetMapping("/api/users/{username}")
	public User getUser(@PathVariable String username) {
		return userJpaRepository.findByUsername(username);
	}
	
	@DeleteMapping("/api/users/{username}")
	public ResponseEntity<Void> deleteUser(@PathVariable String username) {
		userJpaRepository.deleteById(username);

		return ResponseEntity.noContent().build();
	}
	
}
