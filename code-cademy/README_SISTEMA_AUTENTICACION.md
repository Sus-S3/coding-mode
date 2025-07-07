# Sistema de Autenticación e Inscripción - CodeAcademy

## Resumen de Cambios Implementados

Se ha implementado un sistema completo de autenticación e inscripción que reemplaza el sistema anterior basado en localStorage por uno real que usa base de datos y sesiones del servidor.

### Archivos Creados/Modificados:

#### Backend (Java/Servlets):
1. **UsuarioDAO.java** - Implementación completa con métodos para:
   - Registrar usuarios
   - Autenticar usuarios
   - Verificar existencia de usuarios
   - Crear admin por defecto

2. **InscripcionDAO.java** - Implementación completa con métodos para:
   - Registrar inscripciones
   - Verificar si un usuario está inscrito
   - Obtener inscripciones por usuario/curso
   - Cancelar inscripciones

3. **Servlets creados:**
   - `LoginServlet.java` - Maneja autenticación
   - `RegisterServlet.java` - Maneja registro de usuarios
   - `LogoutServlet.java` - Maneja cierre de sesión
   - `InscribirCursoServlet.java` - Maneja inscripción a cursos

4. **Modelos actualizados:**
   - `Inscripcion.java` - Agregado constructor vacío y campo estado

#### Frontend (JSP/JavaScript):
1. **courses.jsp** - Actualizado para:
   - Mostrar estado de autenticación en navbar
   - Botones de inscripción condicionales
   - JavaScript para llamar al backend

2. **login.jsp** - Actualizado para usar backend real
3. **register.jsp** - Actualizado para usar backend real

## Cómo Probar el Sistema

### 1. Preparación de la Base de Datos

Asegúrate de que tu base de datos MySQL esté corriendo y que las credenciales en `ConexionBD.java` sean correctas:

```java
private static final String URL_DB = "jdbc:mysql://localhost:3306/codeacademy";
private static final String USER_DB = "root";
private static final String PASSWORD_DB = "SusanaSuarez";
```

### 2. Ejecutar Pruebas del Sistema

Compila y ejecuta la clase de prueba:

```bash
cd code-cademy
javac -cp "lib/*" src/main/java/com/sus/codecademy/TestSistemaCompleto.java
java -cp "lib/*:src/main/java" com.sus.codecademy.TestSistemaCompleto
```

Esto debería:
- Verificar la conexión a la base de datos
- Crear las tablas necesarias si no existen
- Crear el usuario administrador por defecto
- Probar la funcionalidad básica

### 3. Probar la Aplicación Web

1. **Desplegar la aplicación** en tu servidor (Tomcat, etc.)

2. **Acceder a la aplicación** y probar:

   **Registro de Usuario:**
   - Ve a `register.jsp`
   - Completa el formulario con datos válidos
   - Verifica que se guarde en la base de datos

   **Login:**
   - Ve a `login.jsp`
   - Usa las credenciales del admin: `admin@codeacademy.com` / `admin123`
   - O usa un usuario que hayas registrado

   **Inscripción a Cursos:**
   - Ve a `courses.jsp` (debe estar autenticado)
   - Haz clic en "Inscribirme" en cualquier curso
   - Verifica que se guarde la inscripción en la base de datos

### 4. Verificar en la Base de Datos

Puedes verificar que todo funcione correctamente consultando las tablas:

```sql
-- Ver usuarios registrados
SELECT * FROM usuarios;

-- Ver inscripciones
SELECT * FROM inscripciones;

-- Ver inscripciones con información de usuario y curso
SELECT i.*, u.nombre as nombre_usuario, c.nombre as nombre_curso
FROM inscripciones i
JOIN usuarios u ON i.idUsuario = u.idUsuario
JOIN cursos c ON i.idCurso = c.idCurso;
```

## Funcionalidades Implementadas

### ✅ Autenticación Completa
- Registro de usuarios en base de datos
- Login con validación de credenciales
- Sesiones del servidor (no más localStorage)
- Logout funcional

### ✅ Inscripción a Cursos
- Botones de inscripción solo visibles para usuarios autenticados
- Validación de inscripciones duplicadas
- Almacenamiento en base de datos
- Mensajes de éxito/error

### ✅ Seguridad
- Validación de sesión en servlets
- Redirección automática a login si no autenticado
- Manejo de errores robusto

### ✅ Usuario Administrador
- Se crea automáticamente al iniciar la aplicación
- Credenciales: `admin@codeacademy.com` / `admin123`
- Rol de administrador para futuras funcionalidades

## Estructura de la Base de Datos

### Tabla `usuarios`:
```sql
CREATE TABLE usuarios (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    pais VARCHAR(50) NOT NULL,
    rol VARCHAR(20) NOT NULL DEFAULT 'estudiante',
    password VARCHAR(100) NOT NULL
);
```

### Tabla `inscripciones`:
```sql
CREATE TABLE inscripciones (
    idInscripcion INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT NOT NULL,
    idCurso INT NOT NULL,
    fechaInscripcion DATE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'activa',
    FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario),
    FOREIGN KEY (idCurso) REFERENCES cursos(idCurso)
);
```

## Próximos Pasos Sugeridos

1. **Encriptación de contraseñas** - Implementar hash de contraseñas
2. **Validación de formularios** - Agregar validaciones más robustas
3. **Gestión de perfil** - Permitir editar información del usuario
4. **Panel de administrador** - Para gestionar usuarios y cursos
5. **Historial de inscripciones** - Mostrar cursos inscritos al usuario

## Solución de Problemas

### Error de Conexión a Base de Datos
- Verifica que MySQL esté corriendo
- Confirma las credenciales en `ConexionBD.java`
- Asegúrate de que la base de datos `codeacademy` exista

### Error de Compilación
- Verifica que todas las dependencias estén en el classpath
- Asegúrate de que los archivos JAR de MySQL estén incluidos

### Error de Inscripción
- Verifica que existan cursos en la tabla `cursos`
- Confirma que el usuario esté autenticado
- Revisa los logs del servidor para errores específicos 