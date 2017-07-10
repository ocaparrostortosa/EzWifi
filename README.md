# EzWifi 

![Bandera españa](http://www.stiftsgymnasium-melk.org/nawi/Physik/Team-Physik/files/stacks-image-3e7032f.png) Español 
==================


Aplicación de Android utilizada para guardar datos de redes wifi en función de un usuario registrado soportado por una base de datos Firebase.

Creada por Óscar Caparrós de forma autodidacta para aprender Android y establecer unas bases para el segundo curso del C.F.G.S. de Desarrollo de
 Aplicaciones Multiplataforma del instituto I.E.S. Virgen del Carmen.
 
 La aplicación fue diseñada para satisfacer una necesidad propia y de algunos familiares y amigos. La necesidad consistía en guardar las contraseñas
 de las claves WiFi para poder en el futuro tener una copia de las claves en la nube en caso de un formateo del móvil o del borrado de la clave.
 Como parte de la extensión del proyecto me gustaría obtener del registro del móvil las claves ya guardadas y poder subirlas a la nube.
 
 El uso básico de la aplicación es:
 
 - Registrarse: Permite crear un nuevo usuario en la base de datos y automáticamente iniciar sesión con dicho usuario.
 - Loguearse: Permite iniciar sesión dependiendo de un usuario y una contraseña.
 - Guardar clave: Te permite guardar un lugar, nombre y contraseña para la red WiFi que será almacenada en una base de datos de Firebase
 en el apartado del usuario logueado.
 - Consultar clave: Te permite, dependiendo del usuario, obtener todas las claves guardadas hasta ese momento por el usuario y a la vez copiar 
 la clave en el portapapeles.
 - Otras acciones: Como por ejemplo el bloquear la aplicación si no se tiene una conexión a internet, asegurarse de que los datos del usuario puedan ser
 lo más claro posibles mediante expresiones regulares, varios mensajes hacia el cliente para mejorar su experiencia de usuario, étc.
 
 Problemas conocidos
 -------------------------------------------
 En primer lugar varios de los problemas conocidos son derivados de la falta de tiempo a la hora de hacer el proyecto, igualmente serán corregidos cuando tenga
 tiempo suficiente.
 - Un problema por ejemplo sería tener una conexión WiFi o red de datos de baja estabilidad ya que tardaría mucho en obtener los datos de la base de datos.
 - Un segundo problema sería que los datos del usuario se guardarán en una base de datos pública por lo que cualquiera podría tener acceso a ellos.
 - Los TextFields no tienen control sobre su longitud por lo que el usuario podrá guardar datos tan largos como sea posible.
 - La longitud de la tabla es mayor que la anchura de los dispositivos en la mayoría de los casos por lo que sugiero al usuario girar el dispositivo para una mejor
 experiencia de usuario.
 
 Aplicaciones y servicios usados
 -------------------------------------------
 ![Android Studio](https://3.bp.blogspot.com/-BVQ36vhFc0I/VsOpgnJmD-I/AAAAAAAAAFk/Z4BOOKmbxJ4/s1600/banner.PNG) 
 
 ![Firebase](https://media.licdn.com/mpr/mpr/AAEAAQAAAAAAAAuEAAAAJDllZmUxNmM0LTZiMWEtNGFiNi04ZTUwLTI5ZTcxOGFjZWNhMA.png)
 
 Agradecimientos a la comunidad de:
 
 ![Stackoverflow](https://upload.wikimedia.org/wikipedia/ro/f/f7/Stack_Overflow_logo.png)
 
 

![Bandera inglaterra](http://www.jabarprov.go.id/assets_front/images/english.png) English
==================


Android application used to save data from wifi networks depending on a registered user supported by a Firebase database.
