package com.tkachuk.library.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.tkachuk.library.model.Book;
import com.tkachuk.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class PhotoService {

    private final AmazonS3 configure;

    private final String bucketName;

    private final String bucketName2;

    private final BookRepository bookRepository;

    @Autowired
    public PhotoService(AmazonS3 configure, @Value("${localstack.s3.bucketName}")String bucketName,
                        @Value("${localstack.s3.bucketName2}")String bucketName2, BookRepository bookRepository) {
        this.configure = configure;
        this.bucketName = bucketName;
        this.bucketName2 = bucketName2;
        this.bookRepository = bookRepository;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String uploadFileTos3bucket(String fileName, File file) {
        if (!configure.doesBucketExist(bucketName2)) {
            configure.createBucket(bucketName2);
        }
        try {
            configure.putObject(new PutObjectRequest(bucketName2, fileName, file));
        } catch (AmazonServiceException e) {
            return "uploadFileTos3bucket().Uploading failed :" + e.getMessage();
        }
        return "Uploading Successfull -> ";
    }

    private String uploadPhotoTos3bucket(String fileName, File file) {
        if (!configure.doesBucketExist(bucketName)) {
            configure.createBucket(bucketName);
        }
        try {
            configure.putObject(new PutObjectRequest(bucketName, fileName, file));
        } catch (AmazonServiceException e) {
            return "uploadFileTos3bucket().Uploading failed :" + e.getMessage();
        }
        return "Uploading Successfull -> ";
    }


    private File getFile(MultipartFile multipartFile) throws IOException {
        return convertMultiPartToFile(multipartFile);
    }

    private String getFilename(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename();
    }

    private String uploadWrapper(MultipartFile multipartFile) throws IOException {
        File file = getFile(multipartFile);
        String fileName = getFilename(multipartFile);
        return uploadFileTos3bucket(fileName, file);
    }

    private String uploadPhotoWrapper(MultipartFile multipartFile) throws IOException {
        File file = getFile(multipartFile);
        String fileName = getFilename(multipartFile);
        return uploadPhotoTos3bucket(fileName, file);
    }

    private String saveBookFile(MultipartFile multipartFile, String id) {
        String fileName = getFilename(multipartFile);
        String fileUrl = "";
        fileUrl = bucketName2 + "/" + fileName;
        Optional<Book> book = bookRepository.findById(id);
        book.get().setFileId(fileUrl);
        bookRepository.save(book.get());
        return fileUrl;
    }

    private String savePhoto(MultipartFile multipartFile, String id) {
        String fileName = getFilename(multipartFile);
        String fileUrl = "";
        fileUrl = bucketName + "/" + fileName;
        Optional<Book> book = bookRepository.findById(id);
        book.get().setFileId(fileUrl);
        bookRepository.save(book.get());
        return fileUrl;
    }

    public String uploadBookFile(MultipartFile multipartFile, String id) throws IOException {
        File file = getFile(multipartFile);
        String status = uploadWrapper(multipartFile);
        String fileUrl = saveBookFile(multipartFile, id);

        file.delete();
        return status + " " + fileUrl;
    }

    public String uploadPhoto(MultipartFile multipartFile, String id) throws IOException {
        File file = getFile(multipartFile);
        String status = uploadPhotoWrapper(multipartFile);
        String fileUrl = savePhoto(multipartFile, id);

        file.delete();
        return status + " " + fileUrl;
    }

    public S3Object getBookFileFromS3(String fileUrl) {
        return configure.getObject(bucketName2, fileUrl);
    }

    public S3Object getPhotoFromS3(String fileUrl) {
        return configure.getObject(bucketName, fileUrl);
    }
}
