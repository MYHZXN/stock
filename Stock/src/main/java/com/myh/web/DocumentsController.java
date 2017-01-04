package com.myh.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.myh.domain.CustomResponse;

@Controller
@RequestMapping("/documents")
public class DocumentsController {
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public @ResponseBody CustomResponse uploadDocuments(MultipartHttpServletRequest request){
		try {
            Iterator<String> itr = request.getFileNames();
            String output = "";
            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile file = request.getFile(uploadedFile);
                //String mimeType = file.getContentType();
                //String filename = file.getOriginalFilename();
                //byte[] bytes = file.getBytes();
                byte[] bytes = file.getBytes();
                String dir = "F:/MyEclipseProject/Stock/pic/";
                //String dir = "C:/Stock/pic/";
                String fileName = "DJ_"+System.currentTimeMillis()+".png";
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(dir+fileName)));
                stream.write(bytes);
                stream.close();
                
                output += fileName;
            }
            return new CustomResponse("200",output);
        }
        catch (Exception e) {
            return new CustomResponse("500",e.getMessage());
        }
	}
	
	
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public String downloadDocuments(){
		return null;
	}
}
