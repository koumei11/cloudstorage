package com.storage.jwdnd.cloud.cloudstorage.controller;

import com.storage.jwdnd.cloud.cloudstorage.model.File;
import com.storage.jwdnd.cloud.cloudstorage.model.Note;
import com.storage.jwdnd.cloud.cloudstorage.model.User;
import com.storage.jwdnd.cloud.cloudstorage.service.FileService;
import com.storage.jwdnd.cloud.cloudstorage.service.NoteService;
import com.storage.jwdnd.cloud.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService mUserService;
    private FileService mFileService;
    private NoteService mNoteService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService) {
        mUserService = userService;
        mFileService = fileService;
        mNoteService = noteService;
    }

    @GetMapping
    public String getHome(Model model, Authentication auth) {
        User currentUser = mUserService.getUser(auth.getName());
        model.addAttribute("files", mFileService.getFiles(currentUser.getUserId()));
        model.addAttribute("notes", mNoteService.getNotes(currentUser.getUserId()));
        return "home";
    }

    @PostMapping(params = "file_upload")
    public String postFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication auth, Model model) {
        int rowAdded = 0;
        User user = mUserService.getUser(auth.getName());
        if (user != null || multipartFile.isEmpty()) {
            try {
                File file = new File(null,
                        multipartFile.getOriginalFilename(),
                        multipartFile.getContentType(),
                        String.valueOf(multipartFile.getSize()),
                        user.getUserId(),
                        multipartFile.getBytes());
                rowAdded = mFileService.insertFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (rowAdded > 0) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }

        return "result";
    }

    @PostMapping(params = "file_download")
    public void downloadFile(@RequestParam("fileId") String fileId, HttpServletResponse response, Model model) {
        try {
            File file = mFileService.getFile(Integer.parseInt(fileId));
            response.setContentType(file.getContentType());
            response.setContentLength(Integer.parseInt(file.getFileSize()));
            ServletOutputStream out = response.getOutputStream();
            out.write(file.getFileData());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping(params = "file_delete")
    public String deleteFile(@RequestParam("fileId") String fileId){
        mFileService.delete(Integer.parseInt(fileId));
        return "redirect:/home";
    }

    @PostMapping(params = "add_note")
    public String addNote(@ModelAttribute Note note,
                          @RequestParam("noteId") String noteId,
                          Model model,
                          Authentication auth) {
        int rowAdded = 0;
        User currentUser = mUserService.getUser(auth.getName());
        note.setUserId(currentUser.getUserId());
        if (noteId.equals("")) {
            rowAdded = mNoteService.insertNote(note);
        } else {
            rowAdded = mNoteService.updateNote(note);
        }

        if (rowAdded > 0) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
        }
        return "result";
    }

    @DeleteMapping(params = "note_delete")
    public String deleteNote(@RequestParam("noteId") String noteId) {
        mNoteService.deleteNote(Integer.parseInt(noteId));
        return "redirect:/home";
    }
}
