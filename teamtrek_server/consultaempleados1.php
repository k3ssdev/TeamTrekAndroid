<?php

// Incluye el archivo de configuración
include('config.php');

try {
    // Crear una instancia de PDO para la conexión a la base de datos
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);

    // Establecer el modo de errores de PDO a excepciones
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Verificar si se ha proporcionado un ID de empleado en la solicitud
    $userId = isset($_GET['userid']) ? $_GET['userid'] : null;

    
    // Comprobar si el usuario es administrador
    $stmt = $conn->prepare("SELECT GROUP_CONCAT(r.Nombre SEPARATOR ', ') AS Roles FROM Usuarios u LEFT JOIN UsuarioRoles ur ON u.EmpleadoID = ur.UsuarioID LEFT JOIN Roles r ON ur.RolID = r.ID WHERE u.EmpleadoID = :userId");
    $stmt->bindParam(':userId', $userId, PDO::PARAM_INT);
    $stmt->execute();

    $userRoles = $stmt->fetch(PDO::FETCH_ASSOC);
    $isAdmin = strpos($userRoles['Roles'], 'Administrador') !== false;

    if ($isAdmin) {
        // Usuario es administrador, puede obtener todos los empleados
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
    } else {
        // Usuario no es administrador, solo puede ver su propia información
        $userId = filter_var($userId, FILTER_SANITIZE_NUMBER_INT);

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
    }

    $stmt = $conn->prepare($sql);

    // Si no es administrador, enlazar userId a la consulta
    if (!$isAdmin) {
        $stmt->bindParam(':userId', $userId, PDO::PARAM_INT);
    }

    $stmt->execute(); // Ejecutar la consulta

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

} catch (PDOException $e) {
    // En caso de error en la conexión o consulta, manejar la excepción
    die("Error en la conexión: " . $e->getMessage());
}

?>
