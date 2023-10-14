package com.ahinski.library.service;

import com.ahinski.library.dto.BookDTO;
import com.ahinski.library.dto.LibraryRecordDTO;
import com.ahinski.library.entity.Book;
import com.ahinski.library.entity.LibraryRecord;
import com.ahinski.library.exception.BookNotFoundException;
import com.ahinski.library.mapper.BookMapper;
import com.ahinski.library.mapper.LibraryRecordMapper;
import com.ahinski.library.repository.LibraryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private LibraryRecordRepository libraryRecordRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private LibraryRecordMapper libraryRecordMapper;

    @Override
    public LibraryRecordDTO addRecord(BookDTO bookDTO) {
        Book book = bookMapper.bookDTOToBook(bookDTO);
        LibraryRecord libraryRecord = new LibraryRecord();
        libraryRecord.setBook(book);

        return libraryRecordMapper.libraryRecordToDTO(libraryRecordRepository.save(libraryRecord));
    }

    @Override
    public List<LibraryRecordDTO> fetchAllFreeLibraryRecords() {
        List<LibraryRecord> libraryRecords = libraryRecordRepository.findWhereBorrowTimeIsNull();
        return libraryRecordMapper.libraryRecordsToLibraryRecordDTOs(libraryRecords);
    }

    @Override
    public LibraryRecordDTO borrowBook(Long bookId) throws BookNotFoundException {
        Optional<LibraryRecord> libraryRecordOptional = libraryRecordRepository.findByBookId(bookId);

        if (libraryRecordOptional.isPresent()) {
            if (!isBookBorrowed(bookId)) {
                LibraryRecord libraryRecord = libraryRecordOptional.get();
                libraryRecord.setBorrowTime(LocalDateTime.now());
                libraryRecord.setReturnTime(LocalDateTime.now().plusDays(30));

                return libraryRecordMapper.libraryRecordToDTO(libraryRecordRepository.save(libraryRecord));
            } else {
                throw new BookNotFoundException("The book was not returned");
            }
        } else {
            throw new BookNotFoundException("No book found to return");
        }
    }

    @Override
    public LibraryRecordDTO returnBook(Long bookId) throws BookNotFoundException {
        Optional<LibraryRecord> libraryRecordOptional = libraryRecordRepository.findByBookId(bookId);

        if (libraryRecordOptional.isPresent()) {
            if (isBookBorrowed(bookId)) {
                LibraryRecord libraryRecord = libraryRecordOptional.get();
                libraryRecord.setBorrowTime(null);
                libraryRecord.setReturnTime(null);

                return libraryRecordMapper.libraryRecordToDTO(libraryRecordRepository.save(libraryRecord));
            } else {
                throw new BookNotFoundException("The book was not borrowed");
            }
        } else {
            throw new BookNotFoundException("No book found to return");
        }
    }

    @Override
    public boolean isBookBorrowed(Long bookId) throws BookNotFoundException {
        if (libraryRecordRepository.findByBookId(bookId).isPresent()) {
            return libraryRecordRepository.existsByBookIdAndBorrowTimeIsNotNull(bookId);
        } else {
            throw new BookNotFoundException("No book found to check");
        }
    }

    @Override
    public void deleteByBookId(Long bookId) throws BookNotFoundException {
        if (libraryRecordRepository.findByBookId(bookId).isPresent()) {
            libraryRecordRepository.deleteById(bookId);
        } else {
            throw new BookNotFoundException("No book found to delete");
        }
    }
}
