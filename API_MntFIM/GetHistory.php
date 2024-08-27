<?php
require_once 'Connection.php';

$method = $_SERVER['REQUEST_METHOD'];
$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $ID = isset($_POST['ID']) ? $_POST['ID'] : null;
    $PIC = isset($_POST['PIC']) ? $_POST['PIC'] : null;
    $line = isset($_POST['idline']) ? $_POST['idline'] : null;
    $mesin = isset($_POST['idmesin']) ? $_POST['idmesin'] : null;
    $masalah = isset($_POST['idmasalah']) ? $_POST['idmasalah'] : null;
    $start = isset($_POST['start']) ? $_POST['start'] : null;
    $end = isset($_POST['end']) ? $_POST['end'] : null;
    $penyebab = isset($_POST['idpenyebab']) ? $_POST['idpenyebab'] : null;
    $penanganan = isset($_POST['idpenanganan']) ? $_POST['idpenanganan'] : null;
    $SparePart = isset($_POST['SparePart']) ? $_POST['SparePart'] : null;
    $tanggal = isset($_POST['tanggal']) ? $_POST['tanggal'] : null;
    $tanggal_selesai = isset($_POST['tanggal_selesai']) ? $_POST['tanggal_selesai'] : null;
    $nomor_mesin = isset($_POST['nomor_mesin']) ? $_POST['nomor_mesin'] : null;
    $proses = isset($_POST['idproses']) ? $_POST['idproses'] : null;
    $quantity = isset($_POST['quantity']) ? $_POST['quantity'] : null;

    $query = "INSERT INTO history ('PIC', 'line', 'mesin', 'masalah', 'start', 'end', 'penyebab', 'penanganan', 'SparePart', 'tanggal', 'tanggal_selesai', 'nomor_mesin', 'proses', 'quantity') 
              VALUES ('$PIC', '$line', '$mesin', '$masalah', '$start', '$end', '$penyebab', '$penanganan', '$SparePart', '$tanggal', '$tanggal_selesai', '$nomor_mesin', '$proses', '$quantity')";

    $result = mysqli_query($conn, $query);
    if ($result === false) {
        // Query execution failed
        $error = mysqli_error($conn);
        echo json_encode(array("kode" => 0, "pesan" => "Data Gagal Disimpan", "error" => $error));
    } else {
        // Query executed successfully
        $insertedId = mysqli_insert_id($conn);
        if ($insertedId) {
            // Fetch the inserted row
            $selectQuery = "SELECT * FROM history WHERE ID = $insertedId";
            $selectResult = mysqli_query($conn, $selectQuery);
            if ($selectResult !== false) {
                $history = mysqli_fetch_object($selectResult);
                echo json_encode(array("kode" => 1, "pesan" => "Data Berhasil Disimpan", "history" => $history));
            } else {
                $selectError = mysqli_error($conn);
                echo json_encode(array("kode" => 0, "pesan" => "Data Gagal Disimpan", "error" => $selectError));
            }
        } else {
            echo json_encode(array("kode" => 0, "pesan" => "Data Gagal Disimpan"));
        }
    }
}
