<?php

// Incluye el archivo de configuración
include('config.php');

try {
    // Crear una instancia de PDO para la conexión a la base de datos
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);

    // Establecer el modo de errores de PDO a excepciones
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Consulta SQL para obtener todos los registros de la tabla "usuarios"
    $sql = "SELECT NombreUsuario, Contrasena, EmpleadoID FROM Usuarios";
    $stmt = $conn->query($sql);

    // Crear el documento XML de respuesta
    header('Content-Type: text/xml');
    $xml = new SimpleXMLElement('<respuesta/>');

    // Recorrer los registros y agregarlos al XML
    foreach ($stmt as $row) {
        $usuario = $xml->addChild('usuario');
        $usuario->addChild('nombreUsuario', $row['NombreUsuario']);
        $usuario->addChild('contrasena', $row['Contrasena']);
        $usuario->addChild('empleadoid', $row['EmpleadoID']);
    }

    // Imprimir el documento XML de respuesta
    echo $xml->asXML();
} catch (PDOException $e) {
    // En caso de error en la conexión o consulta, manejar la excepción
    die("Error en la conexión: " . $e->getMessage());
}
?>
