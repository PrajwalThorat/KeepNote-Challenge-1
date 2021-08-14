package com.stackroute.keepnote.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.repository.NoteRepository;

/*Annotate the class with @Controller annotation. @Controller annotation is used to mark 
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 * */
@Controller
public class NoteController {
	
//	private NoteRepository noteRepository;
	
	/*
	 * From the problem statement, we can understand that the application
	 * requires us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the collection. Each note 
	 *    should contain Note Id, title, content, status and created date.
	 * 2. Add a new note which should contain the note id, title, content and status.
	 * 3. Delete an existing note.
	 * 4. Update an existing note.
	 */
	ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	Note note = (Note) context.getBean("note");
	NoteRepository noteRepository = (NoteRepository) context.getBean("noteRepository");
	/* 
	 * Get the application context from resources/beans.xml file using ClassPathXmlApplicationContext() class.
	 * Retrieve the Note object from the context.
	 * Retrieve the NoteRepository object from the context.
	 */
//	@GetMapping("/")
//	public String Note(ModelMap model) {
//		List<Note> noteList = new ArrayList<Note>();
//		noteList = noteRepository.getAllNotes();
//		model.addAttribute("noteList", noteList);
//		return "index";
//	}
//	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getAllNotes(ModelMap model) {
		model.addAttribute("noteList",noteRepository.getAllNotes());
		return "index";
	}
	
	/*Define a handler method to read the existing notes by calling the getAllNotes() method 
	 * of the NoteRepository class and add it to the ModelMap which is an implementation of Map 
	 * for use when building model data for use with views. it should map to the default URL i.e. "/" */
	@RequestMapping(value = "/saveNote", method = RequestMethod.POST)
	public String addNote(ModelMap model,@RequestParam("noteId") Integer noteId,@RequestParam("noteTitle") String noteTitle ,@RequestParam("noteContent") String noteContent, @RequestParam("noteStatus") String noteStatus) {
		if(noteId==null || noteTitle.isEmpty() || noteContent.isEmpty() || noteStatus.isEmpty()) {
			model.addAttribute("error" , "Please fill all Content");
		}else if(noteRepository.exists(noteId)) {
			model.addAttribute("error", "NoteId Exists");
		}else {
			Note note1 = (Note) context.getBean("note");
			note1.setNoteId(noteId);
			note1.setNoteTitle(noteTitle);
			note1.setNoteContent(noteContent);
			note1.setNoteStatus(noteStatus);
			note1.setCreatedAt(LocalDateTime.now());
			noteRepository.addNote(note1);
		}
		model.addAttribute("noteList", noteRepository.getList());
		return "index";
		
	}
	
	/*Define a handler method which will read the Note data from request parameters and
	 * save the note by calling the addNote() method of NoteRepository class. Please note 
	 * that the createdAt field should always be auto populated with system time and should not be accepted 
	 * from the user. Also, after saving the note, it should show the same along with existing 
	 * notes. Hence, reading notes has to be done here again and the retrieved notes object 
	 * should be sent back to the view using ModelMap.
	 * This handler method should map to the URL "/saveNote". 
	*/
	@RequestMapping(value = "/deleteNote")
	public String deleteNote(ModelMap model , @RequestParam int noteId) {
		noteRepository.deleteNote(noteId);
		model.addAttribute("noteList", noteRepository.getAllNotes());
		return "redirect:"+"/";
	}
	
	@GetMapping("/updateNote/{noteId}")
	public String UpdateNote(@PathVariable int noteId,ModelMap modelMap) {
		
		modelMap.addAttribute("noteItem",noteRepository.getNote(noteId));
		modelMap.addAttribute("noteList",noteRepository.getAllNotes());
		return "index";
	}
	
	/* Define a handler method to delete an existing note by calling the deleteNote() method 
	 * of the NoteRepository class
	 * This handler method should map to the URL "/deleteNote" 
	*/
	
}
