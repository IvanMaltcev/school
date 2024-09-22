package ru.hogwarts.school.dto;

import lombok.Data;
import ru.hogwarts.school.model.Student;

@Data
public class AvatarDto {
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private Student student;
}
