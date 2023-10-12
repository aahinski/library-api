package com.ahinski.library.repository;

import com.ahinski.library.entity.LibraryRecord;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRecordRepository extends JpaRepository<LibraryRecord, Long> {

     boolean existsByBookIdAndBorrowTimeIsNotNull(Long bookId);
     @Transactional
     void deleteByBookId(Long bookId);
     @Query(nativeQuery = true, value = "SELECT * FROM library_record WHERE borrow_time IS NULL")
     List<LibraryRecord> findWhereBorrowTimeIsNull();
     Optional<LibraryRecord> findByBookId(Long bookId);

}
