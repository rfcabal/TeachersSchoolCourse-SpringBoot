package com.zreactor.profesoresplanta.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.zreactor.profesoresplanta.model.SocialMedia;
import com.zreactor.profesoresplanta.model.Teacher;
import com.zreactor.profesoresplanta.model.TeacherSocialMedia;
import com.zreactor.profesoresplanta.service.SocialMediaService;
import com.zreactor.profesoresplanta.service.TeacherService;
import com.zreactor.profesoresplanta.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value="/v1")
public class TeacherController {
	
	@Autowired
	private TeacherService _teacherService;

	@Autowired
	private SocialMediaService _socialMediaService;
	
	//GET 
	@RequestMapping(value = "/teachers", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<Teacher>> getTeachers(@RequestParam(value="name", required=false) String name){
		List<Teacher> teachers = new ArrayList<Teacher>();
		
		if (name == null) {
			teachers = _teacherService.findAllTeachers();
	        if (teachers.isEmpty()) {
	            return new ResponseEntity(HttpStatus.NO_CONTENT);
	            // You many decide to return HttpStatus.NOT_FOUND
	        }
		   
			return new ResponseEntity<List<Teacher>>(teachers, HttpStatus.OK);
		} else {
			Teacher teacher = _teacherService.findByName(name);
			if (teacher == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			
			teachers.add(teacher);
			return new ResponseEntity<List<Teacher>>(teachers, HttpStatus.OK);
		}
		
		

    }
	
	//FIND BY ID
	@RequestMapping(value = "/teachers/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Teacher> getCourseById(@PathVariable("id") Long id){
		Teacher teacher = _teacherService.findById(id);
        if (teacher == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
    }
	
	//DELETE
	@RequestMapping(value = "/teachers/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) {
		Teacher teacher = _teacherService.findById(id);
        if (teacher == null) {
        	System.out.println("Unable to delete. teacher with id not found. " + id);
            
            return new ResponseEntity(new CustomErrorType("Unable to delete. teacher with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        
        _teacherService.deleteTeacherById(id);
        return new ResponseEntity<Teacher>(HttpStatus.NO_CONTENT);
    }

    public static final String TEACHER_UPLOADED_FOLDER = "images/teachers/";
    // CREATE TEACHER IMAGE
	@RequestMapping(value = "/teachers/image", method = RequestMethod.POST, headers = ("content-type=multipart/form-data"))
	public ResponseEntity<byte[]> uploadTeacherImage(@RequestParam("id_teacher") Long idTeacher,
													 @RequestParam("file")	MultipartFile multipartFile,
													 UriComponentsBuilder componentsBuilder) {

		if (idTeacher == null) {
			return new ResponseEntity(new CustomErrorType("Please set id_teacher"), HttpStatus.BAD_REQUEST);
		}

		if (multipartFile.isEmpty()) {
			return new ResponseEntity(new CustomErrorType("Please select a file to upload"), HttpStatus.BAD_REQUEST);
		}

		Teacher teacher = _teacherService.findById(idTeacher);

		if (teacher == null) {
			return new ResponseEntity(new CustomErrorType("Teacher with id teacher: " + idTeacher + " not found"), HttpStatus.NO_CONTENT);
		}

		if (!teacher.getAvatar().isEmpty() || teacher.getAvatar() != null) {
			String fileName = teacher.getAvatar();
			Path path = Paths.get(fileName);
			File f = path.toFile();
			if(f.exists()) {
				f.delete();
			}
		}

		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String dateName = dateFormat.format(date);

			String fileName = String.valueOf(idTeacher) + "-pictureTeacher" + dateName + "." +  multipartFile.getContentType().split("/")[1];
			teacher.setAvatar(TEACHER_UPLOADED_FOLDER + fileName);

			byte[] bytes = multipartFile.getBytes();
			Path path = Paths.get(TEACHER_UPLOADED_FOLDER + fileName);
			Files.write(path, bytes);

			_teacherService.updateTeacher(teacher);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new CustomErrorType("Error during upload: " + multipartFile.getOriginalFilename()), HttpStatus.BAD_REQUEST);
		}

	}


	// GET IMAGE
	@RequestMapping(value = "/teachers/{id_teacher}/images", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getTeacherImage(@PathVariable("id_teacher") Long idTeacher) {
		if (idTeacher == null) {
			return new ResponseEntity(new CustomErrorType("Please set id_teacher"), HttpStatus.BAD_REQUEST);
		}

		Teacher teacher = _teacherService.findById(idTeacher);
		if(teacher == null) {
			return new ResponseEntity(new CustomErrorType("Teacher with id teacher: " + idTeacher + " not found"), HttpStatus.NO_CONTENT);
		}

		try {

			String fileName = teacher.getAvatar();
			Path path = Paths.get(fileName);
			File f = path.toFile();
			if(!f.exists()) {
				return new ResponseEntity(new CustomErrorType("Image not found"), HttpStatus.NO_CONTENT);
			}

			byte[] image = Files.readAllBytes(path);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new CustomErrorType("Error to show image"), HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/teachers/{id_teacher}/images", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<?> deleteTeacherImage(@PathVariable("id_teacher") Long idTeacher) {

		if (idTeacher == null) {
			return new ResponseEntity(new CustomErrorType("Please set id_teacher"), HttpStatus.BAD_REQUEST);
		}

		Teacher teacher = _teacherService.findById(idTeacher);
		if(teacher == null) {
			return new ResponseEntity(new CustomErrorType("Teacher with id teacher: " + idTeacher + " not found"), HttpStatus.NO_CONTENT);
		}

		if(teacher.getAvatar().isEmpty() || teacher.getAvatar() == null) {
			return new ResponseEntity(new CustomErrorType("this teacher doesn't have image assigned"), HttpStatus.NO_CONTENT);
		}

		try {

			String fileName = teacher.getAvatar();
			Path path = Paths.get(fileName);
			File f = path.toFile();
			if(f.exists()) {
				f.delete();
				teacher.setAvatar("");
				_teacherService.updateTeacher(teacher);
				return new ResponseEntity<Teacher>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity(new CustomErrorType("Image not found"), HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new CustomErrorType("Error to show image"), HttpStatus.BAD_REQUEST);
		}


	}

	@RequestMapping(value = "/teachers/socialMedias", method = RequestMethod.PATCH, headers = "Accept=application/json")
	public ResponseEntity<?> assignTeacherSocialMedia(@RequestBody Teacher teacher, UriComponentsBuilder uriComponentsBuilder) {
		if (teacher.getIdTeacher() == null) {
			return new ResponseEntity(new CustomErrorType("We nee almost id_teacher, id_social_media and nickname"), HttpStatus.NO_CONTENT);
		}

		Teacher teacherSaved = _teacherService.findById(teacher.getIdTeacher());
		if (teacherSaved == null) {
			return new ResponseEntity(new CustomErrorType("The id_teacher: " + teacher.getIdTeacher() + " not found"), HttpStatus.NO_CONTENT);
		}

		if (teacher.getTeacherSocialMedia().size() == 0) {
			return new ResponseEntity(new CustomErrorType("We nee almost id_teacher, id_social_media and nickname"), HttpStatus.NO_CONTENT);
		}else{
			Iterator<TeacherSocialMedia> i = teacher.getTeacherSocialMedia().iterator();
			while (i.hasNext()) {
				TeacherSocialMedia teacherSocialMedia = i.next();
				if (teacherSocialMedia.getSocialMedia().getIdSocialMedia() == null || teacherSocialMedia.getNickname() == null) {
					return new ResponseEntity(new CustomErrorType("We need almost id_teacher, id_social_media and nickname"), HttpStatus.BAD_REQUEST);
				} else {
					TeacherSocialMedia tsmAux = _socialMediaService.findSocialMediaByIdAndName(
							teacherSocialMedia.getSocialMedia().getIdSocialMedia(),
							teacherSocialMedia.getNickname());

					if(tsmAux != null) {
						return new ResponseEntity(new CustomErrorType("The Social Media: " +
								teacherSocialMedia.getSocialMedia().getIdSocialMedia() +
								" already exist"), HttpStatus.NO_CONTENT);
					}

					SocialMedia socialMedia = _socialMediaService.findById(teacherSocialMedia.getSocialMedia().getIdSocialMedia());
					if(socialMedia == null) {
						return new ResponseEntity(new CustomErrorType("Social media id: " + teacher.getIdTeacher() + " not found"), HttpStatus.NO_CONTENT);
					}

					teacherSocialMedia.setSocialMedia(socialMedia);
					teacherSocialMedia.setTeacher(teacherSaved);
					
					if(tsmAux == null) {
						teacherSaved.getTeacherSocialMedia().add(teacherSocialMedia);
					} else {
						LinkedList<TeacherSocialMedia> teacherSocialMedias = new LinkedList<>();
						teacherSocialMedias.addAll(teacherSaved.getTeacherSocialMedia());
						for (int j = 0; j < teacherSocialMedias.size(); j++) {
							TeacherSocialMedia teacherSocialMedia2 = teacherSocialMedias.get(j);
							if (teacherSocialMedia.getTeacher().getIdTeacher() == teacherSocialMedia2.getTeacher().getIdTeacher() &&
							teacherSocialMedia.getSocialMedia().getIdSocialMedia() == teacherSocialMedia2.getSocialMedia().getIdSocialMedia()) {
								teacherSocialMedia2.setNickname(teacherSocialMedia.getNickname());
								teacherSocialMedias.set(j, teacherSocialMedia2);
							} else {
								teacherSocialMedias.set(j, teacherSocialMedia2);
							}
						}

						teacherSaved.getTeacherSocialMedia().clear();
						teacherSaved.getTeacherSocialMedia().addAll(teacherSocialMedias);

					}

				}
			}
		}

		_teacherService.updateTeacher(teacherSaved);
		return new ResponseEntity<Teacher>(teacherSaved, HttpStatus.OK);
	}

}
