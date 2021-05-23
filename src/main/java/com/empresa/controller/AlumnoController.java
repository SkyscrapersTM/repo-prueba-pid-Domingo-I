package com.empresa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.entity.Alumno;
import com.empresa.service.AlumnoService;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping("/rest/alumno")
@CrossOrigin(origins = "*")
public class AlumnoController {

	@Autowired
	private AlumnoService service;

	@GetMapping("/listar")
	public ResponseEntity<List<Alumno>> lista() {
		log.info(">>> lista");
		List<Alumno> list = service.listaAlumno();
		return new ResponseEntity<List<Alumno>>(list, HttpStatus.OK);
	}

	@PostMapping("/registar")
	public ResponseEntity<Alumno> registra(@RequestBody Alumno obj) {
		log.info(">>> registra" + obj.getIdAlumno());
		Alumno insert = service.insertaActualizaAlumno(obj);
		return new ResponseEntity<Alumno>(insert, HttpStatus.OK);
	}

	@PutMapping("/actualizar")
	public ResponseEntity<Alumno> actualiza(@RequestBody Alumno obj) {
		log.info(">>> actualiza" + obj.getIdAlumno());
		Optional<Alumno> optAlumnmo = service.obtienePorId(obj.getIdAlumno());
		if (optAlumnmo.isPresent()) {
			return ResponseEntity.ok(service.insertaActualizaAlumno(obj));
		} else {
			log.info(">>> elimina" + obj.getIdAlumno() + " No encontrado");
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<Alumno> eliminar(@PathVariable("id") int idAlumno) {
		log.info(">>> elimina" + idAlumno);
		Optional<Alumno> optAlumnmo = service.obtienePorId(idAlumno);
		if (optAlumnmo.isPresent()) {
			service.eliminaAlumno(idAlumno);
			 return ResponseEntity.ok(optAlumnmo.get());
		} else {
			log.info(">>> elimina" + idAlumno + " No encontrado");
			return ResponseEntity.notFound().build();
		}
	}
	

}
