package com.sahana.StudentAttendance.Util;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class MultipartInputStreamFileResource extends InputStreamResource implements MultipartFile {

    private final String filename;
    private final long size;

    public MultipartInputStreamFileResource(InputStream inputStream, String filename) throws IOException {
        super(inputStream);
        this.filename = filename;
        this.size = inputStream.available(); // Read file size directly from input stream
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() throws IOException {
        return size;
    }

    @Override
    public String getName() {
        return filename; // Returning filename here
    }

    @Override
    public String getOriginalFilename() {
        return filename; // Return the original filename
    }

    @Override
    public String getContentType() {
        // Assuming you want to set content type as image type, can be derived from filename or custom logic
        if (filename != null && (filename.endsWith(".jpg") || filename.endsWith(".jpeg"))) {
            return "image/jpeg";
        } else if (filename != null && filename.endsWith(".png")) {
            return "image/png";
        }
        return "application/octet-stream"; // Default type
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        try (InputStream inputStream = getInputStream()) {
            return inputStream.readAllBytes();
        }
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (InputStream inputStream = getInputStream();
             OutputStream outputStream = new FileOutputStream(dest)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
