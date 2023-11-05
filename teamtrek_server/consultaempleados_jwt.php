<?php

// Incluye el archivo de configuración y las dependencias de JWT
include('config.php');
require_once('./vendor/autoload.php'); // Asegúrate de que esta ruta coincida con la de tu autoloader de Composer

use Firebase\JWT\JWT;
use Firebase\JWT\Key;

// Clave secreta para verificar el JWT
$jwtKey = 'tu_clave_secreta';

try {
    // Obtener el token JWT de la cabecera de autorización o de otro lugar donde lo hayas colocado
    $authHeader = getallheaders()['Authorization'] ?? '';
    preg_match('/Bearer\s(\S+)/', $authHeader, $matches);
    if(!isset($matches[1])) {
        throw new Exception("Token no proporcionado o inválido");
    }

    // Decodificar el token
    $token = $matches[1];
    $decoded = JWT::decode($token, new Key($jwtKey, 'HS256'));

    // Aquí continúa la lógica después de verificar el token...

    // Crear una instancia de PDO para la conexión a la base de datos
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Verificar si se ha proporcionado un ID de empleado en la solicitud
    $userId = $_GET['userid'] ?? null;

    if ($userId) {
        // Filtrar el ID de usuario para prevenir inyección SQL
        $userId = filter_var($userId, FILTER_SANITIZE_NUMBER_INT);

        // Consulta SQL parametrizada para obtener toda la información del empleado por su ID
        $sql = "SELECT 
		    e.ID, 
		    e.Nombre, 
		    e.FechaIngreso, 
		    d.Nombre AS NombreDepartamento, 
		    h.Descripcion AS DescripcionHorario, 
		    h.HoraInicio, 
		    h.HoraFin, 
		    u.Usuario, 
		    GROUP_CONCAT(r.Nombre SEPARATOR ', ') AS Roles,
		    dp.Telefono,
		    dp.Direccion,
		    dp.Email,
		    dp.FechaNacimiento,
		    dp.NIF
		FROM 
		    Empleados e
		    LEFT JOIN Departamentos d ON e.DepartamentoID = d.ID
		    LEFT JOIN EmpleadoHorario eh ON e.ID = eh.EmpleadoID
		    LEFT JOIN Horarios h ON eh.HorarioID = h.ID
		    LEFT JOIN Usuarios u ON e.ID = u.EmpleadoID
		    LEFT JOIN UsuarioRoles ur ON u.EmpleadoID = ur.UsuarioID
		    LEFT JOIN Roles r ON ur.RolID = r.ID
		    LEFT JOIN DatosPersonales dp ON e.ID = dp.EmpleadoID
		WHERE e.ID = :userId
		GROUP BY 
		    e.ID, 
		    e.Nombre, 
		    e.FechaIngreso, 
		    d.Nombre, 
		    h.Descripcion, 
		    h.HoraInicio, 
		    h.HoraFin, 
		    u.Usuario,
		    dp.Telefono,
		    dp.Direccion,
		    dp.Email,
		    dp.FechaNacimiento,
		    dp.NIF";
        $stmt = $conn->prepare($sql);
        $stmt->bindParam(':userId', $userId, PDO::PARAM_INT);
    } else {
        // Si no se proporciona userId, puedes lanzar un error o manejar la situación como consideres necesario
// Consulta SQL para obtener todos los empleados
        $sql = "SELECT 
		    e.ID, 
		    e.Nombre, 
		    e.FechaIngreso, 
		    d.Nombre AS NombreDepartamento, 
		    h.Descripcion AS DescripcionHorario, 
		    h.HoraInicio, 
		    h.HoraFin, 
		    u.Usuario, 
		    GROUP_CONCAT(r.Nombre SEPARATOR ', ') AS Roles,
		    dp.Telefono,
		    dp.Direccion,
		    dp.Email,
		    dp.FechaNacimiento,
		    dp.NIF
		FROM 
		    Empleados e
		    LEFT JOIN Departamentos d ON e.DepartamentoID = d.ID
		    LEFT JOIN EmpleadoHorario eh ON e.ID = eh.EmpleadoID
		    LEFT JOIN Horarios h ON eh.HorarioID = h.ID
		    LEFT JOIN Usuarios u ON e.ID = u.EmpleadoID
		    LEFT JOIN UsuarioRoles ur ON u.EmpleadoID = ur.UsuarioID
		    LEFT JOIN Roles r ON ur.RolID = r.ID
		    LEFT JOIN DatosPersonales dp ON e.ID = dp.EmpleadoID
		GROUP BY 
		    e.ID, 
		    e.Nombre, 
		    e.FechaIngreso, 
		    d.Nombre, 
		    h.Descripcion, 
		    h.HoraInicio, 
		    h.HoraFin, 
		    u.Usuario,
		    dp.Telefono,
		    dp.Direccion,
		    dp.Email,
		    dp.FechaNacimiento,
		    dp.NIF";
        $stmt = $conn->prepare($sql);
        throw new Exception("No se ha proporcionado el ID del usuario.");
    }

    // Ejecutar la consulta
    $stmt->execute();

    // Crear el documento XML de respuesta
    header('Content-Type: text/xml');
    
    // Start of XML structure
    $xml = new SimpleXMLElement('<root/>');

	// Fetch the data and create XML structure in a loop
	while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
	    // Empleado node
	    $empleado = $xml->addChild('empleado');

	    // Añadir datos de identificación del empleado
	    $identificacion = $empleado->addChild('identificacion');
	    $identificacion->addChild('ID', $row['ID']);
	    $identificacion->addChild('Nombre', $row['Nombre']);
	    $identificacion->addChild('FechaIngreso', $row['FechaIngreso']);
	    $identificacion->addChild('Usuario', $row['Usuario']);

	    // Añadir información del departamento
	    $departamento = $empleado->addChild('departamento');
	    $departamento->addChild('NombreDepartamento', $row['NombreDepartamento']);

	    // Añadir información de horario
	    $horario = $empleado->addChild('horario');
	    $horario->addChild('DescripcionHorario', $row['DescripcionHorario']);
	    $horario->addChild('HoraInicio', $row['HoraInicio']);
	    $horario->addChild('HoraFin', $row['HoraFin']);

	    // Añadir información de roles
	    $roles = $empleado->addChild('roles');
	    $roles->addChild('Roles', $row['Roles']);

	    // Añadir datos personales
	    $datosPersonales = $empleado->addChild('datosPersonales');
	    $datosPersonales->addChild('Telefono', $row['Telefono']);
	    $datosPersonales->addChild('Direccion', $row['Direccion']);
	    $datosPersonales->addChild('Email', $row['Email']);
	    $datosPersonales->addChild('FechaNacimiento', $row['FechaNacimiento']);
	    $datosPersonales->addChild('NIF', $row['NIF']);
	}

    // Output or save the XML
    echo $xml->asXML();

    // Recuerda cerrar la conexión a la base de datos si es necesario
    $conn = null;

} catch (Exception $e) {
    // Si hay un error en el token o en la consulta, manejar aquí
    header('HTTP/1.0 401 Unauthorized');
    echo json_encode(["error" => $e->getMessage()]);
    exit;
}

?>
