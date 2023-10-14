package com.ahinski.library.mapper;

import com.ahinski.library.dto.LibraryRecordDTO;
import com.ahinski.library.entity.LibraryRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryRecordMapper {

    LibraryRecordMapper INSTANCE = Mappers.getMapper(LibraryRecordMapper.class);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "borrowTime", target = "borrowTime")
    @Mapping(source = "returnTime", target = "returnTime")
    LibraryRecordDTO libraryRecordToDTO(LibraryRecord libraryRecord);

    @Mapping(source = "bookId", target = "book.id")
    @Mapping(source = "borrowTime", target = "borrowTime")
    @Mapping(source = "returnTime", target = "returnTime")
    LibraryRecord DTOToLibraryRecord(LibraryRecordDTO libraryRecordDTO);

    List<LibraryRecordDTO> libraryRecordsToLibraryRecordDTOs(List<LibraryRecord> libraryRecords);

}
