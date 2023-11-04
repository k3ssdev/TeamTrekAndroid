<?php
// Incluye el archivo de configuración
include('config.php');

try {
    // Crear una instancia de PDO para la conexión a la base de datos
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    
    // Establecer el modo de errores de PDO a excepciones
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    // Obtener los datos del formulario
    $usuario = $_POST["usuario"];
    $contrasena = $_POST["contrasena"];
    
    // Simular valores de usuario y contraseña para la prueba
    //$usuario = '';
    //$contrasena = '';
    
    // Validar y filtrar el valor del usuario (usaremos FILTER_SANITIZE_STRING)
    $usuario = filter_var($usuario, FILTER_SANITIZE_STRING);
    
    // Validar y filtrar el valor de la contraseña (puedes personalizar este filtro)
    $contrasena = filter_var($contrasena, FILTER_SANITIZE_STRING);
    
    // Consulta SQL parametrizada para comprobar si el usuario y la contraseña son válidos
    $sql = "SELECT * FROM Usuarios WHERE Usuario = :usuario AND Contrasena = :contrasena";
    $stmt = $conn->prepare($sql);
    
    // Escapar los valores antes de incluirlos en la consulta
    $stmt->bindParam(':usuario', $usuario);
    $stmt->bindParam(':contrasena', $contrasena);
    $stmt->execute();

    // Crear el documento XML de respuesta
    header('Content-Type: text/xml');
    $xml = new SimpleXMLElement('<respuesta/>');

    // Comprobar si la consulta devuelve algún resultado
    if ($stmt->rowCount() > 0) {
        // Usuario y contraseña válidos
        $user = $stmt->fetch(PDO::FETCH_ASSOC); // Obtiene el resultado
        $xml->addChild('estado', 'ok');
        $xml->addChild('EmpleadoID', $user['EmpleadoID']);
    } else {
        // Usuario o contraseña incorrectos
        $xml->addChild('estado', 'ko');
    }

    // Imprimir el documento XML de respuesta
    echo $xml->asXML();
} catch (PDOException $e) {
    // En caso de error en la conexión o consulta, manejar la excepción
    die("Error en la conexión: " . $e->getMessage());
}
?>
