package net.sjl.spring.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StreamingService {
	
    @RequestMapping(method = RequestMethod.GET, value = "/api/jobs/{jobId}/logs")
    public ResponseEntity<Resource> getClusterInputForm(@PathVariable("jobId") String jobId) {
        String file = "/Users/mobile/spcdump.txt";
    	
        InputStreamResource resource;
		try {
			resource = new InputStreamResource(new FileInputStream(file));
	        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, String.format("inline; filename=\"" + file + "\""))
//	                .headers(headers)
//	                .contentLength(file.length())
	                .contentType(MediaType.parseMediaType("application/octet-stream"))
	                .body(resource);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

}
