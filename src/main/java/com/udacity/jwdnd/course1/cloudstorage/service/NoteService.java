package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper mMapper;

    public NoteService(NoteMapper mapper) {
        mMapper = mapper;
    }

    public List<Note> getNotes(Integer userId) {
        return mMapper.getNotes(userId);
    }

    public int insertNote(Note note) {
        return mMapper.insert(note);
    }

    public int updateNote(Note note) {
        return mMapper.update(note);
    }

    public int deleteNote(int id) {
        return mMapper.delete(id);
    }
}
