# 🏥 MediTrust — Healthcare Management System

> **Trusted Care, Seamless Connection**

A full-stack healthcare management platform built with **Angular 17** and **Spring Boot (Java 21)** that enables hospitals and clinics to manage patient records, schedule appointments, conduct telehealth consultations, and streamline care coordination.

---

## ✨ Features

### 🔐 Authentication & Role-Based Access
- Secure JWT-based authentication with BCrypt password hashing
- Separate registration and login flows for **Patients** and **Doctors**
- Role-based UI — patients see only their data, doctors manage everything

### 📅 Appointment Scheduling & Telehealth
- Book in-person and virtual appointments
- Auto-generated meeting links for virtual consultations
- Doctors can complete, cancel, or mark appointments as no-show
- Patients can book appointments and cancel scheduled ones
- **Appointment completion requires prescribing medicines** — ensures no appointment is closed without treatment

### 📋 Electronic Health Records (EHR)
- Doctors can create, update, and manage patient health records
- Store diagnoses, lab results, treatment plans, and clinical notes
- Patients can view their own records in read-only mode
- Full audit trail with timestamps

### 💊 E-Prescriptions with Multi-Medicine Support
- Doctors can prescribe **multiple medicines** in a single prescription
- Structured fields: medicine name, dosage, frequency, duration, and special instructions
- Prescription status tracking: Active, Completed, Cancelled, Expired
- Patients can view their prescriptions with medication tags

### 📊 Dashboard & Analytics
- Real-time dashboard with key metrics (patients, appointments, prescriptions)
- Role-specific views — doctors see practice overview, patients see personal summary
- Recent appointments list with status indicators
- Quick action shortcuts for common tasks

### 🌗 Dark / Light Mode
- Full dark mode support across the entire application
- Toggle in sidebar (when logged in) and floating button on auth pages
- Theme preference persists across sessions
- Smooth transitions between modes

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| **Frontend** | Angular 17 (Standalone Components) |
| **Backend** | Spring Boot 3.2 · Java 21 |
| **Database** | MySQL 8.0 |
| **Auth** | JWT (JSON Web Tokens) + BCrypt |
| **Security** | Spring Security with CORS configuration |
| **Styling** | Custom CSS with CSS Variables (no external UI library) |
| **Build** | Maven (Backend) · Angular CLI (Frontend) |

---

## 📁 Project Structure

```
MediTrust/
├── meditrust-backend/          # Spring Boot REST API
│   └── src/main/java/com/mediconnect/mediconnect_backend/
│       ├── controller/         # REST Controllers (7 files)
│       ├── dto/                # Data Transfer Objects (9 files)
│       ├── entity/             # JPA Entities (6 files)
│       ├── repository/         # Spring Data JPA Repositories (6 files)
│       ├── service/            # Business Logic Services (7 files)
│       ├── security/           # JWT + Spring Security Config (3 files)
│       └── exception/          # Global Exception Handler
│
└── meditrust-frontend/         # Angular 17 SPA
    └── src/app/
        ├── models/             # TypeScript Interfaces (7 files)
        ├── services/           # HTTP Services (8 files including ThemeService)
        ├── guards/             # Route Guards
        ├── interceptors/       # HTTP Interceptors (JWT)
        └── pages/              # Page Components
            ├── login/
            ├── register/
            ├── dashboard/
            ├── patients/       # Doctor-only
            ├── doctors/        # Doctor-only
            ├── appointments/
            ├── health-records/
            └── prescriptions/
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 21** — [Download](https://www.oracle.com/java/technologies/downloads/#java21)
- **Maven 3.9+** — [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** — [Download](https://dev.mysql.com/downloads/mysql/)
- **Node.js 18+** — [Download](https://nodejs.org)
- **Angular CLI 17** — `npm install -g @angular/cli@17`

### 1. Database Setup

```sql
CREATE DATABASE mediconnect_db;
```

### 2. Backend Setup

```bash
cd meditrust-backend

# Update database credentials in src/main/resources/application.properties
# spring.datasource.username=root
# spring.datasource.password=YOUR_PASSWORD

# Run the application
mvn spring-boot:run
```

Backend starts at **http://localhost:8081**

### 3. Frontend Setup

```bash
cd meditrust-frontend

# Install dependencies
npm install

# Start development server
ng serve
```

Frontend starts at **http://localhost:4200**

### 4. Test the Application

1. Open **http://localhost:4200**
2. Register as a **Doctor** (e.g., Dr. Smith, Cardiology)
3. Logout → Register as a **Patient** (e.g., John Doe)
4. Login as Doctor → manage appointments, prescribe medicines
5. Login as Patient → book appointments, view prescriptions

---

## 🔌 API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register patient or doctor |
| POST | `/api/auth/login` | Login and get JWT token |

### Patients (Doctor only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/patients` | Get all patients |
| GET | `/api/patients/{id}` | Get patient by ID |
| PUT | `/api/patients/{id}` | Update patient |
| DELETE | `/api/patients/{id}` | Delete patient |

### Doctors
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/doctors` | Get all doctors |
| GET | `/api/doctors/available` | Get available doctors |
| GET | `/api/doctors/specialization/{spec}` | Filter by specialization |

### Appointments
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/appointments` | Get all appointments |
| GET | `/api/appointments/patient/{id}` | Get by patient |
| GET | `/api/appointments/doctor/{id}` | Get by doctor |
| POST | `/api/appointments` | Create appointment |
| PUT | `/api/appointments/{id}/status` | Update status |
| DELETE | `/api/appointments/{id}` | Delete appointment |

### Health Records
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health-records` | Get all records |
| GET | `/api/health-records/patient/{id}` | Get by patient |
| POST | `/api/health-records` | Create record |
| PUT | `/api/health-records/{id}` | Update record |

### Prescriptions
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/prescriptions` | Get all prescriptions |
| GET | `/api/prescriptions/patient/{id}` | Get by patient |
| POST | `/api/prescriptions` | Create prescription |
| PUT | `/api/prescriptions/{id}/status` | Update status |

### Analytics
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/analytics/dashboard` | Get dashboard stats |

---

## 📸 Screenshots

| Login Page | Doctor Dashboard |
|:---:|:---:|
| *Split-panel login with role toggle* | *Stats, recent appointments, quick actions* |

| Appointment + Prescription | Dark Mode |
|:---:|:---:|
| *Multi-medicine prescription on completion* | *Full dark mode support* |

---

## 🔒 Security Features

- **JWT Authentication** — Stateless token-based auth with 24-hour expiry
- **BCrypt Hashing** — Passwords are never stored in plain text
- **Role-Based Access** — Patients cannot access doctor-only endpoints
- **CORS Protection** — Configured for frontend origin only
- **Input Validation** — Server-side validation on all entities

---

## 🗺️ Roadmap

- [ ] Email/SMS notifications for appointments
- [ ] Real video call integration (WebRTC)
- [ ] Admin panel for hospital management
- [ ] PDF export for prescriptions and health records
- [ ] Patient medical history timeline view
- [ ] Pharmacy integration for prescription fulfillment

---

## 👤 Author

**Your Name**
- GitHub: Faizan(https://github.com/yourusername)
- LinkedIn: - (https://linkedin.com/in/yourprofile)

---

## 📄 License

This project is for educational and portfolio demonstration purposes.

---

<p align="center">
  Built with ❤️ using Angular 17 + Spring Boot + MySQL
</p>