# SERVICIO TIPO DE CAMBIO

## DESCRIPCION FUNCIONAL
Este es un servicio de intercambio de monedas. Para ello se requiere especificar el tipo de moneda con el que va comprar y el tipo de moneda que desea recibir así como tambien puede visualizar tipo de cambio por el cual se procesara la transacción. El servicio cumple las siguientes funciones:
- Lista las distintas monedas de cambio: Monedas Origen, Monedas Destino y el Valor de Tipo Cambio.
- Realiza la transacción.
 - Especificar la información a ingresar: Moneda Origen, Moneda Destino y el Monto Entrante.
 - Información recibida: Moneda Origen, Moneda Destino, Monto Entrante, Monto a recibir y el Valor de Tipo de Cambio.
- Actualiza el Valor de Tipo de Cambio de acuerdo a las monedas de intercambio.
- Manejo de token de sesión para las transacciones.

## DESCRIPCION TECNICA

### Ejecutar la Aplicación sobre un Contenedor
Para ejecutar la aplicación debes realizar los siguientes pasos:
1. Tener instalado las siguientes herramientas en la Laptop o PC:
 - Maven
 - Docker
 - Postman (Testear aplicación)
2. Crear un folder en el directorio de su preferencia. Ingresar al folder y desde ahí abrir el terminal. A continuación escribir el siguiente comando para descargar las fuentes del GitHub:

 `git clone https://github.com/nilton-arotingo/tipo-cambio.git`
 
3. Ingresar al proyecto por el terminal:

 `cd tipo-cambio/tipocambio-service`

4. Ejecutar los 2 comandos Maven para compilar el proyecto:

 `mvn clean`

 `mvn install`
 
5. Ejecutar los 2 comandos Docker:

 `docker build -t tipocambio-service.jar .`
 
 `docker run -p 8080:8080 tipocambio-service.jar`

6. Abrir el Postman y colocar los siguientes Enpoint especificados más abajo. Para los servicios Procesar Cambio y Actualizar Tipo Cambio pasar el parametro **token** en el header con ese nombre y su respectivo valor encriptado el cual se obtuvo de Listar.

------------



Este servicio consta de 3 endpoint:
### Listar Monedas con Tipo de Cambio: Method **GET**
 **Request**: 

http://localhost:8080/tata/tipocambio
 
 **Response**:
 
 *Header*: Retorna un token que tiene un tiempo de expiración de 60 seg. y un valor encriptado que simula al usuario que esta realizando la trasacción.
 

    [
        {
            "monedaOrigen": "PEN",
            "monedaDestino": "USD",
            "tipoCambio": 3.9
        },
        {
            "monedaOrigen": "USD",
            "monedaDestino": "PEN",
            "tipoCambio": 3.6
        },
        {
            "monedaOrigen": "PEN",
            "monedaDestino": "EUR",
            "tipoCambio": 4.9
        },
        {
            "monedaOrigen": "EUR",
            "monedaDestino": "PEN",
            "tipoCambio": 4.4
        },
        {
            "monedaOrigen": "USD",
            "monedaDestino": "EUR",
            "tipoCambio": 1.19
        },
        {
            "monedaOrigen": "EUR",
            "monedaDestino": "USD",
            "tipoCambio": 0.84
        }
    ]

### Procesar Cambio de Moneda: Method **POST**
 **Request**: 

http://localhost:8080/tata/tipocambio

*Header*: Pasar el token de sesión que devolvio el servicio listar

    {
        "monto": 100,
        "monedaOrigen": "USD",
        "monedaDestino": "PEN"
    }
 
 **Response**:
 

    {
        "monto": 100,
        "montoConTipoCambio": 360,
        "monedaOrigen": "USD",
        "monedaDestino": "PEN",
        "tipoCambio": 3.6
    }

### Actualizar el Tipo Cambio: Method **POST**

**Request**: 

http://localhost:8080/tata/tipocambio/actualizacion

*Header*: Pasar el token de sesión que devolvio el servicio listar

    {
        "monedaOrigen": "USD",
        "monedaDestino": "PEN",
        "tipoCambio": 4
    }

**Response**:

    {
        "mensaje": "Actualizacion exitosa!"
    }

 *En caso el token de sesión es invalido, mostrara el siguiente resultado con status code 403:*


    {
        "mensaje": "La sesion es invalida"
    }

------------


### Base de Datos H2 
Para visualizar la información de la tabla en la base de datos embebida ingresar a siguiente url y colocar los valores especificados:
- http://localhost:8080/tata/h2-console
- Especificar el JDBC URL de acuerdo al archivo **application.properties**
- Colocar el User Name de acuerdo al archivo **application.properties**
- Colocar el Password de acuerdo al archivo **application.properties**

------------



