package com.employeemanagement.employeemanagement.Repository;


import com.employeemanagement.employeemanagement.Entity.FileUploader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploaderRepository extends JpaRepository<FileUploader,Long> {
}
