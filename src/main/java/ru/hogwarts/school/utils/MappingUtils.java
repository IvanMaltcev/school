package ru.hogwarts.school.utils;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.AvatarDto;
import ru.hogwarts.school.model.Avatar;

@Service
public class MappingUtils {

    public AvatarDto mapToAvatarDto(Avatar avatar) {
        AvatarDto dto = new AvatarDto();
        dto.setId(avatar.getId());
        dto.setFilePath(avatar.getFilePath());
        dto.setFileSize(avatar.getFileSize());
        dto.setMediaType(avatar.getMediaType());
        dto.setStudent(avatar.getStudent());
        return dto;
    }

    public Avatar mapToAvatar(AvatarDto dto) {
        Avatar avatar = new Avatar();
        avatar.setId(dto.getId());
        avatar.setFilePath(dto.getFilePath());
        avatar.setFileSize(dto.getFileSize());
        avatar.setMediaType(dto.getMediaType());
        avatar.setStudent(dto.getStudent());
        return avatar;
    }
}
