Reach me out ,ready to help with issues
# 🎓 Student Attendance System using Face Recognition (Java + CompreFace)

This project is a **Student Attendance Management System** built with **Java (Spring Boot)** and integrates with **CompreFace**, an open-source face recognition service. It captures student faces, verifies them via CompreFace, and records attendance in a **PostgreSQL** database.

## 📌 Features
- 🔍 Face recognition via CompreFace REST API  
- 📷 Capture live images via webcam (frontend or device)  
- 🗂️ Student data and attendance stored in PostgreSQL  
- 🧠 Face recognition-based identification  
- 🧾 Attendance logs with timestamps  
- 🚀 Spring Boot-based RESTful backend  

## 🧰 Tech Stack
- Java 17+  
- Spring Boot  
- PostgreSQL  
- CompreFace (Face recognition engine)  
- Docker (for CompreFace)  
- Maven or Gradle  
- Optional: React/HTML5 for frontend camera capture  

## 🖼️ System Architecture
[Client Device] --> [Spring Boot API] --> [CompreFace API]  
                                |  
                           [PostgreSQL]  

## 🚀 Getting Started

### ✅ Prerequisites
- Java 17+  
- Maven or Gradle  
- Docker (to run CompreFace)  
- PostgreSQL installed or Dockerized  

### 🧱 Clone the Repository
git clone https://github.com/Sagar1205b/StudentAttendance_FACE_recognition.git  
cd StudentAttendance_FACE_recognition  

### 🔧 Setting up CompreFace
1. Clone CompreFace:  
   git clone https://github.com/exadel-inc/CompreFace.git  
   cd CompreFace  

2. Start CompreFace using Docker:  
   docker-compose -f docker-compose.yml up  

3. Access the CompreFace UI:  
   👉 http://localhost:8000  

4. Create a Face Recognition service and copy:  
   - API_KEY  
   - API_URL (e.g. http://localhost:8000/api/v1/recognition/12345678)  

5. Add these to your Spring Boot `application.properties`:  
   compreface.api.key=your_api_key  
   compreface.api.url=http://localhost:8000/api/v1/recognition/{your_service_id}  

### 🗃️ Setting up PostgreSQL
If using Docker:  
docker run --name attendance-db -e POSTGRES_DB=attendance -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres  

Or configure your `application.properties`:  
spring.datasource.url=jdbc:postgresql://localhost:5432/attendance  
spring.datasource.username=postgres  
spring.datasource.password=postgres  
spring.jpa.hibernate.ddl-auto=update  

### ▶️ Run the Spring Boot App
If using Maven:  
./mvnw spring-boot:run  

 


  

## 📝 To Do
- [ ] Improve face matching thresholds  
- [ ] Add CSV export for attendance    

## 🤝 Contribution
Contributions are welcome!  
Feel free to fork this repo, make changes, and open a pull request.

## ⭐ Support
If you find this project helpful, please ⭐ this repo — it helps it reach more developers.

## 👤 Author
**Sagar B.**  
GitHub: https://github.com/Sagar1205b

## 📄 License
This project is licensed under the MIT License.

<img width="1884" height="850" alt="image" src="https://github.com/user-attachments/assets/4dd6c507-979c-4685-a36c-386ec87af81c" />
<img width="1885" height="841" alt="image" src="https://github.com/user-attachments/assets/a9d6ce66-0ded-4d81-899b-a8542f4b1648" />
<img width="1884" height="849" alt="image" src="https://github.com/user-attachments/assets/47a4d801-aaaa-49b8-b0a7-3f49c713971f" />
<img width="1899" height="859" alt="image" src="https://github.com/user-attachments/assets/94104bde-39d0-45b2-9dc4-c38875ae9243" />
<img width="1837" height="854" alt="image" src="https://github.com/user-attachments/assets/8496cafc-05c7-4332-875d-f8a67927f5bc" />
<img width="1878" height="865" alt="image" src="https://github.com/user-attachments/assets/8bf34f31-de82-4a1c-a5a6-d9b59ca3b1d7" />
