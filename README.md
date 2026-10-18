\# 📱 CORPALIV App — Equipo 13



Aplicación móvil institucional y de catálogo social para \*\*CORPALIV\*\* (Corporación de Padres y Amigos por el Limitado Visual), desarrollada en \*\*Android nativo con Kotlin y Jetpack Compose\*\* como parte de la asignatura \*\*DSY1105 — Desarrollo de Aplicaciones Móviles\*\* (Evaluación Parcial 2).



\---



\## 📋 Descripción del proyecto



\*\*CORPALIV App\*\* es una aplicación móvil que permite a los usuarios:



\- Conocer la misión, programas e impacto social de CORPALIV.

\- Explorar un catálogo de productos artesanales y servicios de \*\*Alba Lab\*\*.

\- Enviar solicitudes de colaboración (donaciones, alianzas, voluntariado, compras) mediante un formulario reactivo con validación en tiempo real.

\- Visualizar un resumen de su solicitud antes de confirmarla.

\- Guardar solicitudes, productos favoritos y consultar un historial local.

\- Calcular el impacto social de una donación simulada.

\- Acceder a recursos nativos del dispositivo (cámara y geolocalización).

\- Compartir fichas de productos o causas con otras aplicaciones.



Toda la persistencia es \*\*local\*\* (Room + SQLite), sin backend remoto. La información mostrada es \*\*sintética\*\* (no se usan datos reales de beneficiarios, conforme a RNF6).



\---



\## 👥 Integrantes del equipo



| Nombre | Rol | GitHub |

|---|---|---|

|Carlos Labbe | 

|Damian Marro | 



\*\*Equipo:\*\* N° Grupo 13

\*\*Sección:\*\* DSY1105\_001V 

\*\*Docente:\*\* Nicolas Andres Muñoz Mora



\---



\## 🎯 Contexto del cliente



\*\*CORPALIV\*\* es una corporación sin fines de lucro que trabaja con personas con discapacidad visual. A través de \*\*Alba Lab\*\*, comercializa productos artesanales y ofrece talleres y servicios. La app busca digitalizar la captación de solicitudes de colaboración y dar visibilidad a su impacto social.



\---



\## 🛠️ Tecnologías utilizadas



| Categoría | Tecnología |

|---|---|

| Lenguaje | Kotlin |

| UI | Jetpack Compose + Material Design 3 |

| Arquitectura | MVVM + UDF (Unidirectional Data Flow) |

| Gestión de estado | ViewModel + StateFlow + SharedFlow (eventos) |

| Navegación | Navigation Compose |

| Persistencia local | Room (SQLite) |

| Carga de imágenes | Coil |

| Recursos nativos | CameraX / ActivityResultContracts + Fused Location Provider |

| Animaciones | Compose Animation API |

| Control de versiones | Git + GitHub |

| Gestión de tareas | Trello |



\---



\## 🏗️ Arquitectura



El proyecto sigue el patrón \*\*MVVM\*\* con \*\*flujo unidireccional de datos (UDF)\*\* y una navegación basada en \*\*eventos\*\* emitidos por el `ViewModel`.



```

UI (Compose)  ──eventos──►  ViewModel  ──acciones──►  Repository  ──►  Room / Recursos nativos

&#x20;             ◄──StateFlow──                        ◄── Flow ──

```



\*\*Estructura de paquetes:\*\*



```

com.example.corpalivappeq13/

├── MainActivity.kt              // Punto de entrada + NavHost

├── data/                        // Room: Entity, DAO, Database, Repository

├── domain/                      // Modelos: Product, Request, Favorite

├── ui/

│   ├── screens/                 // Una carpeta por pantalla

│   ├── components/              // Composables reutilizables

│   └── theme/                   // Colores, tipografía, formas (M3)

├── navigation/                  // Screen.kt, NavigationEvent.kt

└── viewmodel/                   // Un ViewModel por pantalla + SharedViewModel

```



\---



\## ✅ Funcionalidades implementadas



> ⚠️ \*\*TODO:\*\* Actualizar esta sección a medida que avance el proyecto. Marca `\[x]` solo cuando esté \*\*funcionando y probado\*\*.



\### Navegación

\- \[x] Navegación entre pantallas con `NavHost` y eventos del `ViewModel`

\- \[ ] Transiciones animadas entre pantallas



\### Pantallas

\- \[x] Pantalla de Inicio (estructura básica)

\- \[ ] Pantalla de Inicio con métricas e historias de impacto (RF1)

\- \[ ] Catálogo de productos y servicios (RF2)

\- \[ ] Formulario reactivo de contacto (RF3)

\- \[ ] Pantalla de resumen de solicitud (RF6)

\- \[ ] Historial de solicitudes (RF12)

\- \[ ] Calculadora de impacto social (RF11)

\- \[ ] Pantalla de perfil / ajustes



\### Formularios y validación

\- \[ ] Validaciones en tiempo real en el ViewModel (RF4)

\- \[ ] Retroalimentación visual con `isError` y `supportingText` (RF5)

\- \[ ] Persistencia del formulario ante rotación de pantalla (RNF11)



\### Persistencia local

\- \[ ] Configuración de Room (Entity, DAO, Database) (RF7)

\- \[ ] Guardado de solicitudes de contacto (RF7)

\- \[ ] Guardado de productos favoritos (RF10)

\- \[ ] Consulta del historial de solicitudes (RF12)



\### Recursos nativos

\- \[ ] Acceso a la cámara del dispositivo (RF8)

\- \[ ] Acceso a geolocalización (RF8)



\### Catálogo

\- \[ ] Búsqueda por nombre (RF9)

\- \[ ] Filtro por categoría (RF9)

\- \[ ] Compartir ficha de producto con `ACTION\_SEND` (RF14)



\### UX / Pulido

\- \[ ] Animaciones de transición y microinteracciones (RNF5)

\- \[ ] Tema oscuro/claro dinámico (RNF8)

\- \[ ] Accesibilidad: `contentDescription` en íconos e imágenes (RNF10)

\- \[ ] Manejo de errores con Snackbar (RNF13)

\- \[ ] AlertDialog de confirmación al eliminar (RF13)



\---



\## 🚀 Cómo ejecutar el proyecto



\### Requisitos previos



\- \*\*Android Studio\*\* Ladybug (2024.2.1) o superior.

\- \*\*JDK 17\*\* o superior.

\- \*\*Android SDK\*\* con API 34 (Android 14) y mínimo \*\*API 26\*\*.

\- \*\*Emulador configurado como Google Pixel 8\*\* (dispositivo objetivo del proyecto).

\- Conexión a internet para la primera sincronización de Gradle.



\### Pasos



1\. \*\*Clonar el repositorio:\*\*

&#x20;  ```bash

&#x20;  git clone https://github.com/carllabbe-dot/corpaliv-app-eq13.git

&#x20;  ```



2\. \*\*Abrir el proyecto en Android Studio:\*\*

&#x20;  - `File → Open` → seleccionar la carpeta clonada.



3\. \*\*Sincronizar Gradle:\*\*

&#x20;  - Esperar a que Gradle descargue las dependencias (primera vez puede tardar unos minutos).



4\. \*\*Configurar el emulador:\*\*

&#x20;  - `Tools → Device Manager → Create Device → Pixel 8 → API 34`.



5\. \*\*Ejecutar:\*\*

&#x20;  - Presionar \*\*Run ▶️\*\* (Shift + F10).

&#x20;  - La app se abrirá en el emulador.



\### Notas



\- La app otorgará permisos de \*\*cámara\*\* y \*\*ubicación\*\* en tiempo de ejecución. Deben aceptarse para probar las funcionalidades nativas.

\- No se requiere backend ni configuración adicional.



\---



\## 🌿 Flujo de trabajo en Git



Seguimos una convención de \*\*ramas por funcionalidad\*\* (`feature/\*`) y \*\*commits descriptivos en español técnico\*\*.



\### Ramas



\- `main` → rama estable (solo merge de ramas revisadas).

\- `feature/<nombre-funcionalidad>` → una rama por requisito implementado. Ejemplos:

&#x20; - `feature/rf03-formulario-contacto`

&#x20; - `feature/rf07-room-persistencia`

&#x20; - `feature/rf08-camara-geolocalizacion`



\### Convención de commits



```

<tipo>(<alcance>): <descripción corta en imperativo>



<cuerpo opcional>

```



\*\*Tipos permitidos:\*\* `feat`, `fix`, `refactor`, `style`, `docs`, `chore`, `test`.



\*\*Ejemplos:\*\*

\- `feat(form): implementar validación de email en FormViewModel`

\- `fix(nav): corregir doble navegación al volver desde resumen`

\- `docs(readme): agregar instrucciones de ejecución`



\---



\## 🗂️ Gestión de tareas — Trello



El tablero de planificación está disponible en:



🔗 ⚠️ TODO: pegar aquí el enlace público del tablero de Trello



Columnas utilizadas: \*\*Backlog → To Do → In Progress → Review → Done\*\*.



\---



\## 📸 Capturas de pantalla



> ⚠️ TODO: agregar capturas a medida que avance el proyecto. Se recomienda crear la carpeta `/screenshots` en el repo.



| Pantalla | Captura |

|---|---|

| Inicio | ⚠️ TODO |

| Catálogo | ⚠️ TODO |

| Formulario | ⚠️ TODO |

| Resumen | ⚠️ TODO |

| Historial | ⚠️ TODO |



\---



\## 📌 Requisitos del encargo (trazabilidad)



Este proyecto busca cumplir los 14 requisitos funcionales (RF1–RF14) y los 13 no funcionales (RNF1–RNF13) definidos en el documento de encargo de la asignatura DSY1105. El detalle completo de cada requisito se encuentra en el documento de especificación del equipo.



\---



\## 🔒 Consideraciones de privacidad



Conforme a \*\*RNF6\*\*, todo dato mostrado en la app es \*\*sintético o anonimizado\*\*. No se utilizan RUTs, fotografías ni información real de beneficiarios.



\---



\## 📄 Licencia



Proyecto académico desarrollado para la asignatura \*\*DSY1105 — Desarrollo de Aplicaciones Móviles\*\*.  

Uso educativo. Todos los derechos reservados a sus autores y a \*\*CORPALIV\*\*.



\---



<p align="center">

&#x20; Hecho con ❤️ por el Equipo 13 · DSY1105 · 2026

</p>

