package com.tmrs.poc.nextgen.rest;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/help")
public class MarkdownViewerController {
	
	
	// Logger
	private static final Logger logger = LogManager.getLogger(MarkdownViewerController.class);
	
	@GetMapping(produces="text/html")
	public ResponseEntity<String> getHelp() {
		
		File mdFile = new File("help/HELP.md");
		Reader fileReader =  null;
		Node document = null;
		Parser parser = Parser.builder().build();
		try {
			fileReader = new FileReader(mdFile);
			document = parser.parseReader(fileReader);
		} catch(FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		
		return new ResponseEntity<String>(renderer.render(document), HttpStatus.OK);
	}

	
	@GetMapping("/actuator")
	public ResponseEntity<String> getActuatorHelp() {
		
		File mdFile = new File("help/actuator.md");
		Reader fileReader =  null;
		Node document = null;
		Parser parser = Parser.builder().build();
		try {
			fileReader = new FileReader(mdFile);
			document = parser.parseReader(fileReader);
		} catch(FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		
		return new ResponseEntity<String>(renderer.render(document), HttpStatus.OK);
	}

}
