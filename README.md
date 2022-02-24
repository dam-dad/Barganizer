# Barganizer

Aplicación de gestión de un negocio de hostelería.

![Image text](https://github.com/dam-dad/Barganizer/blob/main/src/main/resources/images/barganizer.PNG)



## Miembros del grupo

------

- Daniel Cabrera Cabrera
- Rubén Pablo Jorge Díaz
- Marvin N'gabu García

## Introducción 

:fork_and_knife: Barganizer es una aplicación destinada al entorno de la hostelería.  En concreto, hemos orientado nuestra app a la gestión de un restaurante que pretenda controlar desde sus empleados hasta la gestión de las mesas del local, así como la carta con todos sus productos. :fork_and_knife:



## Herramientas de apoyo para el desarrollo

- Herramienta de gestión y construcción de proyectos: Maven
- Herramienta de mapeo objeto-relacional: Hibernate
- Hosting privado para el almacenamiento de la base de datos.
- Generador de reportes e informes: JasperReports 
- Plugin que permite empaquetar aplicaciones Java: JavaPackager



## Guía de uso

#### Login

La primera ventana al iniciar Barganizer será el "Login". El usuario deberá acceder con su nombre de usuario y su contraseña para poder entrar a la aplicación. Desde dentro de la aplicación dispondremos de una pestaña para insertar nuevos empleados que tengan acceso a la aplicación.

![login](https://user-images.githubusercontent.com/90828057/155431594-13d82afc-3aee-424b-8966-9f07f91a20e0.PNG)



#### Vista principal

Una vez el usuario se ha  hecho "login" de manera exitosa, tendrá acceso al total de funcionalidades de la aplicación.  La vista principal controla el grueso del funcionamiento de un restaurante. El usuario podrá cerrar la cesión al presionar "Cerrar cesión".

La vista principal nos permite acceder a todas las pestañas de la aplicación, visualizar el usuario logeado y ver la fecha y hora actual. Desde ella, se puede visualizar todos los platos, bebidas y postres que disponga en cada carta definida. A su vez, podrá crear comandas para cada mesa que seleccione. Las mesas que se visualizan son aquellas que están ocupadas en ese momento. 

Podremos generar el ticket o factura de las mesas que dispongan de elementos en su comanda, eliminando la comanda tras ello y quitando las mesas de la lista de mesas ocupadas.

##### Especificaciones: 

- Debe tener una mesa seleccionada para insertar platos, bebidas o postres.
- Al hacer doble click sobre un plato, bebida o postre se añadirá a la comanda de la mesa seleccionada.
- La mesa seleccionada se marcará en verde, igual que el elemento de la carta seleccionado.
- Se podrán borrar elementos de la comanda.

![inicio](https://user-images.githubusercontent.com/90828057/155436229-399c85cf-c74c-4f0b-b83f-652d1852258a.PNG)


#### Mesas

La pestaña "Mesas" será la encargada gestionar las mesas del local.

Desde ella se podrá visualizar en color verde las mesas que están actualmente ocupadas y en color rojo las mesas que están libres. Podremos seleccionar una mesa para poder modificar la cantidad de personas que permite o si está ocupada o deja de estarlo. De igual manera se podrán añadir todas las mesas requeridas.

##### Especificaciones: 

- Debe tener una mesa seleccionada para eliminar y modificar mesas.
- No podrá borrar mesas con comandas activas.
- La mesa seleccionada se marcará en azul.

![mesas](https://user-images.githubusercontent.com/90828057/155431979-890eb903-ec7e-47ea-8009-ad3130e57d6c.PNG)



#### Reservas

La pestaña "Reservas" permitirá gestionar las reservas del restaurante. Se podrá añadir, eliminar y modificar las reservas del restaurante. 

Al acceder a la pestaña se listarán todas las reservas en pantalla.

##### Especificaciones: 

- Debe tener una reserva seleccionada para eliminar y modificar mesas.
- La reserva seleccionada se marcará en naranja.
- Las reservas se asignan a un empleado, a uno hora y día determinado.

![reservas](https://user-images.githubusercontent.com/90828057/155431972-642f1850-fe50-4b26-86e8-e13a6771c42a.PNG)



#### Carta

La pestaña "Carta" nos permite gestionar tanto la carta como los platos que la conforman.

 El empleado podrá crear las cartas que considere, así como eliminarlas y gestionar cada una añadiendo todos los platos, bebidas o postres que requiera.

##### Especificaciones: 

- Debe tener una carta seleccionada para poder añadir o borrar platos.
- Si desea borrar una carta se borrarán todos los platos asignados a ella.
- Para modificar un plato debe hacer doble click sobre él.


![carta](https://user-images.githubusercontent.com/90828057/155436252-7ef514f2-d19f-4d6b-a455-814cc73f2062.PNG)



#### Empleado 

La pestaña "Empleado" será la encargada de la gestión de empleados del sistema. Los empleados son los que tendrán acceso mediante el "login" a la App. 

##### Especificaciones: 

- Al añadir empleado se añade con datos pre-definidos.
- Para modificar un empleado debe seleccionarlo en la lista, lo que habilitará el formulario de la derecha. En el formulario puede modificar los datos deseados y debe pulsar el botón "Modificar".

![empleado](https://user-images.githubusercontent.com/90828057/155436269-d4db8ecb-cd86-4a51-ab20-8e50fdb3f2fe.PNG)

