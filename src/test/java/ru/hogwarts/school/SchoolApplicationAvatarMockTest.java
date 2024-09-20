package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarServiceImp;
import ru.hogwarts.school.service.FacultyServiceImp;
import ru.hogwarts.school.service.StudentServiceImp;

import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SchoolApplicationAvatarMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private StudentServiceImp studentService;

    @SpyBean
    private FacultyServiceImp facultyService;

    @SpyBean
    private AvatarServiceImp avatarService;

    @InjectMocks
    private AvatarController avatarController;

    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;

    private Avatar avatar;

    @BeforeEach
    void init() {
        id = 1L;
        filePath = "avatars";
        fileSize = 10923;
        mediaType = "image/png";
        avatar = new Avatar();
        avatar.setId(id);
        avatar.setFilePath(filePath);
        avatar.setFileSize(fileSize);
        avatar.setMediaType(mediaType);
    }

    @Test
    public void getListOfAvatars() throws Exception {
        JSONObject avatarObject = new JSONObject();
        avatarObject.put("filePath", filePath);
        avatarObject.put("fileSize", fileSize);
        avatarObject.put("mediaType", mediaType);

        Page<Avatar> page = new PageImpl<>(List.of(avatar));

        when(avatarRepository.findAll(isA(PageRequest.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/students/list-of-avatars?page=1&size=1")
                        .content(avatarObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].filePath").value(filePath))
                .andExpect(jsonPath("$[0].fileSize").value(fileSize))
                .andExpect(jsonPath("$[0].mediaType").value(mediaType));
    }


}


