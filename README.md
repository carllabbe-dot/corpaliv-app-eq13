# 📱 CORPALIV App — Equipo 13

Aplicación móvil institucional para **CORPALIV** (Corporación de Padres y Amigos por el Limitado Visual), desarrollada en Android nativo con Kotlin y Jetpack Compose para la asignatura **DSY1105 — Desarrollo de Aplicaciones Móviles, Evaluación Parcial 4**.

## 📋 Descripción del proyecto

CORPALIV App facilita la difusión de la labor social de la corporación y Alba Lab, la recepción y administración de solicitudes de colaboración y el acceso a información pública sobre discapacidad en Chile. Integra una aplicación Android, un microservicio REST propio y una API externa independiente.

## 👥 Integrantes

| Nombre | Equipo |
|---|---|
| Carlos Labbe | 13 |
| Damian Marro | 13 |

- **Sección:** DSY1105_001V
- **Docente:** Nicolas Andres Muñoz Mora
- **Repositorio:** https://github.com/carllabbe-dot/corpaliv-app-eq13
- **Trello:** https://trello.com/b/I9KvOhUd/desarrolloappmovil

## 🎯 Contexto del cliente

CORPALIV es una corporación sin fines de lucro que trabaja con personas con discapacidad visual. A través de Alba Lab impulsa iniciativas y productos artesanales. La aplicación busca facilitar la vinculación con potenciales colaboradores y dar visibilidad a información relacionada con la discapacidad y la inclusión.

## ✅ Funcionalidades implementadas en el código

- Pantalla de inicio, información institucional, perfil y ajustes.
- Navegación mediante NavHost, Drawer y eventos de MainViewModel.
- Header y Drawer reutilizables.
- Formulario de colaboración con validación de nombre, correo, clave, dirección, logo y aceptación de términos.
- Selección de imagen del logo desde el dispositivo y visualización con Coil.
- Retroalimentación de guardado mediante mensaje animado.
- Creación, consulta, edición y eliminación de solicitudes mediante el microservicio propio.
- Copia local de solicitudes creadas mediante Room.
- Consulta de conjuntos de datos públicos sobre discapacidad desde una API externa mediante Retrofit.
- Visualización de títulos, descripciones y enlaces a archivos PDF y CSV, con estados de carga, error y reintento.
- Pruebas unitarias de validación, navegación y repositorio.
- Generación de APK de release y almacén de claves `.jks`.

La presencia de estas funcionalidades en el código no sustituye la comprobación final de su ejecución en el emulador y en el APK de release.

## 🛠️ Tecnologías utilizadas

| Categoría | Tecnología |
|---|---|
| Lenguajes | Kotlin 2.2.10 y Java (nivel 17 en backend) |
| Interfaz | Jetpack Compose, Material Design 3 |
| Arquitectura móvil | MVVM, flujo unidireccional de estados y eventos |
| Estado y asincronía | ViewModel, StateFlow, SharedFlow, corrutinas |
| Navegación | Navigation Compose 2.7.7 |
| Persistencia local | Room 2.8.5 (SQLite) |
| Consumo REST | Retrofit 2.12.0 y Gson |
| Imágenes | Coil 2.7.0 |
| Backend | Spring Boot 4.2.0 (versión declarada en pom.xml), Maven |
| Persistencia remota | Spring Data JPA, Hibernate, H2 en archivo |
| API externa | CKAN — Ministerio de Desarrollo Social y Familia |
| Pruebas | JUnit 4.13.2, MockK 1.13.12, kotlinx-coroutines-test |
| Compilación Android | AGP 9.4.1, Gradle Wrapper 9.8.0, KSP |
| Control de versiones y planificación | Git, GitHub y Trello |

## 🏗️ Arquitectura

```text
Android / Jetpack Compose
  ├── MainViewModel → eventos de navegación → NavHost
  ├── UsuarioViewModel → SolicitudRepository → Retrofit → Spring Boot
  │                    └── Room (copia local tras creación remota)
  ├── SolicitudesScreen → SolicitudRepository → Retrofit → Spring Boot
  └── DiscapacidadViewModel → DiscapacidadRepository → Retrofit → API CKAN

Spring Boot → Controller → Service → Repository JPA → H2
```

La interfaz observa estados del ViewModel y presenta datos o mensajes de error. La pantalla de administración de solicitudes utiliza directamente el repositorio para consultar y modificar registros. Cada API dispone de su propio cliente Retrofit.

### Estructura de paquetes y carpetas

```text
corpalivappeq13/
├── app/
│   ├── src/main/java/com/example/corpaliv_app_eq13/
│   │   ├── MainActivity.kt
│   │   ├── components/         # Header y CorpalivDrawer
│   │   ├── data/
│   │   │   ├── local/          # Room: Entity, DAO, Database
│   │   │   ├── remote/         # API propia, DTO y RetrofitClient
│   │   │   ├── external/       # API CKAN, DTO y cliente Retrofit
│   │   │   └── repository/     # Repositorios de solicitudes y discapacidad
│   │   ├── model/              # Estados y errores del formulario
│   │   ├── navigation/         # Screen y NavigationEvent
│   │   ├── ui/                 # screens, theme y utils
│   │   └── viewmodel/
│   ├── src/test/               # Pruebas unitarias
│   └── release/app-release.apk
├── backend/ms-solicitudes/
│   ├── src/main/java/cl/corpaliv/ms_solicitudes/
│   │   ├── controller/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   ├── src/main/resources/application.yaml
│   ├── pom.xml
│   └── mvnw.cmd
└── README.md
```

## 🗄️ Microservicio y base de datos

El microservicio `ms-solicitudes` se ejecuta independientemente de la aplicación Android en el puerto `8081`.

Utiliza H2 en modo archivo (`jdbc:h2:file:./data/corpalivdb`). Hibernate crea o actualiza la estructura mediante `ddl-auto: update` al iniciar el servidor. La entidad JPA `Solicitud` representa la tabla `solicitudes`:

| Campo | Tipo Java | Descripción |
|---|---|---|
| id | Long | Identificador autogenerado |
| nombre | String | Nombre del colaborador |
| correo | String | Correo de contacto |
| direccion | String | Dirección |
| logoEmpresa | String | URI o referencia al logo seleccionado |

Room mantiene una copia local tras la creación remota. Las modificaciones y eliminaciones remotas no se sincronizan automáticamente con Room. El logo se almacena como referencia, no como archivo binario subido al servidor.

### Endpoints REST

URL base en el computador: `http://localhost:8081/`

| Método | Endpoint | Operación |
|---|---|---|
| GET | `/api/solicitudes` | Listar todas las solicitudes |
| GET | `/api/solicitudes/{id}` | Buscar por identificador |
| POST | `/api/solicitudes` | Crear una solicitud (201) |
| PUT | `/api/solicitudes/{id}` | Actualizar una solicitud |
| DELETE | `/api/solicitudes/{id}` | Eliminar una solicitud (204) |

Ejemplo de cuerpo JSON para POST:

```json
{
  "nombre": "Empresa de ejemplo",
  "correo": "contacto@example.com",
  "direccion": "Santiago",
  "logoEmpresa": null
}
```

El CRUD se realiza desde el formulario de colaboración y la pantalla de solicitudes recibidas. La búsqueda individual por ID está disponible en la API y el repositorio.

## 🌐 API externa: Discapacidad e inclusión

Fuente: catálogo público del Ministerio de Desarrollo Social y Familia de Chile (CKAN).

```text
GET https://bid-ckan.ministeriodesarrollosocial.gob.cl/api/3/action/package_search?q=discapacidad&rows=10
```

La consulta devuelve metadatos de conjuntos de datos en JSON. La app muestra sus títulos, descripciones y enlaces a recursos originales. No calcula estadísticas de los archivos CSV.

Componentes: `DiscapacidadDto`, `DiscapacidadApi`, `DiscapacidadRetrofitClient`, `DiscapacidadRepository`, `DiscapacidadViewModel` y `DiscapacidadScreen`.

La API externa utiliza HTTPS, requiere conexión a Internet y no depende del microservicio local. Se accede desde la opción Discapacidad e inclusión del Drawer.

## 🚀 Cómo ejecutar el proyecto

### Requisitos previos

- Android Studio compatible con AGP 9.4.1 y Gradle 9.8.0.
- JDK compatible con la configuración de Gradle; backend configurado para Java 17.
- Android SDK API 37 (compileSdk y targetSdk declarados); minSdk 24.
- Emulador Android compatible, preferentemente Google Pixel 8.
- Internet para descargar dependencias y consultar la API externa.

### 1. Clonar y abrir

```powershell
git clone https://github.com/carllabbe-dot/corpaliv-app-eq13.git
cd corpaliv-app-eq13
```

Abrir la raíz en Android Studio y sincronizar Gradle. Si se usa el ZIP de entrega, descomprimirlo y abrir la carpeta `corpalivappeq13`.

### 2. Ejecutar el backend primero

Desde la raíz del proyecto, en PowerShell:

```powershell
cd backend\ms-solicitudes
.\mvnw.cmd spring-boot:run
```

Alternativamente, abrir el módulo backend en IntelliJ IDEA y ejecutar `MsSolicitudesApplication.java`. Mantener el servidor activo en el puerto 8081.

Verificar desde el navegador: `http://localhost:8081/api/solicitudes`.

### 3. Ejecutar Android

En Android Studio, seleccionar el módulo `app`, iniciar el emulador y presionar Run. La aplicación usa `http://10.0.2.2:8081/` para comunicarse con Spring Boot desde el emulador.

El backend **no se inicia automáticamente** con Android ni está incorporado en el APK. En un teléfono físico se requiere configurar una dirección de servidor accesible desde ese dispositivo y recompilar, o configurar una conexión equivalente.

### 4. Consultar H2

Con el servidor activo, abrir `http://localhost:8081/h2-console`.

| Campo | Valor |
|---|---|
| Driver Class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:file:./data/corpalivdb` |
| User Name | `sa` |
| Password | Vacío (configuración local académica) |

```sql
SELECT * FROM SOLICITUDES;
```

## 🧪 Pruebas unitarias

El reporte incluido en el ZIP, generado el 24 de noviembre de 2026, registra 18 pruebas aprobadas, 0 fallos, 0 omitidas y 100% de éxito en 5,375 segundos.

| Clase | Pruebas aprobadas | Alcance |
|---|---:|---|
| UsuarioViewModelTest | 7 | Validaciones del formulario |
| SolicitudRepositoryTest | 7 | CRUD, lista vacía y error de eliminación mediante mocks |
| MainViewModelTest | 3 | Eventos de navegación |
| ExampleUnitTest | 1 | Prueba de ejemplo |
| **Total** | **18** | |

Ejecutar desde la raíz Android:

```powershell
.\gradlew.bat testDebugUnitTest
```

Abrir el reporte:

```powershell
start .\app\build\reports\tests\testDebugUnitTest\index.html
```

El porcentaje de pruebas exitosas no equivale a cobertura de código. El indicador de pruebas unitarias fue acreditado por el docente según lo informado por el integrante; no se declara un porcentaje de cobertura no respaldado por el reporte.

## 📦 APK firmado

- Variante: release.
- Versión: 1.0 (versionCode 1).
- APK incluido: `app/release/app-release.apk`.
- Almacén de claves: `corpaliv.jks`, ubicado en la raíz del ZIP de entrega, fuera de la carpeta del proyecto Android.

Se generó desde **Build → Generate Signed App Bundle or APK → APK → release**. Para generar una actualización, utilizar la misma llave y alias correspondientes. No publicar la llave privada ni sus contraseñas en GitHub.

Para instalar en un emulador con ADB configurado:

```powershell
adb install -r .\app\release\app-release.apk
```

Si existe una instalación debug firmada con otra llave, puede ser necesario desinstalarla primero (esto elimina sus datos locales). Verificar la firma con `apksigner verify --verbose --print-certs` del Android SDK Build Tools antes de la entrega.

El APK y el JKS están presentes en el ZIP revisado; la comprobación criptográfica de la firma y la instalación deben realizarse antes de la entrega definitiva.

## 🌿 Control de versiones y Trello

Repositorio: https://github.com/carllabbe-dot/corpaliv-app-eq13

Convención propuesta de commits: `feat`, `fix`, `refactor`, `docs`, `test` y `chore`, con descripciones del cambio. Ejemplo: `feat(api): integrar catálogo público de discapacidad`.

Tablero Trello: **[Agregar enlace real del tablero]**. Adjuntar evidencia de tareas, responsables y avances. No se ha verificado el estado remoto del repositorio ni del tablero.

## 📸 Evidencias de ejecución

Agregar capturas reales en `screenshots/` antes de entregar. Las siguientes rutas son propuestas, no archivos incluidos actualmente:

| Evidencia | Archivo sugerido |
|---|---|
| Inicio y Drawer | `screenshots/inicio.png` |
| Formulario validado | `screenshots/formulario.png` |
| Creación, consulta, edición y eliminación | `screenshots/crud.png` |
| API externa en ejecución | `screenshots/api-externa.png` |
| Consola H2 con datos de prueba | `screenshots/h2.png` |
| Reporte de pruebas | `screenshots/tests.png` |
| Generación del APK firmado | `screenshots/apk-firmado.png` |
| Archivo .jks (sin mostrar contraseñas) | `screenshots/keystore.png` |
| Trello y commits | `screenshots/gestion.png` |

## 📌 Trazabilidad — Evaluación Parcial 4

| Indicador | Evidencia |
|---|---|
| Interfaz, navegación y formularios | Pantallas Compose y validaciones |
| Microservicio y base de datos | Spring Boot, JPA y H2 |
| Integración CRUD | Formulario, solicitudes, Retrofit y endpoints |
| API externa | Catálogo CKAN en DiscapacidadScreen |
| Pruebas unitarias | Reporte Gradle y clases de prueba |
| APK firmado | APK release y JKS, pendientes de verificación final |
| Gestión del proyecto | GitHub, Trello y README; evidencias por completar |

## 🔒 Privacidad y limitaciones

Proyecto académico: utilizar datos ficticios en las solicitudes. Los recursos públicos de discapacidad provienen del Ministerio. La clave del formulario se valida localmente y no se incluye en el DTO remoto. No equivale a un sistema de autenticación.

El backend HTTP y la consola H2 sin contraseña son configuraciones de demostración local, no apropiadas para producción. Antes de un despliegue real se requiere autenticación, autorización, HTTPS, protección de datos y una política de sincronización y almacenamiento de imágenes. No publicar datos personales, contraseñas ni claves privadas.

## 📄 Licencia

Proyecto académico desarrollado para DSY1105 — Desarrollo de Aplicaciones Móviles. Uso educativo.

---

Equipo 13 · DSY1105 · 2026
