<!DOCTYPE html>
<html>
<head>
    <title>Product Shorthand Table</title>
    <style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #e8d2d5;
    }

    .container {
        max-width: 500px;
        margin: 50px auto;
        padding: 20px;
        border: 1px solid #ccc;
        background-color: #FFFFFF;
    }

    h1 {
        text-align: center;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
    }

    th, td {
        border: 1px solid #ccc;
        padding: 8px;
        text-align: center;
    }

    th {
        background-color: #f2f2f2;
    }

    form {
        display: flex;
        flex-direction: column;
    }

    label {
        margin-top: 10px;
    }

</style>
</head>
<body><div class="container">
    <h1>Product Shorthand Table</h1>
    
    <?php
    // Database configuration
    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "project";
    
    // Create connection
    $conn = new mysqli($servername, $username, $password, $dbname);
    
    // Check connection
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }
    
    // Fetch data from the table
    $query = "SELECT pid, name, cost FROM pk";
    $result = $conn->query($query);
    
    if ($result->num_rows > 0) {
        echo "<table>";
        echo "<tr><th>Product ID</th><th>Name</th><th>Cost</th></tr>";
        while ($row = $result->fetch_assoc()) {
            echo "<tr><td>" . $row["pid"] . "</td><td>" . $row["name"] . "</td><td>" . $row["cost"] . "</td></tr>";
        }
        echo "</table>";
    } else {
        echo "No products found.";
    }
    
    $conn->close();
    ?>
    </div>
</body>
</html>