# PasarelaPagos

Pasarela de pagos en consola desarrollada en Java.  
Permite cobrar un importe mediante **efectivo**, **cuenta bancaria (CCC)** o **tarjeta de crédito**, validando los datos introducidos por el usuario.

## Características

- Solicitud de importe con validación (no se aceptan valores <= 0).
- Menú en consola con tres métodos de pago:
  - Pago en efectivo: cálculo de cambio en billetes y monedas.
  - Pago por cuenta bancaria (CCC): cálculo y verificación de dígitos de control.
  - Pago con tarjeta de crédito: detección de tipo (American Express, VISA, MasterCard) y validación con algoritmo de Luhn.
- Estructura de código basada en buenas prácticas vistas en Entornos de Desarrollo:
  - Nombres de variables y métodos descriptivos.
  - Comentarios no obvios (explican el porqué, no lo evidente).
  - JavaDoc en clases y métodos principales.
  - Proyecto versionado con Git y workflow profesional.

## Requisitos

- Java JDK 17 o superior (recomendado).
- Eclipse IDE para Java Developers (cualquier versión reciente).
- Git instalado (para el control de versiones).

## Cómo ejecutar el proyecto (Eclipse)

1. Clonar el repositorio o importarlo en Eclipse:
   - `File → Import → Existing Projects into Workspace`.
2. Asegurarse de que el proyecto compila sin errores.
3. En el archivo `PasarelaPagos.java`, botón derecho:
   - `Run As → Java Application`.
4. Seguir las instrucciones que aparecen en la consola.

## Workflow Git utilizado

- Rama `main`: contiene la versión estable del proyecto (lista para entregar).
- Rama `develop`: rama de desarrollo donde se integran las nuevas funcionalidades.
- Ramas `feature/*`: una rama por funcionalidad nueva.  
  Ejemplos:
  - `feature/pago-tarjeta`
  - `feature/validacion-ccc`

Flujo típico:

1. Crear rama de feature desde `develop`.
2. Programar y hacer commits pequeños y descriptivos.
3. Merge de la feature en `develop` cuando está lista.
4. Cuando la versión es estable, hacer merge de `develop` en `main`.

## Autor

- Nombre: Alejandro Silvestre  
- Asignatura: Entornos de Desarrollo  
- IDE: Eclipse
