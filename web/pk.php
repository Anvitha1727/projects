<?php
// Database connection credentials
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

// Initialize variables for form fields
$pid = $brand = $name = $cost = $quantity = "";

// Function to sanitize user input
function sanitize_input($input)
{
    return htmlspecialchars(trim($input));
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Get data from the POST request and sanitize the input
    $pid = sanitize_input($_POST['pid']);
    $brand = sanitize_input($_POST['brand']);
    $name = sanitize_input($_POST['name']);
    $cost = sanitize_input($_POST['cost']);
    $quantity = sanitize_input($_POST['quantity']);

    // Check if the given pid already exists in the table
    $sql_check = "SELECT * FROM pk WHERE pid = ?";
    $stmt_check = $conn->prepare($sql_check);
    $stmt_check->bind_param("s", $pid);
    $stmt_check->execute();
    $result_check = $stmt_check->get_result();

    if ($result_check->num_rows > 0) {
        // If the pid exists, fetch the existing details and populate the form fields
        $row = $result_check->fetch_assoc();
        $existing_brand = $row['brand'];
        $existing_name = $row['name'];
        $existing_cost = $row['cost'];

        // Check if the details are the same
        if ($brand === $existing_brand && $name === $existing_name && $cost === $existing_cost) {
            // Update the quantity for the existing row
            $existing_quantity = $row['quantity'];
            $new_quantity = $existing_quantity + $quantity;

            $sql_update = "UPDATE pk SET quantity = ? WHERE pid = ?";
            $stmt_update = $conn->prepare($sql_update);
            $stmt_update->bind_param("is", $new_quantity, $pid);

            if ($stmt_update->execute()) {
                echo "<script>alert('Product data updated successfully');</script>";
            } else {
                echo "<script>alert('Error updating product data: " . $conn->error . "');</script>";
            }
        } else {
            // Show an alert box about the pid already existing with different details
            echo "<script>alert('Product with PID $pid already exists with different details');</script>";
        }
    } else {
        // If the pid does not exist, insert a new row into the table
        $sql_insert = "INSERT INTO pk (pid, brand, name, cost, quantity) VALUES (?, ?, ?, ?, ?)";
        $stmt_insert = $conn->prepare($sql_insert);
        $stmt_insert->bind_param("sssss", $pid, $brand, $name, $cost, $quantity);

        if ($stmt_insert->execute()) {
            echo "<script>alert('Product data inserted successfully');</script>";
            // Reset form fields after successful insertion
            $pid = $brand = $name = $cost = $quantity = "";
        } else {
            echo "<script>alert('Error inserting product data: " . $conn->error . "');</script>";
        }
    }
}

// Close the database connection
$conn->close();
?>
<!DOCTYPE html>
<html>
<head>
  <title>User Input Form</title>

</head>

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
align-items: center;

      justify-content: center;
  border: 1px solid #ccc;
  background-color: #FFFFFF;
}

h2 {
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-top: 10px;
}


input[type="submit"] {
  background-color: #001f3f;
  color: #fff;
  padding: 10px;
  cursor: pointer;
}


</style>


<body>
  <div class="container">

    <h2>Product Upload Form</h2>

    <form action="pk.php" method="POST" onsubmit="return validateForm()">
      <label for="pid">Product ID:</label>
      <input type="number" id="pid" name="pid" required>
      <label for="brand">Brand:</label>
      <input type="text" id="brand" name="brand" required>
      <label for="name">Name:</label>
      <input type="text" id="name" name="name" required>
      <label for="address">Cost:</label>
      <input type="text" id="cost" name="cost" required>
      <label for="quantity">Quantity:</label>
      <input type="text" id="quantity" name="quantity" required><br>
      <input type="submit" value="Submit">
    </form>
    <script>
      function validateForm() {
        var pid = document.getElementById('pid').value;
        var brand = document.getElementById('brand').value;
        var name = document.getElementById('name').value;
        var cost = document.getElementById('cost').value;
        var quantity = document.getElementById('quantity').value;

        if (pid === '' || brand === '' || name === '' || cost === '' || quantity === '') {
          alert('Please fill in all fields.');
          return false;
        }

        if (isNaN(parseInt(cost)) || isNaN(parseInt(quantity))) {
          alert('Cost and Quantity must be integers.');
          return false;
        }

        if (!isValidNumber(cost) || !isValidNumber(quantity)) {
          alert('Cost and Quantity must be valid numbers.');
          return false;
        }
        if (isNaN(parseFloat(cost)) || isNaN(parseFloat(quantity)) || parseFloat(cost) <= 0 || parseFloat(quantity) <= 0) {
          alert('Cost and Quantity must be positive numbers.');
          return false;
        }

        // Implement your logic to check if the PID already exists in the database.
        if (checkIfPidExists(pid)) {
          alert('Product ID already exists. Please enter another PID.');
          return false;
        }

        return true;
      }

      function isValidNumber(value) {
        return !isNaN(parseFloat(value)) && isFinite(value);
      }

      function checkIfPidExists(pid) {
        // You should implement this function to check if the given PID already exists in your database.
        // Return true if it exists, false otherwise.
        // For demonstration purposes, I'll simulate checking by returning false.
        return false;
      }
    </script>
  </div>
 
</body>
</html>